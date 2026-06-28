package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.block.EchoCrystalBlock;
import com.echogemmod.block.EchoLanternBlock;
import com.echogemmod.block.VoidInfuserBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // ── Overworld ──────────────────────────────────────────────────────────────
    public static final Block ECHO_CRYSTAL_ORE = new Block(
            FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f, 3.0f).requiresTool());

    // ── Nether ─────────────────────────────────────────────────────────────────
    public static final Block VOID_CRYSTAL_ORE = new Block(
            FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(3.5f, 3.5f).requiresTool());

    // ── End ────────────────────────────────────────────────────────────────────
    public static final Block CELESTIAL_ORE = new Block(
            FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4.0f, 4.0f).requiresTool());

    // ── Decorative/Storage ─────────────────────────────────────────────────────
    public static final Block ECHO_CRYSTAL_BLOCK = new EchoCrystalBlock(
            FabricBlockSettings.create()
                    .mapColor(MapColor.CYAN)
                    .strength(5.0f, 6.0f)
                    .requiresTool()
                    .luminance(state -> 7)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK));

    public static final Block VOID_CRYSTAL_BLOCK = new Block(
            FabricBlockSettings.create()
                    .mapColor(MapColor.PURPLE)
                    .strength(5.0f, 6.0f)
                    .requiresTool()
                    .luminance(state -> 5)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK));

    public static final Block CELESTIAL_BLOCK = new Block(
            FabricBlockSettings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(5.0f, 6.0f)
                    .requiresTool()
                    .luminance(state -> 12)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK));

    // ── Utility ────────────────────────────────────────────────────────────────
    public static final Block ECHO_LANTERN = new EchoLanternBlock(
            FabricBlockSettings.create()
                    .mapColor(MapColor.CYAN)
                    .strength(0.3f, 0.5f)
                    .luminance(state -> 15)
                    .sounds(BlockSoundGroup.LANTERN)
                    .nonOpaque());

    public static final Block VOID_INFUSER = new VoidInfuserBlock(
            FabricBlockSettings.create()
                    .mapColor(MapColor.PURPLE)
                    .strength(4.0f, 8.0f)
                    .requiresTool()
                    .luminance(state -> 4)
                    .sounds(BlockSoundGroup.STONE));

    public static void register() {
        reg("echo_crystal_ore",   ECHO_CRYSTAL_ORE);
        reg("void_crystal_ore",   VOID_CRYSTAL_ORE);
        reg("celestial_ore",      CELESTIAL_ORE);
        reg("echo_crystal_block", ECHO_CRYSTAL_BLOCK);
        reg("void_crystal_block", VOID_CRYSTAL_BLOCK);
        reg("celestial_block",    CELESTIAL_BLOCK);
        reg("echo_lantern",       ECHO_LANTERN);
        reg("void_infuser",       VOID_INFUSER);
    }

    private static void reg(String name, Block block) {
        Registry.register(Registries.BLOCK, new Identifier(EchoGemsMod.MOD_ID, name), block);
    }
}
