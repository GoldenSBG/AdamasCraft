package ch.goldensbg.adamasCraft.commands;

import ch.goldensbg.communityserver.Main;
import ch.goldensbg.communityserver.utils.MessageConfig;
import ch.goldensbg.communityserver.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class VanishCommand implements CommandExecutor {
    MiniMessage miniMessage = MiniMessage.miniMessage();
    Component onv = miniMessage.deserialize(" <gradient:green:#90EE90>Du bist nun im Vanish!");
    Component offv = miniMessage.deserialize(" <gradient:red:dark_red>Du bist nun nicht mehr im Vanish!");
    public static Component NoPerms = MessageUtil.getInstance().parseMessage(Main.Prefix + Main.getInstance().getConfig().getString("Moderation.NoPerms"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.Prefix + "Dieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("communitysystem.admin.command.vanish")) {
            if (!player.hasMetadata("vanish")) {
                player.setMetadata("vanish", new FixedMetadataValue(Main.getInstance(), true));
                player.sendMessage(MessageUtil.getInstance().parseMessage(Main.Prefix + "<gradient:green:#90EE90>Du bist nun im Vanish!"));
                Main.getInstance().getServer().broadcastMessage(ChatColor.RED + "- " + ChatColor.GOLD + player.getName());
                player.getServer().getOnlinePlayers().forEach(p -> p.hidePlayer(Main.getInstance(), player));
            } else {
                player.removeMetadata("vanish", Main.getInstance());
                player.sendMessage(MessageUtil.getInstance().parseMessage(Main.Prefix + "<gradient:red:dark_red>Du bist nun nicht mehr im Vanish!"));
                Main.getInstance().getServer().broadcastMessage(ChatColor.GREEN + "+ " + ChatColor.GOLD + player.getName());
                player.getServer().getOnlinePlayers().forEach(p -> p.showPlayer(Main.getInstance(), player));
            }
        } else {
            sender.sendMessage(NoPerms);
        }

        return true;
    }
}