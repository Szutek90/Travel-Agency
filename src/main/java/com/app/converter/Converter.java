package com.app.converter;

public interface Converter <T,U>{
    U convert(T t);
}
