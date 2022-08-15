package com.kaibutsusama.stock.system.common.exception.constants;

public enum WarningLevelEnum {

    /**
     *  错误级别: 一般,  场景比如常规性的校验, 必填项等, 不能满足充分的业务条件
     */
    COMMON(0, "一般"),

    /**
     * 错误级别: 警告, 场景比如用户不存在, 必要的条件缺失等。
     */
    WARN(1, "警告"),

    /**
     * 错误级别: 严重， 场景比如发生了比较严重的业务异常， 违反了数据规范； 或着不可控的异常， 数据库连接异常等等。
     */
    ERROR(2, "严重"),


    ;


    /**
     * 错误级别的编号
     */
    private int code;

    /**
     * 错误级别的名称
     */
    private String name;

    WarningLevelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
