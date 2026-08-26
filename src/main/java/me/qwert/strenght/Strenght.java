package me.qwert.strenght;

import org.bukkit.plugin.java.JavaPlugin;

import me.qwert.strenght.command.StrengthCommand;
import me.qwert.strenght.listener.BottleListener;
import me.qwert.strenght.listener.CombatListener;
import me.qwert.strenght.listener.KillListener;
import me.qwert.strenght.listener.RapidFireListener;
import me.qwert.strenght.perk.PerkManager;
import me.qwert.strenght.placeholder.StrengthExpansion;

public final class Strenght extends JavaPlugin {

    private StrenghtManager strengthManager;
    private StrenghtBottle strengthBottle;
    private PerkManager perkManager;

    @Override
    public void onEnable() {

        strengthManager = new StrenghtManager(this);
        strengthBottle = new StrenghtBottle(this);
        perkManager = new PerkManager(this);

        getServer().getPluginManager().registerEvents(
            new KillListener(this),
            this
        );

        getServer().getPluginManager().registerEvents(
            new BottleListener(this),
            this
        );

        getServer().getPluginManager().registerEvents(
            new CombatListener(this),
            this
        );

        getServer().getPluginManager().registerEvents(
          new RapidFireListener(this),
          this
        );

        getCommand("strength").setExecutor(
            new StrengthCommand(this)
        );

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {

        new StrengthExpansion(this).register();

        getLogger().info("PlaceholderAPI подключён!");
    } else {

        getLogger().warning(
                "PlaceholderAPI не найден. Placeholder'ы Strength недоступны."
        );
    }

        getLogger().info("Strenght включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Strenght выключен!");
    }

    public StrenghtManager getStrengthManager() {
        return strengthManager;
    }

    public StrenghtBottle getStrengthBottle() {
        return strengthBottle;
    }

    public PerkManager getPerkManager() {
        return perkManager;
    }
}