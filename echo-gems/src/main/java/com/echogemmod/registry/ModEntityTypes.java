package com.echogemmod.registry;

import com.echogemmod.EchoGemsMod;
import com.echogemmod.entity.CelestialArrowEntity;
import com.echogemmod.entity.EchoArrowEntity;
import com.echogemmod.entity.VoidArrowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityTypes {

    public static final EntityType<VoidArrowEntity> VOID_ARROW =
            Registry.register(Registries.ENTITY_TYPE,
                    new Identifier(EchoGemsMod.MOD_ID, "void_arrow"),
                    FabricEntityTypeBuilder.<VoidArrowEntity>create(SpawnGroup.MISC, VoidArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(20)
                            .build());

    public static final EntityType<EchoArrowEntity> ECHO_ARROW =
            Registry.register(Registries.ENTITY_TYPE,
                    new Identifier(EchoGemsMod.MOD_ID, "echo_arrow"),
                    FabricEntityTypeBuilder.<EchoArrowEntity>create(SpawnGroup.MISC, EchoArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(20)
                            .build());

    public static final EntityType<CelestialArrowEntity> CELESTIAL_ARROW =
            Registry.register(Registries.ENTITY_TYPE,
                    new Identifier(EchoGemsMod.MOD_ID, "celestial_arrow"),
                    FabricEntityTypeBuilder.<CelestialArrowEntity>create(SpawnGroup.MISC, CelestialArrowEntity::new)
                            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                            .trackRangeBlocks(4).trackedUpdateRate(20)
                            .build());

    public static void register() {
        // Static initializer triggers registration when class is loaded.
        // Call this method from onInitialize() to ensure registration order.
    }
}
