package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.menus;

import ch.goldensbg.adamasCraft.AdamasCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        // Haupt-GUI Verarbeitung
        if (event.getView().getTitle().equals("Spieler-Whitelist")) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

            String playerName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            Player target = AdamasCraft.getInstance().getServer().getPlayer(playerName);

            if (target != null) {
                PlayerManagementMenu.open(player, target);
            } else {
                player.sendMessage(ChatColor.RED + "Spieler nicht gefunden!");
            }
        }

        // Spieler-Verwaltungs-GUI Verarbeitung
        if (event.getView().getTitle().contains("Verwalte")) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

            String action = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            String targetName = event.getView().getTitle().substring(9);
            Player target = Bukkit.getPlayerExact(targetName);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Spieler ist nicht verfügbar.");
                return;
            }

            switch (action) {
                case "Herz hinzufügen":
                    target.setMaxHealth(target.getMaxHealth() + 2);
                    player.sendMessage(ChatColor.GREEN + target.getName() + " wurde ein Herz hinzugefügt.");
                    break;
                case "Herz entfernen":
                    if (target.getMaxHealth() > 2) {
                        target.setMaxHealth(target.getMaxHealth() - 2);
                        player.sendMessage(ChatColor.RED + target.getName() + " wurde ein Herz entfernt.");
                    } else {
                        player.sendMessage(ChatColor.RED + target.getName() + " kann keine weiteren Herzen verlieren!");
                    }
                    break;
                case "Herz reseten":
                    double standardHealth = 20.0;
                    if (target.getMaxHealth() != standardHealth) {
                        target.setMaxHealth(standardHealth);
                        player.sendMessage(ChatColor.GREEN + target.getName() + " wurde auf den Standardwert der Herzen zurückgesetzt.");
                    } else {
                        player.sendMessage(ChatColor.RED + target.getName() + " hat bereits den Standardwert der Herzen.");
                    }
                    break;
                case "Spieler bannen":
                    target.kickPlayer(ChatColor.RED + "Du wurdest gebannt!");
                    AdamasCraft.getInstance().getServer().getBanList(org.bukkit.BanList.Type.NAME).addBan(target.getName(), "Gebannt von " + player.getName(), null, null);
                    player.sendMessage(ChatColor.RED + target.getName() + " wurde gebannt.");
                    break;

                case "Spieler entbannen":
                    AdamasCraft.getInstance().getServer().getBanList(org.bukkit.BanList.Type.NAME).pardon(target.getName());
                    player.sendMessage(ChatColor.GREEN + target.getName() + " wurde entbannt.");
                    break;

                case "Inventar ansehen":
                    Inventory inv = Bukkit.createInventory(null, 54, target.getName() + "s Inventar");
                    inv.setContents(target.getInventory().getContents());
                    player.openInventory(inv);
                    break;

                case "Enderchest ansehen":
                    player.openInventory(target.getEnderChest());
                    break;

                case "Standort anzeigen":
                    player.sendMessage(ChatColor.AQUA + target.getName() + " ist bei: " +
                            "X: " + target.getLocation().getBlockX() +
                            ", Y: " + target.getLocation().getBlockY() +
                            ", Z: " + target.getLocation().getBlockZ());
                    break;

                case "Teleportieren":
                    player.teleport(target.getLocation());
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Du wurdest zu " + target.getName() + " teleportiert.");
                    break;

                default:
                    player.sendMessage(ChatColor.RED + "Unbekannte Aktion.");
                    break;
            }
        }
    }
}