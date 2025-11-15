package com.jia6261.redstonecomputer.world.structure;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {
    public static final DeferredRegister<Structure> STRUCTURES =
            DeferredRegister.create(Registries.STRUCTURE, RedstoneComputer.MOD_ID);

    // 秘密基地结构 (Secret Base Structure)
    // 这是一个占位符，真正的结构实现将更复杂
    public static final RegistryObject<Structure> SECRET_BASE =
            STRUCTURES.register("secret_base", () -> new SecretBaseStructure());

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}
