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
            // 检查是否在世界边境生成
            // 简化：检查区块中心点到世界中心的距离
            BlockPos centerOfChunk = context.chunkPos().getMiddleBlockPosition(0);
            double distance = Math.sqrt(centerOfChunk.getX() * centerOfChunk.getX() + centerOfChunk.getZ() * centerOfChunk.getZ());

            final int MIN_DISTANCE = 10000; // 最小生成距离
            final int MAX_DISTANCE = 20000; // 最大生成距离
            final int CHANCE = 500; // 1/500 的概率生成

            if (distance < MIN_DISTANCE || distance > MAX_DISTANCE) {
                return new StructureStart(context.structure(), context.chunkPos(), 0, 0L);
            }

            // 在指定区域内，以极低概率生成
            if (context.random().nextInt(CHANCE) != 0) {
                return new StructureStart(context.structure(), context.chunkPos(), 0, 0L);
            }

            BlockPos pos = new BlockPos(centerOfChunk.getX(), 0, centerOfChunk.getZ());

            SecretBaseStart start = new SecretBaseStart(context.structure(), context.chunkPos(), 0, context.seed());
            start.addPieces(context.structureTemplateManager(), pos);

            return start;
        };
    }
}
