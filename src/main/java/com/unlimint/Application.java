package com.unlimint;

import com.unlimint.converter.Converter;
import com.unlimint.entity.Order;
import com.unlimint.entity.OutputOrder;
import com.unlimint.parser.CallableWrapper;
import com.unlimint.parser.FileParser;
import com.unlimint.parser.FileParserFactory;
import com.unlimint.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private Converter converter;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Pair<FileParser, List<Order>>>> futureOrders = new ArrayList<>();

        for (String filename : args) {
            String extension = filename.split("\\.")[1];
            FileParser parser = FileParserFactory.getParser(extension, filename);
            Callable<Pair<FileParser, List<Order>>> callable = new CallableWrapper(parser);

            Future<Pair<FileParser, List<Order>>> t = executor.submit(callable);
            futureOrders.add(t);
        }

        List<Future<Pair<FileParser, List<Order>>>> sync = Collections.synchronizedList(futureOrders);
        for (Future<Pair<FileParser, List<Order>>> currentPair : sync) {
            executor.submit(() -> {
                List<OutputOrder> out = null;
                try {
                    out = converter.convert(currentPair.get().getFirst().getFilename(), currentPair.get().getSecond());
                    converter.toOutputFormat(out);
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
