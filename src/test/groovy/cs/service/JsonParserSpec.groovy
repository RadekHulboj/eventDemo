package cs.service

import cs.domain.Event
import spock.lang.Specification

class JsonParserSpec extends Specification {
    JsonParser SUT

    def setup() {
        SUT = new JsonParser()
        SUT.fileService = Mock(FileService)
    }

    def 'the log file should return six events' () {
        given:'mock on file service'
        def goodPath = "/logSixEvents.json"
        mockFileService(goodPath)
        when: 'create from json Event array'
        Event[] events = SUT.createFromJson(goodPath)
        then:'the array size is'
        events.size() == 6
    }

    def 'the path is wrong, so the return size is zero' () {
        given:'bad path to json resource'
        def badPath = "bad path, do not exist"
        mockBadFileService(badPath)
        when:
        Event[] events = SUT.createFromJson(badPath)
        then:
        events.size() == 0
    }

    private void mockFileService(String path) {
        SUT.fileService.getFileFrom(path) >> new File(getClass().getResource(path).toURI())
    }
    private void mockBadFileService(String path) {
        SUT.fileService.getFileFrom(path) >> null
    }
}
