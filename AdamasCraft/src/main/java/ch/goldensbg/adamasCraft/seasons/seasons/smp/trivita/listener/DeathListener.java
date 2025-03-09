package ch.goldensbg.adamasCraft.seasons.smp.trivita.listener;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.seasons.smp.trivita.LivesManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeathListener implements Listener {

    private final LivesManager livesManager;

    public DeathListener(LivesManager livesManager) {
        this.livesManager = livesManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();

        if (livesManager.isDead(uuid)) return; // Sicherheit

        livesManager.removeLife(uuid);
        int livesLeft = livesManager.getLives(uuid);
        player.sendMessage("§cDu hast ein Leben verloren. Verbleibende Leben: §e" + livesLeft);

        if (livesLeft <= 0) {
            livesManager.markAsDead(uuid);
            Bukkit.getScheduler().runTaskLater(AdamasCraft.getInstance(), () -> {
                player.kickPlayer("§cDu hast alle Leben verloren.");
                Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(player.getName(), "Alle Leben verbraucht.", null, null);
            }, 20L);
        }
    }
}
