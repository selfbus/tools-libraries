@echo off
setlocal

cd libs
set CLASSPATH=%CLASSPATH%;.
java -jar MAIN_JAR

if %ERRORLEVEL% neq 0 (
   echo.
   echo Please note: This application requires Java 7
   echo (This notice might not be related to your problem above)
   echo.
   pause
)
