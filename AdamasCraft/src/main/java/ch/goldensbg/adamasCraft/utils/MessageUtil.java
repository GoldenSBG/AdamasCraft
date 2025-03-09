package ch.goldensbg.adamasCraft.utils;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class MessageUtil {

    @Getter
    private static final MessageUtil instance = new MessageUtil();
    private static MiniMessage miniMessage;
    private static BukkitAudiences adventure;


    private MessageUtil() {
        this.miniMessage = MiniMessage.miniMessage();
    }

    public Component parseMessage(String message) {
        return miniMessage.deserialize(message);
    }
    public String parseStringMessage(String message) {
        return miniMessage.deserialize(message).toString();
    }


    public static void setAdventure(BukkitAudiences adventureInstance) {
        adventure = adventureInstance;
    }

    public static String miniMessageParse(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        return LegacyComponentSerializer.legacySection().serialize(text);
    }

    public static void miniMessageSender(Player player, String message) {
        if (adventure == null) {
            throw new IllegalStateException("BukkitAudiences is not initialized.");
        }
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        adventure.player(player).sendMessage(text);
    }
}