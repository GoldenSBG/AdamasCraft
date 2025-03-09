package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class MainPlayerMenu {

    public static void open(Player viewer) {
        // GUI erstellen
        Inventory gui = Bukkit.createInventory(null, 54, "Spieler-Whitelist");

        for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();

            if (meta != null) {
                meta.setOwningPlayer(player);
                meta.setDisplayName(player.getName());
                head.setItemMeta(meta);
            }

            gui.addItem(head);
        }

        viewer.openInventory(gui);
    }
}