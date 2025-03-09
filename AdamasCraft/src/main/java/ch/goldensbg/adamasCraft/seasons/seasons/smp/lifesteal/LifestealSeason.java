package ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal;

import ch.goldensbg.adamasCraft.seasons.LoadSeason;
import ch.goldensbg.adamasCraft.seasons.Season;
import ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.LifestealCommand;
import ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.listeners.HeadPlaceListener;
import ch.goldensbg.adamasCraft.seasons.seasons.smp.lifesteal.listeners.PlayerDeathListener;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;

@LoadSeason
public class LifestealSeason extends Season {

    private PlayerDataManager playerDataManager;

    public LifestealSeason() {
        super(Material.ENDER_PEARL, SeasonType.SMP);
    }

    @Override
    public void update() {
        super.update();

    }


    @Override
    public void register() {
        super.register();
        playerDataManager = new PlayerDataManager();

        plugin.getCommand("Lifesteal").setExecutor(new LifestealCommand());
        plugin.getInstance().getServer().getPluginManager().registerEvents(new HeadPlaceListener(playerDataManager), plugin.getInstance());
        plugin.getInstance().getServer().getPluginManager().registerEvents(new PlayerDeathListener(playerDataManager), plugin.getInstance());
    }

    @Override
    public void unregister() {
        super.unregister();

        plugin.getCommand("Lifesteal").setExecutor(null);
        HandlerList.unregisterAll();
        plugin.getInstance().getServer().getPluginManager().registerEvents(null, plugin.getInstance());
    }

}