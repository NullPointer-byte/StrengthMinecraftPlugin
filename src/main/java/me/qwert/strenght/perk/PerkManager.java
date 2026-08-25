package me.qwert.strenght.perk;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.qwert.strenght.Strenght;
import me.qwert.strenght.perk.perks.RapidFirePerk;

public class PerkManager {

    private final Strenght plugin;

    private final Map<String, Perk> perks = new HashMap<>();
    private final Map<UUID, Set<String>> ownedPerks = new HashMap<>();

    public PerkManager(Strenght plugin) {
        this.plugin = plugin;

        registerPerks();
    }

    private void registerPerks() {

        perks.put(
                "rapidfire",
                new RapidFirePerk()
        );
    }

    public Collection<Perk> getPerks() {
        return perks.values();
    }

    public Perk getPerk(String id) {

        if (id == null) {
            return null;
        }

        return perks.get(
                id.toLowerCase()
        );
    }

    public boolean isAvailable(
            Player player,
            Perk perk
    ) {

        double strength =
                plugin.getStrengthManager()
                        .getStrength(player);

        return strength >=
                perk.getRequiredStrength();
    }

    public boolean hasPerk(
            Player player,
            Perk perk
    ) {

        Set<String> playerPerks =
                ownedPerks.get(
                        player.getUniqueId()
                );

        if (playerPerks == null) {
            return false;
        }

        return playerPerks.contains(
                perk.getId()
        );
    }

    public boolean buyPerk(
            Player player,
            Perk perk
    ) {

        if (!isAvailable(player, perk)) {
            return false;
        }

        if (hasPerk(player, perk)) {
            return false;
        }

        ownedPerks
                .computeIfAbsent(
                        player.getUniqueId(),
                        uuid -> new HashSet<>()
                )
                .add(perk.getId());

        return true;
    }
}