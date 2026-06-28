package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static Potion ECHO_VISION_POTION = new Potion(
            new StatusEffectInstance(ModEffects.ECHO_VISION, 3600, 0)); // 3 minutes

    public static Potion ECHO_VISION_LONG_POTION = new Potion(
            new StatusEffectInstance(ModEffects.ECHO_VISION, 7200, 0)); // 6 minutes

    public static void register() {
        ECHO_VISION_POTION = Registry.register(Registries.POTION,
                new Identifier(EchoGemsMod.MOD_ID, "echo_vision"),
                ECHO_VISION_POTION);

        ECHO_VISION_LONG_POTION = Registry.register(Registries.POTION,
                new Identifier(EchoGemsMod.MOD_ID, "long_echo_vision"),
                ECHO_VISION_LONG_POTION);

        // Awkward Potion + Echo Gem → Echo Vision Potion (3 min)
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                Potions.AWKWARD,
                ModItems.ECHO_GEM,
                ECHO_VISION_POTION);

        // Echo Vision Potion + Redstone → Long Echo Vision (6 min)
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                ECHO_VISION_POTION,
                Items.REDSTONE,
                ECHO_VISION_LONG_POTION);
    }
}
