package com.kaibutsusama.stock.system.common.exception.constants;


/**
 * 代表组件类型异常, 比如内部的工具组件处理错抛出的异常错误码信息
 */
public enum ComponentErrorCodeEnum implements IErrorCodeEnum {


	COMPONENT_LOAD_PROPERTIES_OBJ_HAD_EXIST("0001", "配置文件加载类已经存在" ),
	;

	private String module;

	private String code;

	private String message;

	private WarningLevelEnum warningLevel;


	ComponentErrorCodeEnum(String code, String message, WarningLevelEnum warningLevelEnum) {
		this.code = code;
		this.message = message;
		this.warningLevel = warningLevelEnum;
	}

    ComponentErrorCodeEnum(String module, String code, String message, WarningLevelEnum warningLevelEnum) {
	    this.module = module;
        this.code = code;
        this.message = message;
        this.warningLevel = warningLevelEnum;
    }

    ComponentErrorCodeEnum(String module, String code, String message) {
        this.module = module;
        this.code = code;
        this.message = message;
        this.warningLevel = WarningLevelEnum.COMMON;;
    }


    ComponentErrorCodeEnum(String code, String message) {
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
}
