package com.gouge.template.java8.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * @author GouGe
 * @data 2019/5/29 8:55
 */
public class lambdaTest {

    /**
     * 1.   lamdba表达式中的参数类型都是由编译器推断得出(javac根据上下文)
     * 2.   方法引用：抽象方法的参数列表，必须与方法引用方法的参数列表保持一致
     * Object::实例方法
     * ClassName::实例方法（第一个参数是调用对象，第二个参数是需要引用方法的第二个参数（或无参）时）
     * ClassName::静态方法
     * 3.   构造器引用：ClassName::new（构造器参数要与抽象方法参数列表一致）
     * 4.   数组引用：type[]::new
     * 5.   内置四大函数式接口：
     * Consumer<T>:    void accept(T t)
     * Supplier<T>:    T get()
     * Function<T,R>:  R apply(T t)
     * Predicate<T>:   boolean test(T t)
     */

    @Test
    public void test1() {
        FunctionArgReturn temp = (x) -> {
            System.out.println("有参有返回");
            return x * x;
        };
        System.out.println(temp.calculate(10));
    }

    @Test
    public void test2() {
        FunctionArg temp = x -> {
            System.out.println("有参无返回");
            System.out.println(x);
        };
        temp.calculate(100);
    }

    @Test
    public void test3() {
        FunctionNoNo temp = () -> {
            System.out.println("无参无返回");
        };
        temp.run();
    }

    @Test
    public void test4() {
        FunctionGenericity<Integer> temp = (a) -> {
            System.out.println("泛型");
            return a - 10;
        };
        System.out.println(temp.calculate(10));
    }

    @Test
    public void test5() {
        String newStr = toUpperString((str) -> str.toUpperCase(), "aBc");
        System.out.println(newStr);
    }

    public String toUpperString(FunctionAsParam<String> mf, String str) {
        return mf.asParam(str);
    }

    @Test
    public void test6() {
        /**
         * 数组引用
         */
        Function<Integer, Integer[]> temp = Integer[]::new;
    }
    @Test
    public void test7(){

    }
}
