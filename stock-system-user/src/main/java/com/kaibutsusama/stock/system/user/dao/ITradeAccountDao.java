package com.kaibutsusama.stock.system.user.dao;

import com.kaibutsusama.stock.system.entity.user.TradeAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:48
 */
@Repository
public interface ITradeAccountDao {

    /**
     * 新增交易账号信息
     * @param record
     * @return
     */
    int insert(TradeAccount record);


    /**
     * 根据账号编号获取交易用户对象
     * @param accountNo
     * @return
     */
    TradeAccount getByAccountNo(@Param("accountNo") String accountNo);
}
