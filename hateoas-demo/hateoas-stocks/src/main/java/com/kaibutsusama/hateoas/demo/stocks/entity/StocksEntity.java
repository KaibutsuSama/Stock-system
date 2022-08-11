package com.kaibutsusama.hateoas.demo.stocks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 8:46
 */
@Entity
@Data
@Table(name = "T_STOCKS")
@NoArgsConstructor
@AllArgsConstructor
public class StocksEntity extends BaseEntity {

    /**
     * 股票名称
     */
    private String name;

    /**
     * 股票价格
     */
    private Double price;
}
