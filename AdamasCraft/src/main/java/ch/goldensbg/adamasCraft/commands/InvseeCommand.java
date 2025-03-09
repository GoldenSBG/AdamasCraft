package ch.goldensbg.adamasCraft.commands;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InvseeCommand implements CommandExecutor {
    public static Component NoPerms = MessageUtil.getInstance().parseMessage(AdamasCraft.getInstance().getConfig().getString("Moderation.NoPerms"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component usage = miniMessage.deserialize(" <gradient:aqua:#ADD8E6>Usage: /invsee <player>");
        Component off = miniMessage.deserialize(" <gradient:red:dark_red>Dieser Spieler ist nicht online!");

        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }
        if (sender.hasPermission("communitysystem.admin.command.invsee")) {
            if (args.length != 1) {
                sender.sendMessage(usage);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(off);
                return true;
            }

            Player player = (Player) sender;
            PlayerInventory targetInventory = target.getInventory();


            player.openInventory(targetInventory);

            ItemStack[] armorContents = targetInventory.getArmorContents();
            for (int i = 0; i < armorContents.length; i++) {
                ItemStack armorItem = armorContents[i];
                player.getInventory().setItem(i + 36, armorItem); // Verschiebe die Rüstung auf die Inventarplätze 36 bis 39
            }
            ItemStack offHandItem = targetInventory.getItemInOffHand();
            player.getInventory().setItem(40, offHandItem); // Verschiebe den Offhand-Gegenstand auf den Inventarplatz 40

        } else {
            sender.sendMessage(NoPerms);
        }
        return true;
    }
}