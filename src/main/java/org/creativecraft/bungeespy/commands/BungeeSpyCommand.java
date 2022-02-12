package org.creativecraft.bungeespy.commands;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.HelpEntry;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.creativecraft.bungeespy.BungeeSpyPlugin;
import net.md_5.bungee.api.plugin.Listener;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@CommandAlias("%bungeespy")
@Description("Simple network-wide command spying for BungeeCord.")
public final class BungeeSpyCommand extends BaseCommand implements Listener {
    private final BungeeSpyPlugin plugin;

    public BungeeSpyCommand(BungeeSpyPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Retrieve the plugin help.
     *
     * @param sender The command sender.
     */
    @HelpCommand
    @Syntax("[page]")
    @Description("View the plugin help.")
    @CommandCompletion("@nothing")
    public void onHelp(CommandSender sender, CommandHelp help) {
        plugin.sendRawMessage(sender, plugin.localize("messages.help.header"));

        for (HelpEntry entry : help.getHelpEntries()) {
            plugin.sendRawMessage(
                sender,
                plugin.localize("messages.help.format")
                    .replace("{command}", entry.getCommand())
                    .replace("{parameters}", entry.getParameterSyntax())
                    .replace("{description}", plugin.localize("messages." + entry.getCommand().split("\\s+")[1] + ".description"))
            );
        }

        plugin.sendRawMessage(sender, plugin.localize("messages.help.footer"));
    }

    /**
     * Toggle network-wide command spying.
     *
     * @param player The proxied player.
     */
    @Default
    @Subcommand("toggle")
    @CommandPermission("bungeespy.use")
    @Description("Toggle network-wide command spying.")
    public void onToggle(ProxiedPlayer player) {
        if (plugin.getSpyManager().isSpy(player.getUniqueId())) {
            plugin.getSpyManager().removeSpy(player.getUniqueId());
            plugin.sendMessage(player, plugin.localize("messages.toggle.off"));

            return;
        }

        plugin.getSpyManager().addSpy(player.getUniqueId());
        plugin.sendMessage(player, plugin.localize("messages.toggle.on"));
    }

    /**
     * List players who have spy enabled.
     *
     * @param sender The command sender.
     */
    @Subcommand("list")
    @CommandPermission("bungeespy.list")
    @Description("List players who have spy enabled.")
    public void onList(CommandSender sender) {
        Set<String> spies = new HashSet<String>();

        plugin.getSpyManager().getSpies().forEach(spy -> {
            ProxiedPlayer player = plugin.getProxy().getPlayer(UUID.fromString(spy));

            if (player == null) {
                return;
            }

            spies.add(player.getName());
        });

        plugin.sendMessage(
            sender,
            plugin.localize("messages.list.format")
                .replace("{0}", spies.size() != 0 ?
                    String.join(plugin.localize("messages.list.delimiter"), spies) :
                    plugin.localize("messages.list.none")
                )
        );
    }

    /**
     * Reload the plugin configuration.
     *
     * @param sender The command sender.
     */
    @Subcommand("reload")
    @CommandPermission("bungeespy.admin")
    @Description("Reload the plugin configuration.")
    public void onReload(CommandSender sender) {
        try {
            plugin.getConfig().forceReload();
            plugin.getMessages().forceReload();

            plugin.sendMessage(sender, plugin.localize("messages.reload.success"));
        } catch (Exception e) {
            plugin.sendMessage(sender, plugin.localize("messages.reload.failed"));
        }
    }
}
