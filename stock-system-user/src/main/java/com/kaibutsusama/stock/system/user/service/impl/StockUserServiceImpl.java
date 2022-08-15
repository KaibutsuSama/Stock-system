package com.kaibutsusama.stock.system.user.service.impl;

import com.kaibutsusama.stock.system.common.encrypt.EncryptUtil;
import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.common.exception.constants.ApplicationErrorCodeEnum;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.user.dao.mapper.IStockUserDao;
import com.kaibutsusama.stock.system.user.service.IStockUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 用户登陆
     * @param userNo
     * @param userPwd
     * @return
     */
    @Override
    public TradeUser userLogin(String userNo, String userPwd) throws ComponentException {

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
}