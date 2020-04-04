package ua.dkotov.randomspawn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.dkotov.randomspawn.Core;

@org.bukkit.plugin.java.annotation.command.Command(
        name = "randomspawn",
        permission = "randomspawn.use",
        aliases = {"rsp"})
public class RandomSpawn implements CommandExecutor {

    private Core core;

    public RandomSpawn(Core core) {
        this.core = core;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String args, String[] label) {
        Player p =  (Player) sender;
        core.spawnRandom(p.getUniqueId());
        return false;
    }
}
