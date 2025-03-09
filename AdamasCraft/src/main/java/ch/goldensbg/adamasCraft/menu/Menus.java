package ch.goldensbg.adamasCraft.menu;

import ch.goldensbg.adamasCraft.AdamasCraft;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;

@Getter
public class Menus {

    public Menus() {
    }

    private void registerListener() {
        PluginManager pluginManager = AdamasCraft.getInstance().getServer().getPluginManager();

    }

}