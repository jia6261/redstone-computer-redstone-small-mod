package com.jia6261.redstonecomputer.world.structure;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import com.jia6261.redstonecomputer.world.structure.ModStructurePieceTypes;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.RandomSource;
import com.jia6261.redstonecomputer.item.ModItems;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Random;

// 结构部件类型注册占位符
public class SecretBasePieces {
    // 假设结构部件类型
    public static final StructurePieceType SECRET_BASE_PIECE = ModStructurePieceTypes.SECRET_BASE_PIECE.get();

    // 秘密基地结构部件
    public static class Piece extends StructurePiece {
        private final ResourceLocation templateLocation;
        private final BlockPos position;

        public Piece(StructureTemplateManager templateManager, ResourceLocation templateLocation, BlockPos pos) {
            super(SecretBasePieces.SECRET_BASE_PIECE, 0, BoundingBox.getInfinite());
            this.templateLocation = templateLocation;
            this.position = pos;

            // 实际加载模板并设置 BoundingBox
            net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate template = templateManager.getOrCreate(templateLocation);
            this.boundingBox = template.getBoundingBox(pos, net.minecraft.world.level.block.Rotation.NONE, net.minecraft.world.level.block.Mirror.NONE);
        }

        // 占位符构造函数，用于序列化
        public Piece(StructurePieceSerializationContext context) {
            super(SecretBasePieces.SECRET_BASE_PIECE, context.tag());
            this.templateLocation = new ResourceLocation(context.tag().getString("Template"));
            this.position = BlockPos.of(context.tag().getLong("Pos"));
            // 实际需要读取 BoundingBox, Rotation, Mirror 等
        }

        @Override
        public void postProcess(ServerLevelAccessor level, net.minecraft.world.level.levelgen.structure.Structure.GenerationContext context, Random random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
            // 结构放置的核心逻辑
            StructureTemplateManager templateManager = level.getLevel().getStructureManager();
            net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate template = templateManager.getOrCreate(this.templateLocation);

            if (this.templateLocation.equals(new ResourceLocation(RedstoneComputer.MOD_ID, "secret_base"))) {
                StructurePlaceSettings settings = new StructurePlaceSettings(random.nextLong())
                        .setRotation(net.minecraft.world.level.block.Rotation.NONE)
                        .setMirror(net.minecraft.world.level.block.Mirror.NONE)
                        .setBoundingBox(box)
                        .setIgnoreEntities(false);

                // 放置结构
                template.placeInWorld(level, this.position, this.position, settings, random, 3);

                // 注入稀有物品到箱子
                BlockPos chestPos = this.position.offset(0, 1, 0); // 假设箱子在结构原点上方 (0, 1, 0)
                BlockEntity blockEntity = level.getBlockEntity(chestPos);

                // 放置光刻机 (Lithography Machine)
                // 假设光刻机在结构原点上方 (0, 2, 0)
                BlockPos machinePos = this.position.offset(0, 2, 0);
                level.setBlock(machinePos, ModBlocks.LITHOGRAPHY_MACHINE.get().defaultBlockState(), 3);

                if (blockEntity instanceof ChestBlockEntity chest) {
                    RandomSource randomSource = RandomSource.create(context.seed());

                    // 放置电脑芯片
                    chest.setItem(randomSource.nextInt(chest.getContainerSize()), new ItemStack(ModItems.COMPUTER_CHIP.get(), 1));
                    // 放置红石缩小器材
                    chest.setItem(randomSource.nextInt(chest.getContainerSize()), new ItemStack(ModItems.REDSTONE_SHRINKER.get(), 1));
                    // 放置一些额外的红石材料
                    chest.setItem(randomSource.nextInt(chest.getContainerSize()), new ItemStack(Items.REDSTONE, 64));
                    chest.setItem(randomSource.nextInt(chest.getContainerSize()), new ItemStack(Items.REPEATER, 8));
                }
            }
        }
    }
}
