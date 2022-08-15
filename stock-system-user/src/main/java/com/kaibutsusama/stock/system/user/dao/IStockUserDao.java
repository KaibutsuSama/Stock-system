package com.kaibutsusama.stock.system.user.dao.mapper;

import com.kaibutsusama.stock.system.entity.user.TradeUser;
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
}
