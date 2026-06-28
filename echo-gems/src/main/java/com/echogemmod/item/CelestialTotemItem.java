package com.echogemmod.item;

import net.minecraft.item.Item;

/**
 * Celestial Totem: when held in either hand at the moment of death, prevents
 * dying and instead grants Regeneration III (45s), Absorption IV (30s), and
 * Resistance II (10s).  Consumed on use (unless in creative).
 *
 * Death cancellation is wired in EchoGemsMod.registerDeathEvent().
 */
public class CelestialTotemItem extends Item {

    public CelestialTotemItem(Settings settings) {
        super(settings.maxCount(1));
    }
}
