package com.thailam.piggywallet.data.source.base;

public interface DataHandler<P, T> {
    T execute(P[] params);
}
