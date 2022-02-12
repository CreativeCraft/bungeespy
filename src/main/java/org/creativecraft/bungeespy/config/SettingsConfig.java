package org.creativecraft.bungeespy.config;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.creativecraft.bungeespy.BungeeSpyPlugin;

import java.util.Arrays;

public class SettingsConfig {
    private final BungeeSpyPlugin plugin;
    private Config config;

    /**
     * Initialize the settings config instance.
     *
     * @param plugin BungeeSpyPlugin
     */
    public SettingsConfig(BungeeSpyPlugin plugin) {
        this.plugin = plugin;

        registerConfig();
    }

    /**
     * Register the config configuration.
     */
    public void registerConfig() {
        config = new Config("config.yml", plugin.getDataFolder().getPath());
        config.setReloadSettings(ReloadSettings.MANUALLY);

        config.setDefault("command", "bungeespy");
        config.setDefault("show-own-commands", true);
        config.setDefault("show-worldedit", true);
        config.setDefault("show-empty", true);
        config.setDefault("excluded-servers", Arrays.asList("example"));
        config.setDefault("blacklist", Arrays.asList("/example"));
    }

    /**
     * Retrieve the config configuration.
     *
     * @return Config
     */
    public Config getConfig() {
        return config;
    }
}
