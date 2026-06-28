package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ECHO_GEMS_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.echogems.echo_gems"))
            .icon(() -> new ItemStack(ModItems.ECHO_GEM))
            .entries((ctx, entries) -> {
                // Raw materials
                entries.add(ModItems.ECHO_GEM);
                entries.add(ModItems.VOID_SHARD);
                entries.add(ModItems.CELESTIAL_PRISM);

                // Ores and blocks
                entries.add(ModItems.ECHO_CRYSTAL_ORE_ITEM);
                entries.add(ModItems.VOID_CRYSTAL_ORE_ITEM);
                entries.add(ModItems.CELESTIAL_ORE_ITEM);
                entries.add(ModItems.ECHO_CRYSTAL_BLOCK_ITEM);
                entries.add(ModItems.VOID_CRYSTAL_BLOCK_ITEM);
                entries.add(ModItems.CELESTIAL_BLOCK_ITEM);
                entries.add(ModItems.ECHO_LANTERN_ITEM);
                entries.add(ModItems.VOID_INFUSER_ITEM);

                // Echo armor (T1)
                entries.add(ModItems.ECHO_HELMET);
                entries.add(ModItems.ECHO_CHESTPLATE);
                entries.add(ModItems.ECHO_LEGGINGS);
                entries.add(ModItems.ECHO_BOOTS);

                // Echo tools (T1)
                entries.add(ModItems.ECHO_PICKAXE);
                entries.add(ModItems.ECHO_SWORD);

                // Void tools (T2)
                entries.add(ModItems.VOID_SWORD);
                entries.add(ModItems.VOID_PICKAXE);
                entries.add(ModItems.VOID_AXE);

                // Celestial (T3)
                entries.add(ModItems.CELESTIAL_SWORD);
                entries.add(ModItems.CELESTIAL_STAFF);
                entries.add(ModItems.CELESTIAL_BOOTS);

                // Consumables
                entries.add(ModItems.ECHO_CHARGE);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP,
                new Identifier(EchoGemsMod.MOD_ID, "echo_gems"),
                ECHO_GEMS_GROUP);
    }
}
