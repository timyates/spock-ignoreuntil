package com.bloidonia.spock;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

import java.time.LocalDate;
import java.time.ZoneOffset;

public final class IgnoreUntilExtension extends AbstractAnnotationDrivenExtension<IgnoreUntil> {

    private static boolean dateValid(IgnoreUntil ignoreUntil) {
        LocalDate parse = LocalDate.parse(ignoreUntil.value());
        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        if (parse.isAfter(now.plusDays(30))) {
            throw new IgnoreUntilTooFarInTheFutureException(parse);
        }
        return parse.isAfter(now);
    }

    @Override
    public void visitSpecAnnotation(IgnoreUntil ignoreUntil, SpecInfo spec) {
        if (dateValid(ignoreUntil) && spec.getIsBottomSpec()) {
            spec.setSkipped(true);
        } else if (!dateValid(ignoreUntil) && ignoreUntil.failAfter()) {
            throw new IgnoreUntilExpiredException(spec.getName());
        }
    }

    @Override
    public void visitFeatureAnnotation(IgnoreUntil ignoreUntil, FeatureInfo feature) {
        if (dateValid(ignoreUntil)) {
            feature.setSkipped(true);
        } else if (ignoreUntil.failAfter()) {
            throw new IgnoreUntilExpiredException(feature.getName());
        }
    }

    public final class IgnoreUntilExpiredException extends RuntimeException {

        IgnoreUntilExpiredException(String name) {
            super("IgnoreUntil has expired for " + name);
        }

    }

    public static final class IgnoreUntilTooFarInTheFutureException extends RuntimeException {

        IgnoreUntilTooFarInTheFutureException(LocalDate parse) {
            super(parse + " is invalid, as it is more than 30 days in the future.");
        }

    }
}