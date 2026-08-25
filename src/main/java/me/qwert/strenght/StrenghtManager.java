package me.qwert.strenght;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class StrenghtManager {

    private final Strenght plugin;
    private final Map<UUID, Double> strengths = new HashMap<>();

    private final File file;
    private final YamlConfiguration config;

    public StrenghtManager(Strenght plugin) {
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "strengths.yml");
        config = YamlConfiguration.loadConfiguration(file);

        load();
    }

    public double getStrength(Player player) {
        return strengths.getOrDefault(player.getUniqueId(), 0.0);
    }

    public void addStrength(Player player, double amount) {
        double current = getStrength(player);
        double newStrength = current + amount;

        strengths.put(player.getUniqueId(), newStrength);

        savePlayer(player);
    }

    public int getLevel(Player player) {
        return (int) Math.floor(getStrength(player));
    }

    public void removePlayer(Player player) {
        strengths.remove(player.getUniqueId());
    }

    private void load() {
        if (!file.exists()) {
            return;
        }

        for (String uuidString : config.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                double strength = config.getDouble(uuidString);

                strengths.put(uuid, strength);
            } catch (IllegalArgumentException ignored) {
                plugin.getLogger().warning(
                        "Не удалось прочитать UUID: " + uuidString
                );
            }
        }
    }

    public void setStrength(Player player, double strength) {
        strengths.put(player.getUniqueId(), strength);
    }

    private void savePlayer(Player player) {
        UUID uuid = player.getUniqueId();

        config.set(uuid.toString(), getStrength(player));

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe(
                    "Не удалось сохранить Strength игрока "
                            + player.getName()
            );
            e.printStackTrace();
        }
    }
}