package me.qwert.strenght.combat;

import me.qwert.strenght.Strenght;
import org.bukkit.entity.Player;

public class StrengthCombat {

    private final Strenght plugin;

    public StrengthCombat(Strenght plugin) {
        this.plugin = plugin;
    }

    public double getDamageMultiplier(Player player) {
        double strength =
                plugin.getStrengthManager().getStrength(player);

        return 1.0 + (strength * 0.05);
    }

    public double applyDamage(Player player, double damage) {
        return damage * getDamageMultiplier(player);
    }
}