package com.jia6261.redstonecomputer.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.Structure;
import com.jia6261.redstonecomputer.world.structure.SecretBaseStart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.StructureStartFactory;
import net.minecraft.world.level.levelgen.structure.StructureStart;

// 秘密基地结构类
public class SecretBaseStructure extends Structure {

    // 结构配置的编解码器，用于从 JSON 文件加载配置
    public static final Codec<SecretBaseStructure> CODEC = simpleCodec(SecretBaseStructure::new);

    // 构造函数，用于 CODEC
    public SecretBaseStructure(Structure.Settings settings) {
        super(settings);
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.SECRET_BASE.get();
    }

    @Override
    public StructureStartFactory getStartFactory() {
        return (context) -> {
            // 检查是否在世界边境生成（简化为只生成一次）
            // 实际的“世界边境”逻辑需要更复杂的坐标计算和生物群系检查
            if (context.random().nextInt(1000) != 0) { // 极低概率生成
                return new StructureStart(context.structure(), context.chunkPos(), 0, 0L);
            }

            BlockPos centerOfChunk = context.chunkPos().getMiddleBlockPosition(0);
            BlockPos pos = new BlockPos(centerOfChunk.getX(), 0, centerOfChunk.getZ());

            SecretBaseStart start = new SecretBaseStart(context.structure(), context.chunkPos(), 0, context.seed());
            start.addPieces(context.structureTemplateManager(), pos);

            return start;
        };
    }
}
