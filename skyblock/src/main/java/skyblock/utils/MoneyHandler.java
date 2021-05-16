package skyblock.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import skyblock.SkyblockMain;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class MoneyHandler {
    private HashMap<UUID, Integer> money = new HashMap<>(); // change to java.lang.Long if scoreboard is no longer used FOR MORE MONEY
    private Scoreboard scoreboard;

    public MoneyHandler() {
        ScoreboardManager scoreboardManager = SkyblockMain.instance.getServer().getScoreboardManager();
        if (scoreboardManager != null) {
            this.scoreboard = scoreboardManager.getNewScoreboard();
            this.createScoreboard();
        }
    }

    public void createScoreboard() {
        Objective objective = this.scoreboard.registerNewObjective("money", "money", "Money");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    public void setScoreboard(Player player) {
        player.setScoreboard(this.scoreboard);
    }

    public void addMoney(Player player, int amount) {
        if (amount > 0) {
            UUID uuid = player.getUniqueId();
            if (this.money.containsKey(uuid)) {
                int balance = this.money.get(uuid);
                this.money.put(uuid, balance + amount);
            } else {
                this.money.put(uuid, amount);
            }

            this.updateScoreboard(player);
        }
    }

    public boolean removeMoney(Player player, int amount) {
        if (amount > 0) {
            UUID uuid = player.getUniqueId();
            if (this.money.containsKey(uuid)) {
                int balance = this.money.get(uuid);
                if (balance - amount >= 0) {
                    this.money.put(uuid, balance - amount);
                    this.updateScoreboard(player);
                    return true;
                }
            } else {
                this.money.put(uuid, 0);
            }
            return false;
        }
        return false;
    }

    public int getMoney(Player player) {
        if (this.money.containsKey(player.getUniqueId())) {
            return this.money.get(player.getUniqueId());
        }
        return -1;
    }

    private void updateScoreboard(Player player) {
        Objective objective = this.scoreboard.getObjective("money");
        if (objective != null) {
            Score score = objective.getScore(player);
            score.setScore(this.money.get(player.getUniqueId()));
        }
    }

    public void saveData() {
        Connection connection = SkyblockMain.databaseHandler.getDatabaseConnection();
        try {
            for (UUID key : this.money.keySet()) {
                PreparedStatement countQuery = connection.prepareStatement("SELECT COUNT(*) AS total FROM playerdata WHERE uuid=?");
                countQuery.setString(1, key.toString());
                ResultSet countResult = countQuery.executeQuery();
                int count = countResult.getInt("total");

                PreparedStatement preparedStatement;
                if (count > 0) {
                    preparedStatement = connection.prepareStatement("UPDATE playerdata SET money=? WHERE uuid=?");
                    preparedStatement.setInt(1, this.money.get(key));
                    preparedStatement.setString(2, key.toString());
                } else {
                    preparedStatement = connection.prepareStatement("INSERT INTO playerdata(uuid,money) VALUES(?,?)");
                    preparedStatement.setString(1, key.toString());
                    preparedStatement.setInt(2, this.money.get(key));
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        Connection connection = SkyblockMain.databaseHandler.getDatabaseConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM playerdata");
            while (resultSet.next()) {
                String rawUuid = resultSet.getString("uuid");
                UUID uuid = UUID.fromString(rawUuid);
                int money = resultSet.getInt("money");
                this.money.put(uuid, money);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
