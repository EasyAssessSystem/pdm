package com.stardust.easyassess.pdm.exceptions;

import com.stardust.easyassess.core.exception.MessageException;

public class DuplicatedKeyException extends MessageException {
    public DuplicatedKeyException() {
        super("索引字段不能重复", 10001);
    }

    public DuplicatedKeyException(String fields) {
        super("字段值:'" + fields + "'存在重复纪录", 10001);
    }
}
