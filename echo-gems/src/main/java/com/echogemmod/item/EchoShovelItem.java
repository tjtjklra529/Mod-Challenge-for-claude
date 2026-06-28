package com.echogemmod.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Echo Shovel: while sneaking, excavates a flat 5×5 area (same Y-level) around
 * the broken block — perfect for clearing large flat surfaces fast.
 * Each extra block costs 1 durability.
 */
public class EchoShovelItem extends ShovelItem {

    public EchoShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state,
                            BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && miner instanceof PlayerEntity player && player.isSneaking()) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (dx == 0 && dz == 0) continue;
                    BlockPos adj = pos.add(dx, 0, dz);
                    BlockState adjState = world.getBlockState(adj);
                    if (!adjState.isAir() && this.isSuitableFor(adjState)) {
                        ((ServerWorld) world).breakBlock(adj, true, miner);
                        stack.damage(1, player,
                                p -> p.sendToolBreakStatus(player.getActiveHand()));
                        if (stack.isEmpty()) return true;
                    }
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
