package com.echogemmod.block;

import com.echogemmod.registry.ModEffects;
import com.echogemmod.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Void Infuser: an altar block that can be right-clicked to apply special infusions.
 *
 * Interactions:
 *  • Echo Gem       → applies Echo Vision (Night Vision + Glowing) for 3 minutes
 *  • Void Shard     → applies Strength II for 2 minutes
 *  • Celestial Prism → applies Absorption III + Resistance II for 1 minute
 *
 * Each infusion consumes 1 of the held item (not in creative).
 */
public class VoidInfuserBlock extends Block {

    public VoidInfuserBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.SUCCESS;

        ItemStack held = player.getStackInHand(hand);

        if (held.isOf(ModItems.ECHO_GEM)) {
            player.addStatusEffect(new StatusEffectInstance(ModEffects.ECHO_VISION, 3600, 0));
            consumeItem(player, held);
            spawnParticlesAndSound(world, pos, player, "§b[Echo Infuser] §fEcho Vision granted for 3 minutes!");

        } else if (held.isOf(ModItems.VOID_SHARD)) {
            player.addStatusEffect(new StatusEffectInstance(
                    net.minecraft.entity.effect.StatusEffects.STRENGTH, 2400, 1));
            consumeItem(player, held);
            spawnParticlesAndSound(world, pos, player, "§5[Echo Infuser] §fStrength II granted for 2 minutes!");

        } else if (held.isOf(ModItems.CELESTIAL_PRISM)) {
            player.addStatusEffect(new StatusEffectInstance(
                    net.minecraft.entity.effect.StatusEffects.ABSORPTION, 1200, 2));
            player.addStatusEffect(new StatusEffectInstance(
                    net.minecraft.entity.effect.StatusEffects.RESISTANCE, 1200, 1));
            consumeItem(player, held);
            spawnParticlesAndSound(world, pos, player, "§e[Echo Infuser] §fAbsorption III + Resistance II granted!");

        } else {
            player.sendMessage(Text.literal("§7[Echo Infuser] §fInsert an Echo Gem, Void Shard, or Celestial Prism."), true);
        }

        return ActionResult.SUCCESS;
    }

    private void consumeItem(PlayerEntity player, ItemStack stack) {
        if (!player.isCreative()) stack.decrement(1);
    }

    private void spawnParticlesAndSound(World world, BlockPos pos, PlayerEntity player, String msg) {
        world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.BLOCKS, 0.8f, 1.4f);
        player.sendMessage(Text.literal(msg), true);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 3; i++) {
            world.addParticle(ParticleTypes.PORTAL,
                    pos.getX() + random.nextDouble(),
                    pos.getY() + 1.0,
                    pos.getZ() + random.nextDouble(),
                    0, 0.1, 0);
        }
    }
}
