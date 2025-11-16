package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ShrinkerBlock extends Block {

    public ShrinkerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            // 占位符：假设红石缩小器材方块也有 BlockEntity
            // if (entity instanceof ShrinkerBlockEntity) {
            //     NetworkHooks.openScreen(((ServerPlayer) player), (ShrinkerBlockEntity) entity, pos);
            // } else {
            //     throw new IllegalStateException("Our Container provider is missing!");
            // }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // 占位符：实际应该返回 ModBlockEntities.SHRINKER_BLOCK_ENTITY.get().create(pos, state);
        return null;
    }
}
