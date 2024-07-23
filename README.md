# Trading Solutions Coding Challenge - NFL Depth Charts

This is a design and implementation of the NFL Depth Charts based on the provided requirements.

## Prerequisites

This a Maven based Java project that requires Java JDK 21 or newer and Maven 3.9 or newer. These are enforced by the plugin `maven-enforcer-plugin` to make sure we are using the correct versions. All plugins are pinned down to a specific version to ensure a reliable build. Google code formatting style are done and checked automatically when building the project using the plugin `spotless-maven-plugin` to ensure consistent coding style and code comparison.

## Building and Running

To build the project, just run the following command

```sh
mvn clean compile
```

To run all the test cases

```sh
mvn clean test
```

