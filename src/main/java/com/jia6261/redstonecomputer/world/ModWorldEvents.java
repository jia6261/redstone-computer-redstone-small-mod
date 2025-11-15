package com.jia6261.redstonecomputer.world;

import com.jia6261.redstonecomputer.RedstoneComputer;
import com.jia6261.redstonecomputer.world.structure.ModPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedstoneComputer.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        // 这是一个占位符，实际需要根据策划案（世界边境）来限制生成
        // 暂时在所有生物群系中添加结构
        event.getGeneration().addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.SECRET_BASE_PLACED_KEY);
    }
}
