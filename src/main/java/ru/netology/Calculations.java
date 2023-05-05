package ru.netology;

import com.univocity.parsers.tsv.TsvParserSettings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class Calculations {

    protected static HashMap<String, Integer> categoriesTotalSum = new HashMap<>();
    protected static final HashMap<String, ArrayList<String>> categories = tsvToHashMap();

    protected static HashMap<String, ArrayList<String>> tsvToHashMap() {

        final String dataFilePath = "categories.tsv";
        HashMap<String, ArrayList<String>> categories = new HashMap<>();
        String good;
        String category;

        TsvParserSettings settings = new TsvParserSettings();
        com.univocity.parsers.tsv.TsvParser parser = new com.univocity.parsers.tsv.TsvParser(settings);
        List<String[]> allRows = parser.parseAll(new File(dataFilePath), "UTF-8");

        for (int i = 0; i < allRows.size(); i++) {
            good = Arrays.asList(allRows.get(i)).get(0);
            category = Arrays.asList(allRows.get(i)).get(1);
            if (categories.containsKey(category)) {
                categories.get(category).add(good);
            } else {
                categories.put(category, new ArrayList<>());
                categories.get(category).add(good);
            }
        }
        return categories;
    }

    protected static void addTotalSumInfo(BuyNote buyNote) {
        String categ = "другое";
        for (var elem : categories.entrySet()) {
            if (elem.getValue().contains(buyNote.getTitle())) {
                categ = elem.getKey();
            }
        }

        if (categoriesTotalSum.containsKey(categ)) {
            categoriesTotalSum.put(categ, categoriesTotalSum.get(categ) + buyNote.getSum());
        } else {
            categoriesTotalSum.put(categ, buyNote.getSum());
        }
        categoriesTotalSum.entrySet().stream().forEach(System.out::println);
    }

    protected static JSONObject getJsonObject(Map.Entry<String, Integer> maxEntry) {
        JSONObject maxCategory = new JSONObject();

        maxCategory.put("category", maxEntry.getKey());
        maxCategory.put("sum", maxEntry.getValue());

        JSONArray maxMain = new JSONArray();
        maxMain.put(maxCategory);

        JSONObject mainObject = new JSONObject();
        mainObject.put("maxCategory", maxMain);
        return mainObject;
    }
    protected static JSONObject getMaxCategoryReturnJsonObject() {
        Map.Entry<String, Integer> maxEntry = categoriesTotalSum.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        JSONObject mainObject = getJsonObject(maxEntry);
        return mainObject;
    }
}
