package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.adamasCraft.AdamasCraft;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerMotdListener implements Listener {

    MiniMessage miniMessage = MiniMessage.miniMessage();
    FileConfiguration config = AdamasCraft.getInstance().getConfig();

    @EventHandler
    public void onListPing(ServerListPingEvent e) {
        String motd = config.getString("motd.txt");

        Component themotd = miniMessage.deserialize(motd);

        if (config.getBoolean("motd.enabled")) {
            e.motd(themotd);
        } else {
            return;
        }

    }
}