package com.echogemmod.entity;

import com.echogemmod.registry.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

/**
 * Celestial Arrow: heals the shooter for 3 health on a successful hit
 * and spawns a starburst particle effect at the impact point.
 */
public class CelestialArrowEntity extends ArrowEntity {

    public CelestialArrowEntity(EntityType<? extends ArrowEntity> type, World world) {
        super(type, world);
    }

    public CelestialArrowEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.CELESTIAL_ARROW, owner, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity shooter) {
            shooter.heal(3.0f);
        }
        if (this.getWorld() instanceof ServerWorld sw) {
            sw.spawnParticles(ParticleTypes.END_ROD,
                    entityHitResult.getPos().x,
                    entityHitResult.getPos().y,
                    entityHitResult.getPos().z,
                    12, 0.3, 0.3, 0.3, 0.15);
        }
    }
}
