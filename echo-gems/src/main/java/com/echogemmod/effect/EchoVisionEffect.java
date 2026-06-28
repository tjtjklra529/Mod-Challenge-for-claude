package com.echogemmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Echo Vision: grants Night Vision and Glowing (entity outlines) while active.
 * Applied by drinking an Echo Potion or right-clicking the Void Infuser with an Echo Gem.
 */
public class EchoVisionEffect extends StatusEffect {

    public EchoVisionEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x00D4B8);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.NIGHT_VISION, 300, 0, true, false, false));
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.GLOWING, 300, 0, true, false, false));
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 200 == 0;
    }
}
