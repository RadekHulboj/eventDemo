package cs.service

import spock.lang.Specification

/**
 * Created by radoslaw on 11.08.18.
 */
class FileServiceSpec extends Specification {
    FileService fileService

    def setup() {
        fileService = new FileService()
    }

    def 'get content from the path'() {
        given: 'create sql'
        def path = "db_sql/createTableEvent.sql"
        when:
        def from = fileService.getStringFrom(path)
        then:
        from.contains("CREATE TABLE IF NOT EXISTS") == true
    }
}
