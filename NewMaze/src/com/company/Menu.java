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
public class Menu {

    //On incremente la class du timer
    Chrono timer = new Chrono();

    public void menu() throws IOException {

        //Commande pour clear la console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        //Affichage pour le menu sur la console
        showTitle("Menu");

        System.out.println("Do you want to play at a maze game ?");
        System.out.println("-------------------------------------------------------");
        System.out.println("            1. Choose your difficulty");
        System.out.println("            2. Quit the game");
        System.out.println("-------------------------------------------------------");

        System.out.println(" ");
        System.out.println("Enter the correct number :");
        System.out.print("-> ");

        //Scanner pour mettre le nombre choisit
        Scanner menu = new Scanner(System.in);
        int choix = menu.nextInt();

        if (choix == 1) { //Si 1 choisit alors choisir la difficulté

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

            //Scanner pour mettre le nombre choisit
            Scanner difficulty = new Scanner(System.in);
            int choix2 = difficulty.nextInt();

            if (choix2 == 1) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                //On incremente la class MazeGenerator afin de créer le labyrinthe au niveau facile
                MazeGenerator maze = new MazeGenerator(12, 6);

                //On affiche le labyrinthe avec la fonction display
                maze.display();

                //Lancement du timer après création du labyrinthe
                timer.start();

            }else if (choix2 == 2) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                //On incremente la class MazeGenerator afin de créer le labyrinthe au niveau moyen
                MazeGenerator maze = new MazeGenerator(25, 13);

                //On affiche le labyrinthe avec la fonction display
                maze.display();

                //Lancement du timer après création du labyrinthe
                timer.start();

            }else if (choix2 == 3) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                //On incremente la class MazeGenerator afin de créer le labyrinthe au niveau difficile
                MazeGenerator maze = new MazeGenerator(50, 25);

                //On affiche le labyrinthe avec la fonction display
                maze.display();

                //Lancement du timer après création du labyrinthe
                timer.start();

            }else if (choix2 == 4) {
                System.out.println(" ");
                System.out.println("  ⬇️");

                //On incremente la class MazeGenerator afin de créer le labyrinthe au niveau très difficile
                MazeGenerator maze = new MazeGenerator(100, 50);

                //On affiche le labyrinthe avec la fonction display
                maze.display();

                //Lancement du timer après création du labyrinthe
                timer.start();

            }else if (choix2 == 5) {

                //Niveau Custom pour faire son propre labyrinthe

                //On choisit une largeur de labyrinthe
                System.out.println(" ");
                showTitle("Select  a  maze  width  =");

                Scanner largeur = new Scanner(System.in);
                int width = largeur.nextInt();

                //On choisit une longueur de labyrinthe
                System.out.println(" ");
                showTitle("Select  a  maze  length  =");

                Scanner longueur = new Scanner(System.in);
                int length = longueur.nextInt();

                System.out.println(" ");
                System.out.println("  ⬇️");

                //On incremente la class MazeGenerator afin de créer le labyrinthe au niveau custom
                MazeGenerator maze = new MazeGenerator(width, length);

                //On affiche le labyrinthe avec la fonction display
                maze.display();

                //Lancement du timer après création du labyrinthe
                timer.start();

            }

        }else if (choix == 2) {
            //On stop le system
            return;

        }else {
            System.out.println("This option doesn't exist");
            //On relance le menu du début
            menu();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    public void finish() throws IOException {

        //Après affichage du labyrinthe on demande si le joueur à trouvé tout seul ou s'il veut la solution
        System.out.println("Write 'FINISH' when you arrive at the Maze's end or write 'SOLUCE' to have the Maze's resolution :");
        System.out.print("->");

        Scanner fin = new Scanner(System.in);
        String end = fin.next();

        if (Objects.equals(end, "FINISH")) {

            //Stop du timer après victoire du joueur
            timer.stop();

            showTitle("You  win  against  the  Maze  !");

            //Affichage du résultat du timer
            System.out.println("You put : " + Chrono.timeToHMS(timer.getDureeSec()) + " to get to the end");

            menu();

        }else if (Objects.equals(end, "SOLUCE")){

            //Stop du timer après abandon du joueur
            timer.stop();

            showTitle("Voici  la  reponse  = ");

            //On incremente la class InputStream
            InputStream f = new FileInputStream("maze.txt");

            String[] lines = readLines (f);
            char[][] mazeSolver = decimateHorizontally (lines);
            solveMaze (mazeSolver);
            String[] solvedLines = expandHorizontally (mazeSolver);
            for (int i = 0  ;  i < solvedLines.length  ;  i++) {
                System.out.println (solvedLines[i]);
            }

            //Affichage du résultat du timer
            System.out.println("You put : " + Chrono.timeToHMS(timer.getDureeSec()) + " before giving up");

            showTitle("Try again");

            //On met un moment de pause afin de laisser afficher le titre
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //On affiche de nouveau le menu pour une nouvelle partie
            menu();
        }

    }

    //Fonction afin d'afficher un texte en grand dans la console prit d'une API
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
