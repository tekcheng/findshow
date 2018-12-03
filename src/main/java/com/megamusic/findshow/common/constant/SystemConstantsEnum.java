package com.megamusic.findshow.common.constant;

/**
 * Created by maita on 17/8/20.
 */
public enum SystemConstantsEnum {
    SUCCESS(1001, "success", "成功"),
    PARAM_ERROR(1002, "param_error", "参数异常"),
    VERIFY_CODE_WRONG(1003, "check_verify_code_wrong", "验证码错误"),
    FAIL(4004, "fail", "失败"),
    CALL_REMOTE_FAIL(4005, "call_remote_fail", "调用远程服务失败");

    private Integer code;
    private String value;
    private String description;

    private SystemConstantsEnum(Integer code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
