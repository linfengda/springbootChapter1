package com.linfengda.sb.support.lettuce.config;

import lombok.Getter;
import lombok.Setter;

public enum ClientType {
    JEDIS(0, "jedis"),
    LETTUCE(1, "lettuce");

    @Setter @Getter
    private Integer code;
    @Setter @Getter
    private String name;

    ClientType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
