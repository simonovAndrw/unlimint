package com.unlimint.parser;

import com.unlimint.entity.Order;

import java.io.IOException;
import java.util.List;

public interface FileParser {

    List<Order> parse() throws IOException;

    String getFilename();
}
