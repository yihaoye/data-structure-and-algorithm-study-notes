#!/bin/bash

current_dir=$(dirname "$0")
cd "$current_dir"

javac DAG.java
java DAG
