package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    public static void main(String[] args){
        try (Socket clientSocket = new Socket(HOST,PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            String response = in.readLine();
            System.out.println(response);

//            Scanner sc = new Scanner(System.in);
//            String city = sc.nextLine();
            out.println("{\"title\": \"булка\", \"date\": \"2022.02.08\", \"sum\": 200}");
            out.println("{\"title\": \"сухарики\", \"date\": \"2022.02.08\", \"sum\": 50}");
            out.println("{\"title\": \"мыло\", \"date\": \"2022.02.08\", \"sum\": 100}");
            out.println("{\"title\": \"акции\", \"date\": \"2022.02.08\", \"sum\": 10000}");
            out.println("q");


            response = in.readLine();
            System.out.println(response);
            System.out.println(in.readLine());
//            while (!"q".equals(response)) {
//                System.out.println(response);
//                response = in.readLine();
//            }





//
//            response = in.readLine();
//            System.out.println(response);




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}