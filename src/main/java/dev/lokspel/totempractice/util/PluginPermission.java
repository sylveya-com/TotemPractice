package dev.lokspel.totempractice.util;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public enum PluginPermission {

    USE_CMD(new Permission("totempractice.use", PermissionDefault.TRUE)),
    RELOAD_CMD(new Permission("totempractice.reload", PermissionDefault.OP)),
    SET_LOBBY_CMD(new Permission("totempractice.set.lobby", PermissionDefault.OP)),
    SET_MATCH_CMD(new Permission("totempractice.set.match", PermissionDefault.OP));

    private final Permission permission;

    PluginPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission get() {
        return permission;
    }

    public static void registerAll() {
        for (PluginPermission pluginPermission : PluginPermission.values()) {
            try {
                Bukkit.getPluginManager().addPermission(pluginPermission.get());
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public static void unregisterAll() {
        for (PluginPermission pluginPermission : PluginPermission.values()) {
            try {
                Bukkit.getPluginManager().removePermission(pluginPermission.get());
            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}
