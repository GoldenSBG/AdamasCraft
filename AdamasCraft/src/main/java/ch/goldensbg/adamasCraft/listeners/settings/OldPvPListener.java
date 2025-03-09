package ch.goldensbg.adamasCraft.listeners.settings;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;

public class OldPvPListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damagee = event.getEntity();

        // Wenn der Angreifer und das Opfer Spieler sind
        if (damager instanceof Player && damagee instanceof Player) {
            Player attacker = (Player) damager;
            //Player defender = (Player) damagee;
            setAttackSpeed(attacker, 1000.0);
        }

    }

    public void setAttackSpeed(Player player, double value) {
        AttributeInstance attribute = player.getAttribute(Attribute.ATTACK_SPEED);
        if (attribute == null) return;
        attribute.setBaseValue(value);
    }
}