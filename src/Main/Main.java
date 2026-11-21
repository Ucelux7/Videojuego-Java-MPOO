package Main;

import javax.swing.*;

// Ana Guadalupe Bautista Talavera
// Torres Pozos Julio César
// Viveros Reséndiz César
// Semestre: 2027-1
// Grupo: 5

public class Main {

    public static void main (String[] args){


        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(" No sé que nombre darleee ");

        //Agregar el Panel de juegos
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window .pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}
