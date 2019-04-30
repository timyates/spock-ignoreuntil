package com.bloidonia.spock;

import org.spockframework.runtime.extension.ExtensionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ExtensionAnnotation(IgnoreUntilExtension.class)
public @interface IgnoreUntil {

    /**
     * @return The date to ignore the feature or spec until (max 30 days from now)
     */
    String value();

    /**
     * @return By default, we will fail when this has expired, change to false to just run the test
     */
    boolean failAfter() default true;

    /**
     * @return This is just for documenting why you ignored the test, and is not used
     */
    String message() default "";

}