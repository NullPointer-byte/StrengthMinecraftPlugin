package me.qwert.strenght.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class ComboManager {

    private static final long COMBO_TIMEOUT = 1200L;

    private final Map<UUID, Integer> combos = new HashMap<>();
    private final Map<UUID, Long> lastHit = new HashMap<>();

    public int registerHit(Player player) {

        UUID uuid = player.getUniqueId();

        long now = System.currentTimeMillis();

        Long previousHit = lastHit.get(uuid);

        int combo;

        if (previousHit == null ||
                now - previousHit > COMBO_TIMEOUT) {

            combo = 1;

        } else {

            combo = combos.getOrDefault(uuid, 0) + 1;
        }

        combos.put(uuid, combo);
        lastHit.put(uuid, now);

        return combo;
    }

    public int getCombo(Player player) {
        return combos.getOrDefault(
                player.getUniqueId(),
                0
        );
    }

    public void reset(Player player) {

        UUID uuid = player.getUniqueId();

        combos.remove(uuid);
        lastHit.remove(uuid);
    }

    public void reset(UUID uuid) {

        combos.remove(uuid);
        lastHit.remove(uuid);
    }
}