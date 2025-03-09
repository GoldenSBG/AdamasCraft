package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal;

import ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.menus.MainPlayerMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LifestealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lifesteal.manage")) {
            player.sendMessage("Du hast keine Berechtigung für diesen Befehl!");
            return true;
        }

        MainPlayerMenu.open(player);
        return true;
    }
}
