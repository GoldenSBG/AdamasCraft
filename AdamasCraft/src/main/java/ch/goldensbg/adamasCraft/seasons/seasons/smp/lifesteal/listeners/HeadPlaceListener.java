package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.listeners;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class HeadPlaceListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public HeadPlaceListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onHeadPlace(PlayerInteractEvent event) {
        if (event.getAction().name().contains("RIGHT_CLICK") &&
                event.getClickedBlock() != null &&
                event.getClickedBlock().getType() == Material.PLAYER_HEAD) {

            ItemStack head = event.getItem();
            if (head != null && head.getType() == Material.PLAYER_HEAD) {
                String playerName = ChatColor.stripColor(head.getItemMeta().getDisplayName().replace("'s Kopf", ""));
                UUID uuid = AdamasCraft.getInstance().getServer().getOfflinePlayer(playerName).getUniqueId();

                // Unban player and restore inventory
                playerDataManager.unbanPlayer(uuid);
                playerDataManager.restoreInventory(uuid);
                event.getPlayer().sendMessage(ChatColor.GREEN + playerName + " wurde wiederbelebt!");
            }
        }
    }
}