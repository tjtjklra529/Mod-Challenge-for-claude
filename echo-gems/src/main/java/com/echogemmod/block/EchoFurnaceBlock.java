package com.echogemmod.block;

import com.echogemmod.block.entity.EchoFurnaceBlockEntity;
import com.echogemmod.registry.ModBlockEntities;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Echo Furnace: a furnace with 2× fuel efficiency.
 * Uses the vanilla furnace GUI (FurnaceScreenHandler) and smelting recipe type.
 */
public class EchoFurnaceBlock extends AbstractFurnaceBlock {

    public EchoFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void interact(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof EchoFurnaceBlockEntity be) {
            player.openHandledScreen(be);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EchoFurnaceBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.ECHO_FURNACE,
                world.isClient ? EchoFurnaceBlockEntity::clientTick
                               : EchoFurnaceBlockEntity::serverTick);
    }
}
