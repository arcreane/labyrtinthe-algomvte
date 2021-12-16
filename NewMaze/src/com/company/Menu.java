package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Scanner;

import static com.company.Solver.*;
import static com.company.Solver.expandHorizontally;

public class Menu {

    public static void menu() throws IOException {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        showTitle("Menu");

        System.out.println("Do you want to play at a maze game ?");
        System.out.println("-------------------------------------------------------");
        System.out.println("            1. Choose your difficulty");
        System.out.println("            2. Quit the game");
        System.out.println("-------------------------------------------------------");

        System.out.println(" ");
        System.out.println("Enter the correct number :");
        System.out.print("-> ");

        Scanner menu = new Scanner(System.in);
        int choix = menu.nextInt();

        if (choix == 1) {

            System.out.print("\033[H\033[2J");
            System.out.flush();

            showTitle("Choose  your  difficulty");

            System.out.println("-------------------------------------------------------");
            System.out.println("            1. Easy");
            System.out.println("            2. Medium");
            System.out.println("            3. Hard");
            System.out.println("            4. Hardcore");
            System.out.println("            5. Custom");
            System.out.println("-------------------------------------------------------");

            System.out.println(" ");
            System.out.println("Enter the correct number :");

            System.out.print("-> ");

            Scanner difficulty = new Scanner(System.in);
            int choix2 = difficulty.nextInt();

            if (choix2 == 1) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                MazeGenerator maze = new MazeGenerator(12, 6);
                maze.display();

                //finish();
            }else if (choix2 == 2) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                MazeGenerator maze = new MazeGenerator(25, 13);
                maze.display();

                //finish();
            }else if (choix2 == 3) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                MazeGenerator maze = new MazeGenerator(50, 25);
                maze.display();

                //finish();
            }else if (choix2 == 4) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                MazeGenerator maze = new MazeGenerator(100, 50);
                maze.display();

                //finish();
            }else if (choix2 == 5) {

                System.out.println(" ");
                showTitle("Select  a  maze  width  =");

                Scanner largueur = new Scanner(System.in);
                int width = largueur.nextInt();

                System.out.println(" ");
                showTitle("Select  a  maze  length  =");

                Scanner longueur = new Scanner(System.in);
                int length = longueur.nextInt();

                System.out.println(" ");
                System.out.println("  ⬇️");

                MazeGenerator maze = new MazeGenerator(width, length);
                maze.display();

                //finish();
            }

        }else if (choix == 2) {
            return;
        }else {
            System.out.println("This option doesn't exist");
            menu();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    public static void finish() throws IOException {

        System.out.println("Write 'FINISH' when you arrive at the Maze's end or write 'SOLUCE' to have the Maze's resolution :");
        System.out.print("->");

        Scanner fin = new Scanner(System.in);
        String end = fin.next();

        if (Objects.equals(end, "FINISH")) {

            showTitle("You  win  against  the  Maze  !");

        }else if (Objects.equals(end, "SOLUCE")){

            showTitle("Voici  la  reponse  = ");

            InputStream f = new FileInputStream("maze.txt");

            String[] lines = readLines (f);
            char[][] mazeSolver = decimateHorizontally (lines);
            solveMaze (mazeSolver);
            String[] solvedLines = expandHorizontally (mazeSolver);
            for (int i = 0  ;  i < solvedLines.length  ;  i++) {
                System.out.println (solvedLines[i]);
            }

            showTitle("Try again");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            menu();
        }

    }

    public static void showTitle(String text) {
        text = text.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://artii.herokuapp.com/make?text=" + text))
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
