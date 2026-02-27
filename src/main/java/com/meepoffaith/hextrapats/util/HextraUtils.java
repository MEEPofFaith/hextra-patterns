package com.meepoffaith.hextrapats.util;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.castables.SpecialHandler.Factory;
import at.petrak.hexcasting.api.utils.HexUtils;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

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

    public static Text specialHandlerLang(Factory<?> handler, Object... args){
        RegistryKey<Factory<?>> key = IXplatAbstractions.INSTANCE.getSpecialHandlerRegistry().getKey(handler).get();
        return HexUtils.getLightPurple(
            HexUtils.asTranslatedComponent(
                HexAPI.instance().getSpecialHandlerI18nKey(key), args
            )
        );
    }
}
