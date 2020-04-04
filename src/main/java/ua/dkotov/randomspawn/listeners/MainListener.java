package ua.dkotov.randomspawn.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.dkotov.randomspawn.Core;

public class MainListener implements Listener {

    private Core core;

    public MainListener(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        core.spawnRandom(event.getPlayer().getUniqueId());
    }
}
