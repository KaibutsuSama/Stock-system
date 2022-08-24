package com.kaibutsusama.stock.system.user.controller;

import com.kaibutsusama.stock.system.common.exception.BusinessException;
import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.user.service.IStockUserService;
import com.kaibutsusama.system.common.web.ApiRespResult;
import com.kaibutsusama.system.common.web.vo.TradeUserVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:38
 */
@RestController
@RequestMapping("/open")
@Log4j2
public class StockUserOpenController {

    /**
     * 用户的服务层接口
     */
    @Autowired
    private IStockUserService stockUserService;


    /**
     * 用户注册接口
     * @param tradeUser
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiRespResult register(@Valid TradeUserVo tradeUser) {

        ApiRespResult result = null;

        try {
            // 调用用户的注册接口方法
            TradeUser newTradeUser = stockUserService.userRegister(tradeUser);
            result = ApiRespResult.success(newTradeUser);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.error(e.geterrorCodeEnum());
        } catch (ComponentException e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.error(e.getErrorCodeEnum());
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.sysError(e.getMessage());
        }
        return result;

    }
}