package com.meepoffaith.hextrapats.casting.actions;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.ArrayList;
import java.util.List;

public class OpSplitList extends ConstMediaActionBase{
    public int argc = 2;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        ArrayList<Iota> list = stack.getJUSTAList(0);
        int index = stack.getPositiveIntUnder(1, list.size());
        return List.of(new ListIota(list.subList(0, index)), new ListIota(list.subList(index, list.size())));
    }
}
