package me.qwert.strenght.combat;

import org.bukkit.entity.Player;

public class CriticalManager {

    private static final int CRITICAL_COMBO = 5;

    private final ComboManager comboManager;

    public CriticalManager(ComboManager comboManager) {
        this.comboManager = comboManager;
    }

    public boolean shouldCritical(Player player) {

        return comboManager.getCombo(player) >= CRITICAL_COMBO;
    }
}