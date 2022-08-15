package com.kaibutsusama.stock.system.entity.user;

import com.kaibutsusama.stock.system.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:07
 */
@Data
@Entity
@Table(name = "t_trade_user")
public class TradeUser extends BaseEntity {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 最近一次用户登陆IP
     */
    private String lastLoginIp;

    /**
     * 最近一次登陆时间
     */
    private Date lastLoginTime;

    /**
     * 状态(0:有效， 1：锁定， 2：禁用）
     */
    private int status;

    /**
     * 创建时间
     */
    private Date craeteTime;


    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 机构类型
     */
    private String institutionTypeId;

    /**
     * 机构ID
     */
    private Long institutionId;

}
