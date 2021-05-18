package skyblock.utils.quest;

import org.bukkit.entity.Player;
import skyblock.SkyblockMain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Quest {
    private final List<TaskTalk> tasks = new ArrayList<>();
    public HashMap<UUID, Integer> playerProgress = new HashMap<>();

    public void addTask(TaskTalk task) {
        this.tasks.add(task);
    }

    public long triggerTasks(Player player, QuestNPCEntity npc) {
        int currentTask = this.playerProgress.getOrDefault(player.getUniqueId(), 0);
        if (currentTask < this.tasks.size()) {
            TaskTalk task = this.tasks.get(currentTask);
            if (task.matchesEntity(npc)) {
                Quest.State result = task.execute(player);
                if (result == Quest.State.SUCCESS) {
                    this.playerProgress.put(player.getUniqueId(), currentTask + 1);
                    return task.getCooldown();
                } else if (result == State.MISSING_REQUIREMENT) {
                    return 2000L;
                }
            }
        }
        return 0;
    }

    public void saveData(String questName) {
        Connection connection = SkyblockMain.databaseHandler.getDatabaseConnection();
        try {
            for (UUID uuid : this.playerProgress.keySet()) {
                PreparedStatement countQuery = connection.prepareStatement("SELECT COUNT(*) AS total FROM questdata WHERE uuid=? AND questName=?");
                countQuery.setString(1, uuid.toString());
                countQuery.setString(2, questName);
                ResultSet countResult = countQuery.executeQuery();
                int count = countResult.getInt("total");

                if (count > 0) {
                    // update
                    PreparedStatement ps;
                    ps = connection.prepareStatement("UPDATE questdata SET progress=? WHERE uuid=? AND questName=?");
                    ps.setInt(1, this.playerProgress.get(uuid));
                    ps.setString(2, uuid.toString());
                    ps.setString(3, questName);
                    ps.executeUpdate();
                } else {
                    // new
                    PreparedStatement ps;
                    ps = connection.prepareStatement("INSERT INTO questdata(uuid,questName,progress,tasksTotal) VALUES(?,?,?,?)");
                    ps.setString(1, uuid.toString());
                    ps.setString(2, questName);
                    ps.setInt(3, this.playerProgress.get(uuid));
                    ps.setInt(4, this.tasks.size());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String questName) {
        Connection connection = SkyblockMain.databaseHandler.getDatabaseConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM questdata WHERE questName=?");
            statement.setString(1, questName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String rawUuid = resultSet.getString("uuid");
                UUID uuid = UUID.fromString(rawUuid);
                int progress = resultSet.getInt("progress");
                this.playerProgress.put(uuid, progress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public enum State {
        SUCCESS,
        MISSING_REQUIREMENT,
        FAILURE
    }
}
