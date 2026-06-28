package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Celestial Leggings: grants Dolphin's Grace and Swift Sneak equivalent
 * (Speed II while sneaking) via server tick.
 */
public class CelestialLeggingsItem extends ArmorItem {

    public CelestialLeggingsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.LEGGINGS, settings);
    }
}
