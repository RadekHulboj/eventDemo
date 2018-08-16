# Event demo
- the main class: EventSystemApplication
- the application create event table, delete all data (to have each time repeteble scenario) , converts from json to Event domain model and save, than read from db the all procesed events. The events are with alert flag.
- the unit tests are in Spock 

## Short approach
- git clone https://github.com/RadekHulboj/eventDemo.git
- gradle build
- gradle fatJar
- java -jar ./build/libs/all-in-one-event.demo-1.0-SNAPSHOT.jar
- open log file events_log.log
- analyze code in the any IDE.
