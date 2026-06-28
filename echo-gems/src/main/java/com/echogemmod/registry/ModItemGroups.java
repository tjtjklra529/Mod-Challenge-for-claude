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
                entries.add(ModItems.ECHO_GEM);
                entries.add(ModItems.ECHO_CRYSTAL_ORE_ITEM);
                entries.add(ModItems.ECHO_PICKAXE);
                entries.add(ModItems.ECHO_SWORD);
                entries.add(ModItems.ECHO_BOOTS);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP,
                new Identifier(EchoGemsMod.MOD_ID, "echo_gems"),
                ECHO_GEMS_GROUP);
    }
}
