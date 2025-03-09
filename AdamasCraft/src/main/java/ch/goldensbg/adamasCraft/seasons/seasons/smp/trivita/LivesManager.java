package ch.goldensbg.adamasCraft.seasons.smp.trivita;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class LivesManager {

    private final YamlFile yamlFile;

    public LivesManager(AdamasCraft plugin) {
        this.yamlFile = new YamlFile(plugin, "lives");
    }

    public int getLives(UUID uuid) {
        return yamlFile.getConfig().getInt("players." + uuid + ".lives", 3);
    }

    public void setLives(UUID uuid, int lives) {
        yamlFile.getConfig().set("players." + uuid + ".lives", lives);
        save();
    }

    public boolean isDead(UUID uuid) {
        return "dead".equalsIgnoreCase(yamlFile.getConfig().getString("players." + uuid + ".status"));
    }

    public void markAsDead(UUID uuid) {
        yamlFile.getConfig().set("players." + uuid + ".status", "dead");
        save();
    }

    public void revive(UUID uuid, int lives) {
        yamlFile.getConfig().set("players." + uuid + ".status", "alive");
        yamlFile.getConfig().set("players." + uuid + ".lives", lives);
        save();
    }

    public void removeLife(UUID uuid) {
        int current = getLives(uuid);
        if (current > 0) setLives(uuid, current - 1);
    }

    public void save() {
        yamlFile.save();
    }

    public void reload() {
        yamlFile.reload();
    }
}
