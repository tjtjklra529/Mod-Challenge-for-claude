package com.echogemmod.entity;

import com.echogemmod.registry.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

/**
 * Echo Arrow: applies extreme knockback to the hit entity (3× vanilla).
 * Useful for keeping crowds at bay.
 */
public class EchoArrowEntity extends ArrowEntity {

    public EchoArrowEntity(EntityType<? extends ArrowEntity> type, World world) {
        super(type, world);
    }

    public EchoArrowEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.ECHO_ARROW, owner, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity target) {
            // Apply strong knockback by boosting velocity away from shooter
            double dx = target.getX() - this.getX();
            double dz = target.getZ() - this.getZ();
            double len = Math.sqrt(dx * dx + dz * dz);
            if (len > 0) {
                target.addVelocity(dx / len * 2.5, 0.5, dz / len * 2.5);
                target.velocityModified = true;
            }
        }
    }
}
