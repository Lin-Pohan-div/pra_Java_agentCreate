@echo off
rem Javalings launcher for Windows (requires a JDK; PowerShell ships with Windows).
rem Keeps messages ASCII-only so they render under any console codepage.
setlocal

set "ROOT=%~dp0"

powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%tools\build.ps1"
if errorlevel 1 exit /b 1

rem No chcp here: the CLI encodes output in the console's own codepage
rem (e.g. cp950), so CJK text renders without switching to 65001.
java -cp "%ROOT%build\classes" dev.javaling.Javalings %*
exit /b %ERRORLEVEL%
