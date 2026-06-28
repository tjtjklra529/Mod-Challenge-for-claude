package com.echogemmod;

import com.echogemmod.command.EchoGemsCommand;
import com.echogemmod.config.EchoGemsConfig;
import com.echogemmod.item.*;
import com.echogemmod.registry.ModBlocks;
import com.echogemmod.registry.ModEffects;
import com.echogemmod.registry.ModItemGroups;
import com.echogemmod.registry.ModItems;
import com.echogemmod.registry.ModPotions;
import com.echogemmod.world.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EchoGemsMod implements ModInitializer {

    public static final String MOD_ID = "echogems";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        EchoGemsConfig.load();

        ModEffects.register();
        ModBlocks.register();
        ModItems.register();
        ModPotions.register();
        ModItemGroups.register();

        if (EchoGemsConfig.enableOreGeneration) {
            ModOreGeneration.generateOres();
        }

        CommandRegistrationCallback.EVENT.register(EchoGemsCommand::register);

        registerTickEvents();
        registerDamageEvents();
        registerDeathEvent();

        LOGGER.info("[Echo Gems] Initialized — 3 tiers, 34 items, 8 blocks, potions, commands.");
    }

    // ── Per-tick armor effects ─────────────────────────────────────────────────
    private void registerTickEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                applyArmorEffects(player);
                applyMagnetEffect(player);
                attractXpOrbs(player);
            }
        });
    }

    private void applyArmorEffects(ServerPlayerEntity player) {
        ItemStack head  = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs  = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet  = player.getEquippedStack(EquipmentSlot.FEET);

        // ── Echo T1 ──────────────────────────────────────────────────────────
        if (head.getItem()  instanceof EchoHelmetItem)     fx(player, StatusEffects.NIGHT_VISION,  300, 0);
        if (legs.getItem()  instanceof EchoLeggingsItem)   fx(player, StatusEffects.SATURATION,     40, 0);
        if (feet.getItem()  instanceof EchoBootsItem)      fx(player, StatusEffects.SPEED,          60, 0);

        boolean fullEcho = head.getItem() instanceof EchoHelmetItem
                        && chest.getItem() instanceof EchoChestplateItem
                        && legs.getItem() instanceof EchoLeggingsItem
                        && feet.getItem() instanceof EchoBootsItem;
        if (fullEcho && EchoGemsConfig.enableSetBonus) {
            fx(player, StatusEffects.ABSORPTION, 60, 1);
            fx(player, StatusEffects.STRENGTH,   60, 0);
        }

        // ── Void T2 ──────────────────────────────────────────────────────────
        if (head.getItem() instanceof VoidHelmetItem) {
            applyGlowingToNearbyHostiles(player);
        }
        if (legs.getItem() instanceof VoidLeggingsItem) {
            fx(player, StatusEffects.FIRE_RESISTANCE, 60, 0);
        }
        if (feet.getItem() instanceof EchoBootsItem    // void_boots reuse EchoBoots class
                && legs.getItem() instanceof VoidLeggingsItem) {
            fx(player, StatusEffects.SPEED, 60, 0);
        }

        boolean fullVoid = head.getItem() instanceof VoidHelmetItem
                        && chest.getItem() instanceof VoidChestplateItem
                        && legs.getItem() instanceof VoidLeggingsItem;
        if (fullVoid && EchoGemsConfig.enableSetBonus) {
            fx(player, StatusEffects.FIRE_RESISTANCE, 60, 0);
            fx(player, StatusEffects.RESISTANCE,      60, 0);
        }

        // ── Celestial T3 ─────────────────────────────────────────────────────
        if (feet.getItem()  instanceof CelestialBootsItem)    { fx(player, StatusEffects.SLOW_FALLING, 60, 0); fx(player, StatusEffects.JUMP_BOOST, 60, 1); }
        if (chest.getItem() instanceof CelestialChestplateItem) { fx(player, StatusEffects.RESISTANCE, 60, 0); fx(player, StatusEffects.LUCK, 60, 0); }
        if (legs.getItem()  instanceof CelestialLeggingsItem) {
            if (player.isSneaking()) fx(player, StatusEffects.SPEED, 40, 1);
            else                     fx(player, StatusEffects.DOLPHINS_GRACE, 40, 0);
        }

        boolean fullCelestial = head.getItem() instanceof CelestialHelmetItem
                             && chest.getItem() instanceof CelestialChestplateItem
                             && legs.getItem() instanceof CelestialLeggingsItem
                             && feet.getItem() instanceof CelestialBootsItem;
        if (fullCelestial && EchoGemsConfig.enableSetBonus) {
            fx(player, StatusEffects.HASTE,         60, 1);
            fx(player, StatusEffects.HERO_OF_THE_VILLAGE, 60, 0);
        }
    }

    /** Applies Glowing to all hostile mobs within 16 blocks for the Void Helmet. */
    private void applyGlowingToNearbyHostiles(ServerPlayerEntity player) {
        Box box = player.getBoundingBox().expand(16.0);
        List<HostileEntity> hostiles = player.getServerWorld().getEntitiesByClass(
                HostileEntity.class, box, EntityPredicates.VALID_ENTITY);
        for (HostileEntity mob : hostiles) {
            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40, 0, false, false, false));
        }
    }

    /** Attracts XP orbs if wearing a Celestial Helmet. */
    private void attractXpOrbs(ServerPlayerEntity player) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        if (!(head.getItem() instanceof CelestialHelmetItem)) return;

        Box box = player.getBoundingBox().expand(12.0);
        List<ExperienceOrbEntity> orbs = player.getServerWorld().getEntitiesByClass(
                ExperienceOrbEntity.class, box, Entity::isAlive);
        for (ExperienceOrbEntity orb : orbs) {
            double dx = player.getX() - orb.getX();
            double dy = player.getY() + 1.0 - orb.getY();
            double dz = player.getZ() - orb.getZ();
            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
            if (dist > 1.0) {
                double speed = 0.2;
                orb.setVelocity(dx/dist*speed, dy/dist*speed, dz/dist*speed);
                orb.velocityModified = true;
            }
        }
    }

    /** Runs the Echo Magnet item vacuum if the player has an active magnet in their hotbar. */
    private void applyMagnetEffect(ServerPlayerEntity player) {
        for (int i = 0; i < 9; i++) {
            ItemStack s = player.getInventory().getStack(i);
            if (s.getItem() instanceof EchoMagnetItem
                    && s.getOrCreateNbt().getBoolean(EchoMagnetItem.KEY_ACTIVE)) {
                EchoMagnetItem.pullItems(player);
                return;
            }
        }
    }

    // ── Damage events ──────────────────────────────────────────────────────────
    private void registerDamageEvents() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(this::onDamage);
    }

    private boolean onDamage(LivingEntity entity, DamageSource source, float amount) {
        if (!(entity instanceof PlayerEntity player)) return true;

        // Echo Boots / CelestialBoots: cancel fall damage
        if (source.isOf(DamageTypes.FALL)) {
            ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
            if (boots.getItem() instanceof EchoBootsItem
                    || boots.getItem() instanceof CelestialBootsItem) {
                return false;
            }
        }

        // Echo Chestplate: reduce damage by 20%
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof EchoChestplateItem && amount > 0) {
            entity.damage(entity.getDamageSources().generic(), amount * 0.8f);
            return false;
        }

        // Void Chestplate: reflect 35% of incoming damage back at source entity
        if (chest.getItem() instanceof VoidChestplateItem && amount > 1.0f) {
            Entity attacker = source.getAttacker();
            if (attacker instanceof LivingEntity living) {
                living.damage(entity.getDamageSources().magic(), amount * 0.35f);
            }
        }

        return true;
    }

    // ── Death event: record coordinates into Echo Mirror ──────────────────────
    private void registerDeathEvent() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, damage) -> {
            if (entity instanceof ServerPlayerEntity player) {
                String dim = player.getWorld().getRegistryKey().getValue().toString();
                // Find Echo Mirror in inventory and update its NBT
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack s = player.getInventory().getStack(i);
                    if (s.getItem() instanceof EchoMirrorItem) {
                        EchoMirrorItem.recordDeath(s,
                                player.getX(), player.getY(), player.getZ(), dim);
                    }
                }
            }
            return true; // still allow the death
        });
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    private static void fx(PlayerEntity player, net.minecraft.entity.effect.StatusEffect effect,
                            int duration, int amplifier) {
        if (!player.hasStatusEffect(effect)
                || player.getStatusEffect(effect).getDuration() < 20) {
            player.addStatusEffect(new StatusEffectInstance(
                    effect, duration, amplifier, false, false, true));
        }
    }
}
