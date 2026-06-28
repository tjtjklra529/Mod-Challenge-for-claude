package com.echogemmod.item;

import com.echogemmod.entity.EchoArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EchoArrowItem extends ArrowItem {
    public EchoArrowItem(Settings settings) { super(settings); }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new EchoArrowEntity(world, shooter);
    }
}
