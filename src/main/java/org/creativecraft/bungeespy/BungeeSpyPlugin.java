package org.creativecraft.bungeespy;

import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.CommandReplacements;
import co.aikar.commands.MessageType;
import de.leonhard.storage.Config;
import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.MetricsLite;
import org.creativecraft.bungeespy.commands.BungeeSpyCommand;
import org.creativecraft.bungeespy.config.MessagesConfig;
import org.creativecraft.bungeespy.config.SettingsConfig;
import org.creativecraft.bungeespy.config.UserDataConfig;
import org.creativecraft.bungeespy.listener.EventListener;

public final class BungeeSpyPlugin extends Plugin {
    public static BungeeSpyPlugin plugin;
    private SettingsConfig settingsConfig;
    private MessagesConfig messagesConfig;
    private UserDataConfig userDataConfig;
    private SpyManager spyManager;

    /**
     * Enable the plugin.
     */
    @Override
    public void onEnable() {
        plugin = this;
        spyManager = new SpyManager(this);

        registerConfigs();
        registerListeners();
        registerCommands();

        new MetricsLite(this, 14268);
    }

    /**
     * Load the plugin.
     */
    @Override
    public void onLoad() {
        //
    }

    /**
     * Disable the plugin.
     */
    @Override
    public void onDisable() {
        //
    }

    /**
     * Register the plugin configuration.
     */
    public void registerConfigs() {
        settingsConfig = new SettingsConfig(this);
        messagesConfig = new MessagesConfig(this);
        userDataConfig = new UserDataConfig(this);
    }

    /**
     * Register the event listener.
     */
    public void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new EventListener(this));
    }


    /**
     * Register the plugin commands.
     */
    public void registerCommands() {
        BungeeCommandManager commandManager = new BungeeCommandManager(this);
        CommandReplacements replacements = commandManager.getCommandReplacements();

        replacements.addReplacement("bungeespy", getConfig().getString("command"));

        commandManager.setFormat(MessageType.ERROR, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);
        commandManager.setFormat(MessageType.SYNTAX, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);
        commandManager.setFormat(MessageType.HELP, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);
        commandManager.setFormat(MessageType.INFO, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);

        commandManager.registerCommand(new BungeeSpyCommand(this));
        commandManager.enableUnstableAPI("help");
    }

    /**
     * Retrieve the plugin configuration.
     *
     * @return Config
     */
    public Config getConfig() {
        return settingsConfig.getConfig();
    }

    /**
     * Retrieve the messages configuration.
     *
     * @return Config
     */
    public Config getMessages() {
        return messagesConfig.getMessages();
    }

    /**
     * Retrieve the user data configuration.
     *
     * @return Config
     */
    public Config getUserData() {
        return userDataConfig.getUserData();
    }

    /**
     * Retrieve the spy manager instance.
     *
     * @return SpyManager
     */
    public SpyManager getSpyManager() {
        return spyManager;
    }

    /**
     * Retrieve a localized message.
     *
     * @param  key The locale key.
     * @return String
     */
    public String localize(String key) {
        return ChatColor.translateAlternateColorCodes(
            '&',
            messagesConfig.getMessages().getString(key)
        );
    }

    /**
     * Send a message formatted with MineDown.
     *
     * @param sender The command sender.
     * @param value  The message.
     */
    public void sendMessage(CommandSender sender, String value) {
        sender.sendMessage(
            MineDown.parse(messagesConfig.getMessages().getString("messages.generic.prefix") + value)
        );
    }

    /**
     * Send a raw message formatted with MineDown.
     *
     * @param sender The command sender.
     * @param value  The message.
     */
    public void sendRawMessage(CommandSender sender, String value) {
        sender.sendMessage(
            MineDown.parse(value)
        );
    }
}
