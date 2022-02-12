package org.creativecraft.bungeespy.config;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.creativecraft.bungeespy.BungeeSpyPlugin;

public class UserDataConfig {
    private final BungeeSpyPlugin plugin;
    private Config userData;

    /**
     * Initialize the settings config instance.
     *
     * @param plugin BungeeSpyPlugin
     */
    public UserDataConfig(BungeeSpyPlugin plugin) {
        this.plugin = plugin;

        registerConfig();
    }

    /**
     * Register the config configuration.
     */
    public void registerConfig() {
        userData = new Config("userdata.yml", plugin.getDataFolder().getPath());
        userData.setReloadSettings(ReloadSettings.MANUALLY);
    }

    /**
     * Retrieve the config configuration.
     *
     * @return Config
     */
    public Config getUserData() {
        return userData;
    }
}
