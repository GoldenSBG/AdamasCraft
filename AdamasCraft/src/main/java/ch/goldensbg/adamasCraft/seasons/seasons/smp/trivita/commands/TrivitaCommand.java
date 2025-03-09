package ch.goldensbg.adamasCraft.seasons.smp.trivita.commands;

import ch.goldensbg.adamasCraft.seasons.smp.trivita.LivesManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrivitaCommand implements CommandExecutor {

    private final LivesManager livesManager;
    private final int DEFAULT_LIVES = 3;

    public TrivitaCommand(LivesManager livesManager) {
        this.livesManager = livesManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cBenutzung: /trivita <overview|set|remove|revive>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "overview":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cNur Spieler können dieses Menü öffnen.");
                    return true;
                }
                Player player = (Player) sender;
                // Placeholder für GUI
                player.sendMessage("§7[§bTrivita§7] §aÜbersicht wird bald verfügbar sein!");
                return true;

            case "set":
                if (args.length != 3) {
                    sender.sendMessage("§cBenutzung: /trivita set <Spieler> <Anzahl>");
                    return true;
                }
                return handleSet(sender, args[1], args[2]);

            case "remove":
                if (args.length != 3) {
                    sender.sendMessage("§cBenutzung: /trivita remove <Spieler> <Anzahl>");
                    return true;
                }
                return handleRemove(sender, args[1], args[2]);

            case "revive":
                if (args.length != 2) {
                    sender.sendMessage("§cBenutzung: /trivita revive <Spieler>");
                    return true;
                }
                return handleRevive(sender, args[1]);

            default:
                sender.sendMessage("§cUnbekannter Unterbefehl. Verfügbare: overview, set, remove, revive");
                return true;
        }
    }

    private boolean handleSet(CommandSender sender, String name, String amountStr) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendMessage("§cSpieler nicht gefunden.");
            return true;
        }

        try {
            int amount = Integer.parseInt(amountStr);
            livesManager.setLives(target.getUniqueId(), amount);
            sender.sendMessage("§aLeben gesetzt: §e" + amount + " §afür §b" + target.getName());
        } catch (NumberFormatException e) {
            sender.sendMessage("§cUngültige Zahl.");
        }
        return true;
    }

    private boolean handleRemove(CommandSender sender, String name, String amountStr) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendMessage("§cSpieler nicht gefunden.");
            return true;
        }

        try {
            int amount = Integer.parseInt(amountStr);
            int current = livesManager.getLives(target.getUniqueId());
            int result = Math.max(current - amount, 0);
            livesManager.setLives(target.getUniqueId(), result);
            sender.sendMessage("§c" + amount + " Leben entfernt. §7Neuer Stand: §e" + result);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cUngültige Zahl.");
        }
        return true;
    }

    private boolean handleRevive(CommandSender sender, String name) {
        Player target = Bukkit.getPlayer(name);
        if (target == null) {
            sender.sendMessage("§cSpieler nicht gefunden oder offline.");
            return true;
        }

        livesManager.revive(target.getUniqueId(), DEFAULT_LIVES);
        sender.sendMessage("§a" + target.getName() + " wurde wiederbelebt mit §e" + DEFAULT_LIVES + " Leben.");
        return true;
    }
}
