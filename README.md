### IgnoreUntil

- Built against spock 1.2-groovy-2.5 and Groovy 2.5.6

![](https://d1gbp99v447ls8.cloudfront.net/wp-content/uploads/2018/05/01011328/under-rug.gif)

#### Why?

`@Ignore` is a handy annotation to get flaky things through CI, or to temporarily remove a test whilst some form of upgrade is performed.
However, it is easily forgotten, and these ignored tests may be Ignored longer than was originally foreseen.

This library adds an new Spock annotation `@IgnoreUntil` that requires a UTC date up to 30 days in the future.

After that time, by default, it will fail the build.  You can set an annotation parameter `failAfter = false` to instead start running this spec or feature again, as if it weren't annotated.

Include in your gradle build with:

    testImplementation("com.bloidonia:spock-ignoreuntil:1.1")

#### Usage

Ignore a specification

    @IgnoreUntil("2019-05-15")
    class MyTests extends Specification
        def "I would fail"() {
            expect:
            1 != 1
        }
    }

Ignore a feature

    class MyTests extends Specification
        @IgnoreUntil("2019-05-15")
        def "I would fail"() {
            expect:
            1 != 1
        }
    }

> Dates may not be more than 30 days in the future.

By default, on expiry, the spec or feature will fail with an `IgnoreUntilExpiredException`.
 
You can change this to make it just run the spec or feature by passing the `failAfter = false` to the annotation, ie:

    class MyTests extends Specification
        @IgnoreUntil(value = "2019-05-15", failAfter = false)
        def "I will run after the 15th May"() {
            expect:
            1 != 1
        }
    }

#### Codenarc

- Built against Codenarc 1.3

If you use this spock extension, you may want to ban the use of `@Ignore` from your build.
If you use Codenarc, you can do this by adding the following to your dependencies:

    codenarc("com.bloidonia:spock-ignoreuntil:1.1")

And then this to your rules configuration file:

    ruleset('rulesets/codenarc-ignore.xml')
 
Then usage of any of the `@Ignore` annotations (Spock, JUnit, etc) will fail with a priority 3 error.

#### Known issues

If you're using Gradle, and a test outcome is cached, then it will not be executed if none of the inputs to the tests have changed.
This means that a test may remain effectively ignored beyond the specified end date.

### Releases

1.1
  - Change to using `value` in the annotation (so we don't need `date =` everywhere)
  - Fix spelling in "over 30 days" error message

1.0
  - First version 