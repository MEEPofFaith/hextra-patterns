package com.meepoffaith.hextra.casting.bases;

import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;

import static com.meepoffaith.hextra.HextraPatterns.LOGGER;

import java.util.List;

import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

// https://github.com/Real-Luxof/Lapisworks/blob/main/src/main/java/com/luxof/lapisworks/nocarpaltunnel/SpellActionNCT.java
public class SpellActionBase extends PatternBase implements SpellAction{
    public boolean requiresEnlightenment = false;

    public SpellAction.Result execute(HexIotaStack stack, CastingEnvironment ctx) {
        throw new IllegalStateException("call executeWithUserdata instead.");
    }

    public SpellAction.Result executeWithUserdata(HexIotaStack stack, CastingEnvironment ctx, NbtCompound userData) {
        return SpellAction.DefaultImpls.executeWithUserdata(this, stack.stack, ctx, userData);
    }

    public interface RenderedSpellBase extends RenderedSpell{

        default void cast(CastingEnvironment ctx) {
            throw new IllegalStateException("call cast(env, image) instead.");
        }

        default CastingImage cast(CastingEnvironment arg0, CastingImage arg1) {
            return RenderedSpell.DefaultImpls.cast(this, arg0, arg1);
        }

    }


    @Override
    public SpellAction.Result execute(List<? extends Iota> stack, CastingEnvironment ctx) {
        this.ctx = ctx;
        this.world = ctx.getWorld();
        _assertIsEnlightenedIfRequiresEnlightenment();
        return execute(new HexIotaStack(stack, getArgc(), ctx), ctx);
    }

    @Override
    public @NotNull Result executeWithUserdata(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment, @NotNull NbtCompound nbtCompound) throws Mishap{
        this.ctx = castingEnvironment;
        this.world = castingEnvironment.getWorld();
        _assertIsEnlightenedIfRequiresEnlightenment();
        return executeWithUserdata(new HexIotaStack(list, getArgc(), castingEnvironment), castingEnvironment, nbtCompound);
    }

    @Override
    public boolean awardsCastingStat(CastingEnvironment arg0) {
        return SpellAction.DefaultImpls.awardsCastingStat(this, arg0);
    }

    @Override
    public boolean hasCastingSound(CastingEnvironment arg0) {
        return SpellAction.DefaultImpls.hasCastingSound(this, arg0);
    }

    @Override
    public OperationResult operate(CastingEnvironment arg0, CastingImage arg1, SpellContinuation arg2) {
        return SpellAction.DefaultImpls.operate(this, arg0, arg1, arg2);
    }

    // reflection jumpscare
    @Override
    public int getArgc() {
        try {
            return this.getClass().getField("argc").getInt(this);
        } catch (NoSuchFieldException e) {
            LOGGER.error("you must have an argc field in the first place.", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("your argc field must be accessible.", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("your argc field must be an int.", e);
        }
        return 0;
    }

    @Override
    public boolean getRequiresEnlightenment() {
        try {
            return this.getClass().getField("requiresEnlightenment").getBoolean(this);
        } catch (IllegalArgumentException e) {
            LOGGER.error("your requiresEnlightenment field must be a boolean.", e);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace(); // Never happens
        }
        return false;
    }
}
