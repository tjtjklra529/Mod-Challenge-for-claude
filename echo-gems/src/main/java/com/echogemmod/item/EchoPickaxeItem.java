package com.echogemmod.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Echo Pickaxe: while sneaking, mines a full 3x3x3 area around the broken block.
 * Each extra block mined also damages the tool by 1, so durability matters.
 */
public class EchoPickaxeItem extends PickaxeItem {

    public EchoPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state,
                            BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && miner instanceof PlayerEntity player && player.isSneaking()) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        BlockPos neighbor = pos.add(dx, dy, dz);
                        BlockState neighborState = world.getBlockState(neighbor);
                        if (!neighborState.isAir()
                                && neighborState.getHardness(world, neighbor) >= 0f
                                && this.isSuitableFor(neighborState)) {
                            ((ServerWorld) world).breakBlock(neighbor, true, miner);
                            stack.damage(1, player,
                                    p -> p.sendToolBreakStatus(player.getActiveHand()));
                        }
                    }
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
