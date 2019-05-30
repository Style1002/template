package com.gouge.template.java8.lambda;

/**
 * @author GouGe
 * @data 2019/5/29 14:02
 */
@FunctionalInterface
public interface FunctionGenericity<T> {

    T calculate(T t);
}
