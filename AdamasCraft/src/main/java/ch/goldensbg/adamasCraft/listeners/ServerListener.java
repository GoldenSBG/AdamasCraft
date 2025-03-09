package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.adamasCraft.AdamasCraft;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + "- " + ChatColor.GOLD + player.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + "+ " + ChatColor.GOLD + player.getName());

        // Hier fügen wir eine Benachrichtigung für den Spieler mit einer bestimmten Permission hinzu
        if (player.hasPermission("clientinfo.view")) {
            startListening();
        }
    }

    // Füge einen PacketListener hinzu, der das Login-Paket überwacht
    public void startListening() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AdamasCraft.getInstance(), PacketType.Play.Server.LOGIN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                String clientMod = getClientMod(event);

                // Wenn ein Spieler einen bestimmten Client verwendet
                AdamasCraft.getInstance().getLogger().info(player.getName() + " hat den Client " + clientMod + " verwendet.");

                // Optional: Eine Nachricht an einen Spieler mit entsprechender Berechtigung senden
                if (player.hasPermission("clientinfo.view")) {
                    player.sendMessage(ChatColor.YELLOW + "Du verwendest den Client " + clientMod);
                }
            }
        });
    }

    // Eine Methode, die den verwendeten Client aus dem Login-Paket extrahiert
    private String getClientMod(PacketEvent event) {
        String clientMod = "Unbekannt";

        // In vielen Fällen kann der Client-Name in der Login-Handshake-Nachricht ausgelesen werden.
        // Probiere, den Client-Mod-Namen zu extrahieren, der durch das Login-Paket übermittelt wird.
        try {
            clientMod = event.getPacket().getStrings().read(0);  // Client-Mod kann hier zu finden sein
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientMod;
    }
}
