package com.echogemmod.material;

import com.echogemmod.registry.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum EchoGemsMaterials implements ToolMaterial {

    ECHO(3, 1200, 8.0f, 3.0f, 16, () -> Ingredient.ofItems(ModItems.ECHO_GEM)),
    VOID(4, 2000, 9.5f, 4.0f, 20, () -> Ingredient.ofItems(ModItems.VOID_SHARD)),
    CELESTIAL(5, 3500, 12.0f, 5.5f, 24, () -> Ingredient.ofItems(ModItems.CELESTIAL_PRISM));

    private final int miningLevel;
    private final int durability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    EchoGemsMaterials(int miningLevel, int durability, float miningSpeed,
                      float attackDamage, int enchantability,
                      Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override public int getDurability()              { return durability; }
    @Override public float getMiningSpeedMultiplier() { return miningSpeed; }
    @Override public float getAttackDamage()          { return attackDamage; }
    @Override public int getMiningLevel()             { return miningLevel; }
    @Override public int getEnchantability()          { return enchantability; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
}
