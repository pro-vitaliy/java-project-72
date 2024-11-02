setup:
	./gradlew wrapper --gradle-version 8.7

start:
	./gradlew run

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew installDist

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

check-deps:
	./gradlew dependencyUpdates -Drevision=release

.PHONY: build