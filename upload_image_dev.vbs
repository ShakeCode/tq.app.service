
rem 配置ssh.exe等工具的环境变量： E:\soft-install\Git\usr\bin

rem win10 开启openssh, 配置环境变量： %SYSTEMROOT%\System32\OpenSSH\


Dim WshShell

Set WshShell=WScript.CreateObject("WScript.Shell")

WshShell.Run "E:/soft-install/Git/bin/bash.exe"

rem WshShell.Run "cmd.exe"

Public username, password

rem 账号
username="dream"

rem 远程ip
host ="192.168.1.130"

rem　远程服务器目录
targetPath="/home/dream/workplace"

rem 密码变量
password="666666"

WScript.Sleep 1500

rem 拼接命令上傳到远程指定目录
WshShell.SendKeys "scp  F:/gitthub-workplace/tq.app.service/target/tq.app.service.jar " & username & "@" & host & ":" & targetPath

WshShell.SendKeys "{ENTER}"

WScript.Sleep 1000

WshShell.SendKeys "ssh " & username & "@" & host

WshShell.SendKeys "{ENTER}"

WshShell.SendKeys password

WshShell.SendKeys "{ENTER}"

WScript.Sleep 1000

WshShell.SendKeys "ssh " & username & "@" & host

WshShell.SendKeys "{ENTER}"

WshShell.SendKeys password

WshShell.SendKeys "{ENTER}"

WScript.Sleep 1000

WshShell.SendKeys "cd /home/dream/workplace && ls -lrt"

WshShell.SendKeys "{ENTER}"