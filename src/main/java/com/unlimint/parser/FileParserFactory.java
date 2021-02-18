package com.unlimint.parser;

public class FileParserFactory {

    public static FileParser getParser(String extension, String filename) {
        FileParser parser;
        switch (extension) {
            case "csv" :
                parser = new CsvFileParser();
                ((CsvFileParser) parser).setFilename(filename);
                break;
            case "json" :
                parser = new JsonFileParser();
                ((JsonFileParser) parser).setFilename(filename);
                break;
            default :
                throw new IllegalArgumentException("This extension is not supported.");
        }

        return parser;
    }
}
