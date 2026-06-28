package com.echogemmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

/**
 * Void Chestplate: reflects 35% of incoming melee damage back at the attacker.
 * Handled in EchoGemsMod via ALLOW_DAMAGE event.
 */
public class VoidChestplateItem extends ArmorItem {

    public VoidChestplateItem(ArmorMaterial material, Settings settings) {
        super(material, Type.CHESTPLATE, settings);
    }
}
