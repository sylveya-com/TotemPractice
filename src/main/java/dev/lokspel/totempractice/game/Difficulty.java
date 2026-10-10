package dev.lokspel.totempractice.game;

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