package com.meepoffaith.hextrapats.casting.actions;

import at.petrak.hexcasting.api.casting.arithmetic.engine.NoOperatorCandidatesException;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexArithmetics;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.util.HextraUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//Replicates OperationAction but with extra handling to keep inputs.
public class NoConsumeOperationAction implements Action{
    int argc = 2;

    HexPattern pattern;

    public NoConsumeOperationAction(HexPattern pattern){
        this.pattern = pattern;
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment env, @NotNull CastingImage img, @NotNull SpellContinuation cont) throws Mishap{
        List<Iota> stack = new ArrayList<>(img.getStack());

        //Only handles 2-arg comparisons
        if(argc > stack.size()) throw new MishapNotEnoughArgs(argc, stack.size());

        try{
            OperationResult result = HexArithmetics.getEngine().run(pattern, env, HextraUtils.copyImage(img, stack.subList(stack.size() - 2, stack.size())), cont);
            List<Iota> resultStack = result.getNewImage().getStack();
            stack.add(resultStack.get(resultStack.size() - 1));
            return new OperationResult(HextraUtils.copyImage(img, stack), List.of(), cont, HexEvalSounds.NORMAL_EXECUTE);
        }catch(NoOperatorCandidatesException e){
            throw new MishapInvalidOperatorArgs(e.getArgs());
        }
    }
}
