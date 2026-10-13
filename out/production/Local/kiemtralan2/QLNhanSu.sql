-- Tạo CSDL QLNhanSu
IF DB_ID(N'QLNhanSu') IS NOT NULL
BEGIN
    ALTER DATABASE QLNhanSu SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QLNhanSu;
END
GO
CREATE DATABASE QLNhanSu;
GO
USE QLNhanSu;
GO

-- Bảng Cấp (a. khóa chính, d. ràng buộc)
CREATE TABLE Cap (
    MaCap    INT           NOT NULL,
    Ten      NVARCHAR(50)  NOT NULL,
    BaoHiem  INT           NOT NULL,
    CONSTRAINT PK_Cap PRIMARY KEY (MaCap),
    CONSTRAINT UQ_Cap_Ten UNIQUE (Ten),
    CONSTRAINT CK_Cap_BaoHiem CHECK (BaoHiem BETWEEN 0 AND 100)
);

-- Bảng Nhân sự (a. khóa chính, b/c. khóa ngoại + quan hệ, d. ràng buộc)
CREATE TABLE NhanSu (
    MNS       INT           NOT NULL,
    TNS       NVARCHAR(50)  NOT NULL,
    TienLuong INT           NOT NULL,
    MaCap     INT           NOT NULL,
    CONSTRAINT PK_NhanSu PRIMARY KEY (MNS),
    CONSTRAINT CK_NhanSu_MNS CHECK (MNS BETWEEN 1 AND 99999),   -- int(5)
    CONSTRAINT CK_NhanSu_TienLuong CHECK (TienLuong >= 0),
    CONSTRAINT CK_NhanSu_TNS CHECK (LEN(LTRIM(RTRIM(TNS))) > 0),
    CONSTRAINT FK_NhanSu_Cap FOREIGN KEY (MaCap) REFERENCES Cap(MaCap)
);
GO

-- Dữ liệu mẫu
INSERT INTO Cap VALUES (1, N'Quản lý', 10), (2, N'Lãnh đạo', 15), (3, N'Tác nghiệp', 5);

INSERT INTO NhanSu VALUES
 (1, N'Nguyễn Văn An',  15000000, 1),
 (2, N'Trần Thị Bình',  25000000, 2),
 (3, N'Lê Hoàng Cường',  9000000, 3),
 (4, N'Phạm Minh Dũng',  9500000, 3);
GO
