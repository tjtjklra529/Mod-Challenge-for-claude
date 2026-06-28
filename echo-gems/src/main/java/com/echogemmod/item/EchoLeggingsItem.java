package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Echo Leggings: grants permanent Saturation I, preventing hunger drain.
 * Effect is applied via server tick in EchoGemsMod.
 */
public class EchoLeggingsItem extends ArmorItem {

    public EchoLeggingsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.LEGGINGS, settings);
    }
}
