package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Celestial Helmet: grants XP orb attraction in a 12-block radius.
 * Handled in EchoGemsMod server tick. Also gives Haste II as part
 * of the Celestial set bonus when all 4 celestial pieces are worn.
 */
public class CelestialHelmetItem extends ArmorItem {

    public CelestialHelmetItem(ArmorMaterial material, Settings settings) {
        super(material, Type.HELMET, settings);
    }
}
