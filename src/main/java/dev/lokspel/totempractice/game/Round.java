package dev.lokspel.totempractice.game;

import java.util.UUID;

public final class Round {

    public static final int STORAGE_SLOTS = 36;

    private static final long FIRST_HIT_DELAY_MILLIS = 3000L;

    private final UUID playerId;
    private final Difficulty difficulty;
    private final long hitIntervalMillis;
    private final double scoreMultiplier;
    private final boolean offhandOnly;

    private int totemsUsed;
    private long nextHitAt;
    private boolean started;

    Round(UUID playerId, Difficulty difficulty, double hitIntervalSeconds, double scoreMultiplier, boolean offhandOnly) {
        this.playerId = playerId;
        this.difficulty = difficulty;
        this.hitIntervalMillis = Math.round(hitIntervalSeconds * 1000.0D);
        this.scoreMultiplier = scoreMultiplier;
        this.offhandOnly = offhandOnly;

        long startedAt = System.currentTimeMillis();
        this.nextHitAt = startedAt + FIRST_HIT_DELAY_MILLIS;
    }

    public UUID playerId() {
        return playerId;
    }

    public Difficulty difficulty() {
        return difficulty;
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean shouldHit(long now) {
        return now >= nextHitAt;
    }

    public long nextHitAt() {
        return nextHitAt;
    }

    public void recordHit(long now) {
        this.started = true;
        this.nextHitAt = now + hitIntervalMillis;
    }

    public void useTotem() {
        totemsUsed++;
    }

    public int totemsUsed() {
        return totemsUsed;
    }

    public double hitIntervalSeconds() {
        return hitIntervalMillis / 1000.0D;
    }

    public double scoreMultiplier() {
        return scoreMultiplier;
    }

    public boolean offhandOnly() {
        return offhandOnly;
    }

    public int score() {
        return Math.max(1, (int) Math.round(totemsUsed * scoreMultiplier));
    }
}