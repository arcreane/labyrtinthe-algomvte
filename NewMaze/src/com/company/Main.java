package com.company;

import java.io.*;
import java.util.Collections;
import java.util.Arrays;
import static com.company.Menu.finish;

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

    //fonction qui va venir afficher la grille du labyrinthe
    public void display() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        //On vient copier le labyrinthe généré dans un fichier txt
        BufferedWriter writer = new BufferedWriter(new FileWriter("maze.txt", false));

        //Pour chaque condition, on génère le labyrinthe dans la console et dans le fichier txt
        for (int i = 0; i < y; i++) {

            //On fait les lignes du dessus de chaque cellule
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "----" : "|   ");
                writer.write((maze[j][i] & 1) == 0 ? "----" : "|   ");
            }
            System.out.println("|");
            writer.write("|\n");

            //On fait les lignes sur les côtés des cellules
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
                writer.write((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
            writer.write("|\n");
        }

        //On fait la dernière ligne du labyrinthe
        for (int j = 0; j < x ; j++) {
            System.out.print("----");
            writer.write("----");
        }
        System.out.println("- \uD83D\uDEAA ");
        writer.write("- \uD83D\uDEAA ");

        writer.flush();
    }

    //Fonction qui va générer les couloirs du labyrinthe à partir de la grille créée
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

    //Fonction où on vient préciser qu'une valeur sera plus petite que l'autre mais toujours au dessus de 0
    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        //On utilise l'initialiseur statique pour résoudre les références directes
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

        Menu menu = new Menu();
        menu.menu();
        finish();

    }








}



