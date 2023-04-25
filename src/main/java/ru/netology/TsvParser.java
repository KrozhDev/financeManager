package ru.netology;

import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TsvParser {



    public static HashMap<String,ArrayList<String>> categories = new HashMap<>();
    public static String good;
    public static String category;
    public static void main(String[] args) {


        tsvToHashMap();
        categories.entrySet().stream().forEach(System.out::println);
    }

    private static HashMap<String,ArrayList<String>> tsvToHashMap() {

        final String dataFilePath = "categories.tsv";

        TsvParserSettings settings = new TsvParserSettings();
        com.univocity.parsers.tsv.TsvParser parser = new com.univocity.parsers.tsv.TsvParser(settings);
        List<String[]> allRows = parser.parseAll(new File(dataFilePath),"UTF-8");

        for (int i = 0; i < allRows.size(); i++){
            good = Arrays.asList(allRows.get(i)).get(0);
            category = Arrays.asList(allRows.get(i)).get(1);
            if (categories.containsKey(category))  {
                categories.get(category).add(good);
            } else {
                categories.put(category, new ArrayList<>() );
                categories.get(category).add(good);
            }

        }
        return categories;
    }
}
