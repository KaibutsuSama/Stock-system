package com.kaibutsusama.hateoas.demo.order.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 8:42
 */
@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(updatable = false) //创建完成后不可修改
    @CreationTimestamp
    private Date createTime;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    private Date updateTime;
}
