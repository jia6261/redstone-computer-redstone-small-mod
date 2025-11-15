package com.jia6261.redstonecomputer.world.structure;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SECRET_BASE_PLACED_KEY = registerKey("secret_base_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        // 这是一个占位符，实际的放置规则需要更复杂的 PlacementModifier
        // 例如：RarityFilter, InSquarePlacement, HeightRangePlacement, BiomeFilter
        context.register(SECRET_BASE_PLACED_KEY, new PlacedFeature(
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.SECRET_BASE_KEY),
                java.util.List.of() // 放置修饰符列表
        ));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(RedstoneComputer.MOD_ID, name));
    }
}
