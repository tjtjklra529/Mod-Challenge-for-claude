package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ECHO_CRYSTAL_ORE = new Block(
            FabricBlockSettings.copyOf(Blocks.STONE)
                    .strength(3.0f, 3.0f)
                    .requiresTool());

    public static void register() {
        Registry.register(Registries.BLOCK,
                new Identifier(EchoGemsMod.MOD_ID, "echo_crystal_ore"),
                ECHO_CRYSTAL_ORE);
    }
}
