.PHONY: clean prepare compile run

all: prepare compile run

clean:
	rm -rf build

prepare:
	mkdir -p build

compile: prepare
	cd src && javac com/meteor/breaker/*.java -d ../build/ --class-path=../lib/*

run: compile
	cd build && java -Djava.library.path="../lib/:../lib/native/linux:../lib/native/windows:../lib/native/macos" --class-path="../lib/*:../res/:." com.meteor.breaker.Game
