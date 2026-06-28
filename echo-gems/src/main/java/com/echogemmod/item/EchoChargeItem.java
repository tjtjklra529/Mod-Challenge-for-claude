package com.echogemmod.item;

import net.minecraft.entity.TntEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Echo Charge: a throwable grenade made from Echo Gems.
 * Right-click to hurl a primed TNT forward with a 2.5-second fuse.
 * Consumed on use (not in creative mode).
 */
public class EchoChargeItem extends Item {

    public EchoChargeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            Vec3d look = user.getRotationVector();
            TntEntity tnt = new TntEntity(world,
                    user.getX() + look.x,
                    user.getEyeY(),
                    user.getZ() + look.z,
                    user);
            tnt.setFuse(50); // 2.5 seconds
            tnt.setVelocity(look.x * 1.8, look.y * 1.8 + 0.15, look.z * 1.8);
            world.spawnEntity(tnt);

            world.playSound(null, user.getBlockPos(),
                    SoundEvents.ENTITY_TNT_PRIMED,
                    SoundCategory.PLAYERS, 1.0f, 1.0f);

            if (!user.isCreative()) {
                stack.decrement(1);
            }
        }

        return TypedActionResult.success(stack);
    }
}
