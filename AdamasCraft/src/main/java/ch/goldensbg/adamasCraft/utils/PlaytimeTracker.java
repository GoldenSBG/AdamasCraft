package ch.goldensbg.adamasCraft.utils;

import ch.goldensbg.adamasCraft.AdamasCraft;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PlaytimeTracker {
    private static Map<Player, Integer> playtimes = new HashMap<>();
    
    public static void startTracking(final Player player) {
        if (playtimes.containsKey(player)) {
            return; // Falls die Zeit bereits für diesen Spieler läuft, nichts tun
        }
        
        playtimes.put(player, 0); // Initialisiere die Zeit auf 0

        // Zeit hochzählen
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    int currentTime = playtimes.get(player);
                    playtimes.put(player, currentTime + 1); // Erhöhe die Spielzeit um 1 Sekunde
                }
            }
        }.runTaskTimer(AdamasCraft.getInstance(), 0L, 20L); // alle 20 Ticks (1 Sekunde)
    }

    public static int getPlaytime(Player player) {
        return playtimes.getOrDefault(player, 0); // Gibt die Spielzeit in Sekunden zurück
    }

    public static void stopTracking(Player player) {
        playtimes.remove(player); // Stoppe das Tracking des Spielers
    }
}
