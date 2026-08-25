package me.qwert.strenght.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.qwert.strenght.Strenght;
import me.qwert.strenght.StrenghtManager;

public class KillListener implements Listener {

    private final Strenght plugin;

    public KillListener(Strenght plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) {
            return;
        }

        StrenghtManager manager =
                plugin.getStrengthManager();

        // Убийца получает +0.2 Strength
        manager.addStrength(killer, 0.2);

        // Смотрим Strength жертвы
        double victimStrength =
                manager.getStrength(victim);

        // Бутылка выпадает только при Strength >= 1
        if (victimStrength >= 1.0) {

            int level =
                    (int) Math.floor(victimStrength);

            victim.getWorld().dropItemNaturally(
                    victim.getLocation(),
                    plugin.getStrengthBottle().create(level)
            );
        }

        // После смерти сила полностью сбрасывается
        manager.setStrength(victim, 0.0);

        killer.sendMessage(
                "§dУбийство! §7Strength: §f"
                        + manager.getStrength(killer)
        );
    }
}