package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.FormatTime;
import ch.goldensbg.adamasCraft.utils.PlaytimeTracker;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScoreboardListener implements Listener {

    @Getter
    private boolean scoreboardEnabled = true;
    private FileConfiguration config;
    private Map<String, Integer> deaths;
    private final YamlFile scoreboardYaml, playerStatsYaml;

    public ScoreboardListener(FileConfiguration config) {
        this.config = config;
        this.deaths = new HashMap<>();

        // Lade die Scoreboard Design-Konfiguration
        this.scoreboardYaml = new YamlFile(AdamasCraft.getInstance(), "Scoreboard.yml");
        this.playerStatsYaml = new YamlFile(AdamasCraft.getInstance(), "playerstats.yml");

        // Lade Scoreboard-Daten aus der YML-Datei
        this.scoreboardYaml.load();
        this.playerStatsYaml.load();

        startScoreboardUpdateTask();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (scoreboardEnabled) {
            Player player = event.getPlayer();
            String playerName = player.getName();
            PlaytimeTracker.startTracking(player); // Startet das Zählen der Spielzeit für den Spieler

            // Tode initialisieren, wenn sie noch nicht existieren
            if (!deaths.containsKey(playerName)) {
                deaths.put(playerName, 0);
            }

        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlaytimeTracker.stopTracking(player); // Stoppt das Zählen der Spielzeit für den Spieler
    }

    private void startScoreboardUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(AdamasCraft.getInstance(), 0, 20 * 5); // Alle 5 Sekunden aktualisieren
    }

    private void updateScoreboard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("stats", "dummy", getFormattedString("scoreboard_title"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        LuckPerms luckPerms = AdamasCraft.getInstance().luckPermsapi;
        String playerName = player.getName();

        String rank = getFormattedString("rank_text").replace("%rank%", luckPerms.getUserManager().getUser(playerName).getPrimaryGroup());
        String deathsText = getFormattedString("deaths_text").replace("%deaths%", String.valueOf(deaths.getOrDefault(playerName, 0)));
        int playtimeInSeconds = PlaytimeTracker.getPlaytime(player); // Holen der Spielzeit in Sekunden
        // Spielzeit formatieren
        String formattedPlaytime = FormatTime.toFormatted(playtimeInSeconds);
        String playtimeText = getFormattedString("playtime_text").replace("%playtime%", formattedPlaytime);

        // Scoreboard-Einträge hinzufügen
        objective.getScore(rank).setScore(5);
        objective.getScore(deathsText).setScore(4);
        objective.getScore(playtimeText).setScore(3);

        player.setScoreboard(scoreboard);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();

        // Tode erhöhen
        deaths.put(playerName, deaths.getOrDefault(playerName, 0) + 1);
        savePlayerStats(playerName);
    }


    private void savePlayerStats(String playerName) {
        // Tode speichern
        playerStatsYaml.set(playerName + ".deaths", deaths.getOrDefault(playerName, 0));

        // Speichern der YML-Datei
        playerStatsYaml.save();
    }

    private String getFormattedString(String path) {
        String value = scoreboardYaml.getString(path, "");
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    public void setScoreboardEnabled(boolean enabled) {
        this.scoreboardEnabled = enabled;
    }

}