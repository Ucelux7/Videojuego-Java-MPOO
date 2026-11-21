package Main;

import entity.Player;
import entity.Taco;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Opciones de pantalla
    final int originalTtitleSize = 16;

    //Es para agrandar la foto de los personajes, quedando 48 * 48!
    final int scale = 3;

    public final int titleSize = originalTtitleSize * scale;
    public final int maxscreenCol = 24; //base
    public final int maxscreenRow = 14; //altura
    public final int screenWidth = titleSize * maxscreenCol; //tamaño del mosaico * base
    public final int screenHeight = titleSize * maxscreenRow; //tamaño del mosaico * altura


    //Ajustes del mundo
    public final int maxWorldCol = 24; // columnas de tu estacion.txt
    public final int maxWorldRow = 14; // filas de tu estacion.txt
    public final int worldX = titleSize * maxscreenCol; //tamaño del mosaico * base
    public final int worldY = titleSize * maxscreenRow; //tamaño del mosaico * altura

    //FPS
    int FPS = 60;

    //Estados del juego
    public int gameState;
    public final int playState = 1;
    public final int statsState = 2;     // tabla de estadísticas (tecla C)
    public final int gameOverState = 3;

    public TileManager tileM = new TileManager(this);
    keyHandler keyH = new keyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    public Player player = new Player(this, keyH);

    //Enemigos
    public Taco[] monster = new Taco[20];
    public int wave = 1;
    int waveTimer = 0;

    public GamePanel() {

        this.setPreferredSize((new Dimension(screenWidth, screenHeight)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        setupGame();
    }

    // Deja todo listo para empezar (también sirve para reiniciar)
    public void setupGame() {
        wave = 1;
        waveTimer = 0;
        gameState = playState;
        player.setDefaultValue();
        spawnWave();
    }

    public void retry() {
        setupGame();
    }

    // Crea los tacos de la oleada. Cada oleada son 5 tacos con 1 punto más de vida
    public void spawnWave() {

        int[][] posiciones = {{3, 4}, {10, 4}, {16, 4}, {8, 9}, {15, 9}};   // {columna, fila}

        for (int i = 0; i < monster.length; i++) {
            monster[i] = null;
        }

        for (int i = 0; i < posiciones.length; i++) {
            monster[i] = new Taco(this);
            monster[i].worldX = posiciones[i][0] * titleSize;
            monster[i].worldY = posiciones[i][1] * titleSize;
            monster[i].maxLife = 4 + (wave - 1);
            monster[i].life = monster[i].maxLife;
        }
    }

    public int tacosVivos() {
        int total = 0;
        for (int i = 0; i < monster.length; i++) {
            if (monster[i] != null && monster[i].alive) {
                total++;
            }
        }
        return total;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Bucle de juego Método del sueño
        double drawInterval = 1000000000 / FPS; //0.0166666 segundos
        double nextDrawTime = System.nanoTime() + drawInterval; //nano time Devuelve el tiempo actual del sistema


        while (gameThread != null) {

            //Esto sirve para:
            //1- Actualizar la información, tal como la posición del personaje
            update();
            //2- Dibujar la pantalla con la información actualizada
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();

                //remainingTime solo lee milisegundos, así que convertimos de nano a mili
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                // Para pausar el bucle de juego, usamos el sleep
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    public void update() {

        if (gameState == playState) {

            player.update();

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null && monster[i].alive) {
                    monster[i].update();
                }
            }

            // Si ya no quedan tacos, a los 3 segundos llega la siguiente oleada
            if (tacosVivos() == 0) {
                waveTimer++;
                if (waveTimer > 180) {
                    wave++;
                    waveTimer = 0;
                    spawnWave();
                    ui.showMessage("¡OLEADA " + wave + "!");
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Primero se dibujan los mosaicos
        tileM.draw(g2);
        //Luego los enemigos
        for (int i = 0; i < monster.length; i++) {
            if (monster[i] != null && monster[i].alive) {
                monster[i].draw(g2);
            }
        }
        //Luego se dibuja el jugador
        player.draw(g2);
        //Y al final la interfaz (barras, mensajes y menús)
        ui.draw(g2);

        g2.dispose();
    }
}