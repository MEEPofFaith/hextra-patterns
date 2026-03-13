package com.meepoffaith.hextrapats.util.generics;

public interface Func3to1<T, U, V, Out>{
    Out apply(T t, U u, V v);
}
