package com.meepoffaith.hextra.casting.handlers;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextra.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextra.casting.bases.HexIotaStack;
import com.meepoffaith.hextra.util.HextraUtils;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XVectorLiteral implements SpecialHandler{
    double x;

    public XVectorLiteral(double x){
        this.x = x;
    }

    @Override
    public Action act(){
        return new InnerAction(x);
    }

    @Override
    public Text getName(){
        return null;
    }

    private static class InnerAction extends ConstMediaActionBase{
        public int argc = 0;
        double x;

        public InnerAction(double x){
            this.x = x;
        }

        @Override
        public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
            return asActionResult(new Vec3Iota(new Vec3d(x, 0, 0)));
        }
    }

    public static class XVectorLiteralFactory implements Factory<XVectorLiteral> {
        @Override
        public @Nullable XVectorLiteral tryMatch(HexPattern pattern, CastingEnvironment env){
            String sig = pattern.anglesSignature();
            if(sig.startsWith("aeqqqqqaw") || sig.startsWith("aqeeeeedw")){
                double val = HextraUtils.numericalReflection(sig.substring(9)) *
                    (sig.startsWith("aqeeeeedw") ? -1.0 : 1.0);
                return new XVectorLiteral(val);
            }else{
                return null;
            }
        }
    }
}
