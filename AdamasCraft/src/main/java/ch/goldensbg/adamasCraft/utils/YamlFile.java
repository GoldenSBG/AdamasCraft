package ch.goldensbg.adamasCraft.utils;

import ch.goldensbg.adamasCraft.AdamasCraft;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class YamlFile {
    private static final Set<YamlFile> REGISTERED_FILES = new HashSet<>();

    private final AdamasCraft plugin;
    private final String fileName;
    @Getter
    private final File file;
    @Getter
    private FileConfiguration config;

    public YamlFile(AdamasCraft plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName.endsWith(".yml") ? fileName : fileName + ".yml";
        this.file = new File(plugin.getDataFolder(), this.fileName);
        createOrLoad();

        REGISTERED_FILES.add(this); // <--- Automatisch registrieren
    }

    private void createOrLoad() {
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                file.createNewFile();
                plugin.getLogger().info("[YamlFile] Erstellt: " + fileName);
            } catch (IOException e) {
                plugin.getLogger().severe("[YamlFile] Fehler beim Erstellen von " + fileName);
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("[YamlFile] Fehler beim Speichern von " + fileName);
            e.printStackTrace();
        }
    }

    public void load() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    // Zusätzliche Methoden für das Abrufen von Werten aus der YML-Datei:
    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }

    public List<String> getStringList(String path) {
        // Wir holen die Liste von Objekten unter dem angegebenen Pfad
        List<?> list = config.getList(path);

        // Wenn die Liste null ist, eine leere Liste zurückgeben
        if (list == null) {
            return new ArrayList<>();
        } else {
            List<String> result = new ArrayList<>();

            // Gehe jedes Element in der Liste durch
            for (Object object : list) {
                // Falls das Element vom Typ String ist, direkt zur Ergebnisliste hinzufügen
                if (object instanceof String) {
                    result.add((String) object);
                } else {
                    // Wenn das Element kein String ist, es in einen String umwandeln und zur Ergebnisliste hinzufügen
                    result.add(String.valueOf(object));
                }
            }

            return result;
        }
    }



    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    // STATIC METHOD – alle Dateien speichern
    public static void saveAll() {
        for (YamlFile yamlFile : REGISTERED_FILES) {
            yamlFile.save();
        }
    }

    // Optional: alle neu laden
    public static void reloadAll() {
        for (YamlFile yamlFile : REGISTERED_FILES) {
            yamlFile.reload();
        }
    }
}
