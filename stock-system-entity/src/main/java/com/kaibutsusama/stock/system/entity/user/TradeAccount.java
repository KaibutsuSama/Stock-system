package com.kaibutsusama.stock.system.entity.user;

import com.kaibutsusama.stock.system.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class TradeAccount extends BaseEntity {

    /**
     * 主键标识
     */
    private Long id;

    /**
     */
    private Long userId;

    /**
     * 交易账号
     */
    private String accountNo;

    /**
     * 余额
     */
    private Long balance;

    /**
     * 账户组ID
     */
    private Long tradeGroupId;

    /**
     * 开户时间
     */
    private Date activeTime;

    /**
     * 状态(0:有效， 1：锁定， 2：禁用）
     */
    private int status;

    /**
     * 机构类型id
     */
    private String institutionTypeId;

    /**
     * 对应机构类型下的机构id
     */
    private Long institutionId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 账户组名称
     */
    private String tradeGroupName;

    /**
     *
     * @mbg.generated Tue Jan 07 10:22:01 CST 2020
     */
    private static final long serialVersionUID = 1L;


}