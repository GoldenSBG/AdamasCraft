package ch.goldensbg.adamasCraft;

import ch.goldensbg.adamasCraft.seasons.LoadSeason;
import ch.goldensbg.adamasCraft.seasons.Season;
import ch.goldensbg.adamasCraft.seasons.SeasonManager;
import ch.goldensbg.adamasCraft.utils.YamlFile;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public final class AdamasCraft extends JavaPlugin {

    @Getter
    public static AdamasCraft instance;
    public LuckPerms luckPermsapi;
    @Getter
    private SeasonManager seasonManager;


    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        try {
            luckPermsapi = LuckPermsProvider.get();
            getLogger().info("LuckPerms API loaded successfully!");
        } catch (IllegalStateException e) {
            getLogger().severe("LuckPerms API could not be loaded!");
        }

        this.seasonManager = new SeasonManager();
    }

    @Override
    public void onDisable() {
        YamlFile.saveAll();
    }

    public void addSeason(){
        //Alle Events werden hier hinzugefügt und geladen die ein @LoadEvent haben
        Reflections reflections = new Reflections("ch.goldensbg.adamasCraft.seasons.seasons");
        reflections.getTypesAnnotatedWith(LoadSeason.class).forEach(aClass -> {
            System.out.println(aClass);
            try {
                Season season = (Season) aClass.newInstance();
                seasonManager.loadSeason(season);
                //configurationReader.readEventConfigurableFields(season);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
