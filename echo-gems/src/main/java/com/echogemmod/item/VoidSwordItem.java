package com.echogemmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;

import java.util.List;

/**
 * Void Sword: on hit, deals 4 bonus damage to all living entities within 3 blocks
 * of the target — punishing enemies that mob the player.
 */
public class VoidSwordItem extends SwordItem {

    private static final double RADIUS = 3.0;
    private static final float AOE_DAMAGE = 4.0f;

    public VoidSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            Box searchBox = target.getBoundingBox().expand(RADIUS);
            List<LivingEntity> nearby = target.getWorld().getEntitiesByClass(
                    LivingEntity.class, searchBox,
                    e -> e != target && e != attacker
                            && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test((Entity) e));
            for (LivingEntity entity : nearby) {
                entity.damage(target.getWorld().getDamageSources().magic(), AOE_DAMAGE);
            }
            // Heal attacker slightly
            if (attacker instanceof PlayerEntity player) {
                player.heal(1.0f);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
