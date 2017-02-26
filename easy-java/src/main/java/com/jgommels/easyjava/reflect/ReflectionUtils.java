package com.jgommels.easyjava.reflect;

import java.lang.reflect.Constructor;

public class ReflectionUtils {

    /**
     * Returns a new instance for the given class. The class must have a nullary constructor.
     */
    public static <T> T getInstance(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for(Constructor<?> constructor : constructors) {
            if(constructor.getParameterCount() == 0) {
                return instantiate(constructor, clazz);
            }
        }

        throw new RuntimeException("No nullary constructor found for type " + clazz.getName());
    }

    private static <T> T instantiate(Constructor<?> constructor, Class<T> clazz) {
        try {
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate " + clazz.getName(), e);
        }
    }
}
