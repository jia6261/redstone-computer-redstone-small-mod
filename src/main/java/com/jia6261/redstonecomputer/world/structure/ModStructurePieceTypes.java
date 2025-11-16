package com.jia6261.redstonecomputer.world.structure;

import com.jia6261.redstonecomputer.RedstoneComputer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PIECE, RedstoneComputer.MOD_ID);

    public static final RegistryObject<StructurePieceType> SECRET_BASE_PIECE =
            STRUCTURE_PIECE_TYPES.register("secret_base_piece", () -> SecretBasePieces.Piece::new);

    public static void register(IEventBus eventBus) {
        STRUCTURE_PIECE_TYPES.register(eventBus);
    }
}
