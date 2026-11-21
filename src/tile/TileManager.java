package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public boolean[][] blocked;   // casillas ocupadas por objetos (taquilla, vallas...)

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[200]; //Para los tipos de mosaicos (si se ocupan más, cambiar el nombre)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        blocked = new boolean[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        setCollisions();
        loadMap();
        setObjectCollisions();

    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_a.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_b.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_crack.png"));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_soot.png"));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_burnt.png"));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tactile_edge.png"));
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tactile_dots.png"));
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stripe_h.png"));
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stripe_v.png"));
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_cap.png"));
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_face.png"));
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_band.png"));
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_base.png"));
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_side_l.png"));
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_side_r.png"));
            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car_wall_window.png"));
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car_door_lt.png"));
            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car_door_rt.png"));
            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car_door_lb.png"));
            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car_door_rb.png"));
            tile[20] = new Tile();
            tile[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_yellow.png"));
            tile[21] = new Tile();
            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_blue.png"));
            tile[22] = new Tile();
            tile[22].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_gray.png"));
            tile[23] = new Tile();
            tile[23].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pole_v.png"));
            tile[24] = new Tile();
            tile[24].image = ImageIO.read(getClass().getResourceAsStream("/tiles/handrail_h.png"));
            tile[25] = new Tile();
            tile[25].image = ImageIO.read(getClass().getResourceAsStream("/tiles/barrier.png"));
            tile[26] = new Tile();
            tile[26].image = ImageIO.read(getClass().getResourceAsStream("/tiles/turnstile.png"));
            tile[27] = new Tile();
            tile[27].image = ImageIO.read(getClass().getResourceAsStream("/tiles/trash_can.png"));
            tile[28] = new Tile();
            tile[28].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rubble.png"));
            tile[29] = new Tile();
            tile[29].image = ImageIO.read(getClass().getResourceAsStream("/tiles/papers.png"));
            tile[30] = new Tile();
            tile[30].image = ImageIO.read(getClass().getResourceAsStream("/tiles/puddle.png"));
            tile[31] = new Tile();
            tile[31].image = ImageIO.read(getClass().getResourceAsStream("/tiles/track_top.png"));
            tile[32] = new Tile();
            tile[32].image = ImageIO.read(getClass().getResourceAsStream("/tiles/track_ballast.png"));
            tile[33] = new Tile();
            tile[33].image = ImageIO.read(getClass().getResourceAsStream("/tiles/platform_edge.png"));
            tile[34] = new Tile();
            tile[34].image = ImageIO.read(getClass().getResourceAsStream("/tiles/smoke.png"));
            tile[35] = new Tile();
            tile[35].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fire_1.png"));
            tile[36] = new Tile();
            tile[36].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fire_2.png"));
            tile[37] = new Tile();
            tile[37].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_floor.png"));
            tile[38] = new Tile();
            tile[38].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_cap.png"));
            tile[39] = new Tile();
            tile[39].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_wall_face.png"));
            tile[40] = new Tile();
            tile[40].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_wall_base.png"));
            tile[41] = new Tile();
            tile[41].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_clock.png"));
            tile[42] = new Tile();
            tile[42].image = ImageIO.read(getClass().getResourceAsStream("/tiles/monitor_ok.png"));
            tile[43] = new Tile();
            tile[43].image = ImageIO.read(getClass().getResourceAsStream("/tiles/monitor_flat.png"));
            tile[44] = new Tile();
            tile[44].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_cap.png"));
            tile[45] = new Tile();
            tile[45].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_wall.png"));
            tile[46] = new Tile();
            tile[46].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_wall_pipes.png"));
            tile[47] = new Tile();
            tile[47].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_floor.png"));
            tile[48] = new Tile();
            tile[48].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_floor_grate.png"));
            tile[49] = new Tile();
            tile[49].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_floor_edge_up.png"));
            tile[50] = new Tile();
            tile[50].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_floor_edge_down.png"));
            tile[51] = new Tile();
            tile[51].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_door.png"));
            tile[52] = new Tile();
            tile[52].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_vent.png"));
            tile[53] = new Tile();
            tile[53].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_top.png"));
            tile[54] = new Tile();
            tile[54].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lamp_on.png"));
            tile[55] = new Tile();
            tile[55].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lamp_dim.png"));
            tile[56] = new Tile();
            tile[56].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lamp_off.png"));
            tile[57] = new Tile();
            tile[57].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lamp_emerg_on.png"));
            tile[58] = new Tile();
            tile[58].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lamp_emerg_off.png"));
            tile[59] = new Tile();
            tile[59].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_stain.png"));
            tile[60] = new Tile();
            tile[60].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_crack.png"));
            tile[61] = new Tile();
            tile[61].image = ImageIO.read(getClass().getResourceAsStream("/tiles/handprint.png"));
            tile[62] = new Tile();
            tile[62].image = ImageIO.read(getClass().getResourceAsStream("/tiles/poster_torn.png"));
            tile[63] = new Tile();
            tile[63].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_scratches.png"));
            tile[64] = new Tile();
            tile[64].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_stain.png"));
            tile[65] = new Tile();
            tile[65].image = ImageIO.read(getClass().getResourceAsStream("/tiles/shoe.png"));
            tile[66] = new Tile();
            tile[66].image = ImageIO.read(getClass().getResourceAsStream("/tiles/phone_floor.png"));
            tile[67] = new Tile();
            tile[67].image = ImageIO.read(getClass().getResourceAsStream("/tiles/backpack_floor.png"));
            tile[68] = new Tile();
            tile[68].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cable_hang.png"));
            tile[69] = new Tile();
            tile[69].image = ImageIO.read(getClass().getResourceAsStream("/tiles/drip_1.png"));
            tile[70] = new Tile();
            tile[70].image = ImageIO.read(getClass().getResourceAsStream("/tiles/drip_2.png"));
            tile[71] = new Tile();
            tile[71].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car_window_broken.png"));
            tile[72] = new Tile();
            tile[72].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_band_scorched.png"));
            tile[73] = new Tile();
            tile[73].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_burnt.png"));
            tile[74] = new Tile();
            tile[74].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_yellow_f.png"));
            tile[75] = new Tile();
            tile[75].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_blue_f.png"));
            tile[76] = new Tile();
            tile[76].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_gray_f.png"));
            tile[77] = new Tile();
            tile[77].image = ImageIO.read(getClass().getResourceAsStream("/tiles/seat_burnt_f.png"));
            tile[78] = new Tile();
            tile[78].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_floor_dirty.png"));
            tile[79] = new Tile();
            tile[79].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_floor_blood.png"));
            tile[80] = new Tile();
            tile[80].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_door_top.png"));
            tile[81] = new Tile();
            tile[81].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_door_bot.png"));
            tile[82] = new Tile();
            tile[82].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_exit_sign.png"));
            tile[83] = new Tile();
            tile[83].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_light_on.png"));
            tile[84] = new Tile();
            tile[84].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_light_off.png"));
            tile[85] = new Tile();
            tile[85].image = ImageIO.read(getClass().getResourceAsStream("/tiles/iv_stand.png"));
            tile[86] = new Tile();
            tile[86].image = ImageIO.read(getClass().getResourceAsStream("/tiles/chair_wait.png"));
            tile[87] = new Tile();
            tile[87].image = ImageIO.read(getClass().getResourceAsStream("/tiles/evidence_bag.png"));
            tile[88] = new Tile();
            tile[88].image = ImageIO.read(getClass().getResourceAsStream("/tiles/hosp_window_night.png"));
            tile[89] = new Tile();
            tile[89].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sheet_pile.png"));
            tile[90] = new Tile();
            tile[90].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_floor_a.png"));
            tile[91] = new Tile();
            tile[91].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_floor_crack.png"));
            tile[92] = new Tile();
            tile[92].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_floor_glow.png"));
            tile[93] = new Tile();
            tile[93].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_wall.png"));
            tile[94] = new Tile();
            tile[94].image = ImageIO.read(getClass().getResourceAsStream("/tiles/altar_step.png"));
            tile[95] = new Tile();
            tile[95].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pedestal_empty.png"));
            tile[96] = new Tile();
            tile[96].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pedestal_full.png"));
            tile[97] = new Tile();
            tile[97].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brazier_on.png"));
            tile[98] = new Tile();
            tile[98].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brazier_off.png"));
            tile[99] = new Tile();
            tile[99].image = ImageIO.read(getClass().getResourceAsStream("/tiles/folder_pile.png"));
            tile[100] = new Tile();
            tile[100].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cup_pile.png"));
            tile[101] = new Tile();
            tile[101].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gear_rust.png"));
            tile[102] = new Tile();
            tile[102].image = ImageIO.read(getClass().getResourceAsStream("/tiles/file_cabinet.png"));
            tile[103] = new Tile();
            tile[103].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rail_end.png"));
            tile[104] = new Tile();
            tile[104].image = ImageIO.read(getClass().getResourceAsStream("/tiles/elec_panel_off.png"));
            tile[105] = new Tile();
            tile[105].image = ImageIO.read(getClass().getResourceAsStream("/tiles/elec_panel_on.png"));
            tile[106] = new Tile();
            tile[106].image = ImageIO.read(getClass().getResourceAsStream("/tiles/elec_panel_spark.png"));
            tile[107] = new Tile();
            tile[107].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tun_floor_cables.png"));
            tile[108] = new Tile();
            tile[108].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick_wall.png"));
            tile[109] = new Tile();
            tile[109].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick_top.png"));
            tile[110] = new Tile();
            tile[110].image = ImageIO.read(getClass().getResourceAsStream("/tiles/vec_floor.png"));
            tile[111] = new Tile();
            tile[111].image = ImageIO.read(getClass().getResourceAsStream("/tiles/vec_door_top.png"));
            tile[112] = new Tile();
            tile[112].image = ImageIO.read(getClass().getResourceAsStream("/tiles/vec_door_bot.png"));
            tile[113] = new Tile();
            tile[113].image = ImageIO.read(getClass().getResourceAsStream("/tiles/vec_window.png"));
            tile[114] = new Tile();
            tile[114].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ropa_tendida.png"));
            tile[115] = new Tile();
            tile[115].image = ImageIO.read(getClass().getResourceAsStream("/tiles/veladora_on.png"));
            tile[116] = new Tile();
            tile[116].image = ImageIO.read(getClass().getResourceAsStream("/tiles/veladora_off.png"));
            tile[117] = new Tile();
            tile[117].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cama_vieja.png"));
            tile[118] = new Tile();
            tile[118].image = ImageIO.read(getClass().getResourceAsStream("/tiles/frasco_medicina.png"));
            tile[119] = new Tile();
            tile[119].image = ImageIO.read(getClass().getResourceAsStream("/tiles/planta_muerta.png"));
            tile[120] = new Tile();
            tile[120].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_frost.png"));
            tile[121] = new Tile();
            tile[121].image = ImageIO.read(getClass().getResourceAsStream("/tiles/coin_pile.png"));
            tile[122] = new Tile();
            tile[122].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wind_1.png"));
            tile[123] = new Tile();
            tile[123].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wind_2.png"));
            tile[124] = new Tile();
            tile[124].image = ImageIO.read(getClass().getResourceAsStream("/tiles/paper_fly.png"));
            tile[125] = new Tile();
            tile[125].image = ImageIO.read(getClass().getResourceAsStream("/tiles/office_carpet_a.png"));
            tile[126] = new Tile();
            tile[126].image = ImageIO.read(getClass().getResourceAsStream("/tiles/office_carpet_b.png"));
            tile[127] = new Tile();
            tile[127].image = ImageIO.read(getClass().getResourceAsStream("/tiles/office_wall.png"));
            tile[128] = new Tile();
            tile[128].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cubicle_h.png"));
            tile[129] = new Tile();
            tile[129].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cubicle_v.png"));
            tile[130] = new Tile();
            tile[130].image = ImageIO.read(getClass().getResourceAsStream("/tiles/desk.png"));
            tile[131] = new Tile();
            tile[131].image = ImageIO.read(getClass().getResourceAsStream("/tiles/desk_monitor_on.png"));
            tile[132] = new Tile();
            tile[132].image = ImageIO.read(getClass().getResourceAsStream("/tiles/desk_monitor_off.png"));
            tile[133] = new Tile();
            tile[133].image = ImageIO.read(getClass().getResourceAsStream("/tiles/office_chair.png"));
            tile[134] = new Tile();
            tile[134].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_cooler.png"));
            tile[135] = new Tile();
            tile[135].image = ImageIO.read(getClass().getResourceAsStream("/tiles/office_plant.png"));
            tile[136] = new Tile();
            tile[136].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gears_floor.png"));
            tile[137] = new Tile();
            tile[137].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cable_pile.png"));
            tile[138] = new Tile();
            tile[138].image = ImageIO.read(getClass().getResourceAsStream("/tiles/reja_closed.png"));
            tile[139] = new Tile();
            tile[139].image = ImageIO.read(getClass().getResourceAsStream("/tiles/reja_open.png"));
            tile[140] = new Tile();
            tile[140].image = ImageIO.read(getClass().getResourceAsStream("/tiles/keypad.png"));
            tile[141] = new Tile();
            tile[141].image = ImageIO.read(getClass().getResourceAsStream("/tiles/instrumental_floor.png"));
            tile[142] = new Tile();
            tile[142].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wheelchair.png"));
            tile[143] = new Tile();
            tile[143].image = ImageIO.read(getClass().getResourceAsStream("/tiles/azulejo_wall.png"));
            tile[144] = new Tile();
            tile[144].image = ImageIO.read(getClass().getResourceAsStream("/tiles/face_wall_a.png"));
            tile[145] = new Tile();
            tile[145].image = ImageIO.read(getClass().getResourceAsStream("/tiles/face_wall_b.png"));
            tile[146] = new Tile();
            tile[146].image = ImageIO.read(getClass().getResourceAsStream("/tiles/eye_wall.png"));
            tile[147] = new Tile();
            tile[147].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cert_paper.png"));
            tile[148] = new Tile();
            tile[148].image = ImageIO.read(getClass().getResourceAsStream("/tiles/poster_smile.png"));
            tile[149] = new Tile();
            tile[149].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_band_olive.png"));
            tile[150] = new Tile();
            tile[150].image = ImageIO.read(getClass().getResourceAsStream("/tiles/door_wrong.png"));
            tile[151] = new Tile();
            tile[151].image = ImageIO.read(getClass().getResourceAsStream("/tiles/altar_piedra.png"));
            tile[152] = new Tile();
            tile[152].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bench_blue.png"));
            tile[153] = new Tile();
            tile[153].image = ImageIO.read(getClass().getResourceAsStream("/tiles/curtain.png"));
            tile[154] = new Tile();
            tile[154].image = ImageIO.read(getClass().getResourceAsStream("/tiles/entidad_zapata.png"));
            tile[155] = new Tile();
            tile[155].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gurney_covered.png"));
            tile[156] = new Tile();
            tile[156].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gurney_empty.png"));
            tile[157] = new Tile();
            tile[157].image = ImageIO.read(getClass().getResourceAsStream("/tiles/map_sign.png"));
            tile[158] = new Tile();
            tile[158].image = ImageIO.read(getClass().getResourceAsStream("/tiles/monarca_a.png"));
            tile[159] = new Tile();
            tile[159].image = ImageIO.read(getClass().getResourceAsStream("/tiles/monarca_b.png"));
            tile[160] = new Tile();
            tile[160].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pillar.png"));
            tile[162] = new Tile();
            tile[162].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sign_linea7.png"));
            tile[163] = new Tile();
            tile[163].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stairs_down.png"));
            tile[164] = new Tile();
            tile[164].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ticket_booth.png"));
            tile[165] = new Tile();
            tile[165].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ticket_machine_blue.png"));
            tile[166] = new Tile();
            tile[166].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ticket_machine_yellow.png"));
            tile[167] = new Tile();
            tile[167].image = ImageIO.read(getClass().getResourceAsStream("/tiles/vending.png"));
            tile[168] = new Tile();
            tile[168].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bolardo_acero.png"));
            tile[169] = new Tile();
            tile[169].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bolardo_acero_oxidado.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Casillas del piso/pared que NO se pueden atravesar
    public void setCollisions() {

        int[] solidos = {9, 10, 11, 12, 13, 14};   // muros de la estación

    //Marcar los tipos de azulejos que actúan como muros (no poder atravesarlos)
        for (int i = 0; i < solidos.length; i++) {
            tile[solidos[i]].collision = true;
        }
    }

    // Marca como ocupadas las casillas de un objeto: block(columna, fila, ancho, alto)+
    //Obstáculos(bolardo)
    public void block(int col, int row, int ancho, int alto) {
        for (int c = col; c < col + ancho; c++) {
            for (int r = row; r < row + alto; r++) {
                if (c >= 0 && r >= 0 && c < gp.maxWorldCol && r < gp.maxWorldRow) {
                    blocked[c][r] = true;
                }
            }
        }
    }

    // Objetos sólidos (las mismas posiciones que en draw())
    // Mapa invisible de colisiones, esto hace que el personaje choque con los objetos, no flote sbre ellos
    public void setObjectCollisions() {

        block(8, 1, 3, 2);     // taquilla
        block(14, 1, 4, 2);    // máquinas amarillas
        block(20, 1, 1, 2);    // máquina de refrescos
        block(13, 10, 1, 1);   // bote de basura

        // fila 6: bolardos (168) y barreras (25). Los torniquetes (26) quedan libres para pasar
        int[] bolardos = {5, 7, 10, 13, 16, 18};
        for (int i = 0; i < bolardos.length; i++) {
            block(bolardos[i], 6, 1, 1);
        }
        block(8, 6, 2, 1);
        block(14, 6, 2, 1);

        // vallas
        block(4, 6, 1, 5);
        block(19, 6, 1, 5);
    }

    public void loadMap() {

        // try-with-resources cierra el archivo SOLO al terminar (antes se cerraba dentro del while)
        try (InputStream is = getClass().getResourceAsStream("/maps/estacion.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            for (int row = 0; row < gp.maxWorldRow; row++) {

                String line = br.readLine();
                if (line == null) {
                    System.out.println("estacion.txt: solo tiene " + row + " filas, esperaba " + gp.maxWorldRow);
                    break;
                }

                String[] numbers = line.trim().split("\\s+");
                if (numbers.length != gp.maxWorldCol) {
                    System.out.println("estacion.txt fila " + row + ": tiene " + numbers.length
                            + " columnas, esperaba " + gp.maxWorldCol);
                }

                for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();   // antes el catch estaba vacio y ocultaba el error
        }
    }

    // Dibuja un objeto en la casilla (col, row); ancho y alto para que no sea como bloque 1x1
    //Es decir, estira la imagen
    //Para usarlo: Se invoca dentro del método principal de dibujo se le pasan los datos del objeto.
    // Por ejemplo, para pintar Letrero L7 id:162, col 13, fila 0 , que mide 2x1 casillas:
    //Consultar línea 494
    public void drawObject(Graphics2D g2, int id, int col, int row, int anchoCasillas, int altoCasillas) {

        int worldX = col * gp.titleSize;
        int worldY = row * gp.titleSize;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(tile[id].image, screenX, screenY,
                     gp.titleSize * anchoCasillas, gp.titleSize * altoCasillas, null);
    }

    public void draw(Graphics2D g2) {

        // 1) CAPA BASE (el piso y las paredes del archivo .txt)
        //Ciclo encargado de pintar el piso, las vías y las paredes del nivel
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {

                int tileNum = mapTileNum[worldCol][worldRow];

                int worldX = worldCol * gp.titleSize;
                int worldY = worldRow * gp.titleSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.titleSize, gp.titleSize, null);
            }
        }
//Lo anterior toma datos numéricos del juego y los convierte en las imágenes (piso, paredes y obstáculos) que el usuario ve en tiempo real mientras se mueve.

        // 2) OBJETOS Y DECORACIÓN (después de la base, para quedar encima)
        // CAPA DECORACIONES Y OBJETOS DEL ESCENARIO (POR FILAS)
        // FILA 0
        // Letrero L7 id:162
        drawObject(g2, 162, 13, 0, 2, 1);
        // FILA 1
        // Mapa metro id:157
        drawObject(g2, 157, 2, 1, 2, 1);
        // Taquilla de Boletos id:164
        drawObject(g2, 164, 8, 1, 3, 2);

        // Máquina de Boletos Amarilla id:166
        drawObject(g2, 166, 14, 1, 1, 2);
        // Máquina de Boletos Amarilla id:166
        drawObject(g2, 166, 15, 1, 1, 2);
        // Máquina de Boletos Amarilla id:166
        drawObject(g2, 166, 16, 1, 1, 2);
        // Máquina de Boletos Amarilla id:166
        drawObject(g2, 166, 17, 1, 1, 2);
        // Máquina Expendedora de Refrescos id:167
        drawObject(g2, 167, 20, 1, 1, 2);
        // FILA 3
        // Papel tirado id:29
        drawObject(g2, 29, 8, 3, 1, 1);
        drawObject(g2, 29, 19, 5, 1, 1);
        // FILA 5
        // Papel tirado id:29
        drawObject(g2, 29, 5, 5, 1, 1);
        // FILA 6
        // Bases de torniquetes id:25, Torniquete id:26 y Charco id:30 en el extremo derecho
        drawObject(g2, 168, 5, 6, 1, 1);
        drawObject(g2, 26, 6, 6, 1, 1);
        drawObject(g2, 168, 7, 6, 1, 1);
        drawObject(g2, 25, 8, 6, 1, 1);
        drawObject(g2, 25, 9, 6, 1, 1);
        drawObject(g2, 168, 10, 6, 1, 1);
        drawObject(g2, 26, 11, 6, 1, 1);
        drawObject(g2, 26, 12, 6, 1, 1);
        drawObject(g2, 168, 13, 6, 1, 1);
        drawObject(g2, 25, 14, 6, 1, 1);
        drawObject(g2, 25, 15, 6, 1, 1);
        drawObject(g2, 168, 16, 6, 1, 1);
        drawObject(g2, 26, 17, 6, 1, 1);
        drawObject(g2, 168, 18, 6, 1, 1);
        drawObject(g2, 30, 22, 6, 1, 1);
        //FILA 8
        //Charcho id:30
        drawObject(g2, 30, 16, 8, 1, 1);
        // FILA 9
        // Charco id:30, Papel tirado id:29
        drawObject(g2, 30, 3, 9, 1, 1);
        drawObject(g2, 29, 11, 9, 1, 1);
        //FILA 10
        //Bote de basura id:27
        drawObject(g2, 27, 13, 10, 1, 1);
        //VALLAS/Bolardos
        drawObject(g2, 169, 4, 6, 1, 1);
        drawObject(g2, 169, 4, 7, 1, 1);
        drawObject(g2, 169, 4, 8, 1, 1);
        drawObject(g2, 169, 4, 9, 1, 1);
        drawObject(g2, 169, 4, 10, 1, 1);
        drawObject(g2, 169, 19, 6, 1, 1);
        drawObject(g2, 169, 19, 7, 1, 1);
        drawObject(g2, 169, 19, 8, 1, 1);
        drawObject(g2, 169, 19, 9, 1, 1);
        drawObject(g2, 169, 19, 10, 1, 1);
    }
}
