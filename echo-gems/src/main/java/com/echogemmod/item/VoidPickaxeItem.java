package com.echogemmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Void Pickaxe: has a 50% chance to double the ore/stone drops from each block mined.
 * Works by fetching the loot table drops again and scattering extra items.
 */
public class VoidPickaxeItem extends PickaxeItem {

    public VoidPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state,
                            BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && miner instanceof PlayerEntity && this.isSuitableFor(state)) {
            if (world.random.nextFloat() < 0.5f) {
                List<ItemStack> bonus = Block.getDroppedStacks(
                        state, (ServerWorld) world, pos, null, miner, stack);
                for (ItemStack drop : bonus) {
                    Block.dropStack(world, pos, drop);
                }
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
