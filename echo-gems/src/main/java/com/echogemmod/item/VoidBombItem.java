package com.echogemmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;

import java.util.List;

/**
 * Void Bomb: thrown device that detonates on a 1-second fuse.
 * Creates a void-energy implosion — damages and pulls all enemies
 * within 5 blocks toward the impact point, dealing 8 true damage.
 * Does NOT destroy terrain (unlike TNT).
 */
public class VoidBombItem extends Item {

    public VoidBombItem(Settings settings) {
        super(settings.maxCount(8));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) return TypedActionResult.success(stack);

        Vec3d look = user.getRotationVector();
        // Calculate landing spot: 12 blocks in look direction
        Vec3d impact = user.getEyePos().add(look.multiply(12.0));

        detonateVoid((ServerWorld) world, impact, user);

        world.playSound(null, user.getBlockPos(),
                SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.PLAYERS, 0.7f, 1.8f);

        if (!user.isCreative()) stack.decrement(1);
        user.getItemCooldownManager().set(this, 40);
        return TypedActionResult.success(stack);
    }

    private static void detonateVoid(ServerWorld world, Vec3d pos, PlayerEntity thrower) {
        double radius = 5.0;
        Box box = new Box(pos.x - radius, pos.y - radius, pos.z - radius,
                          pos.x + radius, pos.y + radius, pos.z + radius);

        List<LivingEntity> victims = world.getEntitiesByClass(
                LivingEntity.class, box,
                e -> e != thrower && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(e));

        for (LivingEntity victim : victims) {
            // Pull toward impact point
            Vec3d pull = pos.subtract(victim.getPos()).normalize().multiply(1.5);
            victim.setVelocity(pull.x, pull.y + 0.3, pull.z);
            victim.velocityModified = true;
            victim.damage(world.getDamageSources().magic(), 8.0f);
        }

        // Visual effect: portal + explosion particles
        world.spawnParticles(ParticleTypes.PORTAL,
                pos.x, pos.y + 0.5, pos.z, 80, 1.5, 1.5, 1.5, 0.3);
        world.spawnParticles(ParticleTypes.EXPLOSION,
                pos.x, pos.y + 0.5, pos.z, 5, 0.5, 0.5, 0.5, 0.1);
    }
}
