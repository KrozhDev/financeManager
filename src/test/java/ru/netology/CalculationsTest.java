package ru.netology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class CalculationsTest {
    private static HashMap<String, ArrayList<String>> categories = new HashMap<>();
    private static List<BuyNote> buyNotes = new ArrayList<>();
    private static String[] jsonStrings;

    @BeforeAll
    public static void initSuite() {
        categories.put("еда", new ArrayList(Arrays.asList("сухарики", "булка")));
        categories.put("быт", new ArrayList(Arrays.asList("мыло", "тряпка")));
        categories.put("финансы", new ArrayList(Arrays.asList("акции", "фьючерсы")));

        jsonStrings = new String[] {
                "{\"title\": \"сухарики\", \"date\": \"2022.02.08\", \"sum\": 300}",
                "{\"title\": \"мыло\", \"date\": \"2022.02.08\", \"sum\": 200}",
                "{\"title\": \"акции\", \"date\": \"2022.02.08\", \"sum\": 500}",
                "{\"title\": \"булка\", \"date\": \"2022.02.08\", \"sum\": 300}",
                "{\"title\": \"сухарики\", \"date\": \"2022.02.08\", \"sum\": 300}"
        };

        for (String jsonString: jsonStrings) {
            ObjectMapper objectMapper = new ObjectMapper();
            BuyNote buyNote = null;
            try {
                buyNote = objectMapper.readValue(jsonString, BuyNote.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Calculations.addTotalSumInfo(buyNote);
        }
    }
    @Test
    void addTotalSumInfo() {

        HashMap<String, Integer> categoriesTotalSumExpected = new HashMap<>();
        categoriesTotalSumExpected.put("еда",900);
        categoriesTotalSumExpected.put("финансы",500);
        categoriesTotalSumExpected.put("быт",200);

        int expect = categoriesTotalSumExpected.hashCode();
        int result = Calculations.categoriesTotalSum.hashCode();

        Assertions.assertEquals(expect, result);
    }

    @Test
    void getMaxCategoryReturnJsonObject() {

        String expect = "{\"maxCategory\":[{\"sum\":900,\"category\":\"еда\"}]}";

        JSONObject mainObject = Calculations.getMaxCategoryReturnJsonObject();
        String result = mainObject.toString();

        Assertions.assertEquals(expect,result);

    }

}