package Main;

import entity.Entity;
import tile.Tile;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Revisa si el siguiente paso de la entidad chocaría con una casilla sólida
    public void checkTile(Entity e) {

        int size = gp.titleSize;

        // los 4 bordes de la caja de colisión, en coordenadas del mundo
        int leftX   = e.worldX + e.solidArea.x;
        int rightX  = leftX + e.solidArea.width - 1;
        int topY    = e.worldY + e.solidArea.y;
        int bottomY = topY + e.solidArea.height - 1;

        // en qué casilla (columna/fila) está cada borde
        int leftCol   = Math.floorDiv(leftX, size);
        int rightCol  = Math.floorDiv(rightX, size);
        int topRow    = Math.floorDiv(topY, size);
        int bottomRow = Math.floorDiv(bottomY, size);

        switch (e.direction) {
            case "up":
                topRow = Math.floorDiv(topY - e.speed, size);   // la casilla a la que iría
                if (isSolid(leftCol, topRow) || isSolid(rightCol, topRow)) e.collisionOn = true;
                break;
            case "down":
                bottomRow = Math.floorDiv(bottomY + e.speed, size);
                if (isSolid(leftCol, bottomRow) || isSolid(rightCol, bottomRow)) e.collisionOn = true;
                break;
            case "left":
                leftCol = Math.floorDiv(leftX - e.speed, size);
                if (isSolid(leftCol, topRow) || isSolid(leftCol, bottomRow)) e.collisionOn = true;
                break;
            case "right":
                rightCol = Math.floorDiv(rightX + e.speed, size);
                if (isSolid(rightCol, topRow) || isSolid(rightCol, bottomRow)) e.collisionOn = true;
                break;
        }
    }

    private boolean isSolid(int col, int row) {

        // fuera del mapa cuenta como pared
        if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) {
            return true;
        }

        if (gp.tileM.blocked[col][row]) {   // objetos (taquilla, vallas...)
            return true;
        }

        Tile t = gp.tileM.tile[gp.tileM.mapTileNum[col][row]];
        return t != null && t.collision;
    }
}
