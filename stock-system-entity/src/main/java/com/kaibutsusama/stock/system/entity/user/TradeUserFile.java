package com.kaibutsusama.stock.system.entity.user;

import com.kaibutsusama.stock.system.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class TradeUserFile extends BaseEntity {
    /**
     * 主键标识
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 业务类型（0：身份证， 1：银行卡， 2：信用卡）
     */
    private int bizType;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 状态(0:有效， 1：无效）
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     * @mbg.generated Tue Jan 07 10:22:01 CST 2020
     */
    private static final long serialVersionUID = 1L;

}