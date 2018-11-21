package me.procedures.astro.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRating {

    private int rating;

    public PlayerRating() {
        this.rating = 1000;
    }

    public PlayerRating(int rating) {
        this.rating = rating;
    }
}
