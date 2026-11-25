set JAVA_HOME=C:\Program Files\Java\jdk-27.0.1.1
set PATH=%JAVA_HOME%\bin;%PATH%
echo %PATH%
java --version
java --enable-preview -cp target\j27preview-1.0-SNAPSHOT.jar tp.loom.structured_concurrency.StructuredConcurrencyApp
pause