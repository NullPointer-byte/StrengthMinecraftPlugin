package me.qwert.strenght.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import me.qwert.strenght.Strenght;
import me.qwert.strenght.StrenghtBottle;

public class BottleListener implements Listener {

    private final Strenght plugin;

    public BottleListener(Strenght plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void onDrink(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        ItemStack item = event.getItem();

        StrenghtBottle bottle =
                plugin.getStrengthBottle();

        // Это не наша бутылка
        if (!bottle.isStrengthBottle(item)) {
            return;
        }

        int level = bottle.getLevel(item);

        if (level <= 0) {
            event.setCancelled(true);
            return;
        }

        // Даём игроку Strength
        plugin.getStrengthManager().addStrength(
                player,
                level
        );

        player.sendMessage(
                "§cВы получили §4+"
                        + level
                        + " Strength"
        );

        player.sendActionBar(
                "§c+" + level + " Strength"
        );
    }
}