package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Celestial Chestplate: grants Resistance I and Luck I permanently while worn.
 * Resistance is applied via server tick; Luck is a passive status effect.
 */
public class CelestialChestplateItem extends ArmorItem {

    public CelestialChestplateItem(ArmorMaterial material, Settings settings) {
        super(material, Type.CHESTPLATE, settings);
    }
}
