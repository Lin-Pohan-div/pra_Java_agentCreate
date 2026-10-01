<#
    Javalings 自我驗證腳本

    對每一題做三件事：
      1. 確認 .original/ 有原題且與練習檔一致（reset 還原得回正確的題目）
      2. 確認「未修改」的狀態下會失敗（題目有意義）
      3. 換上 solutions/ 的解答後會通過（題目可解、測試寫對）

    用法（在專案根目錄）：
        powershell -ExecutionPolicy Bypass -File tools\selftest.ps1
#>

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root

Write-Host "== 編譯 Javalings ==" -ForegroundColor Cyan
$core = Get-ChildItem -Recurse src\main\java -Filter *.java | ForEach-Object { $_.FullName }
New-Item -ItemType Directory -Force -Path build\classes | Out-Null
javac -encoding UTF-8 -d build\classes $core
if ($LASTEXITCODE -ne 0) {
    Write-Host "編譯失敗" -ForegroundColor Red
    exit 1
}

$ids = Get-ChildItem exercises -Directory | Sort-Object Name | ForEach-Object { $_.Name }

function Invoke-Javalings([string[]]$jargs) {
    java -cp build\classes dev.javaling.Javalings @jargs *> $null
    return $LASTEXITCODE
}

$failed = @()

Write-Host "`n== 檢查 1/3：.original/ 的原題應該和練習檔一致 ==" -ForegroundColor Cyan
foreach ($id in $ids) {
    $src = Get-ChildItem "exercises\$id" -Filter *.java | Where-Object { $_.Name -notlike '*Test.java' } | Select-Object -First 1
    $original = "exercises\$id\.original\$($src.Name).orig"
    if (-not (Test-Path $original)) {
        Write-Host "  $id : 找不到原題 $original" -ForegroundColor Red
        $failed += "$id (缺原題)"
    } elseif ((Get-FileHash $original).Hash -ne (Get-FileHash $src.FullName).Hash) {
        Write-Host "  $id : 與 $original 不一致" -ForegroundColor Red
        $failed += "$id (與原題不一致)"
    }
}

Write-Host "`n== 檢查 2/3：所有題目原本都應該失敗 ==" -ForegroundColor Cyan
Invoke-Javalings @('reset', '--all') | Out-Null
foreach ($id in $ids) {
    if ((Invoke-Javalings @('--no-color', 'verify', $id)) -eq 0) {
        Write-Host "  $id : 意外已經通過！" -ForegroundColor Red
        $failed += "$id (未修改就通過)"
    }
}

Write-Host "`n== 檢查 3/3：換上解答後所有題目都應該通過 ==" -ForegroundColor Cyan
$stage = 'build\selftest\exercises'
Remove-Item -Recurse -Force $stage -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $stage | Out-Null

foreach ($id in $ids) {
    $dir = "exercises\$id"
    $src = Get-ChildItem $dir -Filter *.java | Where-Object { $_.Name -notlike '*Test.java' } | Select-Object -First 1
    $tst = Get-ChildItem $dir -Filter *Test.java | Select-Object -First 1
    $dest = "$stage\$id"

    New-Item -ItemType Directory -Force -Path $dest | Out-Null
    Copy-Item "$dir\exercise.json" $dest
    Copy-Item $tst.FullName $dest
    Copy-Item $src.FullName $dest

    $solution = "solutions\$id\$($src.Name)"
    if (-not (Test-Path $solution)) {
        Write-Host "  $id : 找不到解答檔 $solution" -ForegroundColor Red
        $failed += "$id (缺解答)"
        continue
    }
    Copy-Item $solution $dest -Force

    $out = java -cp build\classes dev.javaling.Javalings --no-color --dir $stage verify $id 2>&1 | Out-String
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  $id : PASS" -ForegroundColor Green
    } else {
        Write-Host "  $id : FAIL" -ForegroundColor Red
        Write-Host ($out -replace '\s+', ' ')
        $failed += "$id (解答未通過)"
    }
}

Remove-Item -Recurse -Force build\selftest -ErrorAction SilentlyContinue
Invoke-Javalings @('reset', '--all') | Out-Null

Write-Host ""
if ($failed.Count -eq 0) {
    Write-Host "全部通過：$($ids.Count) 題的測試與解答一致。" -ForegroundColor Green
    exit 0
}
Write-Host "發現 $($failed.Count) 個問題：" -ForegroundColor Red
$failed | ForEach-Object { Write-Host "  - $_" -ForegroundColor Red }
exit 1
