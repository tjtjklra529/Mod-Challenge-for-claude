package com.echogemmod.block.entity;

import com.echogemmod.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

/**
 * Echo Furnace block entity: identical to a vanilla furnace but uses fuel
 * at half the rate (double efficiency). Cook time is unchanged — the
 * benefit is purely in fuel consumption, making any fuel last 2× longer.
 */
public class EchoFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public EchoFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ECHO_FURNACE, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.echogems.echo_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) * 2;
    }

    @Override
    protected RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategory.FURNACE;
    }
}
