package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManagementMenu {

    public static void open(Player viewer, Player target) {
        // GUI erstellen
        Inventory gui = Bukkit.createInventory(null, 27, "Verwalte " + target.getName());

        // Herz hinzufügen
        gui.setItem(1, createItem(Material.REDSTONE, ChatColor.GREEN + "Herz hinzufügen"));
        // Herz entfernen
        gui.setItem(10, createItem(Material.BONE, ChatColor.RED + "Herz entfernen"));
        // Herzen reseten
        gui.setItem(19, createItem(Material.BONE, ChatColor.RED + "Herz reseten"));

        // Spieler bannen
        gui.setItem(2, createItem(Material.BARRIER, ChatColor.DARK_RED + "Spieler bannen"));

        gui.setItem(11, createItem(Material.WARDEN_SPAWN_EGG, ChatColor.RED + "Adminstration"));

        // Spieler entbannen
        gui.setItem(20, createItem(Material.LIME_DYE, ChatColor.GREEN + "Spieler entbannen"));

        // Inventar anzeigen
        gui.setItem(12, createItem(Material.CHEST, ChatColor.GOLD + "Inventar ansehen"));

        // Enderchest anzeigen
        gui.setItem(13, createItem(Material.ENDER_CHEST, ChatColor.DARK_PURPLE + "Enderchest ansehen"));

        // Koordinaten anzeigen
        gui.setItem(14, createItem(Material.COMPASS, ChatColor.AQUA + "Standort anzeigen"));

        // Teleportieren
        gui.setItem(15, createItem(Material.FIREWORK_ROCKET, ChatColor.LIGHT_PURPLE + "Teleportieren"));

        // GUI öffnen
        viewer.openInventory(gui);
    }

    private static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }

        return item;
    }
}
