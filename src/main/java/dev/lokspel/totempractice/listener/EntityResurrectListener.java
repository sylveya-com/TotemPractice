package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.game.Round;
import dev.lokspel.totempractice.game.RoundManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class EntityResurrectListener implements Listener {

    private final RoundManager roundManager;

    public EntityResurrectListener(RoundManager roundManager) {
        this.roundManager = roundManager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void handle(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Round round = roundManager.find(player.getUniqueId());
        if (round == null) {
            return;
        }

        if (!round.offhandOnly()) {
            round.useTotem();
            roundManager.hideRandomTotem(player, round);
            return;
        }

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            round.useTotem();
            roundManager.hideRandomTotem(player, round);
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack offHand = inventory.getItemInOffHand();

        if (offHand.getType() != Material.TOTEM_OF_UNDYING) {
            event.setCancelled(true);
            return;
        }

        TotemPractice plugin = TotemPractice.getInstance();
        if (plugin == null) {
            event.setCancelled(true);
            return;
        }

        ItemStack mainHandSnapshot = inventory.getItemInMainHand().clone();
        ItemStack offHandSnapshot = offHand.clone();

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!player.isOnline() || player.isDead()) {
                return;
            }

            if (roundManager.find(player.getUniqueId()) != round) {
                return;
            }

            PlayerInventory currentInventory = player.getInventory();
            ItemStack currentMainHand = currentInventory.getItemInMainHand();
            ItemStack currentOffHand = currentInventory.getItemInOffHand();

            if (mainHandSnapshot.getType() != Material.TOTEM_OF_UNDYING) {
                return;
            }

            boolean mainHandTotemConsumed =
                    mainHandSnapshot.getAmount() == 1
                            ? currentMainHand.getType() == Material.AIR
                            : currentMainHand.isSimilar(mainHandSnapshot)
                              && currentMainHand.getAmount() == mainHandSnapshot.getAmount() - 1;

            boolean offHandUnchanged =
                    currentOffHand.isSimilar(offHandSnapshot)
                            && currentOffHand.getAmount() == offHandSnapshot.getAmount();

            if (!mainHandTotemConsumed || !offHandUnchanged) {
                return;
            }

            // Restore the main-hand totem consumed by vanilla.
            currentInventory.setItemInMainHand(mainHandSnapshot.clone());

            // Consume exactly one offhand totem.
            if (offHandSnapshot.getAmount() == 1) {
                currentInventory.setItemInOffHand(null);
            } else {
                ItemStack remaining = offHandSnapshot.clone();
                remaining.setAmount(remaining.getAmount() - 1);
                currentInventory.setItemInOffHand(remaining);
            }

            round.useTotem();
            roundManager.hideRandomTotem(player, round);
        });
    }
}