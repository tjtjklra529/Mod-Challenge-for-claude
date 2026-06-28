package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Void Helmet: applies Glowing to all hostile mobs within 16 blocks,
 * letting you see threats through walls. Tracked in EchoGemsMod server tick.
 */
public class VoidHelmetItem extends ArmorItem {

    public VoidHelmetItem(ArmorMaterial material, Settings settings) {
        super(material, Type.HELMET, settings);
    }
}
