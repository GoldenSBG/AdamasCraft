package ch.goldensbg.adamasCraft.listeners;

import ch.goldensbg.adamasCraft.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcePackListener implements Listener {
    private static final String RESOURCE_PACK_URL = "https://download.mc-packs.net/pack/2f77de97a9cd6c2a3618fbff471ea9ca0f81b122.zip";
    private static final String RESOURCE_PACK_HASH = "2f77de97a9cd6c2a3618fbff471ea9ca0f81b122";

    // Methode zur Erstellung der Kick-Nachricht
    private String createKickMessage(String reason, String[][] instructions) {
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.DARK_PURPLE).append("✖ ").append(ChatColor.LIGHT_PURPLE)
                .append(ChatColor.BOLD).append("Golden_SBG Event").append(ChatColor.DARK_PURPLE).append(" ✖\n\n");
        message.append(ChatColor.GREEN).append("Fehler beim Joinen des Eventservers!\n");
        message.append(ChatColor.RESET).append("Grund: ").append(ChatColor.LIGHT_PURPLE).append(reason).append("\n\n");

        for (String[] entry : instructions) {
            message.append(ChatColor.LIGHT_PURPLE).append(entry[0]).append(" ")
                    .append(ChatColor.GREEN).append(entry[1]).append(" ")
                    .append(ChatColor.GREEN).append(entry[2]).append("\n");
        }

        return message.toString();
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        PlayerResourcePackStatusEvent.Status status = event.getStatus();

        switch (status) {
            case ACCEPTED:
                player.sendMessage(MessageUtil.getInstance().parseMessage("<gradient:green:#bfff00>You have accepted the <gradient:gold:yellow>Server Pack"));
                break;

            case DECLINED:
                String[][] declinedInstructions = {
                        {"Versuche erneut zu joinen und ", "akzeptiere das ", "Serverresourcepack!"},
                        {"Ohne dieses Pack werden manche", "Texturen", "komisch erscheinen"},
                        {"", "", ""},
                        {"Falls es weiter nicht funktioniert, ", "kontaktieren", "ein Eventveranstaltungsmitglied"},
                        {"Ansonsten den ", "Support", "auf dem Discord"},
                        {"https://dsc.gg/goldensbg", "", ""},
                        {"https://discord.com/invite/notrix", "", ""}
                };
                player.kickPlayer(createKickMessage("Serverpack nicht akzeptiert", declinedInstructions));
                break;

            case FAILED_DOWNLOAD:
                String[][] failedInstructions = {
                        {"Versuche erneut zu joinen und ", "herunterladen des ", "Serverresourcepacks!"},
                        {"Ohne dieses Pack werden manche", "Texturen", "komisch erscheinen"},
                        {"", "", ""},
                        {"Falls es weiter nicht funktioniert, ", "kontaktieren", "den Support"},
                        {"https://dsc.gg/goldensbg", "", ""}
                };
                player.kickPlayer(createKickMessage("Serverpack konnte nicht heruntergeladen werden!", failedInstructions));
                break;

            case SUCCESSFULLY_LOADED:
                player.sendMessage(MessageUtil.getInstance().parseMessage("<gradient:green:#bfff00>The <gradient:gold:yellow>Server Pack <gradient:green:#bfff00>has been loaded!"));
                break;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setResourcePack(RESOURCE_PACK_URL, RESOURCE_PACK_HASH);
    }
}