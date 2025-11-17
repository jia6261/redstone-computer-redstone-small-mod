package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// 精炼设备方块实体：用于处理玻璃精炼为单质硅的逻辑
public class RefiningDeviceEntity extends BlockEntity {
    public RefiningDeviceEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFINING_DEVICE_ENTITY.get(), pos, state);
    }

    // TODO: 实现精炼逻辑，包括输入槽位（玻璃）、输出槽位（单质硅）和进度条
}
