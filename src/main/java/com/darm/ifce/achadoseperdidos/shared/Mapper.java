package com.darm.ifce.achadoseperdidos.shared;

public interface Mapper<S, T> {
    T map(S source);
}
