package com.unlimint.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.unlimint.entity.Order;
import com.unlimint.entity.OutputOrder;
import com.unlimint.entity.OutputOrderSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Converter {

    public List<OutputOrder> convert(String filename, List<Order> orders) {

        List<OutputOrder> outputOrders = new ArrayList<>();

        for (int i = 0, ordersSize = orders.size(); i < ordersSize; i++) {
            Order order = orders.get(i);
            OutputOrder t = new OutputOrder();

            t.setOrderId(order.getOrderId());
            t.setLineNum(i + 1);
            t.setFilename(filename);
            t.setAmount(order.getAmount());
            t.setComment(order.getComment());
            t.setCurrency(order.getCurrency());
            t.setStatus(order.getStatus());

            outputOrders.add(t);
        }

        return outputOrders;
    }

    public void toOutputFormat(List<OutputOrder> orders) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(OutputOrder.class, new OutputOrderSerializer());

        mapper.registerModule(module);

        for (OutputOrder order : orders) {
            String out = mapper.writeValueAsString(order);
            System.out.println(out);
        }
    }
}
