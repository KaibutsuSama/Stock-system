package com.kaibutsusama.stock.system.common.system.service;

import com.kaibutsusama.stock.system.entity.system.TradeGlobalConfig;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:49
 */
public interface ITradeGlobalConfigService {

    /**
     * 根据编号获取全局系统配置信息
     * @param code
     * @return
     */
    TradeGlobalConfig getTradeGlobalConfigByCode(String code);

    /**
     * 获取指定序列增长ID
     * @param code
     * @return
     */
    Long getNextSeqId(String code);
}
