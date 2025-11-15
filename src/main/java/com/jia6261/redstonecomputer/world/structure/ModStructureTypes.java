package com.jia6261.redstonecomputer.world.structure;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, RedstoneComputer.MOD_ID);

    public static final RegistryObject<StructureType<SecretBaseStructure>> SECRET_BASE =
            STRUCTURE_TYPES.register("secret_base", () -> () -> SecretBaseStructure.CODEC);

    public static void register(IEventBus eventBus) {
        STRUCTURE_TYPES.register(eventBus);
    }
}
