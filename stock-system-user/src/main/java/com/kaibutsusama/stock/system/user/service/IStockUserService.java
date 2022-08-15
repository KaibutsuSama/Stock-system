package com.kaibutsusama.stock.system.user.service;

import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.entity.user.TradeUser;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:50
 */
public interface IStockUserService {


    /**
     * 用户登陆
     * @param userNo
     * @param userPwd
     * @return
     */
    TradeUser userLogin(String userNo, String userPwd) throws ComponentException;
}
