package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// 硅晶圆加工机方块实体：用于处理单质硅和图纸加工为硅晶圆的逻辑
public class SiliconWaferFabricatorEntity extends BlockEntity {
    public SiliconWaferFabricatorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SILICON_WAFER_FABRICATOR_ENTITY.get(), pos, state);
    }

    // TODO: 实现加工逻辑，包括输入槽位（单质硅、图纸）、输出槽位（硅晶圆）和图纸系统
}
