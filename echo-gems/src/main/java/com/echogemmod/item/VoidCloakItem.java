package com.echogemmod.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * Void Cloak: right-click to wrap yourself in void energy for 30 seconds —
 * grants Invisibility II, Fire Resistance, and Speed I.
 * 60-second cooldown. Loses 1 durability per activation.
 */
public class VoidCloakItem extends Item {

    public VoidCloakItem(Settings settings) {
        super(settings.maxDamage(64));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient()) return TypedActionResult.success(stack);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,   600, 1));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,           600, 0));

        world.playSound(null, user.getBlockPos(),
                SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 0.6f, 0.5f);

        user.sendMessage(Text.literal("§5[Void Cloak] §fShrouded in void for 30 seconds."), true);
        stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        user.getItemCooldownManager().set(this, 1200);
        return TypedActionResult.success(stack);
    }
}
