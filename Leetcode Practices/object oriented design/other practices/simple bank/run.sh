#!/bin/bash

mkdir ./bin
cd ./bin
javac -d . ../src/main/java/com/bank/*.java ../src/main/java/com/bank/*/*.java
javac -d . ../src/test/java/com/bank/*.java
java -enableassertions com.bank.SolutionTest
cd ..
