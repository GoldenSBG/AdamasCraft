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

public abstract class FastBreakCheck implements PacketListener {

    private final Map<UUID, Long> lastBreak = new HashMap<>();

    public FastBreakCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(AdamasCraft.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                long currentTime = System.currentTimeMillis();
                long lastTime = lastBreak.getOrDefault(player.getUniqueId(), 0L);
                lastBreak.put(player.getUniqueId(), currentTime);

                if (currentTime - lastTime < 100) {
                    player.sendMessage(ChatColor.RED + "FastBreak erkannt!");
                }
            }
        });
    }
}
