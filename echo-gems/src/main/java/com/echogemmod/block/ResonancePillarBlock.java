package com.echogemmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Resonance Pillar: decorative pillar made from Echo Crystal Blocks.
 * Emits cascading END_ROD and ENCHANT particles from its top face.
 */
public class ResonancePillarBlock extends PillarBlock {

    public ResonancePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;

        if (random.nextFloat() < 0.6f) {
            world.addParticle(ParticleTypes.END_ROD,
                    x + (random.nextDouble() - 0.5) * 0.4,
                    y,
                    z + (random.nextDouble() - 0.5) * 0.4,
                    (random.nextDouble() - 0.5) * 0.04,
                    random.nextDouble() * 0.08 + 0.02,
                    (random.nextDouble() - 0.5) * 0.04);
        }
        if (random.nextFloat() < 0.25f) {
            world.addParticle(ParticleTypes.ENCHANT,
                    x + (random.nextDouble() - 0.5),
                    y - random.nextDouble(),
                    z + (random.nextDouble() - 0.5),
                    0, 0.1, 0);
        }
    }
}
