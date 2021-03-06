USE [master]
GO
/****** Object:  Database [MobileXD]    Script Date: 27/5/2018 12:57:49 PM ******/
CREATE DATABASE [MobileXD]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MobileXD', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\MobileXD.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'MobileXD_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\MobileXD_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [MobileXD] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MobileXD].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MobileXD] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MobileXD] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MobileXD] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MobileXD] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MobileXD] SET ARITHABORT OFF 
GO
ALTER DATABASE [MobileXD] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MobileXD] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MobileXD] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MobileXD] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MobileXD] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MobileXD] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MobileXD] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MobileXD] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MobileXD] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MobileXD] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MobileXD] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MobileXD] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MobileXD] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MobileXD] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MobileXD] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MobileXD] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MobileXD] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MobileXD] SET RECOVERY FULL 
GO
ALTER DATABASE [MobileXD] SET  MULTI_USER 
GO
ALTER DATABASE [MobileXD] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MobileXD] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MobileXD] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MobileXD] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [MobileXD] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'MobileXD', N'ON'
GO
USE [MobileXD]
GO
/****** Object:  Table [dbo].[tblAccount]    Script Date: 27/5/2018 12:57:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblAccount](
	[username] [nvarchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[role] [int] NOT NULL,
	[personID] [int] NULL,
 CONSTRAINT [PK_tblAccount] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblClass]    Script Date: 27/5/2018 12:57:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblClass](
	[classID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[course] [nvarchar](20) NULL,
	[teacherID] [int] NOT NULL,
	[roomID] [int] NOT NULL,
	[slot] [int] NOT NULL,
	[weekday] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_tblClass] PRIMARY KEY CLUSTERED 
(
	[classID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblLearning]    Script Date: 27/5/2018 12:57:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblLearning](
	[learningID] [int] IDENTITY(1,1) NOT NULL,
	[studentID] [int] NOT NULL,
	[classID] [int] NOT NULL,
 CONSTRAINT [PK_tblLearning] PRIMARY KEY CLUSTERED 
(
	[learningID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblRoom]    Script Date: 27/5/2018 12:57:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoom](
	[roomID] [int] IDENTITY(1,1) NOT NULL,
	[roomNumber] [int] NOT NULL,
	[floor] [int] NULL,
 CONSTRAINT [PK_tblRoom] PRIMARY KEY CLUSTERED 
(
	[roomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblStudent]    Script Date: 27/5/2018 12:57:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblStudent](
	[studentID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[birthday] [date] NOT NULL,
	[sex] [nvarchar](20) NOT NULL,
	[nameParent] [nvarchar](100) NOT NULL,
	[phoneStudent] [nvarchar](11) NULL,
	[phoneParent] [nvarchar](11) NULL,
	[tuitionFee] [int] NULL,
	[statusPayment] [bit] NOT NULL,
	[dateStart] [date] NULL,
	[grade_level] [int] NULL,
 CONSTRAINT [PK_tblStudent] PRIMARY KEY CLUSTERED 
(
	[studentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblTeacher]    Script Date: 27/5/2018 12:57:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblTeacher](
	[teacherID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[birthday] [date] NOT NULL,
	[sex] [nvarchar](20) NOT NULL,
	[address] [nvarchar](100) NULL,
	[specialize] [nvarchar](50) NOT NULL,
	[salary] [int] NULL,
 CONSTRAINT [PK_tblTeacher] PRIMARY KEY CLUSTERED 
(
	[teacherID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[tblClass] ON 

INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (5, N'morningSun', NULL, 2, 3, 1, N'sunday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (6, N'noonSun', NULL, 2, 3, 4, N'sunday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (7, N'nightMon', NULL, 2, 3, 5, N'monday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (8, N'noonTue', NULL, 2, 3, 4, N'tuesday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (9, N'nightTue', NULL, 2, 3, 5, N'tuesday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (10, N'nightWed', NULL, 2, 3, 5, N'wednesday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (11, N'noonThur', NULL, 2, 3, 4, N'thursday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (12, N'nightThur', NULL, 2, 3, 5, N'thursday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (13, N'noonFri', NULL, 2, 3, 4, N'friday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (14, N'nightFri', NULL, 2, 3, 5, N'thursday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (15, N'noonSat', NULL, 2, 3, 4, N'saturday')
INSERT [dbo].[tblClass] ([classID], [name], [course], [teacherID], [roomID], [slot], [weekday]) VALUES (16, N'nightSat', NULL, 2, 3, 5, N'saturday')
SET IDENTITY_INSERT [dbo].[tblClass] OFF
SET IDENTITY_INSERT [dbo].[tblLearning] ON 

INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (1, 4, 5)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (2, 5, 5)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (3, 6, 5)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (4, 26, 6)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (5, 27, 6)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (6, 12, 6)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (7, 4, 7)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (8, 21, 7)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (9, 12, 7)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (10, 7, 8)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (11, 19, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (12, 17, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (13, 18, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (14, 22, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (15, 32, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (16, 13, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (17, 16, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (18, 12, 9)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (20, 5, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (21, 4, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (22, 7, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (23, 21, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (24, 12, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (25, 27, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (27, 26, 10)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (28, 7, 11)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (29, 19, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (30, 17, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (31, 18, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (34, 22, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (35, 32, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (36, 13, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (37, 16, 12)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (38, 28, 13)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (39, 27, 13)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (40, 12, 13)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (41, 4, 14)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (42, 21, 14)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (43, 12, 14)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (44, 5, 15)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (45, 4, 15)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (46, 6, 15)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (47, 26, 15)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (48, 27, 15)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (49, 19, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (50, 17, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (51, 18, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (52, 22, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (53, 32, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (54, 13, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (55, 16, 16)
INSERT [dbo].[tblLearning] ([learningID], [studentID], [classID]) VALUES (56, 12, 16)
SET IDENTITY_INSERT [dbo].[tblLearning] OFF
SET IDENTITY_INSERT [dbo].[tblRoom] ON 

INSERT [dbo].[tblRoom] ([roomID], [roomNumber], [floor]) VALUES (3, 1, 1)
SET IDENTITY_INSERT [dbo].[tblRoom] OFF
SET IDENTITY_INSERT [dbo].[tblStudent] ON 

INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (4, N'Nguyễn Trung Kiên', CAST(N'2017-09-30' AS Date), N'male', N'updating', NULL, NULL, 400000, 1, CAST(N'2017-01-07' AS Date), 9)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (5, N'Huy', CAST(N'2018-01-04' AS Date), N'male', N'updating', NULL, NULL, 600000, 1, CAST(N'2017-01-07' AS Date), 9)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (6, N'Thy', CAST(N'2018-03-10' AS Date), N'female', N'updating', NULL, NULL, 400000, 1, CAST(N'2017-07-13' AS Date), 9)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (7, N'Thanh Liêm', CAST(N'1992-09-09' AS Date), N'male', N'updaing', NULL, NULL, 700000, 1, CAST(N'2017-02-08' AS Date), 12)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (9, N'Thanh Nhã', CAST(N'1992-09-09' AS Date), N'unidentified', N'updating', NULL, NULL, 600000, 0, CAST(N'2017-02-08' AS Date), 10)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (12, N'Trần Ngọc Bảo Nhi', CAST(N'1992-09-09' AS Date), N'female', N'updating', NULL, NULL, 300000, 0, CAST(N'2017-01-07' AS Date), 3)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (13, N'Nguyễn Nhật Hào', CAST(N'1992-09-09' AS Date), N'male', N'updating', NULL, NULL, 400000, 1, CAST(N'2017-08-08' AS Date), 7)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (16, N'Tăng Ngọc Tường Vy', CAST(N'2018-08-21' AS Date), N'female', N'updating', NULL, NULL, 400000, 1, CAST(N'2017-01-07' AS Date), 7)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (17, N'Nguyễn Thiên Kim', CAST(N'2018-04-28' AS Date), N'female', N'updating', NULL, NULL, 200000, 1, CAST(N'2017-01-07' AS Date), 6)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (18, N'Trần Đông An', CAST(N'1992-09-09' AS Date), N'male', N'updating', NULL, NULL, 400000, 0, CAST(N'2017-08-08' AS Date), 6)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (19, N'Nguyễn Nhật Ly', CAST(N'1992-09-09' AS Date), N'unidentified', N'updating', NULL, NULL, 400000, 1, CAST(N'2017-08-08' AS Date), 6)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (21, N'Thanh Trúc', CAST(N'2018-02-09' AS Date), N'female', N'updating', NULL, NULL, 200000, 1, CAST(N'2017-08-22' AS Date), 4)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (22, N'Linh', CAST(N'2018-10-10' AS Date), N'female', N'updating', NULL, NULL, 300000, 1, CAST(N'2017-07-11' AS Date), 6)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (25, N'Thư ', CAST(N'1992-09-09' AS Date), N'female', N'updating', NULL, NULL, 2000000, 0, CAST(N'2018-08-05' AS Date), 9)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (26, N'Duy', CAST(N'1992-09-09' AS Date), N'male', N'updating', NULL, NULL, 300000, 0, CAST(N'2018-07-01' AS Date), 8)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (27, N'Kiệt', CAST(N'1992-09-09' AS Date), N'male', N'updating', NULL, NULL, 300000, 0, CAST(N'2017-05-12' AS Date), 8)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (28, N'Đặng Nhật Duy', CAST(N'1992-09-09' AS Date), N'male', N'updating', NULL, NULL, 200000, 0, CAST(N'2018-01-05' AS Date), 3)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (30, N'Lan', CAST(N'1992-09-09' AS Date), N'female', N'updating', NULL, NULL, 400000, 0, CAST(N'2018-07-01' AS Date), 8)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (31, N'Minh Khôi', CAST(N'1992-09-09' AS Date), N'male', N'updating', NULL, NULL, 600000, 0, CAST(N'2017-01-07' AS Date), 9)
INSERT [dbo].[tblStudent] ([studentID], [name], [birthday], [sex], [nameParent], [phoneStudent], [phoneParent], [tuitionFee], [statusPayment], [dateStart], [grade_level]) VALUES (32, N'Hân', CAST(N'1992-09-09' AS Date), N'unidentified', N'updating', NULL, NULL, 400000, 0, CAST(N'2017-03-11' AS Date), 6)
SET IDENTITY_INSERT [dbo].[tblStudent] OFF
SET IDENTITY_INSERT [dbo].[tblTeacher] ON 

INSERT [dbo].[tblTeacher] ([teacherID], [name], [birthday], [sex], [address], [specialize], [salary]) VALUES (2, N'Nguyễn Hoàng Hải Đăng', CAST(N'1992-09-09' AS Date), N'unidentified', N'updating', N'teaching', 9500000)
SET IDENTITY_INSERT [dbo].[tblTeacher] OFF
ALTER TABLE [dbo].[tblAccount]  WITH CHECK ADD  CONSTRAINT [FK_tblAccount_tblStudent] FOREIGN KEY([personID])
REFERENCES [dbo].[tblStudent] ([studentID])
GO
ALTER TABLE [dbo].[tblAccount] CHECK CONSTRAINT [FK_tblAccount_tblStudent]
GO
ALTER TABLE [dbo].[tblAccount]  WITH CHECK ADD  CONSTRAINT [FK_tblAccount_tblTeacher] FOREIGN KEY([personID])
REFERENCES [dbo].[tblTeacher] ([teacherID])
GO
ALTER TABLE [dbo].[tblAccount] CHECK CONSTRAINT [FK_tblAccount_tblTeacher]
GO
ALTER TABLE [dbo].[tblClass]  WITH CHECK ADD  CONSTRAINT [FK_tblClass_tblRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tblRoom] ([roomID])
GO
ALTER TABLE [dbo].[tblClass] CHECK CONSTRAINT [FK_tblClass_tblRoom]
GO
ALTER TABLE [dbo].[tblClass]  WITH CHECK ADD  CONSTRAINT [FK_tblClass_tblTeacher] FOREIGN KEY([teacherID])
REFERENCES [dbo].[tblTeacher] ([teacherID])
GO
ALTER TABLE [dbo].[tblClass] CHECK CONSTRAINT [FK_tblClass_tblTeacher]
GO
ALTER TABLE [dbo].[tblLearning]  WITH CHECK ADD  CONSTRAINT [FK_tblLearning_tblClass] FOREIGN KEY([classID])
REFERENCES [dbo].[tblClass] ([classID])
GO
ALTER TABLE [dbo].[tblLearning] CHECK CONSTRAINT [FK_tblLearning_tblClass]
GO
ALTER TABLE [dbo].[tblLearning]  WITH CHECK ADD  CONSTRAINT [FK_tblLearning_tblStudent] FOREIGN KEY([studentID])
REFERENCES [dbo].[tblStudent] ([studentID])
GO
ALTER TABLE [dbo].[tblLearning] CHECK CONSTRAINT [FK_tblLearning_tblStudent]
GO
USE [master]
GO
ALTER DATABASE [MobileXD] SET  READ_WRITE 
GO
