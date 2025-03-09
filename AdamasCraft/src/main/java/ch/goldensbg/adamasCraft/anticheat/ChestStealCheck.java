package ch.goldensbg.adamasCraft.anticheat;

import ch.goldensbg.adamasCraft.AdamasCraft;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class ChestStealCheck implements PacketListener {

    private final Map<UUID, Long> lastOpen = new HashMap<>();

    public ChestStealCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(AdamasCraft.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                long currentTime = System.currentTimeMillis();
                long lastTime = lastOpen.getOrDefault(player.getUniqueId(), 0L);
                lastOpen.put(player.getUniqueId(), currentTime);

                if (currentTime - lastTime < 200) {
                    player.sendMessage(ChatColor.RED + "Chest Steal verdächtig!");
                }
            }
        });
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {

    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {

    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return null;
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return null;
    }

}
