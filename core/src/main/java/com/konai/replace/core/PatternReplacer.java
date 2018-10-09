package com.konai.replace.core;

public interface PatternReplacer<T, U> {
     T replace(T destination, U target, U replacement);
}
