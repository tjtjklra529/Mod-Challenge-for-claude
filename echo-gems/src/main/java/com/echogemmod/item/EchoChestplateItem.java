package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Echo Chestplate: reduces all incoming damage by 20%.
 * Damage cancellation is registered in EchoGemsMod via ALLOW_DAMAGE event.
 */
public class EchoChestplateItem extends ArmorItem {

    public EchoChestplateItem(ArmorMaterial material, Settings settings) {
        super(material, Type.CHESTPLATE, settings);
    }
}
