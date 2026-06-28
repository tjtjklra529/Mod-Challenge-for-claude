package com.echogemmod.config;

import com.echogemmod.EchoGemsMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Simple properties-file config for Echo Gems.
 * File: config/echogems.properties
 *
 * Defaults are written on first launch. Reload via /echogems reload (admin only).
 */
public class EchoGemsConfig {

    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("echogems.properties");

    // ── Tunable values ─────────────────────────────────────────────────────────
    public static boolean enableEchoBooots      = true;
    public static boolean enableSetBonus        = true;
    public static boolean enableVoidBombTerrain = false; // if true, void bomb breaks blocks
    public static double  magnetRadius          = 8.0;
    public static int     celestialStaffCooldown = 20;   // ticks
    public static boolean enableOreGeneration   = true;

    public static void load() {
        Properties props = defaults();

        if (!Files.exists(CONFIG_PATH)) {
            save(props);
            EchoGemsMod.LOGGER.info("[EchoGems] Created default config at {}", CONFIG_PATH);
        } else {
            try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
                props.load(in);
            } catch (IOException e) {
                EchoGemsMod.LOGGER.warn("[EchoGems] Could not load config, using defaults. {}", e.getMessage());
            }
        }

        apply(props);
    }

    public static void save(Properties props) {
        try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
            props.store(out, "Echo Gems Mod Configuration\nEdit and use /echogems reload in-game.");
        } catch (IOException e) {
            EchoGemsMod.LOGGER.warn("[EchoGems] Could not write config: {}", e.getMessage());
        }
    }

    private static Properties defaults() {
        Properties p = new Properties();
        p.setProperty("enable_echo_boots",         "true");
        p.setProperty("enable_set_bonus",          "true");
        p.setProperty("void_bomb_breaks_terrain",  "false");
        p.setProperty("magnet_radius",             "8.0");
        p.setProperty("celestial_staff_cooldown",  "20");
        p.setProperty("enable_ore_generation",     "true");
        return p;
    }

    private static void apply(Properties p) {
        enableEchoBooots        = Boolean.parseBoolean(p.getProperty("enable_echo_boots",        "true"));
        enableSetBonus          = Boolean.parseBoolean(p.getProperty("enable_set_bonus",         "true"));
        enableVoidBombTerrain   = Boolean.parseBoolean(p.getProperty("void_bomb_breaks_terrain", "false"));
        magnetRadius            = Double.parseDouble(  p.getProperty("magnet_radius",            "8.0"));
        celestialStaffCooldown  = Integer.parseInt(    p.getProperty("celestial_staff_cooldown", "20"));
        enableOreGeneration     = Boolean.parseBoolean(p.getProperty("enable_ore_generation",    "true"));
    }
}
