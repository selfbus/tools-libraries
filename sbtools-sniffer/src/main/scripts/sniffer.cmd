@echo off
setlocal

cd libs
set CLASSPATH=%CLASSPATH%;.
java -jar sbtools-sniffer-0.3-SNAPSHOT.jar org.selfbus.sbtools.devtool.DevTool

if %ERRORLEVEL% neq 0 (
   echo.
   echo Please note: This application requires Java 7
   echo (This notice might not be related to your problem above)
   echo.
   pause
)
