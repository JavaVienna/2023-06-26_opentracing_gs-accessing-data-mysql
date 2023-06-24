echo "this file is not intended to be run directly, but as a reference for shell commands"
exit 1

# first, build the jar file:
./gradlew clean bootJar

# run the jar file
java \
  -jar build/libs/accessing-data-mysql-0.0.1-SNAPSHOT.jar               `# set the jar file to run`


# combined command:
./gradlew clean bootJar; \
  java \
    -jar build/libs/accessing-data-mysql-0.0.1-SNAPSHOT.jar

# generate successful load
while true; do curl -H "username: @username" localhost:8080/demo/all; echo ""; sleep 3; done

# generate unsuccessful load
while true; do curl -H "username: @username" localhost:8080/demo/all; echo ""; sleep 10; done

# for windows, loadgen:
for(;;){curl -H @{"username"="user"} http://localhost:8080/demo/all; Start-Sleep -Seconds 3; }

# unsuccessful:
for(;;){curl -H @{"username"="@user"} http://localhost:8080/demo/all; Start-Sleep -Seconds 10; }