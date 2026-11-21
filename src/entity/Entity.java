package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public int worldX, worldY;
    public int speed;
    public abstract void update();
    public abstract void draw(Graphics2D g2);

    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public String  direction;

    public int spriteCounter = 0;

    public int spriteNum = 1;

    // Caja de colisión (el "cuerpo" del personaje, más chica que el sprite)
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // Estadísticas (las usan el jugador y los enemigos)
    public int maxLife;
    public int life;
    public int attack;    // fuerza del golpe
    public int defense;   // resta daño al recibir golpes
    public int exp;       // jugador: experiencia actual / enemigo: exp que da al morir

    public boolean alive = true;
    public boolean invincible = false;   // después de un golpe, no recibe daño por un ratito
    public int invincibleCounter = 0;

}
