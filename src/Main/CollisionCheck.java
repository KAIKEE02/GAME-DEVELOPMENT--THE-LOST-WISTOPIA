package Main;

import Entities.Entity;
import static Main.MAINGAME.*;
import Entities.Player;
import Entities.Projectile;
import Gamestates.Play;
import Tile.TileManager;
import objects.superObj;

import static Main.MAINGAME.TILES_SIZE;

import java.awt.Rectangle;

import Enemies.enemy_orc;

public class CollisionCheck {

    Play screen;
    TileManager tileManager;
    superObj obj[];
    enemy_orc enemies[];

    public CollisionCheck(Play screen, TileManager tileManager) {
        this.screen = screen;
        this.tileManager = tileManager;
        
        
    }
    public boolean checkSolidTile(int x, int y) {
        int col = x / TILES_SIZE;
        int row = y / TILES_SIZE;
        int tileNum = tileManager.getTileNum(col, row);

        return tileManager.tiles[tileNum].collision;  // Return true if the tile has a collision property
    }
    public boolean isTileBlocking(int x, int y) {
        int tileX = x / TILES_SIZE;  // Convert world coordinates to tile coordinates
        int tileY = y / TILES_SIZE;

        // Ensure the tile coordinates are within the bounds of the map
        if (tileX >= 0 && tileX < maxWorldCol && tileY >= 0 && tileY < maxWorldRow) {
            int tileNum = tileManager.getTileNum(tileX, tileY);
            // Check if the tile is solid
            return tileManager.tiles[tileNum].collision;
        }
        return false;  // Return false if the tile is out of bounds or not solid
    }



    public void checkTile(Entity entity, boolean movingLeft, boolean movingRight, boolean movingUp, boolean movingDown, float speed) {

        int entityLeftWorldX = (int) (entity.worldx + entity.solidArea.x);
        int entityRightWorldX = (int) (entity.worldx + entity.solidArea.x + entity.solidArea.width);
        int entityTopWorldY = (int) (entity.worldy + entity.solidArea.y);
        int entityBottomWorldY = (int) (entity.worldy + entity.solidArea.y + entity.solidArea.height);

        int entityLeftCol = entityLeftWorldX / TILES_SIZE;
        int entityRightCol = entityRightWorldX / TILES_SIZE;
        int entityTopRow = entityTopWorldY / TILES_SIZE;
        int entityBottomRow = entityBottomWorldY / TILES_SIZE;

        int tileNum1, tileNum2;

        if (movingUp) {
            entityTopRow = (int) ((entityTopWorldY - speed) / TILES_SIZE);
            tileNum1 = tileManager.getTileNum(entityLeftCol, entityTopRow);
            tileNum2 = tileManager.getTileNum(entityRightCol, entityTopRow);
            if (tileManager.tiles[tileNum1].collision || tileManager.tiles[tileNum2].collision) {
                entity.collisionON = true;
            }
        }

        if (movingDown) {
            entityBottomRow = (int) ((entityBottomWorldY + speed) / TILES_SIZE);
            tileNum1 = tileManager.getTileNum(entityLeftCol, entityBottomRow);
            tileNum2 = tileManager.getTileNum(entityRightCol, entityBottomRow);
            if (tileManager.tiles[tileNum1].collision || tileManager.tiles[tileNum2].collision) {
                entity.collisionON = true;
            }
        }

        if (movingLeft) {
            entityLeftCol = (int) ((entityLeftWorldX - speed) / TILES_SIZE);
            tileNum1 = tileManager.getTileNum(entityLeftCol, entityTopRow);
            tileNum2 = tileManager.getTileNum(entityLeftCol, entityBottomRow);
            if (tileManager.tiles[tileNum1].collision || tileManager.tiles[tileNum2].collision) {
                entity.collisionON = true;
            }
        }

        if (movingRight) {
            entityRightCol = (int) ((entityRightWorldX + speed) / TILES_SIZE);
            tileNum1 = tileManager.getTileNum(entityRightCol, entityTopRow);
            tileNum2 = tileManager.getTileNum(entityRightCol, entityBottomRow);
            if (tileManager.tiles[tileNum1].collision || tileManager.tiles[tileNum2].collision) {
                entity.collisionON = true;
            }
            
            
        }
    }
    
    public int checkObjectCollision(Entity entity, boolean player, boolean movingLeft, boolean movingRight, boolean movingUp, boolean movingDown, float speed) {
        int index = -1;  // Default value for no collision

        Rectangle originalSolidArea = new Rectangle(entity.solidArea);

        for (int i = 0; i < screen.obj.length; i++) {
            if (screen.obj[i] != null) {
                // Update solidArea positions
                entity.solidArea.x = (int) (entity.worldx + entity.solidAreaDefaultX);
                entity.solidArea.y = (int) (entity.worldy + entity.solidAreaDefaultY);
                screen.obj[i].solidArea.x = screen.obj[i].worldx + screen.obj[i].solidAreaDefaultX;
                screen.obj[i].solidArea.y = screen.obj[i].worldy + screen.obj[i].solidAreaDefaultY;

                // Predict movement
                Rectangle predictedArea = new Rectangle(entity.solidArea);
                if (movingUp) {
                    predictedArea.y -= speed;
                } else if (movingDown) {
                    predictedArea.y += speed;
                } else if (movingLeft) {
                    predictedArea.x -= speed;
                } else if (movingRight) {
                    predictedArea.x += speed;
                }

        

                if (predictedArea.intersects(screen.obj[i].solidArea)) {
                    if (screen.obj[i].collision) {
                        entity.collisionON = true;
                    }
                    System.out.println("Collision detected with object index: " + i);
                    if (player) {
                        index = i;
                    }
                }
            }
        }

        entity.solidArea.setBounds(originalSolidArea);
        return index;
    }
    
    public int checkNpc(Entity entity, boolean player, boolean movingLeft, boolean movingRight, boolean movingUp, boolean movingDown, float speed) {
        int index = -1;  // Default value for no collision

        Rectangle originalSolidArea = new Rectangle(entity.solidArea);

        for (int i = 0; i < screen.npc.length; i++) {
            if (screen.npc[i] != null) {
                // Update solidArea positions
                entity.solidArea.x = (int) (entity.worldx + entity.solidAreaDefaultX);
                entity.solidArea.y = (int) 
                		(entity.worldy + entity.solidAreaDefaultY);
                screen.npc[i].solidArea.x = screen.npc[i].worldx + screen.npc[i].solidAreaDefaultX;
                screen.npc[i].solidArea.y = screen.npc[i].worldy + screen.npc[i].solidAreaDefaultY;

                // Predict movement
                Rectangle predictedArea = new Rectangle(entity.solidArea);
                if (movingUp) {
                    predictedArea.y -= speed;
                } else if (movingDown) {
                    predictedArea.y += speed;
                } else if (movingLeft) {
                    predictedArea.x -= speed;
                } else if (movingRight) {
                    predictedArea.x += speed;
                }

        

                if (predictedArea.intersects(screen.npc[i].solidArea)) {
                    if (screen.npc[i].collision) {
                        entity.collisionON = true;
                    }
                    System.out.println("Collision detected with npc index: " + i);
                    if (player) {
                        index = i;
                    }
                }
            }
        }

        entity.solidArea.setBounds(originalSolidArea);
        return index;
    }

   

    public boolean checkWeaponCollision(Entity player, Entity enemy, Rectangle weaponHitbox) {
        // Get the current hitboxes of the player and the enemy
        Rectangle playerWeaponHitbox = new Rectangle(
            (int) (player.worldx + weaponHitbox.x),
            (int) (player.worldy + weaponHitbox.y),
            weaponHitbox.width,
            weaponHitbox.height
        );

        Rectangle enemyHitbox = new Rectangle(
            (int) (enemy.worldx + enemy.solidArea.x),
            (int) (enemy.worldy + enemy.solidArea.y),
            enemy.solidArea.width,
            enemy.solidArea.height
        );

        // Check if the weapon hitbox intersects with the enemy hitbox
        if (playerWeaponHitbox.intersects(enemyHitbox)) {
        	
        	
        
            return true;  // Weapon hit the enemy
        }

        return false;  // No hit
    }
    public void checkProjectileCollisions() {
        for (Projectile p : screen.projectiles) {
            for (enemy_orc enemy : screen.enemies) {
                if (enemy != null && p.active && p.hitbox.intersects(enemy.solidArea)) {
                    enemy.takeDamage(10); // Example: Deal 10 damage
                    p.active = false;    // Deactivate projectile
                }
            }
        }
    }




}
