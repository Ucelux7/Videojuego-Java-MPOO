package Main;

import java.awt.*;

// Dibuja todo lo que va "pegado" a la pantalla: barras, mensajes y menús, NO DECIDE
public class UI {

    GamePanel gp;
    Font fontNormal = new Font("Arial", Font.BOLD, 20);
    Font fontGrande = new Font("Arial", Font.BOLD, 60);
    Font fontTitulo = new Font("Arial", Font.BOLD, 30);

    String message = "";
    int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    public void showMessage(String text) {
        message = text;
        messageCounter = 0;
    }

    public void draw(Graphics2D g2) {

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawBars(g2);
        drawMessage(g2);

        if (gp.gameState == gp.statsState) { // solo si se presiona E
            drawStats(g2);
        }
        if (gp.gameState == gp.gameOverState) { //solo si el jugador muere
            drawGameOver(g2);
            // gameState:
            // variable que dice en qué pantalla se encuentra el jugador (jugando, estadísticas o game over).
        }
    }

    // Barra de vida, barra de experiencia y nivel (esquina superior izquierda)
    public void drawBars(Graphics2D g2) {

        int x = 20;
        int y = 20;
        int ancho = 220;

        g2.setFont(fontNormal);

        //  VIDA
        //Muestra al jugador su vida tiene en tiempo real.
        g2.setColor(Color.white);
        g2.drawString("VIDA", x, y + 15);

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(x + 62, y, ancho + 4, 22); // fondo
        // Upcasting (double) para mantener precisión matemática y Downcasting (int) para cumplir con los parámetros de fillRect.
        //Lo vimos en clase!! El código utiliza ambas conversiones (la implícita y la explícita)
        g2.setColor(new Color(220, 30, 40));
        g2.fillRect(x + 64, y + 2, (int) ((double) ancho * gp.player.life / gp.player.maxLife), 18);   // vida actual
        g2.setColor(Color.white);
        g2.drawRect(x + 62, y, ancho + 4, 22);                      // borde
        g2.drawString(gp.player.life + "/" + gp.player.maxLife, x + 64 + ancho / 2 - 15, y + 17);

        //  NIVEL y EXPERIENCIA
        y = y + 34;
        g2.drawString("Nv " + gp.player.level, x, y + 14);

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(x + 62, y + 2, ancho + 4, 14);
        g2.setColor(new Color(240, 190, 40));
        g2.fillRect(x + 64, y + 4, (int) ((double) ancho * gp.player.exp / gp.player.nextLevelExp), 10);
        g2.setColor(Color.white);
        g2.drawRect(x + 62, y + 2, ancho + 4, 14);

        //  Oleada y tacos que quedan (esquina superior derecha)
        g2.drawString("Oleada " + gp.wave, gp.screenWidth - 150, 36);
        g2.drawString("Tacos: " + gp.tacosVivos(), gp.screenWidth - 150, 62);
    }

    // Mensaje que dura 2 segundos ("+5 EXP", "¡NIVEL 2!"...)
    public void drawMessage(Graphics2D g2) {

        if (message.equals("")) {
            return;
        }

        messageCounter++;
        g2.setFont(fontTitulo);
        g2.setColor(new Color(0, 0, 0, 180));
        g2.drawString(message, gp.screenWidth / 2 - 58, 83);
        g2.setColor(new Color(255, 230, 80));
        g2.drawString(message, gp.screenWidth / 2 - 60, 80);

        if (messageCounter > 120) {
            message = "";
            messageCounter = 0;
        }
    }

    // Tabla de estadísticas (tecla C)
    public void drawStats(Graphics2D g2) {

        int x = gp.titleSize * 7;
        int y = gp.titleSize * 1;
        int w = gp.titleSize * 10;
        int h = gp.titleSize * 11;

        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRoundRect(x, y, w, h, 25, 25);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x + 5, y + 5, w - 10, h - 10, 25, 25);

        g2.setFont(fontTitulo);
        g2.drawString("ESTUDIANTE", x + 40, y + 55);

        String[] nombres = {"Nivel", "Vida", "Experiencia", "Fuerza", "Defensa", "Ataque", "Velocidad", "Arma", "Tacos derrotados"};
        String[] valores = {
                "" + gp.player.level,
                gp.player.life + " / " + gp.player.maxLife,
                gp.player.exp + " / " + gp.player.nextLevelExp,
                "" + gp.player.strength,
                "" + gp.player.defense,
                "" + gp.player.attack,
                "" + gp.player.speed,
                gp.player.weapon,
                "" + gp.player.kills
        };

        g2.setFont(fontNormal);
        FontMetrics fm = g2.getFontMetrics();
        int lineY = y + 110;

        for (int i = 0; i < nombres.length; i++) {
            g2.setColor(new Color(200, 200, 210));
            g2.drawString(nombres[i], x + 40, lineY);
            g2.setColor(Color.white);
            g2.drawString(valores[i], x + w - 40 - fm.stringWidth(valores[i]), lineY);   // alineado a la derecha
            g2.setColor(new Color(255, 255, 255, 50));
            g2.drawLine(x + 40, lineY + 10, x + w - 40, lineY + 10);
            lineY += 44;
        }

        g2.setColor(new Color(240, 190, 40));
        g2.drawString("[C] cerrar", x + w / 2 - 45, y + h - 25);
    }

    public void drawGameOver(Graphics2D g2) {

        g2.setColor(new Color(0, 0, 0, 190));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(fontGrande);
        g2.setColor(new Color(200, 30, 40));
        g2.drawString("GAME OVER", gp.screenWidth / 2 - 190, gp.screenHeight / 2 - 10);

        g2.setFont(fontNormal);
        g2.setColor(Color.white);
        g2.drawString("Llegaste al nivel " + gp.player.level + " y derrotaste " + gp.player.kills + " tacos",
                gp.screenWidth / 2 - 190, gp.screenHeight / 2 + 40);
        g2.drawString("Presiona ENTER para intentarlo de nuevo", gp.screenWidth / 2 - 180, gp.screenHeight / 2 + 80);
    }
}
