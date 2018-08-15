package cs.business

import spock.lang.Specification

class OperationalEventSpec extends Specification {
    OperationalEvent sut = new OperationalEvent()

    def 'build events grouping by id'() {
        when:
        def events = sut.buildEvents()
        then: '3 events should be'
        events.size() == 3
    }

    def 'the 2 alerts should be'() {
        when:
        def events = sut.buildEvents()
        then: 'iterate all events if duration grater than threshold alert true other way false'
        def alertsCount = 0
        events.each {
            def thresholdAlert = 4
            if (it.getDuration() > thresholdAlert) {
                it.alert
                alertsCount++
            } else !it.alert
        }
        alertsCount == 2
    }
}
