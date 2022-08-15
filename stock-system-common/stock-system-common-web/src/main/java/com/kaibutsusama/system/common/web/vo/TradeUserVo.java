package com.kaibutsusama.system.common.web.vo;

import com.kaibutsusama.system.common.web.BaseVo;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 20:58
 */
@Data
public class TradeUserVo extends BaseVo {


    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    @Size(min = 1, max = 32, message = "姓名长度必须为1到32")
    private String name;

    /**
     * 用户密码
     */
    @Size(min =1, max = 32, message = "密码长度必须为1到32")
    private String userPwd;

    /**
     * 电话号码
     */
    @Pattern(regexp="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message="手机号格式错误")
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



}
