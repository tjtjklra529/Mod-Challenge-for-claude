package com.echogemmod.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

/**
 * Echo Magnet: toggle item vacuum mode with right-click.
 * While active, all item entities within 8 blocks are pulled toward the player
 * every tick. Uses the item's NBT "Active" boolean to track state.
 * The per-tick pull is handled in EchoGemsMod server tick.
 */
public class EchoMagnetItem extends Item {

    public static final String KEY_ACTIVE = "MagnetActive";
    public static final double RADIUS = 8.0;

    public EchoMagnetItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) return TypedActionResult.success(stack);

        boolean active = stack.getOrCreateNbt().getBoolean(KEY_ACTIVE);
        stack.getOrCreateNbt().putBoolean(KEY_ACTIVE, !active);

        user.sendMessage(
            net.minecraft.text.Text.literal(
                active ? "§c[Echo Magnet] §fDeactivated." : "§a[Echo Magnet] §fActivated — pulling items within 8 blocks."),
            true);

        return TypedActionResult.success(stack);
    }

    /** Pulls nearby item entities toward the given player. Called from server tick. */
    public static void pullItems(PlayerEntity player) {
        Box box = player.getBoundingBox().expand(RADIUS);
        List<ItemEntity> items = player.getWorld().getEntitiesByClass(
                ItemEntity.class, box, e -> !e.getStack().isEmpty());
        for (ItemEntity item : items) {
            double dx = player.getX() - item.getX();
            double dy = player.getY() + 1.0 - item.getY();
            double dz = player.getZ() - item.getZ();
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (dist > 0.5) {
                double speed = 0.15;
                item.setVelocity(dx / dist * speed, dy / dist * speed, dz / dist * speed);
                item.velocityModified = true;
            }
        }
    }
}
