package com.unlimint.parser;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public abstract class AbstractParser {

    protected String filename;
    protected volatile static AtomicLong currentId = new AtomicLong(1);
}
