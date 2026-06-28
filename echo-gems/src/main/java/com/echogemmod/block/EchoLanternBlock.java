package com.echogemmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Echo Lantern: full-brightness light source (level 15).
 * Any living entity standing on top gains Regeneration I briefly —
 * makes it a useful camp/base decoration with a practical benefit.
 */
public class EchoLanternBlock extends Block {

    public EchoLanternBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient() && entity instanceof LivingEntity living) {
            living.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.REGENERATION, 40, 0, false, false, false));
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
