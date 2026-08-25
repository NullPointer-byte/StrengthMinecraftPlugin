package me.qwert.strenght.listener;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.qwert.strenght.Strenght;
import me.qwert.strenght.perk.Perk;
import me.qwert.strenght.perk.PerkManager;

public class RapidFireListener implements Listener {

    private static final long DELAY_TICKS = 8L;
    private static final int EXTRA_SHOTS = 8;

    private final Strenght plugin;

    public RapidFireListener(Strenght plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onShoot(EntityShootBowEvent event) {

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack weapon = event.getBow();

        if (weapon == null) {
            return;
        }

        Material type = weapon.getType();

        if (type != Material.BOW
                && type != Material.CROSSBOW) {
            return;
        }

        PerkManager perkManager =
                plugin.getPerkManager();

        Perk rapidFire =
                perkManager.getPerk("rapidfire");

        if (rapidFire == null) {
            return;
        }

        if (!perkManager.hasPerk(
                player,
                rapidFire
        )) {
            return;
        }

        /*
         * Первый выстрел уже сделан Minecraft.
         * Добавляем ещё четыре.
         */

        for (int i = 1; i <= EXTRA_SHOTS; i++) {

            long delay =
                    DELAY_TICKS * i;

            player.getScheduler().runDelayed(
                    plugin,
                    task -> shootArrow(player),
                    null,
                    delay
            );
        }
    }

    private void shootArrow(Player player) {

        if (!player.isOnline()) {
            return;
        }

        Vector direction =
                player.getEyeLocation()
                        .getDirection()
                        .normalize();

        Arrow arrow =
                player.getWorld().spawnArrow(
                        player.getEyeLocation(),
                        direction,
                        3.0f,
                        0.0f
                );

        arrow.setShooter(player);

        arrow.setPickupStatus(
                Arrow.PickupStatus.DISALLOWED
        );
    }
}