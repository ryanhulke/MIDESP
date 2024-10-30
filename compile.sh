#!/bin/bash

### This script compiles all Java files in the src directory and packages them into a JAR file.

# Define output JAR name and directories
OUT_DIR="out"
JAR_NAME="MIDESP.jar"

# Step 1: Create output directory
if [ -d $OUT_DIR ]; then
    rm -r $OUT_DIR
fi
mkdir -p $OUT_DIR

# Step 2: Find and compile all Java files
echo "Compiling Java files..."
find src -name "*.java" > sources.txt
javac -d $OUT_DIR @sources.txt
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi
echo "Compilation successful!"

# Step 3: Package into a JAR
echo "Main-Class: midesp.Main" > manifest.txt  # Replace with your main class if needed
jar cfm $JAR_NAME manifest.txt -C $OUT_DIR .
echo "JAR file created: $JAR_NAME"

# Cleanup
rm manifest.txt sources.txt
rm -r $OUT_DIR

echo "Run your program with: java -jar $JAR_NAME"
