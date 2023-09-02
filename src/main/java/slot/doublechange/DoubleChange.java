package slot.doublechange;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DoubleChange extends JavaPlugin {
    public static Scoreboard board;
    ScoreboardManager sbManager;
    public static Objective mainObject;
    public static Score rush;
    List<String> sbname = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dc")) {
            if (Objects.equals(args[0], "list")) {
                for (String s : sbname) {
                    sender.sendMessage(s);
                }
                return true;
            } else if (Objects.equals(args[0], "reset")) {
                for (String s : sbname) {
                    mainObject = board.getObjective(s);
                    mainObject.getScoreboard().resetScores(s);
                    mainObject.unregister();
                    sender.sendMessage(s + " is Removed!");
                }
                sbname.clear();
                return true;
            } else {
                boolean check = false;
                sender.sendMessage("a");
                for (String s : sbname) {
                    sender.sendMessage("b");
                    if (Objects.equals(args[0], s)) {
                        sender.sendMessage("c");
                        check = true;
                        break;
                    }
                }
                if (!check || sbname.size() == 0) {
                    sender.sendMessage("d");
                    mainObject = board.registerNewObjective(args[0], "dummy");
                    sender.sendMessage("dd");
                    sbname.add(args[0]);
                }
                if (check) {
                    mainObject = board.getObjective(args[0]);
                }
                sender.sendMessage("e");
                mainObject.setDisplayName(args[0]);
                rush = mainObject.getScore(args[1]);
                String dcdata = args[2];
                rush.setScore(Integer.parseInt(dcdata.replace(".0", "")));
                mainObject.setDisplaySlot(DisplaySlot.SIDEBAR);
                mainObject.getScoreboard().resetScores(args[0]);
                return true;
            }
        }
        return true;
    }

    @Override
    public void onEnable() {
        sbManager = Bukkit.getScoreboardManager();
        board = sbManager.getMainScoreboard();
    }

    @Override
    public void onDisable() {
        for (String s : sbname) {
            mainObject = board.getObjective(s);
            mainObject.getScoreboard().resetScores(s);
            mainObject.unregister();
        }
        sbname.clear();
        // Plugin shutdown logic
    }
}
