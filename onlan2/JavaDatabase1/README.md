Trên Linux, sử dụng podman trong thư mục podman:
$ podman compose up -d

Sau đó vào database của my-sqlserver để tiếp tục tạo database:
Ví dụ như sau:

deby@debiana:~/Local/java/gp/java/onlan2/JavaDatabase1$ podman exec -it my-sqlserver /bin/bash
mssql@a394829ab1e1:/$ /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'YourStrongPassword123!' -C -i /dev/stdin < ./database.sql 
Changed database context to 'QLHangHoa'.
 
(3 rows affected)
 
(2 rows affected)


Compile và chạy:
$ mvn compile
$ mvn exec:java