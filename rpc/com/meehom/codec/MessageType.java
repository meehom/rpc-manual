package com.meehom.codec;

import lombok.AllArgsConstructor;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 12:32
 */
@AllArgsConstructor
public enum MessageType {
    REQUEST(0),RESPONSE(1);
    private int code;
    public int getCode() {
        return code;
    }
}
