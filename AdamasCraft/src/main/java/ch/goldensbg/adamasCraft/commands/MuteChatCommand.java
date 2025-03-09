package ch.goldensbg.adamasCraft.commands;


import ch.goldensbg.communityserver.Main;
import ch.goldensbg.communityserver.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class MuteChatCommand implements CommandExecutor {
    private boolean chatMuted;
    private List<String> bypassList;
    public static Component NoPerms = MessageUtil.getInstance().parseMessage(Main.Prefix + Main.getInstance().getConfig().getString("Moderation.NoPerms"));

    public MuteChatCommand() {
        chatMuted = false;
        bypassList = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("communitysystem.admin.command.mutechat")) {
            player.sendMessage(NoPerms);
            return true;
        }

        if (chatMuted) {
            chatMuted = false;
            Bukkit.broadcast(MessageUtil.getInstance().parseMessage(Main.Prefix + "Chat has been unmuted."));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("bypass")) {
            if (player.hasPermission("communitysystem.admin.mutechat.bypass")) {
                player.sendMessage(MessageUtil.getInstance().parseMessage(Main.Prefix + "You are now bypassing chat mute."));
                bypassList.add(player.getName());
                return true;
            } else {
                player.sendMessage(NoPerms);
                return true;
            }
        }

        chatMuted = true;
        Bukkit.broadcast(MessageUtil.getInstance().parseMessage(Main.Prefix + "Chat has been muted."));
        return true;
    }

    public boolean isChatMuted() {
        return chatMuted;
    }

    public boolean canBypass(Player player) {
        return bypassList.contains(player.getName());
    }
}