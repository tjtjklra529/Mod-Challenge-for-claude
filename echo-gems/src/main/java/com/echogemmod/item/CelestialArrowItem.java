package com.echogemmod.item;

import com.echogemmod.entity.CelestialArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CelestialArrowItem extends ArrowItem {
    public CelestialArrowItem(Settings settings) { super(settings); }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new CelestialArrowEntity(world, shooter);
    }
}
