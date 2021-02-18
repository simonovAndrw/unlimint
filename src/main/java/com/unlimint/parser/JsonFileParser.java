package com.unlimint.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.unlimint.entity.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileParser extends AbstractParser implements FileParser {

    @Override
    public List<Order> parse() throws IOException {

        List<Order> orders = new ArrayList<>();
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.enable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,
                DeserializationFeature.WRAP_EXCEPTIONS);

        ObjectReader oReader = jsonMapper.readerFor(Order.class);

        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            Order current = new Order();
            try {
                current = oReader.readValue(line, Order.class);
            } catch (MismatchedInputException e) {
                current.setStatus(e.getMessage());
            }
            current.setOrderId(currentId.getAndIncrement());
            orders.add(current);
        }

        return orders;
    }
}
