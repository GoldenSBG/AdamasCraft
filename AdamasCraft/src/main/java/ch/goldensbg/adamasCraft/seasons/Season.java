package ch.goldensbg.adamasCraft.seasons;

import ch.goldensbg.adamasCraft.AdamasCraft;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class Season implements Listener {

    public enum SeasonType {
        MINIGAME(Material.IRON_SWORD),
        GAME(Material.TOTEM_OF_UNDYING),
        SMP(Material.GRASS_BLOCK),
        HARDCORE(Material.ENCHANTED_GOLDEN_APPLE);

        private Material icon;

        private SeasonType(Material icon) {
            this.icon = icon;
        }

        public String getName() {
            return this.name();
        }

        public String getDescription() {
            return this.name();
        }


        public Material getIcon() {
            return icon;
        }
    }

    @Getter
    private transient final String name;
    @Getter
    private transient final String description;
    @Getter
    private transient final Material icon;
    @Getter
    private final transient SeasonType type;
    protected transient final AdamasCraft plugin;

    public Season(Material icon, SeasonType type) {
        this.name = this.getClass().getSimpleName() + "Name";
        this.description = this.getClass().getSimpleName() + "Description";
        this.icon = icon;
        this.type = type;
        this.plugin = AdamasCraft.getInstance();

    }

    public void register() {
        AdamasCraft.getInstance().getServer().getPluginManager().registerEvents(this, AdamasCraft.getInstance());
    }
    public void unregister() {
        HandlerList.unregisterAll(this);

    }
    public void update(){}

    public ArrayList<Player> getAllSurvivalPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (isPlayerInEvent(player)) {
                players.add(player);
            }
        }
        return players;
    }

    public boolean isPlayerInEvent(Player player) {
        return !player.getGameMode().equals(GameMode.SPECTATOR) && !player.isDead();
    }

}