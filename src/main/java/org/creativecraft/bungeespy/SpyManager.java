package org.creativecraft.bungeespy;

import java.util.Set;
import java.util.UUID;

public class SpyManager {
    private final BungeeSpyPlugin plugin;

    /**
     * Initialize the spy manager instance.
     *
     * @param plugin BungeeSpyPlugin
     */
    public SpyManager(BungeeSpyPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Determine if the player is a spy.
     *
     * @param  player The player UUID.
     * @return boolean
     */
    public boolean isSpy(UUID player) {
        return plugin.getUserData().contains("players." + player);
    }

    /**
     * Add the player as a spy.
     */
    public void addSpy(UUID player) {
        plugin.getUserData().set("players." + player, true);
    }

    /**
     * Remove the player as a spy.
     */
    public void removeSpy(UUID player) {
        plugin.getUserData().remove("players." + player);
    }

    /**
     * Retrieve the current spies.
     */
    public Set<String> getSpies() {
        return plugin.getUserData().getSection("players").keySet();
    }
}
