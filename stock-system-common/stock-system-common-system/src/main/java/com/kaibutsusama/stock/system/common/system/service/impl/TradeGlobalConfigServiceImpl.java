package com.kaibutsusama.stock.system.common.system.service.impl;

import com.kaibutsusama.stock.system.common.system.dao.ITradeGlobalConfigDao;
import com.kaibutsusama.stock.system.common.system.service.ITradeGlobalConfigService;
import com.kaibutsusama.stock.system.entity.system.TradeGlobalConfig;
import com.kaibutsusama.stock.system.entity.system.TradeSeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:50
 */
@Service
public class TradeGlobalConfigServiceImpl implements ITradeGlobalConfigService {

    /**
     * 全局系统配置信息数据层接口
     */
    @Autowired
    private ITradeGlobalConfigDao tradeGlobalConfigDao;

    /**
     * 根据编号获取全局系统配置信息
     * @param code
     * @return
     */
    @Override
    public TradeGlobalConfig getTradeGlobalConfigByCode(String code) {

        return tradeGlobalConfigDao.getByCode(code);
    }


    /**
     * 获取指定序列增长ID
     * @param code
     * @return
     */
    @Override
    public Long getNextSeqId(String code) {
        TradeSeq seq = new TradeSeq();
        seq.setCode(code);
        tradeGlobalConfigDao.getNextId(seq);
        return seq.getNextId();

    }



}
