package com.github.jtail.impostor;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Captures method call to turn them into reusable objects. Sample:
 * <code>
 *     Impostor<Bean> g = Impostor.create(Bean.class);
 *     MethodCall call = g.method(g.stub().someMethodCall());
 * </code>
 *
 * Instances are not tread safe and are intended for use in the static context.
 * @param <T> type of the object being grabbed.
 */
public class Impostor<T> {
    private CaptureInterceptor<T> interceptor;
    private Class<T> clazz;

    public static <T> Impostor<T> create(Class<T> clazz){
        return new Impostor<T>(clazz);
    }

    public Impostor(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Creates new capture interceptor to capture method calls.
     *
     * @return Stub object that will record and remember details on one method call.
     *         Only a single method call is allowed on this object, consequent calls will lead to {@link IllegalStateException}
     */
    public T stub() {
        interceptor = new CaptureInterceptor<T>();
        return spawn(interceptor);
    }

    public InterceptedCall<T, Object> grab(Object capture) {
        return interceptor.getLastCall();
    }


    @SuppressWarnings("unchecked")
    private T spawn(MethodInterceptor callback) {
        Enhancer enhancer = new Enhancer();
        if (clazz.isInterface()) {
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{clazz});
        } else {
            enhancer.setSuperclass(clazz);
        }
        enhancer.setCallback(callback);
        return (T) enhancer.create();
    }

}
