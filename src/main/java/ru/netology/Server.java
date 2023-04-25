package ru.netology;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static final int PORT = 8080;
    private static HashMap<String,Integer> categoriesTotalSum = new HashMap<>();
    private static final HashMap<String,ArrayList<String>> categories = tsvToHashMap();

    public static void main(String[] args) {

        System.out.println("Сервер запущен");

        try (ServerSocket serverSocket = new ServerSocket(PORT);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения

                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {

                    out.println("Сервер запущен");
                    String jsonString = "";




                    while (!"q".equals(jsonString)) {
                        jsonString = in.readLine();
                        ObjectMapper objectMapper = new ObjectMapper();
                        BuyNote buyNote = objectMapper.readValue(jsonString, BuyNote.class);
                        addTotalSumInfo(buyNote);
                    }
                    out.println(categoriesTotalSum.entrySet().size());
                    Map.Entry<String, Integer> maxEntry = categoriesTotalSum.entrySet().stream()
                            .max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
                    out.println(maxEntry.toString());


                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }

    private static void addTotalSumInfo(BuyNote buyNote) {
        String categ = "другое";
        for (var elem: categories.entrySet()) {
            if (elem.getValue().contains(buyNote.getTitle())) {
                categ = elem.getKey();
            }
        }

        if (categoriesTotalSum.containsKey(categ)) {
            categoriesTotalSum.put(categ,categoriesTotalSum.get(categ)+ buyNote.getSum());
        } else {
            categoriesTotalSum.put(categ,buyNote.getSum());
        }
    }

    private static HashMap<String, ArrayList<String>> tsvToHashMap() {

        final String dataFilePath = "categories.tsv";
        HashMap<String,ArrayList<String>> categories = new HashMap<>();
        String good;
        String category;

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
