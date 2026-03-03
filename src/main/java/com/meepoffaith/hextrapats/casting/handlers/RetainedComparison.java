package com.meepoffaith.hextrapats.casting.handlers;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.init.SpecialHandlers;
import com.meepoffaith.hextrapats.util.HextraUtils;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexActions.*;

public class RetainedComparison implements SpecialHandler{
    public static final List<ActionRegistryEntry> OPS = List.of(
        GREATER,
        LESS,
        GREATER_EQ,
        LESS_EQ,
        EQUALS,
        NOT_EQUALS
    );

    ActionRegistryEntry op;

    public RetainedComparison(ActionRegistryEntry op){
        this.op = op;
    }

    @Override
    public Action act(){
        return new InnerAction(op.action());
    }

    @Override
    public Text getName(){
        return HextraUtils.specialHandlerLangSuffix(SpecialHandlers.RETAINED_COMPARISON, ".name",
            HextraUtils.specialHandlerLangSuffix(SpecialHandlers.RETAINED_COMPARISON, ".op." + HexActions.REGISTRY.getId(op))
        );
    }

    public static class InnerAction extends ConstMediaActionBase{
        public int argc = 2;
        public long mediaCost = 0L;
        Action act;

        public InnerAction(Action act){
            this.act = act;
        }

        @Override
        public OperationResult operate(CastingEnvironment env, CastingImage image, SpellContinuation cont){
            List<Iota> stack = image.getStack();
            if(argc > stack.size()) throw new MishapNotEnoughArgs(argc, stack.size());
            List<Iota> stackCopy = stack.subList(stack.size() - argc, stack.size());
            CastingImage image2 = HextraUtils.copyImage(image, stackCopy);
            List<Iota> opStack = act.operate(env, image2, cont).getNewImage().getStack();
            List<Iota> newStack = new ArrayList<>(stack);
            newStack.add(opStack.get(opStack.size() - 1));
            return new OperationResult(HextraUtils.copyImage(image, newStack), List.of(), cont, HexEvalSounds.NOTHING);
        }
    }

    public static class RetainedComparisonFactory implements Factory<RetainedComparison>{
        @Override
        public @Nullable RetainedComparison tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("dd")){
                String op = sig.substring(2);
                for(ActionRegistryEntry p : OPS){
                    String opStart = switch(p.prototype().getStartDir()){
                        case EAST -> "q";
                        case SOUTH_EAST -> "w";
                        case SOUTH_WEST -> "e";
                        case NORTH_EAST -> "a";
                        default -> throw new IllegalStateException(); //Will never occur
                    };
                    if(op.equals(opStart + p.prototype().anglesSignature())){
                        return new RetainedComparison(p);
                    }
                }
            }
            return null;
        }
    }
}
