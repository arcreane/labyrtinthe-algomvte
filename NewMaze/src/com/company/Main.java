package com.company;

import java.io.*;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Collections;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static com.company.Solver.*;

class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;


    public MazeGenerator(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];

        generateMaze(0, 0);
    }

    public void display() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        BufferedWriter writer = new BufferedWriter(new FileWriter("maze.txt", false));

        for (int i = 0; i < y; i++) {

            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "----" : "|   ");
                writer.write((maze[j][i] & 1) == 0 ? "----" : "|   ");
            }
            System.out.println("|");
            writer.write("|\n");

            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
                writer.write((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
            writer.write("|\n");
        }

        // draw the bottom line
        for (int j = 0; j < x ; j++) {
            System.out.print("----");
            writer.write("----");
        }
//        System.out.println("-");
//        writer.write("-\n");
        System.out.println("- \uD83D\uDEAA ");
        writer.write("- \uD83D\uDEAA ");

        writer.flush();
    }

    private void generateMaze(int cx, int cy) throws IOException {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {

                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
//                display();
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

    public static void main(String[] args) throws IOException {

        Menu();

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
            for (int i = 0  ;  i < solvedLines.length  ;  i++)
                System.out.println (solvedLines[i]);

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

    public static void Menu() throws IOException {

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
            Menu();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }


}



