package com.meepoffaith.hextrapats.casting.actions.lists;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpListSwindle extends ConstMediaActionBase{
    public int argc = 2;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        List<Iota> list = stack.getJUSTAList(0);
        long code = stack.getLong(1);
        boolean swindleEnd = code > 0;
        code = swindleEnd ? code : -code;

        List<Long> strides = new ArrayList<>();
        long acc = 1L;
        long n = 1L;
        while(true){
            long f = acc;
            acc *= n;
            n++;
            if(f <= code){
                strides.add(f);
            }else{
                break;
            }
        }

        if(strides.size() > list.size()) throw new MishapInvalidIota(stack.get(0), 1, Text.of("list of at least length " + strides.size()));

        List<Iota> editTarget = swindleEnd ? list.subList(list.size() - strides.size(), list.size()) : list.subList(0, strides.size());
        List<Iota> swap = new ArrayList<>(editTarget);
        long radix = code;
        Collections.reverse(strides);
        for(long divisor : strides){
            int index = (int)(radix / divisor);
            radix %= divisor;
            editTarget.set(0, swap.remove(index));
            editTarget = editTarget.subList(1, editTarget.size());
        }
        return asActionResult(new ListIota(list));
    }
}
