
%关闭其他所有命令回显%
@echo off

@echo start push image program

echo. 开始打包

call mvn clean package


% timeout /nobreak /t 3

start F:\\gitthub-workplace\\tq.app.service\\upload_image_dev.vbs

pause