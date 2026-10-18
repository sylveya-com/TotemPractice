package dev.lokspel.totempractice.game;

import dev.lokspel.totempractice.config.settings.DifficultyEntry;

public final class Difficulty {

    private final String name;
    private final double hitInterval;
    private final double offhandHitInterval;
    private final double scoreMultiplier;
    private final boolean offhandOnly;
    private final boolean randomTotem;

    public Difficulty(String name, double hitInterval, double offhandHitInterval, double scoreMultiplier, boolean offhandOnly, boolean randomTotem) {
        this.name = name;
        this.hitInterval = hitInterval;
        this.offhandHitInterval = offhandHitInterval;
        this.scoreMultiplier = scoreMultiplier;
        this.offhandOnly = offhandOnly;
        this.randomTotem = randomTotem;
    }

    public static Difficulty fromEntry(String name, DifficultyEntry entry) {
        if (entry == null) {
            return null;
        }
        return new Difficulty(
                name,
                entry.getHitInterval(),
                entry.getOffhandHitInterval(),
                entry.getScoreMultiplier(),
                entry.isOffhandOnly(),
                entry.isRandomTotem()
        );
    }

    public String name() {
        return name;
    }

    public double hitInterval() {
        return hitInterval;
    }

    public double offhandHitInterval() {
        return offhandHitInterval;
    }

    public double effectiveHitInterval() {
        return offhandOnly ? offhandHitInterval : hitInterval;
    }

    public double scoreMultiplier() {
        return scoreMultiplier;
    }

    public boolean offhandOnly() {
        return offhandOnly;
    }

    public boolean randomTotem() {
        return randomTotem;
    }
}