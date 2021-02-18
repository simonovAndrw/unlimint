package com.unlimint.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OutputOrderSerializer extends StdSerializer<OutputOrder> {

    public OutputOrderSerializer() {
        this(null);
    }

    protected OutputOrderSerializer(Class<OutputOrder> t) {
        super(t);
    }

    @Override
    public void serialize(OutputOrder order, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", order.getOrderId());
        jsonGenerator.writeNumberField("amount", order.getAmount());
        jsonGenerator.writeStringField("comment", order.getComment());
        jsonGenerator.writeStringField("currency", order.getCurrency());
        jsonGenerator.writeStringField("filename", order.getFilename());
        jsonGenerator.writeNumberField("line", order.getLineNum());
        jsonGenerator.writeStringField("result", order.getStatus());

    }
}
