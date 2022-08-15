package com.kaibutsusama.stock.system.user.controller;

import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.user.service.IStockUserService;
import com.kaibutsusama.system.common.web.ApiRespResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:47
 */
@RestController()
@RequestMapping("/user")
@Log4j2
public class StockUserController {

    @Autowired
    private IStockUserService stockUserService;

    /**
     * 用户登陆接口
     * @param userNo
     * @param userPwd
     * @return
     */
    @RequestMapping("/userLogin")
    public ApiRespResult userLogin(@RequestParam("userNo")String userNo, @RequestParam("userPwd") String userPwd) {

        ApiRespResult  result = null;
        try {
            // 用户登陆逻辑处理
            TradeUser tradeUser = stockUserService.userLogin(userNo, userPwd);
            result = ApiRespResult.success(tradeUser);
        }catch(ComponentException e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.error(e.geterrorCodeEnum());
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.sysError(e.getMessage());
        }

        return result;

    }

}
