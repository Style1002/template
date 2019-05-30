package com.gouge.template.java8.lambda;

/**
 * @author GouGe
 * @data 2019/5/29 14:08
 */
@FunctionalInterface
public interface FunctionAsParam<T> {

    T asParam(T t);
}
