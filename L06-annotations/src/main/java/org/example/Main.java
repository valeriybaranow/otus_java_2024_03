package org.example;

import org.example.annotations.After;
import org.example.annotations.Before;
import org.example.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main {
    public static <T> void main(String[] args) {
        Class<?> testClass = TestClass.class;
        TestClass testObject = new TestClass();
        Method methodsBefore = null;
        ArrayList<Method> methodsTest = new ArrayList<>();
        Method methodsAfter = null;

        for (Method method: testClass.getDeclaredMethods()) {
            if (method.getAnnotation(Before.class) != null) {
                methodsBefore = method;
            }
            if (method.getAnnotation(Test.class) != null) {
                methodsTest.add(method);
            }
            if (method.getAnnotation(After.class) != null) {
                methodsAfter = method;
            }
        }

        for (Method method: methodsTest) {
            try {
                if (methodsBefore != null) {
                    methodsBefore.invoke(testObject);
                }
                method.invoke(testObject);
                if (methodsAfter != null) {
                    methodsAfter.invoke(testObject);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}