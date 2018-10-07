package com.konai.collect.core;

import java.util.List;

public interface PatternSearcher<T, U> {

    List<U> get(T source);
}
