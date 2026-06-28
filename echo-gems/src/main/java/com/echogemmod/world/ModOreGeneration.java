package com.echogemmod.world;

import com.echogemmod.EchoGemsMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

public class ModOreGeneration {

    public static void generateOres() {
        // Overworld: Echo Crystal Ore (Y -64 → 32)
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        new Identifier(EchoGemsMod.MOD_ID, "echo_crystal_ore_placed")));

        // Nether: Void Crystal Ore (all nether biomes)
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        new Identifier(EchoGemsMod.MOD_ID, "void_crystal_ore_placed")));

        // End: Celestial Ore (End highlands / main island)
        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                        new Identifier(EchoGemsMod.MOD_ID, "celestial_ore_placed")));
    }
}
