package Tile;

import java.awt.Graphics2D;



import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import Entities.Player;
import Gamestates.Play;
import static Main.MAINGAME.*;

public class TileManager {

    private Player player;
    private Play playing;
    public tile[] tiles;
    public int[][] mapTileNum;

    public TileManager(Play play, Player player) {
        this.playing = play;
        this.player = player;

        tiles = new tile[69];
        mapTileNum = new int[maxWorldCol][maxWorldRow];

        getTileImg();
        loadMap("/maps/world5");
    }

    public void getTileImg() {
        try {
            InputStream[] tileStreams = {
//                getClass().getResourceAsStream("/tiles/PLAINGRASS.png"), // INDEX 0
//                getClass().getResourceAsStream("/tiles/wall.png"), // INDEX 1
//                getClass().getResourceAsStream("/tiles/water01.png"), // INDEX 2
//                getClass().getResourceAsStream("/tiles/sand.png"), // INDEX 3
//                getClass().getResourceAsStream("/tiles/trees.png"), // INDEX 4
//                getClass().getResourceAsStream("/tiles/road00.png"), // INDEX 5
//                getClass().getResourceAsStream("/tiles/GRASS.png") // INDEX 5
            		  getClass().getResourceAsStream("/tiles/000.png"), // INDEX 0
                      getClass().getResourceAsStream("/tiles/001.png"), // INDEX 1
                      getClass().getResourceAsStream("/tiles/002.png"), // INDEX 2
                      getClass().getResourceAsStream("/tiles/003.png"), // INDEX 3
                      getClass().getResourceAsStream("/tiles/004.png"), // INDEX 4
                      getClass().getResourceAsStream("/tiles/005.png"), // INDEX 5
                      getClass().getResourceAsStream("/tiles/006.png"), // INDEX 5
                      getClass().getResourceAsStream("/tiles/007.png"), 
                      getClass().getResourceAsStream("/tiles/008.png"), 
                      getClass().getResourceAsStream("/tiles/009.png"), 
                      getClass().getResourceAsStream("/tiles/010.png"), 
                      getClass().getResourceAsStream("/tiles/011.png"), 
                      getClass().getResourceAsStream("/tiles/012.png"), 
                      getClass().getResourceAsStream("/tiles/013.png"), 
                      getClass().getResourceAsStream("/tiles/014.png"), 
                      getClass().getResourceAsStream("/tiles/015.png"),  
                      getClass().getResourceAsStream("/tiles/2tree.png"), // INDEX 16
                      getClass().getResourceAsStream("/tiles/017.png"), // INDEX 17
                      getClass().getResourceAsStream("/tiles/018.png"), // INDEX 18
                      getClass().getResourceAsStream("/tiles/019.png"), // INDEX 19
                      getClass().getResourceAsStream("/tiles/020.png"), // INDEX 20
                      getClass().getResourceAsStream("/tiles/021.png"), // INDEX 21
                      getClass().getResourceAsStream("/tiles/022.png"), // INDEX 22
                      getClass().getResourceAsStream("/tiles/023.png"), // INDEX 23
                      getClass().getResourceAsStream("/tiles/024.png"), // INDEX 24
                      getClass().getResourceAsStream("/tiles/025.png"), // INDEX 25
                      getClass().getResourceAsStream("/tiles/026.png"), // INDEX 26
                      getClass().getResourceAsStream("/tiles/027.png"), // INDEX 27
                      getClass().getResourceAsStream("/tiles/028.png"), // INDEX 28
                      getClass().getResourceAsStream("/tiles/029.png"), // INDEX 29
                      getClass().getResourceAsStream("/tiles/030.png"), // INDEX 30
                      getClass().getResourceAsStream("/tiles/031.png"), // INDEX 31
                      getClass().getResourceAsStream("/tiles/032.png"), // INDEX 32
                      getClass().getResourceAsStream("/tiles/033.png"), // INDEX 33
                      getClass().getResourceAsStream("/tiles/034.png"), // INDEX 34
                      getClass().getResourceAsStream("/tiles/035.png"), // INDEX 35
                      getClass().getResourceAsStream("/tiles/036.png"), // INDEX 36
                      getClass().getResourceAsStream("/tiles/037.png"), // INDEX 37
                      getClass().getResourceAsStream("/tiles/038.png"), // INDEX 38
                      getClass().getResourceAsStream("/tiles/039.png"), // INDEX 39
                      getClass().getResourceAsStream("/tiles/040.png"), // INDEX 40
                      getClass().getResourceAsStream("/tiles/041.png"), // INDEX 41
                      getClass().getResourceAsStream("/tiles/042.png"), // INDEX 42
                      getClass().getResourceAsStream("/tiles/043.png"), // INDEX 43
                      getClass().getResourceAsStream("/tiles/044.png"), // INDEX 44
                      getClass().getResourceAsStream("/tiles/045.png"), // INDEX 45
                      getClass().getResourceAsStream("/tiles/046.png"), // INDEX 46
                      getClass().getResourceAsStream("/tiles/047.png"), // INDEX 47
                      getClass().getResourceAsStream("/tiles/048.png"), // INDEX 48
                      getClass().getResourceAsStream("/tiles/049.png"), // INDEX 49
                      getClass().getResourceAsStream("/tiles/050.png"), // INDEX 50
                      getClass().getResourceAsStream("/tiles/051.png"), // INDEX 51
                      getClass().getResourceAsStream("/tiles/052.png"), // INDEX 52
                      getClass().getResourceAsStream("/tiles/053.png"), // INDEX 53
                      getClass().getResourceAsStream("/tiles/054.png"), // INDEX 54
                      getClass().getResourceAsStream("/tiles/055.png"), // INDEX 55
                      getClass().getResourceAsStream("/tiles/056.png"), // INDEX 56
                      getClass().getResourceAsStream("/tiles/057.png"), // INDEX 57
                      getClass().getResourceAsStream("/tiles/058.png"), // INDEX 58
                      getClass().getResourceAsStream("/tiles/059.png"), // INDEX 59
                      getClass().getResourceAsStream("/tiles/060.png"), // INDEX 60
                      getClass().getResourceAsStream("/tiles/061.png"), // INDEX 61
                      getClass().getResourceAsStream("/tiles/062.png"), // INDEX 62
                      getClass().getResourceAsStream("/tiles/063.png"), // INDEX 63
                      getClass().getResourceAsStream("/tiles/064.png"), // INDEX 64
                      getClass().getResourceAsStream("/tiles/065.png"), // INDEX 65
                      getClass().getResourceAsStream("/tiles/066.png"), // INDEX 66
                      getClass().getResourceAsStream("/tiles/067.png"), // INDEX 67
                      getClass().getResourceAsStream("/tiles/068.png"), // INDEX 68


            };

            for (int i = 0; i < tileStreams.length; i++) {
                tiles[i] = new tile();
                tiles[i].img = ImageIO.read(tileStreams[i]);
            }
            tiles[16].collision = true;
            tiles[18].collision = true;
            tiles[19].collision = true;
            tiles[20].collision = true;
            tiles[21].collision = true;
            tiles[22].collision = true;
            tiles[23].collision = true;
            tiles[24].collision = true;
            tiles[25].collision = true;
            tiles[26].collision = true;
            tiles[27].collision = true;
            tiles[28].collision = true;
            tiles[29].collision = true;
            tiles[30].collision = true;
            tiles[31].collision = true;
            tiles[32].collision = true;
            tiles[35].collision = true;
            tiles[38].collision = true;
            tiles[40].collision = true;
            tiles[42].collision = true;
            tiles[43].collision = true;
            tiles[44].collision = true;
            tiles[45].collision = true;
            tiles[46].collision = true;
            tiles[47].collision = true;
            tiles[48].collision = true;
            tiles[49].collision = true;
            tiles[50].collision = true;
            tiles[52].collision = true;
//            tiles[2].collision = true;
//            tiles[4].collision = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < maxWorldRow) {
                String[] numbers = line.split(" ");
                for (int col = 0; col < maxWorldCol && col < numbers.length; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderTile(Graphics2D g2) {
    	

        for (int worldRow = 0; worldRow < maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < maxWorldCol; worldCol++) {
                int tileNum = mapTileNum[worldCol][worldRow];
                if (tileNum >= 0 && tileNum < tiles.length) {
                    int worldX = worldCol * TILES_SIZE;
                    int worldY = worldRow * TILES_SIZE;
                    
                    // Round screen positions to nearest integer
                    int screenX = (int)Math.floor(worldX - player.getWorldX() + player.getScreenX());
                    int screenY = (int)Math.floor(worldY - player.getWorldY() + player.getScreenY());

                    // Draw tiles within view
                    if (worldX + TILES_SIZE > player.getWorldX() - player.getScreenX() &&
                        worldX - TILES_SIZE - 78 < player.getWorldX() + player.getScreenX() &&
                        worldY + TILES_SIZE > player.getWorldY() - player.getScreenY() &&
                        worldY - TILES_SIZE - 120 < player.getWorldY() + player.getScreenY()) {

                        // Optionally expand the tile size slightly to avoid gaps
                        int expandedTileSize = TILES_SIZE + 2;

                        // Render the tile
                        g2.drawImage(tiles[tileNum].img, screenX, screenY, expandedTileSize, expandedTileSize, null);
                    }
                }
            }
        }
    }

    public int getTileNum(int col, int row) {
        // Ensure the column and row are within the map bounds
        if (col >= 0 && col < maxWorldCol && row >= 0 && row < maxWorldRow) {
            return mapTileNum[col][row];
        }
        return -1; // Return an invalid value if the coordinates are out of bounds
    }
}
