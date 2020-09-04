package me.d3li0n.deathplus.utils;

import java.util.UUID;

public class Character {
    private final UUID player;
    private int deaths;

    public Character(final UUID player, final int deaths) {
        this.player = player;
        this.deaths = deaths;
    }

    public UUID getPlayer() {
        return this.player;
    }

    public void addDeaths() {
        this.deaths += 1;
    }

    public int getDeaths() {
        return this.deaths;
    }
}
