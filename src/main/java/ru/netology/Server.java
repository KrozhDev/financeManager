package ru.netology;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static ru.netology.Calculations.addTotalSumInfo;

public class Server {




    private static final int PORT = 8080;


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
                        if (!"q".equals(jsonString)){
                            ObjectMapper objectMapper = new ObjectMapper();
                            BuyNote buyNote = objectMapper.readValue(jsonString, BuyNote.class);
                            Calculations.addTotalSumInfo(buyNote);
                        }
                    }
                    JSONObject mainObject = Calculations.getMaxCategoryReturnJsonObject();
                    out.println(mainObject.toString());
                }
            }
        } catch (
                IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }
}

