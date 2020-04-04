package ua.dkotov.randomspawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import ua.dkotov.randomspawn.commands.RandomSpawn;
import ua.dkotov.randomspawn.listeners.MainListener;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Plugin(name = "RandomSpawn", version = "1.0")
@Author("DKotov")
public class Core extends JavaPlugin {

    private List<Location> spawnLocations;
    private ExecutorService executorService;
    private Iterator<Location> spawnLocationsIterator;
    private double nearRadius;

    @Override
    public void onEnable() {
        spawnLocations = new ArrayList<>();
        saveDefaultConfig();
        nearRadius = getConfig().getDouble("nearRadius", 5);
        executorService = Executors.newSingleThreadScheduledExecutor();
        getCommand("randomspawn").setExecutor(new RandomSpawn(this));
        getServer().getPluginManager().registerEvents(new MainListener(this), this);
    }

    public void spawnRandom(UUID uuid) {
        Player player = getServer().getPlayer(uuid);
        if (!spawnLocationsIterator.hasNext()) {
            generatePositions();
        }
        player.teleport(spawnLocationsIterator.next());
    }

    private void generatePositions() {
        spawnLocations.clear();
        Map<Location, Integer> locs = new TreeMap<>((o1, o2) -> {
            int a = o1.getWorld().getNearbyEntities(o1, nearRadius, nearRadius, nearRadius).size();
            int b = o2.getWorld().getNearbyEntities(o2, nearRadius, nearRadius, nearRadius).size();
            return a - b;
        });
        for (String s : getConfig().getStringList("coords")) {
            String[] strings = s.split(";");
            locs.put(new Location(getServer().getWorld(strings[0]),
                    Double.parseDouble(strings[1]),
                    Double.parseDouble(strings[2]),
                    Double.parseDouble(strings[3])), 0);
        }
        locs.forEach((loc, int1) -> spawnLocations.add(loc));
        spawnLocationsIterator = spawnLocations.iterator();
    }
}
