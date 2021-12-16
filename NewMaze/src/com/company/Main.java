package com.company;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Collections;
import java.util.Arrays;
import java.util.Scanner;

class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;


    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];

        generateMaze(0, 0);
    }

    public void display() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < y; i++) {

            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "----" : "|   ");
            }
            System.out.println("|");

            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }

        // draw the bottom line
        for (int j = 0; j < x; j++) {
            System.out.print("----");
        }
        System.out.println("|");
    }

    private void generateMaze(int cx, int cy) {
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

    public static void main(String[] args) {

//        TestMaze maze = new TestMaze();
//        maze.createMaze();

        Menu();

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

    public static void Menu() {

        showTitle("Menu");

        System.out.println("Do you want to play at a maze game ?");
        System.out.println("-------------------------------------------------------");
        System.out.println("            1. Choose your difficulty");
        System.out.println("            2. Quit the game");
        System.out.println("-------------------------------------------------------");

        System.out.println(" ");
        System.out.println("Enter the correct number :");

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

            Scanner difficulty = new Scanner(System.in);
            int choix2 = difficulty.nextInt();

            if (choix2 == 1) {
                MazeGenerator maze = new MazeGenerator(12, 6);
                maze.display();
            }else if (choix2 == 2) {
                MazeGenerator maze = new MazeGenerator(25, 13);
                maze.display();
            }else if (choix2 == 3) {
                MazeGenerator maze = new MazeGenerator(50, 25);
                maze.display();
            }else if (choix2 == 4) {
                MazeGenerator maze = new MazeGenerator(100, 50);
                maze.display();
            }else if (choix2 == 5) {

                System.out.println(" ");
                showTitle("Select  a  maze  width  =");

                Scanner largueur = new Scanner(System.in);
                int width = largueur.nextInt();

                System.out.println(" ");
                showTitle("Select  a  maze  length  =");

                Scanner longueur = new Scanner(System.in);
                int length = longueur.nextInt();

                MazeGenerator maze = new MazeGenerator(width, length);
                maze.display();
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



