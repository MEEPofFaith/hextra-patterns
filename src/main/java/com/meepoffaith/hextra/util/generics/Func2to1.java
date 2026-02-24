package com.meepoffaith.hextra.util.generics;

public interface Func2to1<T, U, Out>{
    Out apply(T t, U u);
}
