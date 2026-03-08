package com.meepoffaith.hextrapats.util;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler.Factory;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.utils.HexUtils;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

import java.util.List;

public class HextraUtils{
    /** Simulates the accumulation process of Numerical Reflection */
    public static double numericalReflection(String s){
        double accumulator = 0.0;
        for(char ch : s.toCharArray()){
            switch(ch){
                case 'w' -> accumulator++;
                case 'q' -> accumulator += 5;
                case 'e' -> accumulator += 10;
                case 'a' -> accumulator *= 2;
                case 'd' -> accumulator /= 2;
                case 's' -> {}
                default -> throw new IllegalStateException();
            }
        }
        return accumulator;
    }

    public static Text specialHandlerLangSuffix(Factory<?> handler, String suffix, Object... args){
        RegistryKey<Factory<?>> key = IXplatAbstractions.INSTANCE.getSpecialHandlerRegistry().getKey(handler).get();
        return HexUtils.getLightPurple(
            HexUtils.asTranslatedComponent(
                HexAPI.instance().getSpecialHandlerI18nKey(key) + suffix, args
            )
        );
    }

    public static Text specialHandlerLang(Factory<?> handler, Object... args){
        RegistryKey<Factory<?>> key = IXplatAbstractions.INSTANCE.getSpecialHandlerRegistry().getKey(handler).get();
        return HexUtils.getLightPurple(
            HexUtils.asTranslatedComponent(
                HexAPI.instance().getSpecialHandlerI18nKey(key), args
            )
        );
    }

    public static int getInt(Iota x, int arg){
        if(x instanceof DoubleIota doubleIota){
            double d = doubleIota.getDouble();
            int rounded = (int)Math.round(d);
            if(DoubleIota.tolerates(d, rounded)){
                return rounded;
            }
        }
        throw new MishapInvalidIota(x, arg, Text.of("an integer"));
    }

    public static CastingImage copyImage(CastingImage image, List<Iota> stack){
        return image.copy(stack, image.getParenCount(), image.getParenthesized(), image.getEscapeNext(), image.getOpsConsumed(), image.getUserData());
    }

    public static boolean greaterEq(double a, double b){
        return a >= b || DoubleIota.tolerates(a, b);
    }

    public static boolean lessEq(double a, double b){
        return a <= b || DoubleIota.tolerates(a, b);
    }
}
