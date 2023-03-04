#!/bin/bash

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"
PWD=$(pwd)
mkdir "$PROJECT_DIR/bin"
cd "$PROJECT_DIR/bin"
javac -d . ../src/main/java/com/bank/*.java ../src/main/java/com/bank/*/*.java
javac -d . ../src/test/java/com/bank/*.java
java -enableassertions com.bank.SolutionTest
cd "$PWD"
