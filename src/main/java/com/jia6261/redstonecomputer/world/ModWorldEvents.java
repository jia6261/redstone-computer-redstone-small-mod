package com.jia6261.redstonecomputer.world;

import com.jia6261.redstonecomputer.RedstoneComputer;
import com.jia6261.redstonecomputer.world.structure.ModPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedstoneComputer.MOD_ID)
public class ModWorldEvents {

    // 结构生成现在由 SecretBaseStructure.getStartFactory() 控制，
    // 因此不再需要通过 BiomeLoadingEvent 来添加 PlacedFeature。
    // 结构配置（如稀有度、生成位置）应通过数据包 JSON 文件配置。
    // 此处保留事件订阅器，以便未来添加其他世界生成功能。
}
