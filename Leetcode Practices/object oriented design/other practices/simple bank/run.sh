#!/bin/bash

# javac -d ./bin/main/java/ ./src/main/java/com/bank/*.java ./src/main/java/com/bank/*/*.java
javac -sourcepath ./src/main/java/ -d ./bin/test/java/ ./src/test/java/com/bank/*.java
java -enableassertions -classpath ./bin/test/java/ com.bank.SolutionTest
