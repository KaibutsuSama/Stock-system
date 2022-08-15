package com.kaibutsusama.stock.system.entity.system;

import lombok.Data;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:56
 */
@Data
public class TradeSeq {

    /**
     * 业务编号
     */
    private String code;

    /**
     * 序列值
     */
    private Long nextId;
}
