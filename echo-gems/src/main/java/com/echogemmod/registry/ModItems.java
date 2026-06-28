package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.item.*;
import com.echogemmod.material.EchoGemsArmorMaterials;
import com.echogemmod.material.EchoGemsMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // ── Raw materials ──────────────────────────────────────────────────────────
    public static final Item ECHO_GEM       = new Item(new FabricItemSettings());
    public static final Item VOID_SHARD     = new Item(new FabricItemSettings());
    public static final Item CELESTIAL_PRISM = new Item(new FabricItemSettings());

    // ── Block items ────────────────────────────────────────────────────────────
    public static final Item ECHO_CRYSTAL_ORE_ITEM   = new BlockItem(ModBlocks.ECHO_CRYSTAL_ORE,   new FabricItemSettings());
    public static final Item VOID_CRYSTAL_ORE_ITEM   = new BlockItem(ModBlocks.VOID_CRYSTAL_ORE,   new FabricItemSettings());
    public static final Item CELESTIAL_ORE_ITEM      = new BlockItem(ModBlocks.CELESTIAL_ORE,      new FabricItemSettings());
    public static final Item ECHO_CRYSTAL_BLOCK_ITEM = new BlockItem(ModBlocks.ECHO_CRYSTAL_BLOCK, new FabricItemSettings());
    public static final Item VOID_CRYSTAL_BLOCK_ITEM = new BlockItem(ModBlocks.VOID_CRYSTAL_BLOCK, new FabricItemSettings());
    public static final Item CELESTIAL_BLOCK_ITEM    = new BlockItem(ModBlocks.CELESTIAL_BLOCK,    new FabricItemSettings());
    public static final Item ECHO_LANTERN_ITEM       = new BlockItem(ModBlocks.ECHO_LANTERN,       new FabricItemSettings());
    public static final Item VOID_INFUSER_ITEM       = new BlockItem(ModBlocks.VOID_INFUSER,       new FabricItemSettings());

    // ── Echo Tier (T1) armor ───────────────────────────────────────────────────
    public static final Item ECHO_HELMET     = new EchoHelmetItem(EchoGemsArmorMaterials.ECHO,    new FabricItemSettings());
    public static final Item ECHO_CHESTPLATE = new EchoChestplateItem(EchoGemsArmorMaterials.ECHO, new FabricItemSettings());
    public static final Item ECHO_LEGGINGS   = new EchoLeggingsItem(EchoGemsArmorMaterials.ECHO,  new FabricItemSettings());
    public static final Item ECHO_BOOTS      = new EchoBootsItem(EchoGemsArmorMaterials.ECHO,     new FabricItemSettings());

    // ── Echo Tier (T1) tools ───────────────────────────────────────────────────
    public static final Item ECHO_PICKAXE    = new EchoPickaxeItem(EchoGemsMaterials.ECHO,  1, -2.8f, new FabricItemSettings());
    public static final Item ECHO_SWORD      = new EchoSwordItem(EchoGemsMaterials.ECHO,    3, -2.4f, new FabricItemSettings());

    // ── Void Tier (T2) tools ───────────────────────────────────────────────────
    public static final Item VOID_SWORD      = new VoidSwordItem(EchoGemsMaterials.VOID,    4, -2.4f, new FabricItemSettings());
    public static final Item VOID_PICKAXE    = new VoidPickaxeItem(EchoGemsMaterials.VOID,  1, -2.8f, new FabricItemSettings());
    public static final Item VOID_AXE        = new VoidAxeItem(EchoGemsMaterials.VOID,      7.0f, -3.1f, new FabricItemSettings());

    // ── Celestial Tier (T3) ────────────────────────────────────────────────────
    public static final Item CELESTIAL_SWORD  = new CelestialSwordItem(EchoGemsMaterials.CELESTIAL, 5, -2.4f, new FabricItemSettings());
    public static final Item CELESTIAL_STAFF  = new CelestialStaffItem(new FabricItemSettings());
    public static final Item CELESTIAL_BOOTS  = new CelestialBootsItem(EchoGemsArmorMaterials.CELESTIAL, new FabricItemSettings());

    // ── Consumables ────────────────────────────────────────────────────────────
    public static final Item ECHO_CHARGE = new EchoChargeItem(new FabricItemSettings().maxCount(16));

    public static void register() {
        // Materials
        reg("echo_gem",        ECHO_GEM);
        reg("void_shard",      VOID_SHARD);
        reg("celestial_prism", CELESTIAL_PRISM);

        // Block items
        reg("echo_crystal_ore",   ECHO_CRYSTAL_ORE_ITEM);
        reg("void_crystal_ore",   VOID_CRYSTAL_ORE_ITEM);
        reg("celestial_ore",      CELESTIAL_ORE_ITEM);
        reg("echo_crystal_block", ECHO_CRYSTAL_BLOCK_ITEM);
        reg("void_crystal_block", VOID_CRYSTAL_BLOCK_ITEM);
        reg("celestial_block",    CELESTIAL_BLOCK_ITEM);
        reg("echo_lantern",       ECHO_LANTERN_ITEM);
        reg("void_infuser",       VOID_INFUSER_ITEM);

        // Echo armor
        reg("echo_helmet",     ECHO_HELMET);
        reg("echo_chestplate", ECHO_CHESTPLATE);
        reg("echo_leggings",   ECHO_LEGGINGS);
        reg("echo_boots",      ECHO_BOOTS);

        // Echo tools
        reg("echo_pickaxe",    ECHO_PICKAXE);
        reg("echo_sword",      ECHO_SWORD);

        // Void tools
        reg("void_sword",      VOID_SWORD);
        reg("void_pickaxe",    VOID_PICKAXE);
        reg("void_axe",        VOID_AXE);

        // Celestial
        reg("celestial_sword", CELESTIAL_SWORD);
        reg("celestial_staff", CELESTIAL_STAFF);
        reg("celestial_boots", CELESTIAL_BOOTS);

        // Consumables
        reg("echo_charge",     ECHO_CHARGE);
    }

    private static void reg(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(EchoGemsMod.MOD_ID, name), item);
    }
}
