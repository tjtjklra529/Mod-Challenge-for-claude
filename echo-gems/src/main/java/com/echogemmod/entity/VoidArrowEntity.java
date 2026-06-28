package com.echogemmod.entity;

import com.echogemmod.registry.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

/**
 * Void Arrow: inflicts Wither II for 5 seconds on the entity it hits.
 */
public class VoidArrowEntity extends ArrowEntity {

    public VoidArrowEntity(EntityType<? extends ArrowEntity> type, World world) {
        super(type, world);
    }

    public VoidArrowEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.VOID_ARROW, owner, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity target) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1));
        }
    }
}
