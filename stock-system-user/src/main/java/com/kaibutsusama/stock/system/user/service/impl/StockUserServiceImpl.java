package com.kaibutsusama.stock.system.user.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.kaibutsusama.stock.system.common.constants.GlobalConfig;
import com.kaibutsusama.stock.system.common.constants.GlobalSeq;
import com.kaibutsusama.stock.system.common.encrypt.EncryptUtil;
import com.kaibutsusama.stock.system.common.exception.BusinessException;
import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.common.exception.constants.ApplicationErrorCodeEnum;
import com.kaibutsusama.stock.system.common.system.service.ITradeGlobalConfigService;
import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import com.kaibutsusama.stock.system.entity.system.TradeGlobalConfig;
import com.kaibutsusama.stock.system.entity.user.TradeAccount;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.user.dao.ITradeAccountDao;
import com.kaibutsusama.stock.system.user.dao.mapper.IStockUserDao;
import com.kaibutsusama.stock.system.user.service.IStockUserService;
import com.kaibutsusama.stock.system.user.vo.CompanyVo;
import com.kaibutsusama.system.common.web.vo.TradeUserVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:50
 */
@Service
@Log4j2
public class StockUserServiceImpl implements IStockUserService {


    @Autowired
    private IStockUserDao stockUserDao;
    @Autowired
    private ITradeGlobalConfigService tradeGlobalConfigService;
    @Autowired
    private ITradeAccountDao tradeAccountDao;

    /**
     * 用户登录限流处理逻辑
     * @param userNo
     * @param userPwd
     * @param ex
     * @return
     * @throws ComponentException
     */
    public TradeUser userLoginHandler(String userNo, String userPwd, BlockException ex) throws ComponentException {
        log.error("userLogin flow limit, call userLoginHandler: " + ex.getMessage());
        // 1. 获取熔断限流规则
        AbstractRule abstractRule = ex.getRule();

        // 2. 根据不同的规则, 进行不同逻辑处理
        if(abstractRule instanceof DegradeRule){
            throw new ComponentException(ApplicationErrorCodeEnum.SYS_BUSY);
        }else if(abstractRule instanceof FlowRule) {
            throw new ComponentException(ApplicationErrorCodeEnum.SYS_FLOW);
        }
        // 3. 默认, 未捕获对应异常, 不符合配置的规则,进入系统异常
        throw new ComponentException(ApplicationErrorCodeEnum.FAILURE);
    }


    /**
     * 用户登陆
     * @param userNo
     * @param userPwd
     * @return
     */
    @Override
    @SentinelResource(value = "userLogin", blockHandler = "userLoginHandler" )
    public TradeUser userLogin(String userNo, String userPwd) throws ComponentException {

        // 模拟降级异常
        if("error".equals(userNo)) {
            throw new ComponentException(ApplicationErrorCodeEnum.FAILURE);
        }

        // 1. 获取用户对象
        TradeUser tradeUser= stockUserDao.getByUserNo(userNo);
        if(null == tradeUser) {
            throw new ComponentException(ApplicationErrorCodeEnum.USER_NOT_FOUND);
        }

        // 2. 用户密码加密判断
        String encryptPassword = EncryptUtil.encryptSigned(userPwd);
        boolean pwdMatch= tradeUser.getUserPwd().equals(encryptPassword);
        if(!pwdMatch) {
            log.error(ApplicationErrorCodeEnum.USER_PWD_ERROR);
            throw new ComponentException(ApplicationErrorCodeEnum.USER_PWD_ERROR);
        }

        return tradeUser;
    }


    /**
     *  用户注册功能业务接口
     * @param tradeUserVo
     * @return
     * @throws BusinessException
     * @throws ComponentException
     */
    @Override
    public TradeUser userRegister(TradeUserVo tradeUserVo) throws BusinessException, ComponentException {
        // 1.判断用户是否已经注册
        TradeUser newTradeUser = new TradeUser();
        Integer checkResult = stockUserDao.checkRegister(tradeUserVo.getUserNo(), tradeUserVo.getEmail(), tradeUserVo.getPhone());
        if(null != checkResult && checkResult > 0) {
            throw new BusinessException(ApplicationErrorCodeEnum.USER_EXISTS);
        }

        // 2. 对公司信息做校验(传递的公司是合法的)
        CompanyVo companyVo = stockUserDao.getCompanyVoById(tradeUserVo.getCompanyId());
        if(null == companyVo) {
            throw new BusinessException(ApplicationErrorCodeEnum.USER_COMPANY_NOT_FOUND);
        }

        // 3. 构造生成用户信息
        BeanUtils.copyProperties(tradeUserVo, newTradeUser);
        newTradeUser.setUserNo(generateUserNo());
        newTradeUser.setUserPwd(EncryptUtil.encryptSigned(tradeUserVo.getUserPwd()));

        // 4. 完善并冗余必要的用户信息
        newTradeUser.setInstitutionId(companyVo.getInstitutionId());
        newTradeUser.setInstitutionTypeId(companyVo.getInstitutionTypeId());
        newTradeUser.setCompanyName(companyVo.getCompanyName());
        stockUserDao.insert(newTradeUser);


        // 5. 注册即开户, 生成交易账户信息
        TradeGlobalConfig tradeGlobalConfig = tradeGlobalConfigService.getTradeGlobalConfigByCode(GlobalConfig.REG_OPEN_ACCOUNT);
        if(null != tradeGlobalConfig && GlobalConfig.VALUE_TRUE.equals(tradeGlobalConfig.getValue())){
            // 如果存在该项配置, 并且值为Y, 代表注册即开户
            // 生成交易账户信息
            TradeAccount tradeAccount = new TradeAccount();
            tradeAccount.setAccountNo(generateUserAccountNo());
            tradeAccount.setUserId(newTradeUser.getId());
            // 先设置为系统默认组别
            tradeAccount.setTradeGroupId(1L);
            tradeAccount.setStatus(GlobalConfig.STATUS_VALID);
            tradeAccount.setActiveTime(new Date());

            // 冗余信息的设置
            tradeAccount.setInstitutionId(companyVo.getInstitutionId());
            tradeAccount.setInstitutionTypeId(companyVo.getInstitutionTypeId());
            tradeAccount.setCompanyId(companyVo.getId());
            tradeAccount.setUserName(newTradeUser.getName());
            tradeAccount.setTradeGroupName(GlobalConstants.DEFAULT_GROUP_NAME);
            tradeAccount.setBalance(0L);
            // 保存交易账户信息
            tradeAccountDao.insert(tradeAccount);
        }
        // TODO 6. 发送邮件/短信通知
        return newTradeUser;
    }


    /**
     * 生成用户编号
     * @return
     */
    private String generateUserNo() {
        // 获取用户账号
        Long nextUserNo = tradeGlobalConfigService.getNextSeqId(GlobalSeq.USER_NO);
        log.info(" get the next userNo : " + nextUserNo);
        // 其中0表示补零, 后面标识长度
        return String.format("%08d", nextUserNo);
    }

    /**
     * 生成交易账户编号
     * @return
     */
    private String generateUserAccountNo() {
        // 获取用户账号
        Long nextUserNo = tradeGlobalConfigService.getNextSeqId(GlobalSeq.USER_ACC);
        log.info(" get the next userAccountNo : " + nextUserNo);
        // 其中0表示补零, 后面标识长度
        return String.format("%08d", nextUserNo);
    }
}