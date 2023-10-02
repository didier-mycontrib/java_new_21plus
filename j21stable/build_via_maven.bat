cd /d "%-dp0"
set JAVA_HOME=C:\Prog\java\open-jdk\jdk-21
set MAVEN_HOME=C:\Prog\apache-maven-3.9.4
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
REM java -version
mvn clean package