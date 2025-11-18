package com.jia6261.redstonecomputer.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class ModTeleporter implements ITeleporter {
    private final ServerLevel level;
    private final BlockPos pos;

    public ModTeleporter(ServerLevel level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        if (entity instanceof ServerPlayer player) {
            // 传送到目标位置上方
            player.connection.teleport(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, entity.getYRot(), entity.getXRot());
        } else {
            entity.teleportTo(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        }
        return entity;
    }

    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerPlayer, PortalInfo> defaultPortalInfo) {
        return new PortalInfo(new Vec3(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
    }
}
