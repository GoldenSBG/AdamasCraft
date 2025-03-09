package ch.goldensbg.adamasCraft.menu.menus;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.listeners.settings.BlockBreakEvent;
import ch.goldensbg.adamasCraft.listeners.settings.OldPvPListener;
import ch.goldensbg.adamasCraft.menu.builder.MenuBuilder;
import ch.goldensbg.adamasCraft.menu.item.ItemBuilder;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsMenu implements Listener {
    private final List<Inventory> pages = new ArrayList<>();
    private Map<Integer, String> headTextures = new HashMap<>();
    private final Map<Integer, String> itemNames = new HashMap<>();

    private YamlFile settingsFile;


    @Getter
    private static final SettingsMenu instance = new SettingsMenu();

    public SettingsMenu() {
        initializeHeadTextures();
        initializeItemNames();
        this.settingsFile = new YamlFile(AdamasCraft.getInstance(), "Playerdata_Lifesteal.yml");
        this.settingsFile.load();

    }


    public void settingsMenuSave() {
        // Hardcore-Modus
        settingsFile.set("hardcore", AdamasCraft.getInstance().getServer().getWorld("world").isHardcore());
        // Behalten von Inventar
        settingsFile.set("keepInventory", AdamasCraft.getInstance().getServer().getWorld("world").getGameRuleValue(GameRule.KEEP_INVENTORY));
        // Regeneration
        settingsFile.set("naturalRegeneration", AdamasCraft.getInstance().getServer().getWorld("world").getGameRuleValue(GameRule.NATURAL_REGENERATION));
        // Raids
        settingsFile.set("disableRaids", AdamasCraft.getInstance().getServer().getWorld("world").getGameRuleValue(GameRule.DISABLE_RAIDS));
        // Phantome
        settingsFile.set("doInsomnia", AdamasCraft.getInstance().getServer().getWorld("world").getGameRuleValue(GameRule.DO_INSOMNIA));
        // Nether
        settingsFile.set("allowNether", settingsFile.getBoolean("allowNether"));
        // Ende
        settingsFile.set("allowEnd", settingsFile.getBoolean("allowEnd"));

        settingsFile.set("timber", settingsFile.getBoolean("timber"));
        settingsFile.set("oldPvP", settingsFile.getBoolean("oldPvP"));

        // Konfigurationsdatei speichern
        settingsFile.save();
        AdamasCraft.getInstance().getLogger().info("Einstellungen (settings.yml) erfolgreich gespeichert.");
    }

    public void openSettingsInventory(Player player, int page) {
        if (page < 0 || page >= pages.size()) {
            page = 0;
        }
        settingsFile.load();
        buildInventory();
        player.openInventory(pages.get(page));
    }

    private void buildInventory() {
        pages.clear();
        int pageIndex = 0;
        int itemCount = 0;
        int slotIndex = 10;

        MenuBuilder menuBuilder = new MenuBuilder("Settings", 7).setClearCenter(true);
        Inventory inventory = menuBuilder.build();

        int totalItems = itemNames.size();  // Get the total number of items from itemNames

        while (itemCount < totalItems) {
            int textureIndex = (itemCount % headTextures.size()) + 1;
            String itemName = itemNames.getOrDefault(itemCount + 1, "No Name");

            // Set item in the current slot and move to the next slot
            inventory.setItem(slotIndex++, new ItemBuilder(Material.PLAYER_HEAD)
                    .setHeadTexture(getHeadTexture(textureIndex))
                    .setName(itemName)
                    .build());

            itemCount++;

            // Add navigation buttons and create a new page if needed
            if (slotIndex >= inventory.getSize() - 9) { // Check if we're reaching the end of the inventory (minus navigation slots)
                addNavigationButtons(inventory, pageIndex);
                pages.add(inventory);
                pageIndex++;
                inventory = menuBuilder.build();
                slotIndex = 9; // Reset slotIndex for the new page
            }
        }

        // If items remain or no pages were added, add them to a final page
        if (itemCount > 0 || pages.isEmpty()) {
            addNavigationButtons(inventory, pageIndex);
            pages.add(inventory);
        }
    }


    private void addNavigationButtons(Inventory inventory, int pageIndex) {
        if (pageIndex > 0) {
            inventory.setItem(18, new ItemBuilder(Material.PLAYER_HEAD)
                    .setHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk2MTcwMjJjZjlhMWQ1YTg2MDZjMDZlMzg5NGMzMTA4NzRkZmFkMjc2OTA3OTNkNjc1NjkwMTY1OGM2ZTA2NCJ9fX0=")
                    .setName("Previous Page")
                    .setCustomModelData(pageIndex - 1)
                    .build());
        }
        inventory.setItem(26, new ItemBuilder(Material.PLAYER_HEAD)
                .setHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE1Y2U4NDQ4YWNiZDhlNjBjOWI2ZTkzZjQwNjJhMjAzYzQzNGFlYzUwNjgwZDlmMGQwMjhiN2MwOTEyNTczOCJ9fX0=")
                .setName("Next Page")
                .setCustomModelData(pageIndex + 1)
                .build());
    }

    private void initializeHeadTextures() {
        headTextures.put(1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2NkYWE1YmYwYmM1NDIwNTBjNzM0YzU4ZjQyODMyMTVjOWE0ZjBmMThjM2RkYWQ4NzE5MTNkYmY2NjdjZTQzMyJ9fX0=");
        headTextures.put(2, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThkYTdlMjU1ZTA5YzhiMzc4ZWM4NmMwYjkyMmZhODY0YzRiMTlkMGU1ZTVkYTRkOGM3M2MyYjU2OWMyMjUwMiJ9fX0=");
        headTextures.put(3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmI4NWY5OWFmN2M1MDczMWRiMGY5MzcwOGMyNDg5OTI0NzdjOTBiNGQyY2Q0ZDE1Zjc3NDU3YzdmNDk5NzBlOSJ9fX0=");
        headTextures.put(4, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ1NDNlM2RmYjUwNDhiNDNjYTNjM2FiY2YwZGY0YjNmY2ZiMTdhOTk4NTRkNzZjZmFhNjdhNjYxZjU2NGZkOSJ9fX0");
        headTextures.put(5, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRlMjJiOWY5YjU2ODMxOGYzNTgyMTYxNGEwODIxYzY0MmViMjM2ZWVlOTBkOTA0MmJkMzllY2EzYTYyZTUzOCJ9fX0=");
        headTextures.put(6, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRmMzI4ZjUwNDQxMjliNWQxZjk2YWZmZDFiOGMwNWJjZGU2YmQ4ZTc1NmFmZjVjNTAyMDU4NWVlZjhhM2RhZiJ9fX0=");
        headTextures.put(7, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQxNjk1ODFlYTdjMzQzNjJiNDZmOWJhMmJkNmNkZjY4NzVjNTRlMjc4ZmJiNjI4ZDFkMzhmMWUwNzUzYjM3MCJ9fX0=");
        headTextures.put(8, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRiYTQ5Mzg0ZGJhN2I3YWNkYjRmNzBlOTM2MWU2ZDU3Y2JiY2JmNzIwY2Y0ZjE2YzJiYjgzZTQ1NTcifX19");
        headTextures.put(9, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc2NTM0MTM1M2MwMjllOWI2NTVmNGY1NzkzMWFlNmFkYzJjN2E3M2M2NTc5NDVkOTQ1YTMwNzY0MWQzNzc4In19fQ==");
        headTextures.put(10, "");
    }

    private void initializeItemNames() {
        itemNames.put(1, "Hardcore");
        itemNames.put(2, "Regeneration");
        itemNames.put(3, "Keepinventory");
        itemNames.put(4, "Raids");
        itemNames.put(5, "Phantoms");
        itemNames.put(6, "Nether");
        itemNames.put(7, "End");
        itemNames.put(8, "Timber");
        itemNames.put(9, "1.8 PvP");
        itemNames.put(10, "Headdrop");
    }

    private String getHeadTexture(int key) {
        return headTextures.getOrDefault(key,"");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Settings")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                String id = event.getCurrentItem().getItemMeta().getDisplayName();
                Player player = (Player) event.getWhoClicked();
                ItemStack currentItem = event.getCurrentItem();
                ItemMeta itemMeta = currentItem.getItemMeta();
                switch(id) {
                    case "Hardcore":
                        AdamasCraft.getInstance().getServer().getWorld(player.getWorld().getName()).setHardcore(!player.getWorld().isHardcore());
                        player.sendMessage("Hardcore wurde " + (player.getWorld().isHardcore() ? "aktiviert" : "deaktiviert"));
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            onlinePlayer.kickPlayer("Hardcore wurde " + (player.getWorld().isHardcore() ? "aktiviert" : "deaktiviert") + "!");
                        }
                        openSettingsInventory(player, 0);
                        break;

                    case "Regeneration":
                        AdamasCraft.getInstance().getServer().getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION,
                                (!player.getWorld().getGameRuleValue(GameRule.NATURAL_REGENERATION)));
                        player.sendMessage("Naturlüche Regeneration wurde " + (player.getWorld().getGameRuleValue(GameRule.NATURAL_REGENERATION) ? "aktiviert" : "deaktiviert"));
                        openSettingsInventory(player, 0);
                        break;

                    case "Keepinventory":
                        AdamasCraft.getInstance().getServer().getWorld("world").setGameRule(GameRule.KEEP_INVENTORY,
                                (!player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY)));
                        player.sendMessage("Keep Inventory wurde " + ((player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) ? "aktiviert" : "deaktiviert")));
                        openSettingsInventory(player, 0);
                        break;

                    case "Raids":
                        AdamasCraft.getInstance().getServer().getWorld("world").setGameRule(GameRule.DISABLE_RAIDS,
                                !player.getWorld().getGameRuleValue(GameRule.DISABLE_RAIDS));
                        player.sendMessage("Raids wurden " + ((player.getWorld().getGameRuleValue(GameRule.DISABLE_RAIDS) ? "deaktiviert" : "aktiviert")));
                        openSettingsInventory(player, 0);
                        break;

                    case "Phantoms":
                        AdamasCraft.getInstance().getServer().getWorld("world").setGameRule(GameRule.DO_INSOMNIA,
                                (player.getWorld().getGameRuleValue(GameRule.DO_INSOMNIA)));
                        player.sendMessage("Phantoms wurden " + ((player.getWorld().getGameRuleValue(GameRule.DO_INSOMNIA) ? "deaktiviert" : "aktiviert")));
                        openSettingsInventory(player, 0);
                        break;

                    case "Nether":
                        boolean isNetherAllowed = settingsFile.getBoolean("allowNether");
                        settingsFile.set("allowNether", !isNetherAllowed);
                        player.sendMessage("Nether wurde " + (!isNetherAllowed ? "aktiviert" : "deaktiviert"));
                        openSettingsInventory(player, 0);
                        settingsMenuSave();
                        break;

                    case "End":
                        boolean isEndAllowed = settingsFile.getBoolean("allowEnd");
                        settingsFile.set("allowEnd", !isEndAllowed);
                        player.sendMessage("End wurde " + (!isEndAllowed ? "aktiviert" : "deaktiviert"));
                        openSettingsInventory(player, 0);
                        settingsMenuSave();
                        break;

                    case "Timber":
                        boolean isTimberEnabled = settingsFile.getBoolean("timber");
                        settingsFile.set("timber", !isTimberEnabled);
                        player.sendMessage("Timber wurde " + (!isTimberEnabled ? "aktiviert" : "deaktiviert"));
                        openSettingsInventory(player, 0);
                        settingsMenuSave();
                        if (isTimberEnabled) {
                            listenerRegistration(new BlockBreakEvent());
                        } else {
                            listenerUnregistration();
                        }
                        break;
                    case "1.8 PvP":
                        boolean isOldPvP = settingsFile.getBoolean("oldPvP");
                        settingsFile.set("oldPvP", !isOldPvP);
                        player.sendMessage("OldPvP wurde " + (!isOldPvP ? "aktiviert" : "deaktiviert"));
                        openSettingsInventory(player, 0);
                        settingsMenuSave();
                        if (isOldPvP) {
                            listenerRegistration(new OldPvPListener());
                        } else {
                            listenerUnregistration();
                        }
                        break;

                    case "Previous Page":
                    case "Next Page":
                        int page = itemMeta.getCustomModelData();
                        openSettingsInventory(player, page);
                        break;
                    case "default":
                        openSettingsInventory(player, 0);
                        break;
                }
            }
        }
    }


    private void listenerRegistration(Listener listener) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(listener, AdamasCraft.getInstance());
    }

    private void listenerUnregistration() {
        Bukkit.shutdown();
    }
}