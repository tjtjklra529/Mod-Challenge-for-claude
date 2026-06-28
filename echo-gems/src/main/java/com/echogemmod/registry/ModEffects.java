package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.effect.EchoVisionEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect ECHO_VISION = new EchoVisionEffect();

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT,
                new Identifier(EchoGemsMod.MOD_ID, "echo_vision"),
                ECHO_VISION);
    }
}
