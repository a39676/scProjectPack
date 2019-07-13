pid=`(ps -ef | grep target.jar | grep -v "grep") | awk '{print $2}'`
kill -9 $pid
nohup java -jar -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms256m -Xmx256m -Xmn128m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC target.jar --spring.profiles.active=test >/dev/null 2>&1 & 