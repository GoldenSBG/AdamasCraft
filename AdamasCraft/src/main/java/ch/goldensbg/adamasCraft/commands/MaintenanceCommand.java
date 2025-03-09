package ch.goldensbg.adamasCraft.commands;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.MessageUtil;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MaintenanceCommand implements CommandExecutor {

    @Getter
    private static final MaintenanceCommand instance = new MaintenanceCommand();
    private YamlFile maintenanceList;




    public MaintenanceCommand() {
        this.maintenanceList = new YamlFile(AdamasCraft.getInstance(), "MaintenanceList.yml");
        this.maintenanceList.load();
    }


    public void MaitenanceListsave() {
        List<String> bypassedPlayers = maintenanceList.getStringList("ByPass");
        maintenanceList.set("ByPass", bypassedPlayers);
        maintenanceList.save();
        AdamasCraft.getInstance().getLogger().info("Spielerliste erfolgreich gespeichert.");
    }

    public static Component NoPerms = MessageUtil.getInstance().parseMessage(AdamasCraft.getInstance().getConfig().getString("Moderation.NoPerms"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("communitysystem.admin.command.maintenance")) {
                if (args.length < 1) {
                    player.sendMessage(MessageUtil.getInstance().parseMessage("<aqua>Bitte gib eine Aktion an: <on/off> | <add/remove> <player></aqua>"));
                    return true;
                }
                String action = args[0];
                switch (action) {
                    case "add":
                        if (args.length < 2) {
                            sender.sendMessage(MessageUtil.getInstance().parseMessage("<aqua>Bitte gib den Namen des Spielers an, den du hinzufügen möchtest."));
                            return false;
                        }
                        String targetName = args[1];
                        List<String> playerList = maintenanceList.getStringList("ByPass");
                        if (!playerList.contains(targetName)) {
                            playerList.add(targetName);
                            maintenanceList.set("ByPass", playerList);
                            MaitenanceListsave();
                            sender.sendMessage("Spieler " + targetName + " wurde zur Liste hinzugefügt.");
                        } else {
                            sender.sendMessage("Spieler " + targetName + " ist bereits in der Liste.");
                        }
                        break;

                    case "remove":
                        if (args.length < 2) {
                            sender.sendMessage(MessageUtil.getInstance().parseMessage("<aqua>Bitte gib den Namen des Spielers an, den du entfernen möchtest."));
                            return false;
                        }
                        String targetToRemove = args[1];
                        List<String> playerListToRemove = maintenanceList.getStringList("ByPass");
                        if (playerListToRemove.contains(targetToRemove)) {
                            playerListToRemove.remove(targetToRemove);
                            maintenanceList.set("ByPass", playerListToRemove);
                            MaitenanceListsave();
                            sender.sendMessage("Spieler " + targetToRemove + " wurde von der Liste entfernt.");
                        } else {
                            sender.sendMessage("Spieler " + targetToRemove + " ist nicht in der Liste.");
                        }
                        break;

                    case "on":
                        Bukkit.getOnlinePlayers().stream().forEach(players -> {
                                    players.kick(MessageUtil.getInstance().parseMessage("\n\n<green>Server ist wurde in Wartungsarbeiten versetzt!</green>" +
                                            "\n<yellow>Bitte habe geduld. Die Wartungsarbeiten werden bald vorüber sein!"));
                                });
                        maintenanceList.set("maintenanceEnabled", true);
                        MaitenanceListsave();
                        sender.sendMessage("Maintenance mode enabled.");
                        break;

                    case "off":
                        maintenanceList.set("maintenanceEnabled", false);
                        MaitenanceListsave();
                        sender.sendMessage("Maintenance mode disabled.");
                    default:
                        break;
                }


            } else {
                sender.sendMessage(NoPerms);
            }
        } else {
            // Wenn der Befehl von der Konsole ausgeführt wird
            if (!(sender instanceof Player)) {
                AdamasCraft server = AdamasCraft.getInstance();
                if (args.length == 0) {
                    sender.sendMessage("Bitte gib eine Aktion an: <on/off> | <add/remove> <player>");
                    return false;
                }
                if (args.length < 1) {
                    sender.sendMessage(MessageUtil.getInstance().parseMessage("<aqua>Bitte gib eine Aktion an: <on/off> | <add/remove> <player></aqua>"));

                    return true;
                }
                String action = args[0];
                switch (action) {
                    case "add":
                        if (args.length < 2) {
                            sender.sendMessage("Bitte gib den Namen des Spielers an, den du hinzufügen möchtest.");
                            return false;
                        }
                        String targetName = args[1];
                        List<String> playerList = maintenanceList.getStringList("ByPass");
                        if (!playerList.contains(targetName)) {
                            playerList.add(targetName);
                            maintenanceList.set("ByPass", playerList);
                            MaitenanceListsave();
                            sender.sendMessage("Spieler " + targetName + " wurde zur Liste hinzugefügt.");
                        } else {
                            sender.sendMessage("Spieler " + targetName + " ist bereits in der Liste.");
                        }
                        break;

                    case "remove":
                        if (args.length < 2) {
                            sender.sendMessage("Bitte gib den Namen des Spielers an, den du entfernen möchtest.");
                            return false;
                        }
                        String targetToRemove = args[1];
                        List<String> playerListToRemove = maintenanceList.getStringList("ByPass");
                        if (playerListToRemove.contains(targetToRemove)) {
                            playerListToRemove.remove(targetToRemove);
                            maintenanceList.set("ByPass", playerListToRemove);
                            MaitenanceListsave();
                            sender.sendMessage("Spieler " + targetToRemove + " wurde von der Liste entfernt.");
                        } else {
                            sender.sendMessage("Spieler " + targetToRemove + " ist nicht in der Liste.");
                        }
                        break;

                    case "on":
                        Bukkit.getOnlinePlayers().stream().forEach(players -> {
                            players.kick(MessageUtil.getInstance().parseMessage("\n\n<green>Server ist wurde in Wartungsarbeiten versetzt!</green>" +
                                    "\n<yellow>Bitte habe geduld. Die Wartungsarbeiten werden bald vorüber sein!"));
                        });
                        maintenanceList.set("maintenanceEnabled", true);
                        MaitenanceListsave();
                        sender.sendMessage("Maintenance mode enabled.");
                        break;

                    case "off":
                        maintenanceList.set("maintenanceEnabled", false);
                        MaitenanceListsave();
                        sender.sendMessage("Maintenance mode disabled.");
                        break;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    public boolean isMaintenanceEnabled() {
        return maintenanceList.getBoolean("maintenanceEnabled");
    }
}