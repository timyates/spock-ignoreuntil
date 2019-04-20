package com.bloidonia.spock

import org.intellij.lang.annotations.Language
import org.spockframework.runtime.ConditionNotSatisfiedError
import spock.lang.Specification
import spock.util.EmbeddedSpecCompiler
import spock.util.EmbeddedSpecRunner

import java.time.LocalDate
import java.time.ZoneOffset

class IgnoreUntilSpec extends Specification {

    def "cannot ignore features more than 30 days"() {
        when:
        runSpec """
            class Spec extends Specification {
            
                @IgnoreUntil(date = "2100-09-01")
                def "test"() {
                    expect:
                    1 != 1
                }
            }
        """

        then:
        thrown(IgnoreUntilExtension.IgnoreUntilTooFarInTheFutureException)
    }

    def "cannot ignore specifications more than 30 days"() {
        when:
        runSpec """
            @IgnoreUntil(date = "2100-09-01")
            class Spec extends Specification {
            
                def "test"() {
                    expect:
                    1 != 1
                }
            }
        """

        then:
        thrown(IgnoreUntilExtension.IgnoreUntilTooFarInTheFutureException)
    }

    def "ignoring works for features"() {
        when:
        runSpec """
            class Spec extends Specification {

                @IgnoreUntil(date = "${LocalDate.now(ZoneOffset.UTC).plusDays(3).toString()}")
                def "I would fail"() {
                    expect:
                    1 != 1
                }

            }
        """

        then:
        1 == 1
    }

    def "ignoring works for specs"() {
        when:
        runSpec """
            @IgnoreUntil(date = "${LocalDate.now(ZoneOffset.UTC).plusDays(3).toString()}")
            class Spec extends Specification {

                def "I would fail"() {
                    expect:
                    1 != 1
                }

            }
        """

        then:
        1 == 1
    }

    def "ignoring is inherited"() {
        when:
        runSpec """
            @IgnoreUntil(date = "${LocalDate.now(ZoneOffset.UTC).plusDays(3).toString()}")
            class Base extends Specification {
            }
            
            class Spec extends Base {
            
                def "I would fail"() {
                    expect:
                    1 != 1
                }

            }
        """

        then:
        1 == 1
    }

    def "out of date ignored tests throw exceptions be default"() {
        when:
        runSpec """
            class Spec extends Specification {
            
                @IgnoreUntil(date = "2019-01-01")
                def "I would fail"() {
                    expect:
                    1 != 1
                }

            }
        """

        then:
        thrown IgnoreUntilExtension.IgnoreUntilExpiredException
    }

    def "out of date ignored tests can be changed to just run as before"() {
        when:
        runSpec """
            class Spec extends Specification {
            
                @IgnoreUntil(date = "2019-01-01", failAfter = false)
                def "I would fail"() {
                    expect:
                    1 != 1
                }

            }
        """

        then:
        def err = thrown(ConditionNotSatisfiedError)
        err.condition.expression.effectiveRenderedValue == '1 != 1'
    }

    def runSpec(@Language(value = "groovy") String method, String specName = "Spec") {
        def compiler = new EmbeddedSpecCompiler()
        compiler.addClassImport(IgnoreUntil)
        new EmbeddedSpecRunner().runClass(
                compiler.compileWithImports(method)
                        .find { it.simpleName == specName }
        )
    }
}
