package com.unlimint.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.unlimint.entity.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileParser extends AbstractParser implements FileParser {

    @Override
    public List<Order> parse() throws IOException {

        List<Order> orders = new ArrayList<>();
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,
                DeserializationFeature.WRAP_EXCEPTIONS
        );

        CsvSchema schema = CsvSchema.builder()
                .addColumn("orderId", CsvSchema.ColumnType.NUMBER)
                .addColumn("amount", CsvSchema.ColumnType.NUMBER)
                .addColumn("currency", CsvSchema.ColumnType.STRING)
                .addColumn("comment", CsvSchema.ColumnType.STRING)
                .setColumnSeparator(',')
                .build()
                .withoutHeader();

        ObjectReader oReader = csvMapper.readerFor(Order.class).with(schema);

        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            Order current = new Order();
            try {
                current = oReader.readValue(line, Order.class);
            } catch (Exception e) {
                current.setStatus(e.getMessage());
            }
            current.setOrderId(currentId.getAndIncrement());
            orders.add(current);
        }

        return orders;
    }
}
