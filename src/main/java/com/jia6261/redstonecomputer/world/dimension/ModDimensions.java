package com.jia6261.redstonecomputer.world.dimension;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<Level> REDSTONE_SHRINKER_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(RedstoneComputer.MOD_ID, "redstone_shrinker_world"));

    public static final ResourceKey<DimensionType> REDSTONE_SHRINKER_DIMENSION_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY,
            REDSTONE_SHRINKER_WORLD.location());

    public static void register() {
        System.out.println("Registering Mod Dimensions for " + RedstoneComputer.MOD_ID);
    }
}
