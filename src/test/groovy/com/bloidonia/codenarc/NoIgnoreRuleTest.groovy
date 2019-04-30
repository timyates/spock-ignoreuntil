package com.bloidonia.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Violation
import org.junit.Test

class NoIgnoreRuleTest extends AbstractRuleTestCase<NoIgnoreRule> {

    @Override
    protected NoIgnoreRule createRule() {
        return new NoIgnoreRule()
    }

    @Test
    void "finds ignore annotation on method"() {
        def source = '''
                    |class Test extends Specification {
                    |    @some.Ignore
                    |    def "something"() {
                    |    }
                    |}
                    |'''.stripMargin()

        assertSingleViolation(source) { Violation v ->
            v.rule.priority == 3
            v.rule.name == 'NoIgnore'
            v.lineNumber == 4
        }
    }

    @Test
    void "finds ignore annotation on class"() {
        def source = '''
                    |@another.Ignore
                    |class Test extends Specification {
                    |    def "something"() {
                    |    }
                    |}
                    |'''.stripMargin()

        assertSingleViolation(source) { Violation v ->
            v.rule.priority == 3
            v.rule.name == 'NoIgnore'
            v.lineNumber == 3
        }
    }

    @Test
    void "finds multiple ignore annotations"() {
        def source = '''
                    |@yet.more.Ignore
                    |class Test extends Specification {
                    |    @and.another.Ignore
                    |    def "something"() {
                    |    }
                    |
                    |    @Ignore
                    |    def "another thing"() {
                    |    }
                    |}
                    |'''.stripMargin()

        assertViolations(source, [lineNumber: 3], [lineNumber: 5], [lineNumber: 9])
    }

}
