package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Celestial Boots: grants Slow Falling + Jump Boost II while worn.
 * Effects are applied via server tick in EchoGemsMod.
 */
public class CelestialBootsItem extends ArmorItem {

    public CelestialBootsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.BOOTS, settings);
    }
}
