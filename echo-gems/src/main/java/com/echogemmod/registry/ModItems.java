package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.item.EchoBootsItem;
import com.echogemmod.item.EchoPickaxeItem;
import com.echogemmod.item.EchoSwordItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item ECHO_GEM = new Item(new FabricItemSettings());

    public static final Item ECHO_CRYSTAL_ORE_ITEM = new BlockItem(
            ModBlocks.ECHO_CRYSTAL_ORE, new FabricItemSettings());

    public static final Item ECHO_PICKAXE = new EchoPickaxeItem(
            ToolMaterials.DIAMOND, 1, -2.8f, new FabricItemSettings());

    public static final Item ECHO_SWORD = new EchoSwordItem(
            ToolMaterials.DIAMOND, 3, -2.4f, new FabricItemSettings());

    public static final Item ECHO_BOOTS = new EchoBootsItem(
            ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS, new FabricItemSettings());

    public static void register() {
        Registry.register(Registries.ITEM,
                new Identifier(EchoGemsMod.MOD_ID, "echo_gem"), ECHO_GEM);
        Registry.register(Registries.ITEM,
                new Identifier(EchoGemsMod.MOD_ID, "echo_crystal_ore"), ECHO_CRYSTAL_ORE_ITEM);
        Registry.register(Registries.ITEM,
                new Identifier(EchoGemsMod.MOD_ID, "echo_pickaxe"), ECHO_PICKAXE);
        Registry.register(Registries.ITEM,
                new Identifier(EchoGemsMod.MOD_ID, "echo_sword"), ECHO_SWORD);
        Registry.register(Registries.ITEM,
                new Identifier(EchoGemsMod.MOD_ID, "echo_boots"), ECHO_BOOTS);
    }
}
