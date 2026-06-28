package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Void Leggings: grants Fire Resistance and Wither immunity while in the Nether.
 * Also grants Fire Resistance passively regardless of dimension.
 * Handled in EchoGemsMod server tick.
 */
public class VoidLeggingsItem extends ArmorItem {

    public VoidLeggingsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.LEGGINGS, settings);
    }
}
