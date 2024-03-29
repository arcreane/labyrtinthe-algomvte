package com.company;

import java.io.*;
import java.util.*;

public class Solver {

    //Fonction qui va servir de lire le labyrinthe du fichier txt pour le résoudre plus tard
    public static String[] readLines(InputStream f) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(f, "US-ASCII"));
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        while ((line = r.readLine()) != null)
            lines.add(line);
        return lines.toArray(new String[0]);
    }

    //Fonction qui va diviser par 2 le labyrinthe afin de supprimer les espaces en trop pour ne pas confondre de chemin
    public static char[][] decimateHorizontally(String[] lines) {

        final int width = (lines[0].length() + 1) / 2;
        char[][] c = new char[lines.length][width];

        for (int i = 0; i < lines.length; i++)
            for (int j = 0; j < width; j++)
                c[i][j] = lines[i].charAt(j * 2);
        return c;
    }


    //Fonction qui va trouver la solution en fonction de la case
    public static boolean solveMazeRecursively(char[][] maze, int x, int y, int d) {

        boolean ok = false;
        for (int i = 0; i < 4 && !ok; i++)
            if (i != d)
                switch (i) {
                    // 0 = up, 1 = right, 2 = down, 3 = left
                    case 0:
                        //regarde si voisin vide pour rentrer dedans et bouger dans sa direction
                        if (maze[y - 1][x] == ' ')
                            ok = solveMazeRecursively(maze, x, y - 2, 2);
                        break;
                    case 1:
                        if (maze[y][x + 1] == ' ')
                            ok = solveMazeRecursively(maze, x + 2, y, 3);
                        break;
                    case 2:
                        if (maze[y + 1][x] == ' ')
                            ok = solveMazeRecursively(maze, x, y + 2, 0);
                        break;
                    case 3:
                        if (maze[y][x - 1] == ' ')
                            ok = solveMazeRecursively(maze, x - 2, y, 1);
                        break;
                }

        //Savoir si la condition est finie car bout des coordonnées
        if (x == 1 && y == 1)
            ok = true;

        //Quand on a une solution, on l'a marque par un signe
        if (ok) {
            maze[y][x] = '*';
            switch (d) {
                case 0:
                    maze[y - 1][x] = '*';
                    break;
                case 1:
                    maze[y][x + 1] = '*';
                    break;
                case 2:
                    maze[y + 1][x] = '*';
                    break;
                case 3:
                    maze[y][x - 1] = '*';
                    break;
            }
        }
        return ok;
    }

    //On apelle la fonction avec les coordonnées de la fin du labyrinthe
    public static void solveMaze(char[][] maze) {
        solveMazeRecursively(maze, maze[0].length - 2, maze.length - 2, -1);
    }

    //Fonction qui va remettre le labyrinthe en entier après l'avoir divisé par 2 pour remettre les espaces pour l'affichage
    public static String[] expandHorizontally(char[][] maze) {

        char[] tmp = new char[3];
        String[] lines = new String[maze.length];

        for (int i = 0; i < maze.length; i++) {
            StringBuilder sb = new StringBuilder(maze[i].length * 2);
            for (int j = 0; j < maze[i].length; j++)
                if (j % 2 == 0)
                    sb.append(maze[i][j]);
                else {
                    tmp[0] = tmp[1] = tmp[2] = maze[i][j];
                    if (tmp[1] == '*')
                        tmp[0] = tmp[2] = ' ';
                    sb.append(tmp);
                }
            lines[i] = sb.toString();
        }
        return lines;
    }
}
