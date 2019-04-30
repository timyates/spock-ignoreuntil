/*
 * This Spock specification was generated by the Gradle 'init' task.
 */
package sample

import spock.lang.Ignore
import spock.lang.Specification

class LibraryTest extends Specification {

    @org.junit.Ignore
    def "junit ignore"() {
        setup:
        def lib = new Library()

        when:
        def result = lib.someLibraryMethod()

        then:
        result
    }

    @Ignore
    def "spock ignore codenarc failure"() {
        setup:
        def lib = new Library()

        when:
        def result = lib.someLibraryMethod()

        then:
        result
    }

}
