package com.jia6261.redstonecomputer.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

import static com.jia6261.redstonecomputer.RedstoneComputer.MOD_ID;

public class SecretBaseStart extends StructureStart {
    public SecretBaseStart(Structure structure, ChunkPos chunkPos, int references, long seed) {
        super(structure, chunkPos, references, seed);
    }

    public void addPieces(StructureTemplateManager templateManager, BlockPos pos) {
        // 假设结构文件名为 secret_base.nbt
        ResourceLocation templateLocation = new ResourceLocation(MOD_ID, "secret_base");

        // 检查结构文件是否存在（实际开发中需要确保文件存在）
        Optional<net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate> template = templateManager.get(templateLocation);

        if (template.isPresent()) {
            // 添加结构部件
            SecretBasePieces.Piece piece = new SecretBasePieces.Piece(templateManager, templateLocation, pos);
            this.addPiece(piece);
            this.setBoundingBox(piece.getBoundingBox());
        }
    }
}
