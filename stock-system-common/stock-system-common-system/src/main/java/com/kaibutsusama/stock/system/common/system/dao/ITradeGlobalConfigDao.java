package com.kaibutsusama.stock.system.common.system.dao;

import com.kaibutsusama.stock.system.entity.system.TradeGlobalConfig;
import com.kaibutsusama.stock.system.entity.system.TradeSeq;
import org.springframework.stereotype.Repository;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:42
 */
@Repository
public interface ITradeGlobalConfigDao {

    /**
     * 新增全局系统配置
     * @param record
     * @return
     */
    int insert(TradeGlobalConfig record);

    /**
     * 根据编号查询对象
     * @param code
     * @return
     */
    TradeGlobalConfig getByCode(String code);

    /**
     * 根据编号获取序列ID
     * @param code
     * @return
     */
    int getNextId(TradeSeq record);
}
