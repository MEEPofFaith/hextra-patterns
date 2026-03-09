package com.meepoffaith.hextrapats.casting.actions.lists;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.List;

public class OpDelElement extends ConstMediaActionBase{
    boolean first;
    public int argc = 2;
    public long mediaCost = 0L;

    public OpDelElement(boolean first){
        this.first = first;
    }

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        List<Iota> list = stack.getJUSTAList(0);
        Iota element = stack.get(1);

        boolean found = false;
        int i = 0;
        while(i < list.size()){
            if(Iota.tolerates(list.get(i), element)){
                list.remove(i);
                found = true;
                if(first) break;
            }else{
                i++;
            }
        }

        return List.of(new ListIota(list), new BooleanIota(found));
    }
}
