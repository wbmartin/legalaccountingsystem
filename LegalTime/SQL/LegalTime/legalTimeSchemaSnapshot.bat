@echo off
rem for /f "tokens=2-4* delims=/ " %%a in ('DATE /T') do set THEDATE=%%c%%b%%a
rem For /F "tokens=2-4 delims=:" %%a in ('Command /C Echo. ^| Time ^| Find "current"') Do Set THETIME=%%a%%b%%c
rem Set THETIME=%THETIME:~1%
rem Set DATETIME = %THEDATE%%THETIME%
rem echo %THEDATE%%THETIME%
@echo on
 "c:\Program Files\PostgreSQL\8.4\bin\pg_dump" -U postgres -h127.0.0.1 -s  legal_time  > "C:\Users\bmartin\eclipse_workspace\LegalTime\SQL\LegalTime\legal_time_schema.sql"