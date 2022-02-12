package org.creativecraft.bungeespy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.creativecraft.bungeespy.BungeeSpyPlugin;

public class EventListener implements Listener {
    private final BungeeSpyPlugin plugin;

    /**
     * Initialize the event listener instance.
     */
    public EventListener(BungeeSpyPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Sends server and network commands to admins.
     *
     * @param event The chat event.
     */
    @EventHandler
    public void onChatEvent(ChatEvent event) {
        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();

        if (!event.isCommand() && !event.isProxyCommand()) {
            return;
        }

        String command = event.getMessage().split(" ")[0].toLowerCase();

        if (plugin.getConfig().getStringList("excluded-servers").contains(sender.getServer().getInfo().getName())) {
            return;
        }

        if (command.startsWith("//") && !plugin.getConfig().getBoolean("show-worldedit")) {
            return;
        }

        if (command.equals("/") && !plugin.getConfig().getBoolean("show-empty")) {
            return;
        }

        if (plugin.getConfig().getList("blacklist").contains(command)) {
            return;
        }

        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            if (player.hasPermission("bungeespy.use") && plugin.getSpyManager().isSpy(player.getUniqueId())) {
                if (player.getUniqueId() == sender.getUniqueId() && !plugin.getConfig().getBoolean("show-own-commands")) {
                    continue;
                }

                plugin.sendRawMessage(
                    player,
                    plugin.localize("messages.generic.message")
                        .replace("{0}", player.getName())
                        .replace("{1}", event.getMessage())
                );
            }
        }
    }
}
