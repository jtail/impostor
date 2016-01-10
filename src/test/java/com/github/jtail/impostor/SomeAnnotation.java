package com.github.jtail.impostor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SomeAnnotation {
    String value();
}
