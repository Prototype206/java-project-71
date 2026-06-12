.PHONY: build run-dist clean checkstyle test

setup:
	chmod +x ./gradlew

build: setup
	./gradlew build

run-dist:
	./build/install/app/bin/app

clean:
	./gradlew clean

checkstyle:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test
	
	
win-run-dist:
	.\build\install\app\bin\app
	
win-checkstyle:
	.\gradlew checkstyleMain checkstyleTest
	
win-test:
	.\gradlew test