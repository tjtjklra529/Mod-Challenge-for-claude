package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // ── Ores ───────────────────────────────────────────────────────────────────
    public static final Block ECHO_CRYSTAL_ORE = new Block(
            FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block VOID_CRYSTAL_ORE = new Block(
            FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(3.5f, 3.5f).requiresTool());
    public static final Block CELESTIAL_ORE    = new Block(
            FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4.0f, 4.0f).requiresTool());

    // ── Storage blocks ────────────────────────────────────────────────────────
    public static final Block ECHO_CRYSTAL_BLOCK = new EchoCrystalBlock(
            FabricBlockSettings.create().mapColor(MapColor.CYAN).strength(5.0f, 6.0f)
                    .requiresTool().luminance(s -> 7).sounds(BlockSoundGroup.AMETHYST_BLOCK));
    public static final Block VOID_CRYSTAL_BLOCK = new Block(
            FabricBlockSettings.create().mapColor(MapColor.PURPLE).strength(5.0f, 6.0f)
                    .requiresTool().luminance(s -> 5).sounds(BlockSoundGroup.AMETHYST_BLOCK));
    public static final Block CELESTIAL_BLOCK    = new Block(
            FabricBlockSettings.create().mapColor(MapColor.YELLOW).strength(5.0f, 6.0f)
                    .requiresTool().luminance(s -> 12).sounds(BlockSoundGroup.AMETHYST_BLOCK));

    // ── Utility blocks ────────────────────────────────────────────────────────
    public static final Block ECHO_LANTERN  = new EchoLanternBlock(
            FabricBlockSettings.create().mapColor(MapColor.CYAN).strength(0.3f, 0.5f)
                    .luminance(s -> 15).sounds(BlockSoundGroup.LANTERN).nonOpaque());
    public static final Block VOID_INFUSER  = new VoidInfuserBlock(
            FabricBlockSettings.create().mapColor(MapColor.PURPLE).strength(4.0f, 8.0f)
                    .requiresTool().luminance(s -> 4).sounds(BlockSoundGroup.STONE));
    public static final Block ECHO_FURNACE  = new EchoFurnaceBlock(
            FabricBlockSettings.copyOf(Blocks.FURNACE).strength(3.5f, 3.5f).requiresTool()
                    .luminance(state -> state.get(net.minecraft.block.AbstractFurnaceBlock.LIT) ? 13 : 0));

    // ── Decorative ────────────────────────────────────────────────────────────
    public static final Block RESONANCE_PILLAR = new ResonancePillarBlock(
            FabricBlockSettings.create().mapColor(MapColor.CYAN).strength(2.0f, 3.0f)
                    .luminance(s -> 8).sounds(BlockSoundGroup.AMETHYST_BLOCK));

    public static void register() {
        reg("echo_crystal_ore",   ECHO_CRYSTAL_ORE);
        reg("void_crystal_ore",   VOID_CRYSTAL_ORE);
        reg("celestial_ore",      CELESTIAL_ORE);
        reg("echo_crystal_block", ECHO_CRYSTAL_BLOCK);
        reg("void_crystal_block", VOID_CRYSTAL_BLOCK);
        reg("celestial_block",    CELESTIAL_BLOCK);
        reg("echo_lantern",       ECHO_LANTERN);
        reg("void_infuser",       VOID_INFUSER);
        reg("echo_furnace",       ECHO_FURNACE);
        reg("resonance_pillar",   RESONANCE_PILLAR);
    }

    private static void reg(String name, Block block) {
        Registry.register(Registries.BLOCK, new Identifier(EchoGemsMod.MOD_ID, name), block);
    }
}
