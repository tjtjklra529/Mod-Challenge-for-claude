package com.echogemmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Void Axe: when a log is broken, performs a flood-fill tree fell —
 * up to 128 connected logs of the same type are all broken at once.
 * Each extra log costs 1 durability, so enchanting with Unbreaking pays off.
 */
public class VoidAxeItem extends AxeItem {

    private static final int MAX_LOGS = 128;

    public VoidAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state,
                            BlockPos origin, LivingEntity miner) {
        if (!world.isClient()
                && miner instanceof PlayerEntity player
                && state.isIn(BlockTags.LOGS)) {
            Block logBlock = state.getBlock();
            Set<BlockPos> visited = new HashSet<>();
            Queue<BlockPos> queue = new ArrayDeque<>();
            queue.add(origin);
            visited.add(origin);

            while (!queue.isEmpty() && visited.size() <= MAX_LOGS) {
                BlockPos current = queue.poll();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            BlockPos neighbor = current.add(dx, dy, dz);
                            if (!visited.contains(neighbor)
                                    && world.getBlockState(neighbor).getBlock() == logBlock) {
                                visited.add(neighbor);
                                queue.add(neighbor);
                            }
                        }
                    }
                }
            }

            visited.remove(origin); // already broken by the game
            for (BlockPos logPos : visited) {
                ((ServerWorld) world).breakBlock(logPos, true, miner);
                stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
                if (stack.isEmpty()) break;
            }
        }
        return super.postMine(stack, world, state, origin, miner);
    }
}
