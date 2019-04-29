package com.bloidonia.codenarc

import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule

class NoIgnoreRule extends AbstractAstVisitorRule {

    String name = 'NoIgnore'
    int priority = 3
    Class astVisitorClass = NoIgnoreAstVisitor

}

class NoIgnoreAstVisitor extends AbstractAstVisitor {

    private static final String MESSAGE = "Use of @Ignore has been declared as invalid.  Please use @IgnoreUntil and supply a date to revisit the test."

    @Override
    protected void visitClassEx(ClassNode node) {
        check(node)
    }

    @Override
    protected void visitMethodComplete(MethodNode node) {
        check(node)
    }

    private check(AnnotatedNode node) {
        if (node.annotations.any { it.classNode.nameWithoutPackage == 'Ignore' }) {
            addViolation(node, MESSAGE)
        }
    }

}
