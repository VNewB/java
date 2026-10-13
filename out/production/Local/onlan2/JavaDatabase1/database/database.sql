-- Chạy script này trong SQL Server Management Studio (SSMS)
IF DB_ID('QLHangHoa') IS NULL
    CREATE DATABASE QLHangHoa;
GO
USE QLHangHoa;
GO

DROP TABLE IF EXISTS HangHoa;
DROP TABLE IF EXISTS LoaiHang;
GO

-- Bảng Loại hàng: khóa chính tự tăng, tên loại không trùng, tỷ lệ 0..100 (%)
CREATE TABLE LoaiHang (
                          MaLoai INT IDENTITY(1,1) CONSTRAINT PK_LoaiHang PRIMARY KEY,
                          TLH    NVARCHAR(50) NOT NULL CONSTRAINT UQ_LoaiHang_TLH UNIQUE,
                          TyLe   INT NOT NULL CONSTRAINT CK_LoaiHang_TyLe CHECK (TyLe BETWEEN 0 AND 100)
);

-- Bảng Hàng hóa: khóa chính MHH (tối đa 5 chữ số), khóa ngoại MaLoai -> LoaiHang
CREATE TABLE HangHoa (
                         MHH      INT NOT NULL CONSTRAINT PK_HangHoa PRIMARY KEY
                             CONSTRAINT CK_HangHoa_MHH CHECK (MHH BETWEEN 1 AND 99999),
                         THH      NVARCHAR(50) NOT NULL,
                         MaLoai   INT NOT NULL
                             CONSTRAINT FK_HangHoa_LoaiHang FOREIGN KEY REFERENCES LoaiHang(MaLoai),
                         TinhChat NVARCHAR(50) NOT NULL
);
GO

INSERT INTO LoaiHang (TLH, TyLe) VALUES
    (N'Điện tử', 5),
    (N'Đồ thể thao', 3),
    (N'Nhà bếp', 4);

INSERT INTO HangHoa (MHH, THH, MaLoai, TinhChat) VALUES
                                                     (10001, N'Bếp từ AK', 1, N'Chống ẩm'),
                                                     (10002, N'Vợt cầu lông', 2, N'Chống ẩm');
GO