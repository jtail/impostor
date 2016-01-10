package com.github.jtail.impostor;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This is a sample class that shows how to use Impostor in a realistic scenario,
 * when you need to read JPA annotation from a field in model class.
 * @param <T> class which is being introspected.
 */
public class Annotator<T> {
    private final Impostor<T> imp;
    private final PropertyDescriptor[] pds;
    private final Class<T> clazz;

    public static <T> Annotator<T> create(Class<T> clazz) {
        return new Annotator<T>(clazz);
    }

    public Annotator(Class<T> clazz) {
        this.clazz = clazz;
        imp = Impostor.create(clazz);
        pds = PropertyUtils.getPropertyDescriptors(clazz);

    }

    public <A extends Annotation> A annotate(Object capture, Class<A> annotation) {
        Method method = imp.grab(capture).getMethod();
        A a = method.getAnnotation(annotation);
        if (a == null) {
            Field field = getField(method);
            return field.getAnnotation(annotation);
        } else {
            return a;
        }
    }

    public T stub() {
        return imp.stub();
    }

    private Field getField(Method method) {
        String name = getFieldName(method);
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("No field found: [" + name + "]", e);
        }
    }

    private String getFieldName(Method method) {
        for (PropertyDescriptor pd : pds) {
            if (method.equals(pd.getReadMethod())) {
                return pd.getName();
            }
        }
        throw new IllegalArgumentException("No field found for [" + method + "]");
    }

}
