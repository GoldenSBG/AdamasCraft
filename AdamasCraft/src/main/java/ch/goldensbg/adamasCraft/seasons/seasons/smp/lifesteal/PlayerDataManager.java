package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private final HashMap<UUID, ItemStack[]> storedInventories = new HashMap<>();

    private YamlFile playerDataFile;

    public PlayerDataManager() {
        this.playerDataFile = new YamlFile(AdamasCraft.getInstance(), "Playerdata_Lifesteal.yml");
        this.playerDataFile.load();
    }

    public void banPlayer(Player player) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "Keine Herzen mehr!", null, "Lifesteal Plugin");
        player.kickPlayer("Du wurdest gebannt! Deine Herzen sind alle.");
    }

    public void unbanPlayer(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        Bukkit.getBanList(BanList.Type.NAME).pardon(offlinePlayer.getName());
    }

    public void saveInventory(Player player) {
        storedInventories.put(player.getUniqueId(), player.getInventory().getContents());
        playerDataFile.set(player.getUniqueId().toString() + ".inventory", player.getInventory().getContents());
        playerDataFile.save();
    }

    public void restoreInventory(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && storedInventories.containsKey(uuid)) {
            player.getInventory().setContents(storedInventories.get(uuid));
            player.setMaxHealth(20.0);
            player.setHealth(20.0);
        }
    }

}