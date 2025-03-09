package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.communityserver.commands.SettingsCommand;
import ch.goldensbg.communityserver.menus.SettingsMenu;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e){
        SettingsMenu.getInstance().settingsMenuLoadConfig();
        if (e.getTo().getWorld().getEnvironment() == World.Environment.THE_END){
            boolean allowEnd = SettingsMenu.getInstance().getConfig().getBoolean("allowEnd");
            if (!allowEnd) e.setCancelled(true);
            return;
        }
        if (e.getTo().getWorld().getEnvironment() == World.Environment.NETHER){
            boolean allowNether = SettingsMenu.getInstance().getConfig().getBoolean("allowNether");
            if (!allowNether) e.setCancelled(true);
        }
    }
}