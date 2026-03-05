package com.meepoffaith.hextrapats.casting.handlers;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.init.SpecialHandlers;
import com.meepoffaith.hextrapats.util.HextraUtils;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScientificExponent implements SpecialHandler{
    int exponent;

    public ScientificExponent(int exponent){
        this.exponent = exponent;
    }

    @Override
    public Action act(){
        return new InnerAction(exponent);
    }

    @Override
    public Text getName(){
        return HextraUtils.specialHandlerLang(SpecialHandlers.SCIENTIFIC_EXPONENT, exponent);
    }

    public static class InnerAction extends ConstMediaActionBase{
        public int argc = 1;
        public long mediaCost = 0L;
        int exponent;

        public InnerAction(int exponent){
            this.exponent = exponent;
        }

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new DoubleIota(stack.getDouble(0) * Math.pow(10, exponent)));
        }
    }

    public static class ScientificExponentFactory implements Factory<ScientificExponent>{
        @Override
        public @Nullable ScientificExponent tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("waqe") || sig.startsWith("wdeq")){
                boolean divide = sig.startsWith("wdeq");
                char[] chars = sig.substring(4).toCharArray();
                int exponent = 1;
                for(int i = 0; i < chars.length; i++){ //Code based on Sekhmet from Overevaluate
                    if(chars[i] != "qe".charAt((i + (divide ? 1 : 0)) % 2)){
                        return null;
                    }
                    exponent++;
                }
                return new ScientificExponent(exponent * (divide ? -1 : 1));
            }
            return null;
        }
    }
}
