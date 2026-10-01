# Builds the Javalings CLI into build\classes, skipping the build when nothing changed.
#
# Deliberately ASCII-only so it parses correctly under any console codepage,
# and so it needs no UTF-8 BOM.

param()

$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $PSScriptRoot
$classes = Join-Path $root 'build\classes'
$marker = Join-Path $root 'build\.last-build'
$srcRoot = Join-Path $root 'src\main\java'

$sources = @(Get-ChildItem -Recurse -Filter *.java $srcRoot)
if ($sources.Count -eq 0) {
    Write-Host '[javalings] No Java sources found under src\main\java.'
    exit 1
}

$newestSource = ($sources | Sort-Object LastWriteTimeUtc -Descending | Select-Object -First 1).LastWriteTimeUtc

$needsBuild = $true
if (Test-Path $marker) {
    $needsBuild = $newestSource -gt (Get-Item $marker).LastWriteTimeUtc
}

if (-not $needsBuild) {
    exit 0
}

New-Item -ItemType Directory -Force -Path $classes | Out-Null
Write-Host '[javalings] Building...'

# javac accepts the file list as separate arguments here; the .cmd launcher
# instead uses an @argfile because pure-cmd quoting is unreliable.
& javac -encoding UTF-8 -d $classes $sources.FullName
if ($LASTEXITCODE -ne 0) {
    Write-Host '[javalings] Build failed. Is a JDK installed and javac on PATH?'
    exit 1
}

Set-Content -Path $marker -Value 'ok' -Encoding ascii
exit 0
