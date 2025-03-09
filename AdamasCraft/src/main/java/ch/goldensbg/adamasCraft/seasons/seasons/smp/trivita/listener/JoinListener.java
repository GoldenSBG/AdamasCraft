package ch.goldensbg.adamasCraft.seasons.smp.trivita.listener;

import ch.goldensbg.adamasCraft.seasons.smp.trivita.LivesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private final LivesManager livesManager;

    public JoinListener(LivesManager livesManager) {
        this.livesManager = livesManager;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (livesManager.isDead(uuid)) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§cDu hast alle Leben verloren.");
        }
    }
}
