package com.echogemmod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Echo Mirror: records your last death position automatically.
 * Right-click to teleport back to where you died and reclaim your items.
 *
 * Death coordinates are saved by EchoGemsMod into this item's NBT.
 * Keys: DeathX, DeathY, DeathZ (doubles), DeathDim (string).
 */
public class EchoMirrorItem extends Item {

    public static final String KEY_X   = "DeathX";
    public static final String KEY_Y   = "DeathY";
    public static final String KEY_Z   = "DeathZ";
    public static final String KEY_DIM = "DeathDim";

    public EchoMirrorItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) return TypedActionResult.success(stack);

        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(KEY_X)) {
            user.sendMessage(Text.literal("§c[Echo Mirror] §fNo death location recorded yet."), true);
            return TypedActionResult.fail(stack);
        }

        double tx   = nbt.getDouble(KEY_X);
        double ty   = nbt.getDouble(KEY_Y);
        double tz   = nbt.getDouble(KEY_Z);
        String dim  = nbt.getString(KEY_DIM);
        String here = world.getRegistryKey().getValue().toString();

        if (!dim.equals(here)) {
            user.sendMessage(Text.literal(
                "§c[Echo Mirror] §fDeath was in a different dimension (" + dim + ")."), true);
            return TypedActionResult.fail(stack);
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;
        ServerWorld serverWorld = (ServerWorld) world;

        // Ensure landing spot is safe (try up to 3 blocks above)
        BlockPos landing = new BlockPos((int) tx, (int) ty, (int) tz);
        for (int dy = 0; dy < 3; dy++) {
            BlockPos check = landing.up(dy);
            if (serverWorld.getBlockState(check).isAir()
                    && serverWorld.getBlockState(check.up()).isAir()) {
                landing = check;
                break;
            }
        }

        serverPlayer.teleport(serverWorld, tx, landing.getY(), tz,
                user.getYaw(), user.getPitch());

        world.playSound(null, user.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.PLAYERS, 1.0f, 1.0f);

        user.sendMessage(Text.literal(
            String.format("§b[Echo Mirror] §fTeleported to death site at §e%.0f, %.0f, %.0f§f.",
                tx, ty, tz)), false);

        // 30-second cooldown
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(stack);
    }

    /** Called by EchoGemsMod when the player dies. */
    public static void recordDeath(ItemStack mirror, double x, double y, double z, String dimension) {
        NbtCompound nbt = mirror.getOrCreateNbt();
        nbt.putDouble(KEY_X, x);
        nbt.putDouble(KEY_Y, y);
        nbt.putDouble(KEY_Z, z);
        nbt.putString(KEY_DIM, dimension);
    }
}
