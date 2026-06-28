package com.echogemmod;

import com.echogemmod.command.EchoGemsCommand;
import com.echogemmod.config.EchoGemsConfig;
import com.echogemmod.item.*;
import com.echogemmod.registry.*;
import com.echogemmod.world.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        ModBlockEntities.register();
        ModEntityTypes.register();
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

        LOGGER.info("[Echo Gems] Initialized — 3 tiers, 45 items, 10 blocks, 3 custom entities.");
    }

    // ── Per-tick effects ──────────────────────────────────────────────────────
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
        if (head.getItem()  instanceof EchoHelmetItem)    fx(player, StatusEffects.NIGHT_VISION, 300, 0);
        if (legs.getItem()  instanceof EchoLeggingsItem)  fx(player, StatusEffects.SATURATION, 40, 0);
        if (feet.getItem()  instanceof EchoBootsItem)     fx(player, StatusEffects.SPEED, 60, 0);

        boolean fullEcho = head.getItem() instanceof EchoHelmetItem
                        && chest.getItem() instanceof EchoChestplateItem
                        && legs.getItem() instanceof EchoLeggingsItem
                        && feet.getItem() instanceof EchoBootsItem;
        if (fullEcho && EchoGemsConfig.enableSetBonus) {
            fx(player, StatusEffects.ABSORPTION, 60, 1);
            fx(player, StatusEffects.STRENGTH,   60, 0);
        }

        // ── Void T2 ──────────────────────────────────────────────────────────
        if (head.getItem() instanceof VoidHelmetItem) applyGlowToHostiles(player);
        if (legs.getItem() instanceof VoidLeggingsItem) fx(player, StatusEffects.FIRE_RESISTANCE, 60, 0);

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
            fx(player, player.isSneaking() ? StatusEffects.SPEED : StatusEffects.DOLPHINS_GRACE, 40,
               player.isSneaking() ? 1 : 0);
        }

        boolean fullCelestial = head.getItem() instanceof CelestialHelmetItem
                             && chest.getItem() instanceof CelestialChestplateItem
                             && legs.getItem() instanceof CelestialLeggingsItem
                             && feet.getItem() instanceof CelestialBootsItem;
        if (fullCelestial && EchoGemsConfig.enableSetBonus) {
            fx(player, StatusEffects.HASTE, 60, 1);
            fx(player, StatusEffects.HERO_OF_THE_VILLAGE, 60, 0);
        }
    }

    private void applyGlowToHostiles(ServerPlayerEntity player) {
        Box box = player.getBoundingBox().expand(16.0);
        player.getServerWorld()
              .getEntitiesByClass(HostileEntity.class, box, EntityPredicates.VALID_ENTITY)
              .forEach(mob -> mob.addStatusEffect(
                      new StatusEffectInstance(StatusEffects.GLOWING, 40, 0, false, false, false)));
    }

    private void attractXpOrbs(ServerPlayerEntity player) {
        if (!(player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof CelestialHelmetItem)) return;
        Box box = player.getBoundingBox().expand(12.0);
        player.getServerWorld().getEntitiesByClass(ExperienceOrbEntity.class, box, Entity::isAlive)
              .forEach(orb -> {
                  double dx = player.getX() - orb.getX();
                  double dy = player.getY() + 1.0 - orb.getY();
                  double dz = player.getZ() - orb.getZ();
                  double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
                  if (dist > 1.0) { orb.setVelocity(dx/dist*0.2, dy/dist*0.2, dz/dist*0.2); orb.velocityModified = true; }
              });
    }

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

    // ── Damage events ─────────────────────────────────────────────────────────
    private void registerDamageEvents() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(this::onDamage);
    }

    private boolean onDamage(LivingEntity entity, DamageSource source, float amount) {
        if (!(entity instanceof PlayerEntity player)) return true;

        // Echo/Celestial Boots: cancel fall damage
        if (source.isOf(DamageTypes.FALL)) {
            ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
            if (boots.getItem() instanceof EchoBootsItem || boots.getItem() instanceof CelestialBootsItem)
                return false;
        }

        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);

        // Echo Chestplate: reduce incoming damage by 20%
        if (chest.getItem() instanceof EchoChestplateItem && amount > 0) {
            entity.damage(entity.getDamageSources().generic(), amount * 0.8f);
            return false;
        }

        // Void Chestplate: reflect 35% back at attacker
        if (chest.getItem() instanceof VoidChestplateItem && amount > 1.0f) {
            Entity attacker = source.getAttacker();
            if (attacker instanceof LivingEntity living)
                living.damage(entity.getDamageSources().magic(), amount * 0.35f);
        }

        return true;
    }

    // ── Death event: Celestial Totem + Echo Mirror recording ──────────────────
    private void registerDeathEvent() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, damage) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return true;

            // 1. Celestial Totem: prevent death
            for (ItemStack hand : new ItemStack[]{player.getMainHandStack(), player.getOffHandStack()}) {
                if (hand.getItem() instanceof CelestialTotemItem) {
                    player.setHealth(4.0f);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 2));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,   400, 3));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,   200, 1));
                    player.getWorld().playSound(null, player.getBlockPos(),
                            SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    if (!player.isCreative()) hand.decrement(1);
                    return false;
                }
            }

            // 2. Echo Mirror: record death coords into any mirror in inventory
            String dim = player.getWorld().getRegistryKey().getValue().toString();
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack s = player.getInventory().getStack(i);
                if (s.getItem() instanceof EchoMirrorItem) {
                    EchoMirrorItem.recordDeath(s, player.getX(), player.getY(), player.getZ(), dim);
                }
            }

            return true;
        });
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    private static void fx(PlayerEntity player, net.minecraft.entity.effect.StatusEffect effect,
                            int duration, int amplifier) {
        if (!player.hasStatusEffect(effect) || player.getStatusEffect(effect).getDuration() < 20) {
            player.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false, true));
        }
    }
}
