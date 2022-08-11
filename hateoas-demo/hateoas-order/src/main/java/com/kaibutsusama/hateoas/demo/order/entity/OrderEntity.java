package com.kaibutsusama.hateoas.demo.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 9:48
 */

@Entity
@Data
@Table(name="T_ORDER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity extends BaseEntity {

    /**
     * 用户名
     */
    private String user;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 成交数量
     */
    private Integer volume;

    /**
     * 成交价格
     */
    private Double price;
}
