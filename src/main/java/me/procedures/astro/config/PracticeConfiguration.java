package me.procedures.astro.config;

import lombok.Getter;
import me.procedures.astro.utils.MessageUtil;
import org.bukkit.ChatColor;

@Getter
public class PracticeConfiguration {

    public static final String COMING_SOON_MESSAGE = ChatColor.GRAY.toString() + "This feature is currently under development, we'll update you once it's complete.";

    private FileConfig messages = new FileConfig("messages.yml");
    private FileConfig scoreboard = new FileConfig("scoreboard.yml");


    public PracticeConfiguration() {
        this.messages.getConfig().options().copyDefaults(true);
        this.scoreboard.getConfig().options().copyDefaults(true);
    }

    public String getString(String string) {
        if (this.messages.getConfig().getString(string) != null) {
            return MessageUtil.color(this.messages.getConfig().getString(string));

        } else if (this.scoreboard.getConfig().getString(string) != null) {
            return MessageUtil.color(this.scoreboard.getConfig().getString(string));

        }

        return null;
    }
}
