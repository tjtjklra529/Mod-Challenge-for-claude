package com.echogemmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Echo Crystal Block: a decorative storage block (9 Echo Gems → 1 block).
 * Emits light level 7 and occasionally spawns ambient sparkle particles.
 */
public class EchoCrystalBlock extends Block {

    public EchoCrystalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.3f) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.END_ROD, x, y, z,
                    (random.nextDouble() - 0.5) * 0.05,
                    random.nextDouble() * 0.05,
                    (random.nextDouble() - 0.5) * 0.05);
        }
    }
}
