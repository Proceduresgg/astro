package me.procedures.astro.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import me.procedures.astro.managers.ProfileManager;
import me.procedures.astro.player.PlayerState;
import me.procedures.astro.utils.GameUtil;
import me.procedures.astro.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

@CommandAlias("kit")
public class KitCommand extends BaseCommand {

    @Dependency private ProfileManager profileManager;

    @Default
    public void onDefault(Player player) {
        this.profileManager.getProfile(player).setState(PlayerState.FIGHTING);

        GameUtil.resetPlayer(player);

        player.addPotionEffects(Arrays.asList(
                new PotionEffect(PotionEffectType.SPEED, 999999, 1),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 99)
        ));

        player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_SWORD, ChatColor.GRAY + ChatColor.BOLD.toString() + "Join" + ChatColor.GREEN + ChatColor.BOLD.toString() + " UnrankedMenu " + ChatColor.GRAY + ChatColor.BOLD.toString() + "Queue", ChatColor.GRAY + "Right click to queue ranked.").getItem());

        Bukkit.getServer().getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.showPlayer(player);
            player.showPlayer(onlinePlayer);
        });
    }
}
