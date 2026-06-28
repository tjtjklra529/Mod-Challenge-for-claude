package com.echogemmod;

import com.echogemmod.item.*;
import com.echogemmod.registry.ModBlocks;
import com.echogemmod.registry.ModEffects;
import com.echogemmod.registry.ModItemGroups;
import com.echogemmod.registry.ModItems;
import com.echogemmod.registry.ModPotions;
import com.echogemmod.world.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoGemsMod implements ModInitializer {

    public static final String MOD_ID = "echogems";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModEffects.register();
        ModBlocks.register();
        ModItems.register();
        ModPotions.register();
        ModItemGroups.register();
        ModOreGeneration.generateOres();

        registerArmorEvents();
        registerDamageEvents();

        LOGGER.info("Echo Gems Mod initialized — 3 tiers, {} items ready.", countItems());
    }

    // ── Per-tick armor ability grants ──────────────────────────────────────────
    private void registerArmorEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                applyArmorEffects(player);
            }
        });
    }

    private void applyArmorEffects(ServerPlayerEntity player) {
        ItemStack head   = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest  = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs   = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet   = player.getEquippedStack(EquipmentSlot.FEET);

        // ── Echo Helmet: Night Vision ──────────────────────────────────────────
        if (head.getItem() instanceof EchoHelmetItem) {
            effect(player, StatusEffects.NIGHT_VISION, 300, 0);
        }

        // ── Echo Chestplate: tracked via ALLOW_DAMAGE below ───────────────────

        // ── Echo Leggings: Saturation (no hunger) ─────────────────────────────
        if (legs.getItem() instanceof EchoLeggingsItem) {
            effect(player, StatusEffects.SATURATION, 40, 0);
        }

        // ── Echo Boots: Speed I + fall immunity via ALLOW_DAMAGE ──────────────
        if (feet.getItem() instanceof EchoBootsItem) {
            effect(player, StatusEffects.SPEED, 60, 0);
        }

        // ── Celestial Boots: Slow Falling + Jump Boost II ─────────────────────
        if (feet.getItem() instanceof CelestialBootsItem) {
            effect(player, StatusEffects.SLOW_FALLING, 60, 0);
            effect(player, StatusEffects.JUMP_BOOST, 60, 1);
        }

        // ── Full Echo Armor Set Bonus: Absorption II + Strength I ─────────────
        boolean fullEchoSet =
                head.getItem()  instanceof EchoHelmetItem
                && chest.getItem() instanceof EchoChestplateItem
                && legs.getItem()  instanceof EchoLeggingsItem
                && feet.getItem()  instanceof EchoBootsItem;

        if (fullEchoSet) {
            effect(player, StatusEffects.ABSORPTION, 60, 1);
            effect(player, StatusEffects.STRENGTH,   60, 0);
        }
    }

    // ── Damage events ──────────────────────────────────────────────────────────
    private void registerDamageEvents() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (!(entity instanceof PlayerEntity player)) return true;

            // Echo Boots: cancel fall damage entirely
            if (source.isOf(DamageTypes.FALL)) {
                ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
                if (boots.getItem() instanceof EchoBootsItem
                        || boots.getItem() instanceof CelestialBootsItem) {
                    return false;
                }
            }

            // Echo Chestplate: reduce all damage by 20%
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof EchoChestplateItem && amount > 0) {
                // We can't modify `amount` directly via this event's return value.
                // Instead we cancel and re-apply reduced damage using a scheduled call.
                // Fabric 1.20.1 doesn't let us modify damage here, so we cancel and
                // immediately deal 80% of it as generic damage instead.
                entity.damage(entity.getDamageSources().generic(), amount * 0.8f);
                return false;
            }

            return true;
        });
    }

    private static void effect(PlayerEntity player, net.minecraft.entity.effect.StatusEffect effect,
                                int duration, int amplifier) {
        if (!player.hasStatusEffect(effect)
                || player.getStatusEffect(effect).getDuration() < 20) {
            player.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier,
                    false, false, true));
        }
    }

    private int countItems() {
        return 24; // rough count for the log message
    }
}
