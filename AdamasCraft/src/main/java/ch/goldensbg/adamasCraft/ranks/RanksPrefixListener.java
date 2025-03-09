package ch.goldensbg.adamasCraft.ranks;

import ch.goldensbg.adamasCraft.AdamasCraft;
import ch.goldensbg.adamasCraft.utils.Spacing;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class RanksPrefixListener implements Listener {
    private final LuckPerms luckPerms = AdamasCraft.getInstance().luckPermsapi;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            String groupName = user.getPrimaryGroup();

            String prefix = luckPerms.getGroupManager().getGroup(groupName)
                    .getCachedData()
                    .getMetaData()
                    .getPrefix();

            if (prefix != null) {
                player.setDisplayName(prefix + " " + player.getName());
                player.setPlayerListName(prefix + " " + player.getName());
                player.setCustomName(prefix + " " + player.getName());
                player.setCustomNameVisible(true);
            } else {
                if (player.hasPermission("ranks.owner")) {
                    player.setDisplayName(Ranks.OWNER + player.getName());
                    player.setPlayerListName(Ranks.OWNER + player.getName());
                    player.setCustomName(Ranks.OWNER + " " + player.getName());
                    player.setCustomNameVisible(true);
                } else if (player.hasPermission("ranks.streamer")) {
                    player.setDisplayName(Ranks.STREAMER + player.getName());
                    player.setPlayerListName(Ranks.STREAMER + player.getName());
                    player.setCustomName(Ranks.STREAMER + " " + player.getName());
                    player.setCustomNameVisible(true);
                } else if (player.hasPermission("ranks.admin")) {
                    player.setDisplayName(Ranks.ADMIN + player.getName());
                    player.setPlayerListName(Ranks.ADMIN + player.getName());
                    player.setCustomName(Ranks.ADMIN + " " + player.getName());
                    player.setCustomNameVisible(true);
                } else if (player.hasPermission("ranks.moderation")) {
                    player.setDisplayName(Ranks.MODERATION + player.getName());
                    player.setPlayerListName(Ranks.MODERATION + player.getName());
                    player.setCustomName(Ranks.MODERATION + " " + player.getName());
                    player.setCustomNameVisible(true);
                } else if (player.hasPermission("ranks.vip")) {
                    player.setDisplayName(Ranks.VIP + player.getName());
                    player.setPlayerListName(Ranks.VIP + player.getName());
                    player.setCustomName(Ranks.VIP + " " + player.getName());
                    player.setCustomNameVisible(true);
                } else if (player.hasPermission("ranks.subscriber")) {
                    player.setDisplayName(Ranks.SUBSCRIBER + player.getName());
                    player.setPlayerListName(Ranks.SUBSCRIBER + player.getName());
                    player.setCustomName(Ranks.SUBSCRIBER + " " + player.getName());
                    player.setCustomNameVisible(true);
                } else {
                    player.setDisplayName(Ranks.PLAYER + player.getName());
                    player.setPlayerListName(Ranks.PLAYER + player.getName());
                    player.setCustomName(Ranks.PLAYER + " " + player.getName());
                    player.setCustomNameVisible(true);
                }
                player.setDisplayName("!noRankFound!" + player.getName());
                player.setPlayerListName("!noRankFound!" + player.getName());
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            String groupName = user.getPrimaryGroup();

            String prefix = luckPerms.getGroupManager().getGroup(groupName)
                    .getCachedData()
                    .getMetaData()
                    .getPrefix();

            if (prefix != null) {
                event.setFormat(prefix + player.getName() + ": " + event.getMessage());
            } else {
                if (player.hasPermission("ranks.owner") && groupName.equalsIgnoreCase("owner")) {
                    event.setFormat(Ranks.OWNER + Spacing.NEGATIVE64PIXEl.getSpacing() + player.getName() + ": " + event.getMessage());
                } else if (player.hasPermission("ranks.streamer")) {
                    event.setFormat(Ranks.STREAMER + player.getName() + ": " + event.getMessage());
                } else if (player.hasPermission("ranks.admin")) {
                    event.setFormat(Ranks.ADMIN + player.getName() + ": " + event.getMessage());
                } else if (player.hasPermission("ranks.moderation")) {
                    event.setFormat(Ranks.MODERATION + player.getName() + ": " + event.getMessage());
                } else if (player.hasPermission("ranks.vip")) {
                    event.setFormat(Ranks.VIP + Spacing.NEGATIVE32PIXEl.getSpacing() + player.getName() + ": " + event.getMessage());
                } else if (player.hasPermission("ranks.subscriber")) {
                    event.setFormat(Ranks.SUBSCRIBER + player.getName() + ": " + event.getMessage());
                } else {
                    event.setFormat(Ranks.PLAYER + player.getName() + ": " + event.getMessage());
                }
                event.setFormat("!noRankFound!" + player.getName() + ": " + event.getMessage());
            }
        }
    }
}