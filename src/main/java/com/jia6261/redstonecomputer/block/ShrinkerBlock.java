package com.jia6261.redstonecomputer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import com.jia6261.redstonecomputer.world.dimension.ModDimensions;
import com.jia6261.redstonecomputer.world.dimension.ModTeleporter;
import net.minecraft.world.MenuProvider;
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
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ServerLevel serverLevel = serverPlayer.server.getLevel(level.dimension() == ModDimensions.REDSTONE_SHRINKER_WORLD ? Level.OVERWORLD : ModDimensions.REDSTONE_SHRINKER_WORLD);

            if (serverLevel != null) {
                serverPlayer.changeDimension(serverLevel, new ModTeleporter(serverLevel, pos));
            } else {
                // 如果维度不存在，则打开 GUI
                BlockEntity entity = level.getBlockEntity(pos);
                if (entity instanceof MenuProvider) {
                    NetworkHooks.openScreen(serverPlayer, (MenuProvider) entity, pos);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.SHRINKER_BLOCK_ENTITY.get().create(pos, state);
    }
}
