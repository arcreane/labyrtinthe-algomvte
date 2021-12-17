package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.company.Menu.showTitle;

public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;


    public MazeGenerator(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        maze   = new int[this.x][this.y];

        generateMaze(0, 0);
    }

    //fonction qui va venir afficher la grille du labyrinthe
    public void display() throws IOException {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(" ");
        System.out.println("  ⬇️");

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

        //On initialise Le tableau avec les directions
        DIR[] dirs = DIR.values();

        //On mélange les directions et avoir toujours une valeur différente
        Collections.shuffle(Arrays.asList(dirs));

        //On va faire pour chaque direction
        for (DIR dir : dirs) {

            //On calcul les coordonnées de x et y
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;

            //On regarde si les nouvelles coordonnées sont bien dans le labyrinthe et que les cellules ne sont pas visitées
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {

                //On définit la ligne actuelle et la colonne actuelle avec les directions
                maze[cx][cy] |= dir.bit;

                //On définit la nouvelle ligne et la nouvelle colonne avec les directions
                maze[nx][ny] |= dir.opposite.bit;
                display();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

        //On initialise les directions opposées for chaque directions
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        //Constructeur pour les directions
        DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx  = dx;
            this.dy  = dy;
        }
    }

    public static void main(String[] args) throws IOException {

        Menu menu = new Menu();
        boolean partie = true;
        int i = 0;

        while (partie){
            i++;

            //Commande pour clear la console
            System.out.print("\033[H\033[2J");
            System.out.flush();

            showTitle("Partie " + i);
            menu.menu();
            menu.finish();

        }

    }

}

/*

    Lancer le programme sur le terminal :

    cd /Users/landemainetheo/Documents/GitHub/labyrtinthe-algomvte/NewMaze/
    java -jar untitled104.jar

 */



