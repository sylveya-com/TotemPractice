package dev.lokspel.totempractice.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class ColorAPI {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacySection();

    private ColorAPI() {
    }

    public static String legacy(String text) {
        return LEGACY.serialize(MINI_MESSAGE.deserialize(text));
    }
}