package me.qwert.strenght.listener;

import me.qwert.strenght.Strenght;
import me.qwert.strenght.combat.StrengthCombat;
import me.qwert.strenght.combat.ComboManager;
import me.qwert.strenght.combat.CriticalManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    private final StrengthCombat combat;
    private final ComboManager comboManager;
    private final CriticalManager criticalManager;

    public CombatListener(Strenght plugin) {
        this.combat = new StrengthCombat(plugin);

        this.comboManager = new ComboManager();
        this.criticalManager = new CriticalManager(comboManager);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player attacker)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        int combo = comboManager.registerHit(attacker);

        attacker.sendActionBar(
            "§dCombo: §f" + combo
        );

        double originalDamage = event.getDamage();

        double newDamage =
            combat.applyDamage(attacker, originalDamage);

        if (criticalManager.shouldCritical(attacker)) {
            newDamage *= 1.5;

            attacker.sendActionBar(
                "§c§lCRITICAL! §7Combo: §f" + combo
            );
        }

event.setDamage(newDamage);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerDamaged(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        comboManager.reset(victim);
    }
}