package me.procedures.astro.config;

import lombok.Getter;
import me.procedures.astro.utils.MessageUtil;

import java.util.List;

@Getter
public class PracticeConfiguration {

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
