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
                // Materials
                entries.add(ModItems.ECHO_GEM);
                entries.add(ModItems.VOID_SHARD);
                entries.add(ModItems.CELESTIAL_PRISM);
                // Ores & blocks
                entries.add(ModItems.ECHO_CRYSTAL_ORE_ITEM);
                entries.add(ModItems.VOID_CRYSTAL_ORE_ITEM);
                entries.add(ModItems.CELESTIAL_ORE_ITEM);
                entries.add(ModItems.ECHO_CRYSTAL_BLOCK_ITEM);
                entries.add(ModItems.VOID_CRYSTAL_BLOCK_ITEM);
                entries.add(ModItems.CELESTIAL_BLOCK_ITEM);
                entries.add(ModItems.ECHO_LANTERN_ITEM);
                entries.add(ModItems.VOID_INFUSER_ITEM);
                // T1 Echo
                entries.add(ModItems.ECHO_HELMET);
                entries.add(ModItems.ECHO_CHESTPLATE);
                entries.add(ModItems.ECHO_LEGGINGS);
                entries.add(ModItems.ECHO_BOOTS);
                entries.add(ModItems.ECHO_PICKAXE);
                entries.add(ModItems.ECHO_SWORD);
                // T2 Void
                entries.add(ModItems.VOID_HELMET);
                entries.add(ModItems.VOID_CHESTPLATE);
                entries.add(ModItems.VOID_LEGGINGS);
                entries.add(ModItems.VOID_BOOTS);
                entries.add(ModItems.VOID_SWORD);
                entries.add(ModItems.VOID_PICKAXE);
                entries.add(ModItems.VOID_AXE);
                // T3 Celestial
                entries.add(ModItems.CELESTIAL_HELMET);
                entries.add(ModItems.CELESTIAL_CHESTPLATE);
                entries.add(ModItems.CELESTIAL_LEGGINGS);
                entries.add(ModItems.CELESTIAL_BOOTS);
                entries.add(ModItems.CELESTIAL_SWORD);
                entries.add(ModItems.CELESTIAL_STAFF);
                // Specials
                entries.add(ModItems.ECHO_CHARGE);
                entries.add(ModItems.ECHO_MIRROR);
                entries.add(ModItems.ECHO_MAGNET);
                entries.add(ModItems.VOID_BOMB);
                entries.add(ModItems.VOID_CLOAK);
                entries.add(ModItems.CELESTIAL_TOTEM);
                // Arrows
                entries.add(ModItems.ECHO_ARROW);
                entries.add(ModItems.VOID_ARROW);
                entries.add(ModItems.CELESTIAL_ARROW);
                // Food
                entries.add(ModItems.ECHO_CANDY);
                entries.add(ModItems.VOID_TRUFFLE);
                entries.add(ModItems.CELESTIAL_FRUIT);
                // Extra tools & blocks
                entries.add(ModItems.ECHO_SHOVEL);
                entries.add(ModItems.ECHO_FURNACE_ITEM);
                entries.add(ModItems.RESONANCE_PILLAR_ITEM);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP,
                new Identifier(EchoGemsMod.MOD_ID, "echo_gems"),
                ECHO_GEMS_GROUP);
    }
}
