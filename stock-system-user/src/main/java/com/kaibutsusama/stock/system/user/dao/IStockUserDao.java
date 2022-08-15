package com.kaibutsusama.stock.system.user.dao.mapper;

import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.user.vo.CompanyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:48
 */
@Repository
public interface IStockUserDao {

    /**
     * 根据用户编号获取用户对象信息
     * @param userNo
     * @return
     */
    TradeUser getByUserNo(String userNo);

    /**
     * 校验用户是否已经注册（包括手机号， 邮箱， 用户编号）
     * @param accountNo
     * @return
     */
    Integer checkRegister(@Param("userNo") String userNo, @Param("email") String email, @Param("phone")String phone);

    /**
     * 根据公司ID获取公司对象信息
     * @param id
     * @return
     */
    CompanyVo getCompanyVoById(@Param("id")Long id);


    /**
     * 新增用户信息
     * @param tradeUser
     * @return
     */
    int insert(TradeUser tradeUser);

}
