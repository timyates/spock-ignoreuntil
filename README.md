If you find `@Ignore` to be too fee and loose, and have had times when specs are `@Ignore`d and then forgotten, this might work for you:

Allows you to ignore specs or features with an end date, eg...

    @IgnoreUntil(date = "2019-05-15")
    def "I would fail"() {
        expect:
        1 == 1
    }

Dates may not be more than 30 days in the future.

By default, on expiry, the spec or feature will fail with an `IgnoreUntilExpiredException`.
 
You can change this to make it just run the spec or feature by passing the `failAfter = false` to the annotation, ie:

    @IgnoreUntil(date = "2019-05-15", failAfter = false)
    def "I would fail as 1 == 1"() {
        expect:
        1 != 1
    }
