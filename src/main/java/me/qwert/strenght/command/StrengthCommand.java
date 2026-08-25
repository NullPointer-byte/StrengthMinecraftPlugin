package me.qwert.strenght.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qwert.strenght.Strenght;
import me.qwert.strenght.StrenghtManager;
import me.qwert.strenght.perk.Perk;
import me.qwert.strenght.perk.PerkManager;

public class StrengthCommand implements CommandExecutor {

    private final Strenght plugin;

    public StrengthCommand(Strenght plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        // /strength
        if (args.length == 0) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage(
                        ChatColor.RED
                                + "Эта команда доступна только игрокам."
                );

                return true;
            }

            StrenghtManager manager =
                    plugin.getStrengthManager();

            double strength =
                    manager.getStrength(player);

            player.sendMessage(
                    ChatColor.LIGHT_PURPLE
                            + "Твоя сила: "
                            + ChatColor.WHITE
                            + strength
            );

            return true;
        }

        // /strength info
        if (args[0].equalsIgnoreCase("info")) {

            sender.sendMessage(
                    ChatColor.LIGHT_PURPLE
                            + "§m--------------------------"
            );

            sender.sendMessage(
                    ChatColor.LIGHT_PURPLE
                            + "Strenght"
            );

            sender.sendMessage(
                    ChatColor.GRAY
                            + "Версия: "
                            + ChatColor.WHITE
                            + "2.2.8"
            );

            sender.sendMessage(
                    ChatColor.GRAY
                            + "Разработчик: "
                            + ChatColor.WHITE
                            + "C_plus_plus"
            );

            sender.sendMessage("");

            sender.sendMessage(
                    ChatColor.LIGHT_PURPLE
                            + "Команды:"
            );

            sender.sendMessage(
                    ChatColor.GRAY
                            + "/strength"
                            + ChatColor.WHITE
                            + " — твоя сила"
            );

            sender.sendMessage(
                    ChatColor.GRAY
                            + "/strength info"
                            + ChatColor.WHITE
                            + " — информация о плагине"
            );

            sender.sendMessage(
                    ChatColor.GRAY
                            + "/strength perks"
                            + ChatColor.WHITE
                            + " — доступные перки"
            );

            sender.sendMessage(
                    ChatColor.LIGHT_PURPLE
                            + "§m--------------------------"
            );

            return true;
        }

        // /strength perks
        if (args[0].equalsIgnoreCase("perks")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage(
                        ChatColor.RED
                                + "Эта команда доступна только игрокам."
                );

                return true;
            }

            PerkManager perkManager =
                    plugin.getPerkManager();

            // /strength perks
            if (args.length == 1) {

                player.sendMessage(
                        ChatColor.LIGHT_PURPLE
                                + "§m--------------------------"
                );

                player.sendMessage(
                        ChatColor.LIGHT_PURPLE
                                + "§lПерки"
                );

                player.sendMessage("");

                for (Perk perk : perkManager.getPerks()) {

                    boolean available =
                            perkManager.isAvailable(player, perk);

                    boolean owned =
                            perkManager.hasPerk(player, perk);

                    player.sendMessage(
                            (owned
                                    ? ChatColor.GREEN + "✔ "
                                    : available
                                    ? ChatColor.YELLOW + "● "
                                    : ChatColor.RED + "✘ ")
                                    + ChatColor.WHITE
                                    + perk.getName()
                    );

                    player.sendMessage(
                            ChatColor.GRAY
                                    + "  Требуется Strength: "
                                    + ChatColor.WHITE
                                    + perk.getRequiredStrength()
                    );

                    if (owned) {

                        player.sendMessage(
                                ChatColor.GRAY
                                        + "  Статус: "
                                        + ChatColor.GREEN
                                        + "Куплен"
                        );

                    } else if (available) {

                        player.sendMessage(
                                ChatColor.GRAY
                                        + "  Статус: "
                                        + ChatColor.YELLOW
                                        + "Доступен"
                        );

                        player.sendMessage(
                                ChatColor.GRAY
                                        + "  Купить: "
                                        + ChatColor.WHITE
                                        + "/strength perks "
                                        + perk.getId()
                                        + " buy"
                        );

                    } else {

                        player.sendMessage(
                                ChatColor.GRAY
                                        + "  Статус: "
                                        + ChatColor.RED
                                        + "Недоступен"
                        );
                    }

                    player.sendMessage("");
                }

                player.sendMessage(
                        ChatColor.LIGHT_PURPLE
                                + "§m--------------------------"
                );

                return true;
            }

            // /strength perks <perk>
            String perkId =
                    args[1].toLowerCase();

            Perk perk =
                    perkManager.getPerk(perkId);

            if (perk == null) {

                player.sendMessage(
                        ChatColor.RED
                                + "Такого перка не существует."
                );

                return true;
            }

            // /strength perks <perk> buy
            if (args.length >= 3
                    && args[2].equalsIgnoreCase("buy")) {

                if (perkManager.hasPerk(player, perk)) {

                    player.sendMessage(
                            ChatColor.YELLOW
                                    + "Ты уже имеешь этот перк."
                    );

                    return true;
                }

                if (!perkManager.isAvailable(player, perk)) {

                    player.sendMessage(
                            ChatColor.RED
                                    + "У тебя недостаточно силы."
                    );

                    player.sendMessage(
                            ChatColor.GRAY
                                    + "Требуется Strength: "
                                    + ChatColor.WHITE
                                    + perk.getRequiredStrength()
                    );

                    return true;
                }

                boolean bought =
                        perkManager.buyPerk(player, perk);

                if (bought) {

                    player.sendMessage(
                            ChatColor.GREEN
                                    + "✔ Перк "
                                    + ChatColor.WHITE
                                    + perk.getName()
                                    + ChatColor.GREEN
                                    + " успешно получен!"
                    );

                } else {

                    player.sendMessage(
                            ChatColor.RED
                                    + "Не удалось получить перк."
                    );
                }

                return true;
            }

            // /strength perks <perk> без buy
            player.sendMessage(
                    ChatColor.GRAY
                            + "Использование: "
                            + ChatColor.WHITE
                            + "/strength perks "
                            + perk.getId()
                            + " buy"
            );

            return true;
        }

        // Неизвестная команда
        sender.sendMessage(
                ChatColor.RED
                        + "Использование: /strength [info|perks]"
        );

        return true;
    }
}