package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.MessageUtil;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.util.List;

public class MaintenanceListener implements Listener {
    private YamlFile maintenanceList;


    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        this.maintenanceList = new YamlFile(AdamasCraft.getInstance(), "MaintenanceList.yml");;
        Player player = event.getPlayer();

        List<String> playerList = maintenanceList.getStringList("ByPass");

        if (MaintenanceCommand.getInstance().isMaintenanceEnabled()) {
            if (!player.hasPermission("communitysystem.admin.maintenance.bypass") || !playerList.contains(player.getName()) && !player.isOp()) {
                event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, MessageUtil.getInstance().parseMessage("<gradient:gold:yellow>AdamasCraft \nDer Server ist in Wartungsarbeiten! " +
                        "\nErfahre über den Discord, wann der Server wieder erreichbar ist: " +
                        "\n\ndsc.gg/goldenmember"));
            }
        }
    }
}