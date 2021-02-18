package com.unlimint.parser;

import com.unlimint.entity.Order;
import com.unlimint.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

@Component
public class CallableWrapper implements Callable<Pair<FileParser, List<Order>>> {

    private FileParser parser;

    public CallableWrapper(FileParser parser) {
        this.parser = parser;
    }

    @Override
    public Pair<FileParser, List<Order>> call() throws Exception {
        return new Pair<>(parser, parser.parse());
    }
}
