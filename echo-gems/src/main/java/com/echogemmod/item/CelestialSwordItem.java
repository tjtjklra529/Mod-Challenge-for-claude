package com.echogemmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

/**
 * Celestial Sword: two bonuses —
 *  • Lifesteal: heals attacker for 2 health on every hit.
 *  • Execute: deals 15 bonus true damage when the target is below 30% health,
 *    accompanied by a starburst particle effect.
 */
public class CelestialSwordItem extends SwordItem {

    public CelestialSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            // Lifesteal
            if (attacker instanceof PlayerEntity player) {
                player.heal(2.0f);
            }
            // Execute threshold
            if (target.getHealth() < target.getMaxHealth() * 0.3f) {
                target.damage(target.getWorld().getDamageSources().magic(), 15.0f);
                // Particle burst at target
                if (target.getWorld() instanceof ServerWorld sw) {
                    sw.spawnParticles(ParticleTypes.END_ROD,
                            target.getX(), target.getY() + 1.0, target.getZ(),
                            20, 0.5, 0.5, 0.5, 0.2);
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
