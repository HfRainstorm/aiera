package cn.hfstorm.aiera.common.core.constant;

import lombok.Getter;

/**
 * @author : hmy
 * @date : 2025/2/8 15:29
 */
@Getter
public enum RoleEnum {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system"),
    ;

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }
}
