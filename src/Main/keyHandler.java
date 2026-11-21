package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed,leftPressed, rightPressed;
    public boolean spacePressed; // atacar

    public keyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        // E abre y cierra la tabla de estadísticas
        if(code == KeyEvent.VK_E){
            if(gp.gameState == gp.playState){
                gp.gameState = gp.statsState;
            } else if(gp.gameState == gp.statsState){
                gp.gameState = gp.playState;
            }
        }
        // ENTER reinicia cuando el jugar muere
        if(code == KeyEvent.VK_ENTER && gp.gameState == gp.gameOverState){
            gp.retry();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }

    }
