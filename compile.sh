#!/bin/bash
javac -cp lib/*.jar  -d classes  `find src -name "*.java"`
