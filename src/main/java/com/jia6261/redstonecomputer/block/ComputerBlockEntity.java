package com.jia6261.redstonecomputer.block;

import com.jia6261.redstonecomputer.RedstoneComputer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

// 电脑方块实体，继承自 Create Mod 的 KineticBlockEntity 以支持动力
public class ComputerBlockEntity extends KineticBlockEntity {

    public ComputerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // 假设电脑方块需要一定的动力才能运行
    // 这是一个占位符，实际需要实现 Create Mod 的动力相关方法
    @Override
    public float get0Speed() {
        // 假设电脑方块不需要旋转，但需要动力
        return 0;
    }

    // 检查方块是否在运行
    public boolean isPowered() {
        // 假设只要有动力输入，电脑就运行
        return getSpeed() != 0;
    }

    // 实际的 Create Mod 集成需要更复杂的逻辑，例如：
    // - 重写 updateSpeed() 来消耗动力
    // - 实现 IHaveGoggleInformation 接口来显示信息
    // - 实现 IDisplayGaugeCapability 接口来显示动力表
}
