package me.qwert.strenght;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class StrenghtBottle {

    private final Strenght plugin;
    private final NamespacedKey strengthKey;

    public StrenghtBottle(Strenght plugin) {
        this.plugin = plugin;
        this.strengthKey = new NamespacedKey(plugin, "strength_bottle");
    }

    private String gradient(String text) {

    StringBuilder result = new StringBuilder();

    int length = text.length();

    for (int i = 0; i < length; i++) {

        double progress =
                length <= 1
                        ? 0
                        : (double) i / (length - 1);

        int red = (int) (255 * (1.0 - progress));

        String hex = String.format(
                "#%02X0000",
                red
        );

        result.append(
                net.md_5.bungee.api.ChatColor.of(hex)
        );

        result.append(text.charAt(i));
    }

    return result.toString();
}

    public ItemStack create(int level) {
        ItemStack item = new ItemStack(Material.POTION);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(
            gradient("Strength " + level)
        );

        meta.getPersistentDataContainer().set(
                strengthKey,
                PersistentDataType.INTEGER,
                level
        );

        item.setItemMeta(meta);

        return item;
    }

    public boolean isStrengthBottle(ItemStack item) {
        if (item == null || item.getType() != Material.POTION) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        return meta.getPersistentDataContainer().has(
                strengthKey,
                PersistentDataType.INTEGER
        );
    }

    public int getLevel(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return 0;
        }

        return meta.getPersistentDataContainer().getOrDefault(
                strengthKey,
                PersistentDataType.INTEGER,
                0
        );
    }
}