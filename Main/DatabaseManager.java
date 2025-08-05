package Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entities.Player;
import Gamestates.Play;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/thelostwistopia"; // Update if your port is different
    private static final String USER = "root"; // Default user for XAMPP
    private static final String PASSWORD = ""; // Password set in XAMPP (leave blank if none)

    // Method to connect to the database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Save player data
    public int savePlayerData(Player player) {
    	String query = "INSERT INTO player_data (player_name, level, max_life, max_def, life, defense, max_stamina, stamina, x_position, y_position, bullets, orbs, hashkeys, screen_x, screen_y, attack_damage, gun_damage) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        int generatedId = -1;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "JAVAN"); // Use actual player name
            pstmt.setInt(2, player.getLevel());
            pstmt.setInt(3, player.getMaxLife());
            pstmt.setInt(4, player.getMaxDefense());
            pstmt.setInt(5, player.getLife());
            pstmt.setInt(6, player.getDefense());
            pstmt.setInt(7, player.getMaxStamina());
            pstmt.setFloat(8, player.getStamina());
            pstmt.setFloat(9, player.getWorldX());
            pstmt.setFloat(10, player.getWorldY());
            pstmt.setInt(11, player.getBulletCounter());
            pstmt.setInt(12, player.getOrbs()); // Save orbs
            pstmt.setInt(13, player.getKeys()); // Save keys
            pstmt.setFloat(14, player.getScreenX()); // Save screen_x
            pstmt.setFloat(15, player.getScreenY()); // Save screen_y
            pstmt.setInt(16, player.getAttackDamage()); // Save attack damage
            pstmt.setInt(17, player.getGunDamage()); // Save gun damage

            pstmt.executeUpdate();

            // Retrieve the generated ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
                System.out.println("Player data saved successfully! Generated ID: " + generatedId);
            }
        } catch (SQLException e) {
            System.out.println("Error saving player data: " + e.getMessage());
        }

        return generatedId;
    }



    // Load player data by ID
 // Method to load the latest player data
 // Load the latest player data
    public Player loadLatestPlayerData(Play play) {
        String query = "SELECT * FROM player_data ORDER BY id DESC LIMIT 1";
        Player player = null;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Create a Player object from the database data
                player = new Player(rs.getFloat("x_position"), rs.getFloat("y_position"), play);
                player.setLevel(rs.getInt("level"));
                player.setMaxLife(rs.getInt("max_life"));
                player.setMaxDefense(rs.getInt("max_def"));
                player.setLife(rs.getInt("life"));
                player.setDefense(rs.getInt("defense"));
                player.setMaxStamina(rs.getInt("max_stamina"));
                player.setStamina(rs.getFloat("stamina"));
                player.setBulletCounter(rs.getInt("bullets"));
                player.setOrbs(rs.getInt("orbs")); // Load orbs
                player.setKeys(rs.getInt("hashkeys")); // Load keys
//                player.setScreenX(rs.getFloat("screen_x")); // Load screen_x
//                player.setScreenY(rs.getFloat("screen_y")); // Load screen_y
                player.setAttackDamage(rs.getInt("attack_damage")); // Load attack damage
                player.setGunDamage(rs.getInt("gun_damage")); // Load gun damage

                System.out.println("Latest player data loaded successfully!");
            } else {
                System.out.println("No saved player data found!");
            }
        } catch (SQLException e) {
            System.out.println("Error loading latest player data: " + e.getMessage());
        }

        return player;
    }
}
