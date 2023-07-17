git pull origin main
pkill chrome
rm -r target
rm -r evidences
Xvfb :99 &
export DISPLAY=:99
mvn -U clean install exec:java -Dmaven.test.failure.ignore=true -Dsurefire.rerunFailingTestsCount=4 -Dsurefire.suiteXmlFiles=src/test/resources/suites/SmokeTest.xml

pkill chrome