package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.item.*;
import com.echogemmod.material.EchoGemsArmorMaterials;
import com.echogemmod.material.EchoGemsMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // ── Raw materials ─────────────────────────────────────────────────────────
    public static final Item ECHO_GEM        = new Item(new FabricItemSettings());
    public static final Item VOID_SHARD      = new Item(new FabricItemSettings());
    public static final Item CELESTIAL_PRISM = new Item(new FabricItemSettings());

    // ── Block items ───────────────────────────────────────────────────────────
    public static final Item ECHO_CRYSTAL_ORE_ITEM   = new BlockItem(ModBlocks.ECHO_CRYSTAL_ORE,   new FabricItemSettings());
    public static final Item VOID_CRYSTAL_ORE_ITEM   = new BlockItem(ModBlocks.VOID_CRYSTAL_ORE,   new FabricItemSettings());
    public static final Item CELESTIAL_ORE_ITEM      = new BlockItem(ModBlocks.CELESTIAL_ORE,      new FabricItemSettings());
    public static final Item ECHO_CRYSTAL_BLOCK_ITEM = new BlockItem(ModBlocks.ECHO_CRYSTAL_BLOCK, new FabricItemSettings());
    public static final Item VOID_CRYSTAL_BLOCK_ITEM = new BlockItem(ModBlocks.VOID_CRYSTAL_BLOCK, new FabricItemSettings());
    public static final Item CELESTIAL_BLOCK_ITEM    = new BlockItem(ModBlocks.CELESTIAL_BLOCK,    new FabricItemSettings());
    public static final Item ECHO_LANTERN_ITEM       = new BlockItem(ModBlocks.ECHO_LANTERN,       new FabricItemSettings());
    public static final Item VOID_INFUSER_ITEM       = new BlockItem(ModBlocks.VOID_INFUSER,       new FabricItemSettings());
    public static final Item ECHO_FURNACE_ITEM       = new BlockItem(ModBlocks.ECHO_FURNACE,       new FabricItemSettings());
    public static final Item RESONANCE_PILLAR_ITEM   = new BlockItem(ModBlocks.RESONANCE_PILLAR,   new FabricItemSettings());

    // ── Echo Tier (T1) armor ──────────────────────────────────────────────────
    public static final Item ECHO_HELMET     = new EchoHelmetItem    (EchoGemsArmorMaterials.ECHO, new FabricItemSettings());
    public static final Item ECHO_CHESTPLATE = new EchoChestplateItem(EchoGemsArmorMaterials.ECHO, new FabricItemSettings());
    public static final Item ECHO_LEGGINGS   = new EchoLeggingsItem  (EchoGemsArmorMaterials.ECHO, new FabricItemSettings());
    public static final Item ECHO_BOOTS      = new EchoBootsItem     (EchoGemsArmorMaterials.ECHO, new FabricItemSettings());

    // ── Echo Tier (T1) tools ──────────────────────────────────────────────────
    public static final Item ECHO_PICKAXE = new EchoPickaxeItem(EchoGemsMaterials.ECHO, 1, -2.8f, new FabricItemSettings());
    public static final Item ECHO_SWORD   = new EchoSwordItem  (EchoGemsMaterials.ECHO, 3, -2.4f, new FabricItemSettings());
    public static final Item ECHO_SHOVEL  = new EchoShovelItem (EchoGemsMaterials.ECHO, 1.5f, -3.0f, new FabricItemSettings());

    // ── Void Tier (T2) armor ─────────────────────────────────────────────────
    public static final Item VOID_HELMET     = new VoidHelmetItem    (EchoGemsArmorMaterials.VOID, new FabricItemSettings());
    public static final Item VOID_CHESTPLATE = new VoidChestplateItem(EchoGemsArmorMaterials.VOID, new FabricItemSettings());
    public static final Item VOID_LEGGINGS   = new VoidLeggingsItem  (EchoGemsArmorMaterials.VOID, new FabricItemSettings());
    public static final Item VOID_BOOTS      = new EchoBootsItem     (EchoGemsArmorMaterials.VOID, new FabricItemSettings());

    // ── Void Tier (T2) tools ─────────────────────────────────────────────────
    public static final Item VOID_SWORD   = new VoidSwordItem  (EchoGemsMaterials.VOID, 4, -2.4f, new FabricItemSettings());
    public static final Item VOID_PICKAXE = new VoidPickaxeItem(EchoGemsMaterials.VOID, 1, -2.8f, new FabricItemSettings());
    public static final Item VOID_AXE     = new VoidAxeItem    (EchoGemsMaterials.VOID, 7.0f, -3.1f, new FabricItemSettings());

    // ── Celestial Tier (T3) armor ─────────────────────────────────────────────
    public static final Item CELESTIAL_HELMET     = new CelestialHelmetItem    (EchoGemsArmorMaterials.CELESTIAL, new FabricItemSettings());
    public static final Item CELESTIAL_CHESTPLATE = new CelestialChestplateItem(EchoGemsArmorMaterials.CELESTIAL, new FabricItemSettings());
    public static final Item CELESTIAL_LEGGINGS   = new CelestialLeggingsItem  (EchoGemsArmorMaterials.CELESTIAL, new FabricItemSettings());
    public static final Item CELESTIAL_BOOTS      = new CelestialBootsItem     (EchoGemsArmorMaterials.CELESTIAL, new FabricItemSettings());

    // ── Celestial Tier (T3) tools ─────────────────────────────────────────────
    public static final Item CELESTIAL_SWORD = new CelestialSwordItem(EchoGemsMaterials.CELESTIAL, 5, -2.4f, new FabricItemSettings());
    public static final Item CELESTIAL_STAFF = new CelestialStaffItem(new FabricItemSettings());

    // ── Arrows ───────────────────────────────────────────────────────────────
    public static final Item ECHO_ARROW      = new EchoArrowItem     (new FabricItemSettings().maxCount(64));
    public static final Item VOID_ARROW      = new VoidArrowItem     (new FabricItemSettings().maxCount(64));
    public static final Item CELESTIAL_ARROW = new CelestialArrowItem(new FabricItemSettings().maxCount(64));

    // ── Food items ────────────────────────────────────────────────────────────
    public static final Item ECHO_CANDY = new Item(new FabricItemSettings().food(
            new FoodComponent.Builder().hunger(4).saturationModifier(1.2f).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1), 1.0f)
                    .build()));

    public static final Item VOID_TRUFFLE = new Item(new FabricItemSettings().food(
            new FoodComponent.Builder().hunger(7).saturationModifier(1.5f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600, 0), 1.0f)
                    .build()));

    public static final Item CELESTIAL_FRUIT = new Item(new FabricItemSettings().food(
            new FoodComponent.Builder().hunger(10).saturationModifier(2.0f).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, 1), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1), 1.0f)
                    .build()));

    // ── Specials ─────────────────────────────────────────────────────────────
    public static final Item ECHO_CHARGE      = new EchoChargeItem   (new FabricItemSettings().maxCount(16));
    public static final Item ECHO_MIRROR      = new EchoMirrorItem   (new FabricItemSettings());
    public static final Item ECHO_MAGNET      = new EchoMagnetItem   (new FabricItemSettings());
    public static final Item VOID_BOMB        = new VoidBombItem     (new FabricItemSettings().maxCount(8));
    public static final Item VOID_CLOAK       = new VoidCloakItem    (new FabricItemSettings());
    public static final Item CELESTIAL_TOTEM  = new CelestialTotemItem(new FabricItemSettings());

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
        reg("echo_furnace",       ECHO_FURNACE_ITEM);
        reg("resonance_pillar",   RESONANCE_PILLAR_ITEM);

        // Echo T1 armor
        reg("echo_helmet",     ECHO_HELMET);
        reg("echo_chestplate", ECHO_CHESTPLATE);
        reg("echo_leggings",   ECHO_LEGGINGS);
        reg("echo_boots",      ECHO_BOOTS);
        // Echo T1 tools
        reg("echo_pickaxe", ECHO_PICKAXE);
        reg("echo_sword",   ECHO_SWORD);
        reg("echo_shovel",  ECHO_SHOVEL);

        // Void T2 armor
        reg("void_helmet",     VOID_HELMET);
        reg("void_chestplate", VOID_CHESTPLATE);
        reg("void_leggings",   VOID_LEGGINGS);
        reg("void_boots",      VOID_BOOTS);
        // Void T2 tools
        reg("void_sword",   VOID_SWORD);
        reg("void_pickaxe", VOID_PICKAXE);
        reg("void_axe",     VOID_AXE);

        // Celestial T3 armor
        reg("celestial_helmet",     CELESTIAL_HELMET);
        reg("celestial_chestplate", CELESTIAL_CHESTPLATE);
        reg("celestial_leggings",   CELESTIAL_LEGGINGS);
        reg("celestial_boots",      CELESTIAL_BOOTS);
        // Celestial T3 tools
        reg("celestial_sword", CELESTIAL_SWORD);
        reg("celestial_staff", CELESTIAL_STAFF);

        // Arrows
        reg("echo_arrow",      ECHO_ARROW);
        reg("void_arrow",      VOID_ARROW);
        reg("celestial_arrow", CELESTIAL_ARROW);

        // Food
        reg("echo_candy",      ECHO_CANDY);
        reg("void_truffle",    VOID_TRUFFLE);
        reg("celestial_fruit", CELESTIAL_FRUIT);

        // Specials
        reg("echo_charge",     ECHO_CHARGE);
        reg("echo_mirror",     ECHO_MIRROR);
        reg("echo_magnet",     ECHO_MAGNET);
        reg("void_bomb",       VOID_BOMB);
        reg("void_cloak",      VOID_CLOAK);
        reg("celestial_totem", CELESTIAL_TOTEM);
    }

    private static void reg(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(EchoGemsMod.MOD_ID, name), item);
    }
}
