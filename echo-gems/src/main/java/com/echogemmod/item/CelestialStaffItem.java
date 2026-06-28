package com.echogemmod.item;

import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Celestial Staff: right-click to launch a fireball in the direction you are looking.
 * Has a 20-tick (1 second) cooldown to prevent spam.
 */
public class CelestialStaffItem extends Item {

    public CelestialStaffItem(Settings settings) {
        super(settings.maxDamage(256));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            Vec3d look = user.getRotationVector();
            double speed = 1.5;
            SmallFireballEntity fireball = new SmallFireballEntity(
                    world, user,
                    look.x * speed, look.y * speed, look.z * speed);
            fireball.setPosition(
                    user.getX() + look.x * 1.5,
                    user.getEyeY(),
                    user.getZ() + look.z * 1.5);
            fireball.explosionPower = 2;
            world.spawnEntity(fireball);

            world.playSound(null, user.getBlockPos(),
                    SoundEvents.ENTITY_BLAZE_SHOOT,
                    SoundCategory.PLAYERS, 0.8f, 1.2f);

            stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        }

        user.getItemCooldownManager().set(this, 20);
        return TypedActionResult.success(stack);
    }
}
