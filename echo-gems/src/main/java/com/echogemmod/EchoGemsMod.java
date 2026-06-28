package com.echogemmod;

import com.echogemmod.item.EchoBootsItem;
import com.echogemmod.registry.ModBlocks;
import com.echogemmod.registry.ModItemGroups;
import com.echogemmod.registry.ModItems;
import com.echogemmod.world.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoGemsMod implements ModInitializer {

    public static final String MOD_ID = "echogems";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModItems.register();
        ModItemGroups.register();
        ModOreGeneration.generateOres();

        // Echo Boots: grant Speed I every 2 seconds while worn
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
                if (boots.getItem() instanceof EchoBootsItem) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SPEED, 60, 0, false, false, true));
                }
            }
        });

        // Echo Boots: cancel fall damage
        net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.ALLOW_DAMAGE.register(
                (entity, source, amount) -> {
                    if (source.isOf(DamageTypes.FALL) && entity instanceof PlayerEntity player) {
                        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
                        if (boots.getItem() instanceof EchoBootsItem) {
                            return false;
                        }
                    }
                    return true;
                });

        LOGGER.info("Echo Gems Mod initialized!");
    }
}
