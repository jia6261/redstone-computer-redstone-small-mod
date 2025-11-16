package com.jia6261.redstonecomputer.block;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
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

public class ComputerBlock extends DirectionalKineticBlock {

    public ComputerBlock(Properties properties) {
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
            if (entity instanceof ComputerBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) player), (ComputerBlockEntity) entity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // 占位符：实际应该返回 ModBlockEntities.COMPUTER_BLOCK_ENTITY.get().create(pos, state);
        return null;
    }

    // Create Mod 动力相关方法
    @Override
    public boolean hasShaftTowards(Level world, BlockPos pos, BlockState state, net.minecraft.core.Direction face) {
        return face == state.getValue(FACING).getOpposite();
    }

    @Override
    public net.minecraft.core.Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }
}
