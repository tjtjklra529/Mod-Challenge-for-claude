package com.echogemmod.material;

import com.echogemmod.registry.ModItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

public enum EchoGemsArmorMaterials implements ArmorMaterial {

    // Protection indexed as [HELMET=0, CHESTPLATE=1, LEGGINGS=2, BOOTS=3]
    ECHO    ("echo",     20, new int[]{3, 6, 7, 3},  12, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,  2.0f, 0.0f, () -> Ingredient.ofItems(ModItems.ECHO_GEM)),
    VOID    ("void",     30, new int[]{4, 7, 8, 4},  15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f, () -> Ingredient.ofItems(ModItems.VOID_SHARD)),
    CELESTIAL("celestial",40, new int[]{5, 9, 10, 5}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,  4.0f, 0.2f, () -> Ingredient.ofItems(ModItems.CELESTIAL_PRISM));

    // Durability base: [BOOTS=0: 11, LEGS=1: 16, CHEST=2: 15, HEAD=3: 13]
    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    EchoGemsArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts,
                           int enchantability, SoundEvent equipSound,
                           float toughness, float knockbackResistance,
                           Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override public int getEnchantability()          { return enchantability; }
    @Override public SoundEvent getEquipSound()       { return equipSound; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
    @Override public String getName()                 { return name; }
    @Override public float getToughness()             { return toughness; }
    @Override public float getKnockbackResistance()   { return knockbackResistance; }
}
