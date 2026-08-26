package me.qwert.strenght.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.qwert.strenght.Strenght;

import org.bukkit.entity.Player;

public class StrengthExpansion extends PlaceholderExpansion {

    private final Strenght plugin;

    public StrengthExpansion(Strenght plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "strenght";
    }

    @Override
    public String getAuthor() {
        return "C_plus_plus";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(
            Player player,
            String params
    ) {

        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("strength")) {

            double strength =
                    plugin.getStrengthManager()
                            .getStrength(player);

            return String.valueOf(strength);
        }

        if (params.equalsIgnoreCase("strength_rounded")) {

            double strength =
                    plugin.getStrengthManager()
                            .getStrength(player);

            return String.format(
                    "%.1f",
                    strength
            );
        }

        return null;
    }
}