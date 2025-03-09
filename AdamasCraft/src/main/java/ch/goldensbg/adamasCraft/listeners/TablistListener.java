package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.adamasCraft.AdamasCraft;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TablistListener implements Listener {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final List<String> headerAnimation;
    private final List<String> footerAnimation;
    private final String headerColors;
    private final String footerColors;
    private int animationIndex = 0;
    private double offset = 0.0D;

    public TablistListener() {
        FileConfiguration config = AdamasCraft.getInstance().getConfig();
        this.headerAnimation = config.getStringList("tablist.header");
        this.footerAnimation = config.getStringList("tablist.footer");
        this.headerColors = config.getString("tablist.headColors", "#FFFFFF:#FFFFFF");
        this.footerColors = config.getString("tablist.footerColors", "#FFFFFF:#FFFFFF");

        startPeriodicUpdate();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateTablistForPlayer(event.getPlayer());
    }

    private void updateTablistForPlayer(Player player) {
        String header = getAnimatedHeader();
        player.sendPlayerListHeader(miniMessage.deserialize(header));
        String footer = getAnimatedFooter();
        player.sendPlayerListFooter(miniMessage.deserialize(footer));
    }

    private String getAnimatedHeader() {
        offset += 0.01D;
        if (offset > 1.0D)
            offset -= 2.0D;

        String currentFrame = headerAnimation.get(animationIndex);

        animationIndex = (animationIndex + 1) % headerAnimation.size();

        return "<gradient:" + headerColors + ":" + offset + ">" + currentFrame + "</gradient>";
    }

    private String getAnimatedFooter() {
        offset += 0.01D;
        if (offset > 1.0D)
            offset -= 2.0D;

        String currentFrame = footerAnimation.get(animationIndex);

        animationIndex = (animationIndex + 1) % footerAnimation.size();

        return "<gradient:" + footerColors + ":" + offset + ">" + currentFrame + "</gradient>";
    }

    private void updateTablistForAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateTablistForPlayer(player);
        }
    }

    private void startPeriodicUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                updateTablistForAllPlayers();
            }
        }.runTaskTimer(AdamasCraft.getInstance(), 0L, 1L);
    }

}