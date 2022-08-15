package com.kaibutsusama.stock.system.common.exception.constants;


public enum ApplicationErrorCodeEnum implements IErrorCodeEnum {


    SUCCESS("200", "成功"),
    FAILURE("300", "系统异常"),
    COMPONENT_LOAD_PROPERTIES_OBJ_HAD_EXIST("000001", "配置文件加载类已经存在" ),
    SYS_ERROR_ENCRYPT_SINGED(IErrorCodeEnum.MODULE_SYSTEM, "000002", "签名加密错误"),

    USER_NOT_FOUND(IErrorCodeEnum.MODULE_USER, "000003", "用户不存在！"),
    USER_PWD_ERROR(IErrorCodeEnum.MODULE_USER, "000004", "用户密码错误！"),
    ;

    /**
     * 业务模块
     */
    private String module;

    /**
     * 错误编号
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 错误级别
     */
    private WarningLevelEnum warningLevel;


    ApplicationErrorCodeEnum(String code, String message, WarningLevelEnum warningLevelEnum) {
        this.code = code;
        this.message = message;
        this.warningLevel = warningLevelEnum;
    }

    ApplicationErrorCodeEnum(String module, String code, String message, WarningLevelEnum warningLevelEnum) {
        this.module = module;
        this.code = code;
        this.message = message;
        this.warningLevel = warningLevelEnum;
    }

    ApplicationErrorCodeEnum(String module, String code, String message) {
        this.module = module;
        this.code = code;
        this.message = message;
        this.warningLevel = WarningLevelEnum.COMMON;;
    }


    ApplicationErrorCodeEnum(String code, String message) {
        this.module = IErrorCodeEnum.MODULE_SYSTEM;
        this.code = code;
        this.message = message;
        this.warningLevel = WarningLevelEnum.COMMON;
    }


    @Override
    public String getCode() {
        return IErrorCodeEnum.MODULE_SYSTEM + this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public WarningLevelEnum getLevel() {
        return warningLevel;
    }


    @Override
    public String toString() {
        return IErrorCodeEnum.MODULE_SYSTEM + this.code + ", " + this.message;
    }
}
