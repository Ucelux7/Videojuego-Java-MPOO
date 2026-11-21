package entity;

import Main.GamePanel;
import Main.keyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Entity{

    GamePanel gp;
    keyHandler keyH;

    public final int screenX;
    public final int screenY;

    // Estadísticas del jugador
    public int level;
    public int nextLevelExp;   // experiencia necesaria para subir de nivel
    public int strength;       // fuerza
    public int kills;          // tacos derrotados
    public String weapon = "Llave inglesa";

    // Ataque
    boolean attacking = false;
    int attackCounter = 0;

    public  Player(GamePanel gp, keyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        // caja de colisión: solo el "cuerpo" (dentro del sprite de 48x48)
        solidArea = new Rectangle(8, 16, 32, 32);

        screenX = gp.screenWidth/2 - (gp.titleSize/2);
        screenY = gp.screenHeight/2 - (gp.titleSize)/2;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue(){

        worldX = gp.titleSize * 12; // casilla 12 (dentro de la estacion)
        worldY = gp.titleSize * 8;  // fila 8
        speed = 4;
        direction = "down";

        // Estadísticas iniciales
        level = 1;
        maxLife = 8;
        life = maxLife;
        strength = 1;
        defense = 0;
        attack = strength + 1;   // fuerza + daño del arma (llave inglesa = 1)
        exp = 0;
        nextLevelExp = 8;
        kills = 0;
        alive = true;
        invincible = false;
        attacking = false;
    }

    //dibujar al personaje
    public void getPlayerImage(){
        try {

            //arriba
            up1= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            up3= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_3.png"));
            up4= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_4.png"));
            //abajo
            down1= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            down3= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_3.png"));
            down4= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_4.png"));
            //izquierda
            left1= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            left3= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_3.png"));
            left4= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_4.png"));
            //derecha
            right1= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            right3= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_3.png"));
            right4= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_4.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        if (attacking) {
            swing();   // mientras ataca no se mueve
        } else {

            boolean moving = false;

            if (keyH.upPressed == true) {
                direction = "up";
                moving = true;
            } else if (keyH.downPressed == true) {
                direction = "down";
                moving = true;
            } else if (keyH.leftPressed == true) {
                direction = "left";
                moving = true;
            } else if (keyH.rightPressed == true) {
                direction = "right";
                moving = true;
            }

            if (moving) {

                // Primero revisa si el siguiente paso choca con algo
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // Si no choca, se mueve
                if (collisionOn == false) {
                    switch (direction) {
                        case "up":    worldY -= speed; break;
                        case "down":  worldY += speed; break;
                        case "left":  worldX -= speed; break;
                        case "right": worldX += speed; break;
                    }
                }

                // El sprite solo cambia de cuadro si el jugador se mueve
                spriteCounter++;
                if (spriteCounter > 15) { // Controla la velocidad de la animación
                    spriteNum++; //Avanza al siguiente cuadro (1-2-3-4)
                    if (spriteNum > 4) {
                        spriteNum = 1; //Si se pasa de 4, reinicia al 1er cuadro
                    }
                    spriteCounter = 0; //Reinicia el contador
                }
            }

            // ESPACIO = atacar
            if (keyH.spacePressed == true) {
                attacking = true;
                attackCounter = 0;
            }
        }
        keyH.spacePressed = false;

        contactMonster();

        // después de recibir un golpe, 1 segundo sin recibir daño
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    // Zona donde pega el ataque: un cuadro enfrente del jugador
    public Rectangle getAttackArea() {
        switch (direction) {
            case "up":    return new Rectangle(worldX + 4,  worldY - 20, 40, 36);
            case "down":  return new Rectangle(worldX + 4,  worldY + 40, 40, 36);
            case "left":  return new Rectangle(worldX - 28, worldY + 12, 36, 40);
            default:      return new Rectangle(worldX + 40, worldY + 12, 36, 40);   // right
        }
    }

    // El ataque dura 18 frames; el golpe "cae" en el frame 5
    public void swing() {

        attackCounter++;

        if (attackCounter == 5) {

            Rectangle area = getAttackArea();

            for (int i = 0; i < gp.monster.length; i++) {
                Taco m = gp.monster[i];

                if (m != null && m.alive && !m.invincible) {

                    Rectangle cuerpo = new Rectangle(m.worldX + m.solidArea.x, m.worldY + m.solidArea.y,
                            m.solidArea.width, m.solidArea.height);

                    if (area.intersects(cuerpo)) {

                        int damage = Math.max(1, attack - m.defense);   // mínimo 1 de daño
                        m.life -= damage;
                        m.invincible = true;
                        m.invincibleCounter = 0;

                        if (m.life <= 0) {
                            m.alive = false;
                            kills++;
                            exp += m.exp;
                            gp.ui.showMessage("+" + m.exp + " EXP");
                            checkLevelUp();
                        }
                    }
                }
            }
        }

        if (attackCounter > 18) {
            attacking = false;
            attackCounter = 0;
        }
    }

    // Si un taco toca al jugador pierde vida
    public void contactMonster() {

        if (invincible) {
            return;
        }

        Rectangle yo = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);

        for (int i = 0; i < gp.monster.length; i++) {
            Taco m = gp.monster[i];

            if (m != null && m.alive) {

                Rectangle cuerpo = new Rectangle(m.worldX + m.solidArea.x, m.worldY + m.solidArea.y,
                        m.solidArea.width, m.solidArea.height);

                if (yo.intersects(cuerpo)) {
                    int damage = Math.max(1, m.attack - defense);
                    life -= damage;
                    invincible = true;
                    invincibleCounter = 0;

                    if (life <= 0) {
                        life = 0;
                        gp.gameState = gp.gameOverState;
                    }
                    break;   // un golpe por vez
                }
            }
        }
    }

    // Sube de nivel (puede subir varios de golpe) y mejora estadísticas
    public void checkLevelUp() {

        while (exp >= nextLevelExp) {

            exp -= nextLevelExp;
            level++;
            nextLevelExp = level * 8;   // nivel 1->2: 8, 2->3: 16, 3->4: 24...

            maxLife += 2;
            life = maxLife;             // se cura al subir de nivel
            strength += 1;
            attack = strength + 1;
            if (level % 2 == 0) {
                defense += 1;           // cada 2 niveles gana 1 de defensa
            }

            gp.ui.showMessage("¡NIVEL " + level + "!");
        }
    }

    @Override
    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                if(spriteNum == 3){
                    image = up3;
                }
                if(spriteNum == 4){
                    image = up4;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                if(spriteNum == 3){
                    image = down3;
                }
                if(spriteNum == 4){
                    image = down4;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                if(spriteNum == 3){
                    image = left3;
                }
                if(spriteNum == 4) {
                    image = left4;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                if(spriteNum == 3){
                    image = right3;
                }
                if(spriteNum == 4) {
                    image = right4;
                }
                break;
        }

        // parpadea cuando el personaje recibe daño
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, screenX, screenY, gp.titleSize, gp.titleSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Efecto del golpe (un destello blanco enfrente)
        if (attacking && attackCounter >= 3 && attackCounter <= 14) {
            Rectangle a = getAttackArea();
            int ax = a.x - worldX + screenX;
            int ay = a.y - worldY + screenY;
            g2.setColor(new Color(255, 255, 255, 170));
            g2.fillRoundRect(ax, ay, a.width, a.height, 20, 20);
        }
    }
}
