package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Echo Helmet: grants Night Vision while worn.
 * Effect is applied via server tick in EchoGemsMod.
 */
public class EchoHelmetItem extends ArmorItem {

    public EchoHelmetItem(ArmorMaterial material, Settings settings) {
        super(material, Type.HELMET, settings);
    }
}
