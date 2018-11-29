package me.procedures.astro.config;

import lombok.Getter;

@Getter
public class PracticeConfiguration {

    private FileConfig messages = new FileConfig("messages.yml");
    private FileConfig scoreboard = new FileConfig("scoreboard.yml");


    public PracticeConfiguration() {
        this.messages.getConfig().options().copyDefaults(true);
        this.scoreboard.getConfig().options().copyDefaults(true);
    }
}
