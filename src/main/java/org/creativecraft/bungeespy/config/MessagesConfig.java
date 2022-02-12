package org.creativecraft.bungeespy.config;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.creativecraft.bungeespy.BungeeSpyPlugin;

public class MessagesConfig {
    private final BungeeSpyPlugin plugin;
    private Config messages;

    /**
     * Initialize the messages config instance.
     *
     * @param plugin BungeeSpyPlugin
     */
    public MessagesConfig(BungeeSpyPlugin plugin) {
        this.plugin = plugin;

        registerConfig();
    }

    /**
     * Register the messages configuration.
     */
    public void registerConfig() {
        messages = new Config("messages.yml", plugin.getDataFolder().getPath());
        messages.setReloadSettings(ReloadSettings.MANUALLY);

        messages.setDefault("messages.generic.prefix", "&a&lBungee&fSpy &8>&f ");
        messages.setDefault("messages.generic.message", "&7[&8&l✚&7]&7 {0} executed {1}");

        messages.setDefault("messages.toggle.on", "You are now &aspying&f on commands network-wide.");
        messages.setDefault("messages.toggle.off", "You are no longer &aspying&f on commands network-wide.");
        messages.setDefault("messages.toggle.description", "Toggle network-wide command spying.");

        messages.setDefault("messages.list.format", "Current spies: {0}");
        messages.setDefault("messages.list.delimiter", "&7,&f");
        messages.setDefault("messages.list.none", "None");
        messages.setDefault("messages.list.description", "List online players with spy enabled.");

        messages.setDefault("messages.reload.success", "Plugin has been &asuccessfully&f reloaded.");
        messages.setDefault("messages.reload.failed", "Plugin &cfailed&f to reload.");
        messages.setDefault("messages.reload.description", "Reload the plugin configuration.");

        messages.setDefault("messages.help.header", "&a&m+&8&m                           &a&l Bungee&fSpy &8&m                           &a&m+");
        messages.setDefault("messages.help.format", "&8➝ &a/{command} &7{parameters} &f- {description}");
        messages.setDefault("messages.help.footer", "&a&m+&8&m                                                                        &a&m+");
        messages.setDefault("messages.help.description", "View the plugin help.");
    }

    /**
     * Retrieve the messages configuration.
     *
     * @return Config
     */
    public Config getMessages() {
        return messages;
    }
}


