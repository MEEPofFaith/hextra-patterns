package com.meepoffaith.hextra.casting.bases;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.mishaps.MishapUnenlightened;
import net.minecraft.server.world.ServerWorld;

import static at.petrak.hexcasting.api.misc.MediaConstants.*;

// https://github.com/Real-Luxof/Lapisworks/blob/main/src/main/java/com/luxof/lapisworks/nocarpaltunnel/PatternNCTBase.java
public abstract class PatternBase{
    public CastingEnvironment ctx;
    public ServerWorld world;

    protected long dust(double dust) { return (long)(DUST_UNIT * dust); }
    protected long shards(double shards) { return (long)(SHARD_UNIT * shards); }
    /** charged amethyst */
    protected long crystals(double crystals) { return (long)(CRYSTAL_UNIT * crystals); }
    /** charged amethyst */
    protected long charged(double charged) { return (long)(CRYSTAL_UNIT * charged); }
    protected long quenchedShards(double quenchedShards) { return (long)(QUENCHED_SHARD_UNIT * quenchedShards); }
    protected long quenchedBlocks(double quenchedBlocks) { return (long)(QUENCHED_BLOCK_UNIT * quenchedBlocks); }
    protected void _assertIsEnlightenedIfRequiresEnlightenment() {
        if (getRequiresEnlightenment() && !ctx.isEnlightened())
            throw new MishapUnenlightened();
    }

    protected abstract boolean getRequiresEnlightenment();
}
