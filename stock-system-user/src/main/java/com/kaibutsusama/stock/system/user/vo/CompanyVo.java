package com.kaibutsusama.stock.system.user.vo;

import lombok.Data;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:39
 */
@Data
public class CompanyVo {

    /**
     * 公司ID
     */
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 机构类型
     */
    private String institutionTypeId;

    /**
     * 联系人
     */
    private String contactUser;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 管理员账号
     */
    private String adminUser;

    /**
     * 机构ID
     */
    private Long institutionId;

}
