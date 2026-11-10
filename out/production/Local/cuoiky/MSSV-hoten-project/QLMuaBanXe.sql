-- Chạy bằng SQL Server Management Studio
IF DB_ID('QLMuaBanXe') IS NULL CREATE DATABASE QLMuaBanXe;
GO
USE QLMuaBanXe;
GO
CREATE TABLE Khach_Hang(
    MaKH     INT           NOT NULL PRIMARY KEY,
    TenKH    NVARCHAR(50)  NOT NULL,
    CMND     BIGINT        NOT NULL UNIQUE,
    GioiTinh NVARCHAR(10)  NOT NULL CHECK (GioiTinh IN (N'Nam', N'Nữ')),
    DiaChi   NVARCHAR(50)  NULL
);
CREATE TABLE Xe(
    MaXe     INT           NOT NULL PRIMARY KEY,
    TenXe    NVARCHAR(50)  NOT NULL,
    DungTich INT           NOT NULL CHECK (DungTich > 0),
    DonGia   INT           NOT NULL CHECK (DonGia >= 0)
);
CREATE TABLE Mua_Xe(
    MaKH     INT           NOT NULL,
    MaXe     INT           NOT NULL,
    NgayMua  DATE          NOT NULL,
    SoLuong  INT           NOT NULL CHECK (SoLuong > 0),
    GhiChu   NVARCHAR(100) NULL,
    CONSTRAINT PK_Mua_Xe PRIMARY KEY (MaKH, MaXe, NgayMua),
    CONSTRAINT FK_MuaXe_KH FOREIGN KEY (MaKH) REFERENCES Khach_Hang(MaKH),
    CONSTRAINT FK_MuaXe_Xe FOREIGN KEY (MaXe) REFERENCES Xe(MaXe) ON DELETE CASCADE
);
GO
-- Dữ liệu mẫu
INSERT INTO Xe VALUES (1,N'Suzuki X-Bike',125,21500000),(2,N'Honda Wave',110,18000000),
                      (3,N'Yamaha Exciter',150,47000000),(4,N'Towner',100,15000000);
INSERT INTO Khach_Hang VALUES (1,N'Nguyễn Văn Sơn',301458080,N'Nam',N'Q1, TP.HCM'),
                              (2,N'Trần Thị Lan',301458081,N'Nữ',N'Q3, TP.HCM'),
                              (3,N'Lê Minh Tuấn',301458082,N'Nam',N'Thủ Đức');
INSERT INTO Mua_Xe VALUES (1,1,'2011-10-28',1,NULL),(2,1,'2012-10-29',3,NULL),(3,2,'2012-10-28',1,N'Laptop');
GO
