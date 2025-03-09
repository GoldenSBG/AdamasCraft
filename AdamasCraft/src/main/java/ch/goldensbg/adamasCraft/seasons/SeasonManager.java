package ch.goldensbg.adamasCraft.seasons;

import ch.goldensbg.adamasCraft.AdamasCraft;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SeasonManager {
    private final ArrayList<Season> activeSeasons = new ArrayList<>();
    private final Map<Class<? extends Season>, Season> allSeasons = new HashMap<>();


    public SeasonManager() {
    }

    public SeasonManager(List<Season> activeEvent){
        this.activeSeasons.addAll(activeEvent);
    }

    public void registerAllAktiveSeasons(){
        for (Season activeSeason : activeSeasons) {
            activeSeason.register();
        }
    }

    public boolean isSeasonActive(Class<? extends Season> event){
        for (Season activeSeason : activeSeasons) {
            if (activeSeason.getClass().equals(event)){
                return true;
            }
        }
        return false;
    }

    public void loadSeason(Season event){
        if (!isSeasonLoaded(event.getClass())){
            allSeasons.put(event.getClass(), event);
        }
    }
    private boolean isSeasonLoaded(Class<? extends Season> event){
        return allSeasons.containsKey(event);
    }

    public void activateSeason(Class<? extends Season> event){
        if (!isSeasonActive(event)){
            Season e = allSeasons.get(event);
            activeSeasons.add(e);
            e.register();
        }
    }

    public void deactivateSeason(Class<? extends Season> event){
        if (isSeasonActive(event)){
            activeSeasons.removeIf(event1 -> {
                boolean equals = event1.getClass().equals(event);
                if (equals){
                    event1.unregister();
                }
                return equals;
            });
        }
    }

    public Season getActiveSeasontByClass(Class<? extends Season> event){
        for (Season activeSeason : activeSeasons) {
            if (activeSeason.getClass().equals(event)){
                return activeSeason;
            }
        }
        return null;
    }

    public Season getLoadedSeasonByClass(Class<? extends Season> event){
        for (Season activeSeason : activeSeasons) {
            if (activeSeason.getClass().equals(event)){
                return activeSeason;
            }
        }
        return null;
    }


    public void deactivateAllSeasons(){
        if (activeSeasons.isEmpty()) return;
        try {
            for (Season activeSeason : activeSeasons) {
                deactivateSeason(activeSeason.getClass());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        allSeasons.clear();
        AdamasCraft.getInstance().addSeason();
    }

}