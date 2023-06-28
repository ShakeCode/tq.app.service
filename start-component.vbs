Dim WshShell
Set WshShell = WScript.CreateObject("WScript.Shell") 

rem 管理员运行

rem https://blog.csdn.net/zw05011/article/details/113386320

If WScript.Arguments.Length = 0 Then 
  Set ObjShell = CreateObject("Shell.Application") 
  ObjShell.ShellExecute "wscript.exe" _ 
  , """" & WScript.ScriptFullName & """ RunAsAdministrator", , "runas", 1 
  WScript.Quit 
End if 

rem 启动mysql服务
WshShell.run  "cmd.exe /k  net start mysql & E:\\software\\springcloud\\nacos-server-2.2.2\\nacos\\bin\\startup.cmd"

wscript.sleep 2000

WshShell.run  "cmd.exe /k  E:/software/kafka/kafka_2.13-3.4.0/bin/windows/zookeeper-server-start.bat  E:/software/kafka/kafka_2.13-3.4.0/config/zookeeper.properties"

wscript.sleep 2000

WshShell.run  "cmd.exe /k  E:/software/kafka/kafka_2.13-3.4.0/bin/windows/kafka-server-start.bat  E:/software/kafka/kafka_2.13-3.4.0/config//server.properties"

wscript.sleep 2000

WshShell.run  "cmd.exe /k  E:/software/redis/win-redis/Redis-x64-3.2.100/redis-server.exe  E:/software/redis/win-redis/Redis-x64-3.2.100/redis.windows.conf "

wscript.sleep 2000

WshShell.run  "E:/soft-install/NavicatPremium12/navicat.exe"

wscript.sleep 2000

WshShell.run  "E:\\soft-install\\IntelliJ_IDEA_2020.2\\bin\idea64.exe"

wscript.sleep 2000

WshShell.run  "cmd.exe /k E:\\soft-install\\rabbitmq-3.9.13\\rabbitmq_server-3.9.13\\sbin\\rabbitmq-service.bat start"

rem wscript.quit