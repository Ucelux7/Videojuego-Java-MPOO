package entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Taco extends Entity {

    GamePanel gp;
    private BufferedImage taco1, taco2;

    private int actionLockCounter = 0;   // cada cuánto cambia de dirección cuando camina solo
    private boolean chasing = false;     // true si está persiguiendo al jugador

    public Taco(GamePanel gp) {

        this.gp = gp;

        direction = "down";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        attack = 2;      // daño que le hace al jugador
        defense = 0;
        exp = 5;         // experiencia que da al morir
        solidArea = new Rectangle(6, 10, 36, 34);

        getImage();
    }

    public void getImage() {
        try {
            taco1 = ImageIO.read(getClass().getResourceAsStream("/monsters/taco_1.png"));
            taco2 = ImageIO.read(getClass().getResourceAsStream("/monsters/taco_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Decide hacia dónde caminar
    private void setAction() {

        int dx = gp.player.worldX - worldX;
        int dy = gp.player.worldY - worldY;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia < gp.titleSize * 5) {
            // Si el jugador está cerca (5 casillas), lo persigue
            chasing = true;
            if (Math.abs(dx) > Math.abs(dy)) {
                direction = (dx < 0) ? "left" : "right";
            } else {
                direction = (dy < 0) ? "up" : "down";
            }
        } else {
            // Si no, camina al azar y cambia de dirección cada 2 segundos
            chasing = false;
            actionLockCounter++;
            if (actionLockCounter >= 120) {
                int i = new Random().nextInt(100) + 1;   // número del 1 al 100
                if (i <= 25) direction = "up";
                else if (i <= 50) direction = "down";
                else if (i <= 75) direction = "left";
                else direction = "right";
                actionLockCounter = 0;
            }
        }
    }

    @Override
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);

        // Si persigue y se topa con una pared, intenta rodear por el otro eje
        if (collisionOn && chasing) {
            int dx = gp.player.worldX - worldX;
            int dy = gp.player.worldY - worldY;
            if (direction.equals("left") || direction.equals("right")) {
                direction = (dy < 0) ? "up" : "down";
            } else {
                direction = (dx < 0) ? "left" : "right";
            }
            collisionOn = false;
            gp.cChecker.checkTile(this);
        }

        if (!collisionOn) {
            switch (direction) {
                case "up":    worldY -= speed; break;
                case "down":  worldY += speed; break;
                case "left":  worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        } else {
            actionLockCounter = 120;   // choqué: en cuanto pueda elijo otra dirección
        }

        // animación: alterna 2 imágenes
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        // tiempo de invencibilidad después de recibir un golpe (20 frames)
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // solo dibuja si está dentro de la pantalla
        if (screenX > -gp.titleSize && screenX < gp.screenWidth
                && screenY > -gp.titleSize && screenY < gp.screenHeight) {

            BufferedImage image = (spriteNum == 1) ? taco1 : taco2;

            // barra de vida chiquita encima (solo si ya está herido)
            if (life < maxLife) {
                double unidad = (double) gp.titleSize / maxLife;
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 11, gp.titleSize + 2, 8);
                g2.setColor(new Color(255, 40, 40));
                g2.fillRect(screenX, screenY - 10, (int) (unidad * life), 6);
            }

            // parpadea cuando recibe un golpe
            if (invincible) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            g2.drawImage(image, screenX, screenY, gp.titleSize, gp.titleSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
