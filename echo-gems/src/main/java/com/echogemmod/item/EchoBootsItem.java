package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Echo Boots: marker class used by EchoGemsMod to detect the item in equipment slots.
 * Fall damage immunity and Speed I effect are registered in EchoGemsMod.onInitialize().
 */
public class EchoBootsItem extends ArmorItem {

    public EchoBootsItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }
}
