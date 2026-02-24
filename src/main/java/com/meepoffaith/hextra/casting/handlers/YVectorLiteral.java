package com.meepoffaith.hextra.casting.handlers;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextra.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextra.casting.bases.HexIotaStack;
import com.meepoffaith.hextra.init.SpecialHandlers;
import com.meepoffaith.hextra.util.HextraUtils;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class YVectorLiteral implements SpecialHandler{
    double y;

    public YVectorLiteral(double y){
        this.y = y;
    }

    @Override
    public Action act(){
        return new InnerAction(y);
    }

    @Override
    public Text getName(){
        return HextraUtils.specialHandlerLang(
            SpecialHandlers.SCALED_VEC_Y,
            Action.Companion.getDOUBLE_FORMATTER().format(y)
        );
    }

    public static class InnerAction extends ConstMediaActionBase{
        public int argc = 0;
        public long mediaCost = 0L;
        double y;

        public InnerAction(double y){
            this.y = y;
        }

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new Vec3Iota(new Vec3d(0, y, 0)));
        }
    }

    public static class YVectorLiteralFactory implements Factory<YVectorLiteral> {
        @Override
        public @Nullable YVectorLiteral tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("weqqqqqaw") || sig.startsWith("wqeeeeedw")){
                double val = HextraUtils.numericalReflection(sig.substring(9)) *
                    (sig.startsWith("wqeeeeedw") ? -1.0 : 1.0);
                return new YVectorLiteral(val);
            }else{
                return null;
            }
        }
    }
}
