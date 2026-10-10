@echo off
rem Javalings launcher for Windows (requires a JDK; PowerShell ships with Windows).
rem Keeps messages ASCII-only so they render under any console codepage.
setlocal

set "ROOT=%~dp0"

powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\build.ps1"
if errorlevel 1 exit /b 1

rem No chcp here: the CLI encodes output in the console's own codepage
rem (e.g. cp950), so CJK text renders without switching to 65001.
:run
java -cp "%ROOT%build\classes" dev.javaling.Javalings %*
set "RC=%ERRORLEVEL%"

rem Ctrl+C makes cmd ask "Terminate batch job (Y/N)?". Y ends the script right
rem there; N falls through to here, so restart instead of exiting anyway.
rem Java exits with 130 on Ctrl+C; -1073741510 is STATUS_CONTROL_C_EXIT.
if "%RC%"=="130" goto run
if "%RC%"=="-1073741510" goto run
exit /b %RC%
