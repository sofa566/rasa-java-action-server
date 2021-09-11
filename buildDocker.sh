#/bin/bash
# copy rasa-java-sdk.jar from project "rasa-java-sdk"/target to libs/rasa-java-sdk-2.3.0.jar
SDK=rasa-java-sdk.jar
SDK_VER=rasa-java-sdk-2.3.0.jar

SDK_SRC=../rasa-java-sdk/target/rasa-java-sdk.jar
mkdir -p libs && cp $SDK_SRC libs/$SDK_VER

docker build -t sofa566/java-action-server:2.3.0 .
