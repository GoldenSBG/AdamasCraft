package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.listeners;

import ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerDeathListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        Player killer = deceased.getKiller();

        if (killer != null) {
            // Add a heart to the killer
            double killerHealth = Math.min(killer.getMaxHealth() + 2.0, 40.0);
            killer.setMaxHealth(killerHealth);
            killer.setHealth(killerHealth);

            // Remove a heart from the deceased
            double deceasedHealth = deceased.getMaxHealth() - 2.0;

            if (deceasedHealth <= 0) {
                // Ban the player and save inventory
                playerDataManager.banPlayer(deceased);
                playerDataManager.saveInventory(deceased);
                event.getDrops().clear();

                // Drop player's head
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                head.getItemMeta().setDisplayName(ChatColor.RED + deceased.getName() + "'s Kopf");
                deceased.getWorld().dropItemNaturally(deceased.getLocation(), head);
            } else {
                deceased.setMaxHealth(deceasedHealth);
                deceased.setHealth(deceasedHealth);
            }
        }
    }
}