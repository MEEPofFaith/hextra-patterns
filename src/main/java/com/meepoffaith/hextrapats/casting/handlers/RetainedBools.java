package com.meepoffaith.hextrapats.casting.handlers;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.SpellList.LList;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.HextraPatterns;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import net.minecraft.text.Text;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexActions.*;

public class RetainedBools implements SpecialHandler{
    public static final List<HexPattern> OPS = List.of(
        GREATER.prototype(),
        LESS.prototype(),
        GREATER_EQ.prototype(),
        LESS_EQ.prototype(),
        EQUALS.prototype(),
        NOT_EQUALS.prototype()
    );

    HexPattern toRun;

    public RetainedBools(HexPattern toRun){
        this.toRun = toRun;
    }

    @Override
    public Action act(){
        return new InnerAction(toRun);
    }

    @Override
    public Text getName(){
        return null;
    }

    public static class InnerAction extends ConstMediaActionBase{
        public int argc = 2;
        public long mediaCost = 0L;
        HexPattern toRun;

        public InnerAction(HexPattern toRun){
            this.toRun = toRun;
        }

        @Override
        public OperationResult operate(CastingEnvironment env, CastingImage image, SpellContinuation cont){
            List<Iota> stack = image.getStack();
            if(argc > stack.size()) throw new MishapNotEnoughArgs(argc, stack.size());
            return exec(env, image, cont, stack);
        }

        //Replicates Hermes, but without consuming from the stack
        public OperationResult exec(CastingEnvironment env, CastingImage image, SpellContinuation cont, List<Iota> stack){
            SpellList toCast = new LList(0, asActionResult(new PatternIota(toRun)));
            FrameEvaluate frame = new FrameEvaluate(toCast, true);
            return new OperationResult(image, List.of(), cont.pushFrame(frame), HexEvalSounds.NORMAL_EXECUTE);
        }
    }

    public static class RetainedBoolsFactory implements Factory<RetainedBools>{
        @Override
        public @Nullable RetainedBools tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("dd")){
                String op = sig.substring(2);
                for(HexPattern p : OPS){
                    String opStart = switch(p.getStartDir()){
                        case EAST -> "q";
                        case SOUTH_EAST -> "w";
                        case SOUTH_WEST -> "e";
                        case NORTH_EAST -> "a";
                        default -> throw new IllegalStateException(); //Will never occur
                    };
                    HextraPatterns.LOGGER.info("Checking: {} == {}", op, opStart + p.anglesSignature());
                    if(op.equals(opStart + p.anglesSignature())){
                        return new RetainedBools(p);
                    }
                }
            }
            return null;
        }
    }
}
