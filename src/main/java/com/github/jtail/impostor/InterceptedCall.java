package com.github.jtail.impostor;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 */
class InterceptedCall<T, U> {
    private Method method;
    private MethodProxy proxy;
    private Object[] args;

    InterceptedCall(Method method, MethodProxy proxy, Object[] args) {
        this.method = method;
        this.proxy = proxy;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "com.github.jtail.impostor.InterceptedCall{" +
                "method=" + method +
                ", proxy=" + proxy +
                ", args=" + (args == null ? null : Arrays.asList(args)) +
                '}';
    }
}
