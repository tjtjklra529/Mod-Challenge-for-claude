package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.block.entity.EchoFurnaceBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static BlockEntityType<EchoFurnaceBlockEntity> ECHO_FURNACE;

    public static void register() {
        ECHO_FURNACE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(EchoGemsMod.MOD_ID, "echo_furnace"),
                FabricBlockEntityTypeBuilder
                        .create(EchoFurnaceBlockEntity::new, ModBlocks.ECHO_FURNACE)
                        .build());
    }
}
