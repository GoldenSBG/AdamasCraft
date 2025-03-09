package ch.goldensbg.adamasCraft.ranks;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.menu.builder.MenuBuilder;
import ch.goldensbg.adamasCraft.menu.item.ItemBuilder;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RanksMenu implements Listener {
    LuckPerms luckPerms = AdamasCraft.getInstance().luckPermsapi;

    public void openRanksMenu(Player player) {
        List<Group> groups = new ArrayList<>(luckPerms.getGroupManager().getLoadedGroups());

        int size = Math.max(9, (int) (Math.ceil(groups.size() / 9.0) * 9));

        Inventory inv = new MenuBuilder("Ranks", size / 9).build();

        int slot = 0;
        for (Group group : groups) {
            Color color = Color.fromRGB(
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255));

            int playerCount = (int) Bukkit.getOnlinePlayers().stream()
                    .filter(p -> {
                        UUID uuid = p.getUniqueId();
                        return luckPerms.getUserManager().getUser(uuid) != null
                                && luckPerms.getUserManager().getUser(uuid).getPrimaryGroup().equals(group.getName());
                    }).count();

            inv.setItem(slot, new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setName(group.getName())
                    .setColor(color)
                    .setMiniMessageLore("<gradient:#08FB71:#10DAF6>Spieler in dieser Gruppe: </gradient>" + playerCount)
                    .build());

            slot++;
        }

        player.openInventory(inv);
    }

    public void openGroupMembersMenu(Player player, String groupName) {
        List<Player> groupMembers = Bukkit.getOnlinePlayers().stream()
                .filter(p -> luckPerms.getUserManager().getUser(p.getUniqueId()) != null
                        && luckPerms.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().equals(groupName))
                .collect(Collectors.toList());

        int size = Math.max(9, (int) (Math.ceil(groupMembers.size() / 9.0) * 9));
        Inventory inv = new MenuBuilder(groupName + " Members", size / 9).build();

        int slot = 10;
        for (Player groupMember : groupMembers) {
            inv.setItem(slot, new ItemBuilder(Material.PLAYER_HEAD)
                    .setHead(groupMember.getName())
                    .setName(groupMember.getName())
                    .build());
            slot++;
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Ranks")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.LEATHER_CHESTPLATE) {
                String groupName = clickedItem.getItemMeta().getDisplayName(); // Gruppenname aus dem Item extrahieren
                openGroupMembersMenu(player, groupName);
                //player.sendMessage("Du hast die Gruppe " + clickedItem.getItemMeta().getDisplayName() + " ausgewählt.");
            }
        } else if (event.getView().getTitle().contains(" Members")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE) {
                openRanksMenu(player);
            }
        }
    }
}