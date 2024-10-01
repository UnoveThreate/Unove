
USE unove_cine;



-- Bảng PremiumType: Lưu các loại Premium của người dùng
CREATE TABLE PremiumType (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description LONGTEXT CHARACTER SET utf8mb4,
    discount_percent DECIMAL(5,2) NOT NULL,
    is_global BOOLEAN NOT NULL
);

-- Bảng CinemaChain: Lưu thông tin chuỗi rạp chiếu phim
CREATE TABLE CinemaChain (
    CinemaChainID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Information VARCHAR(255) CHARACTER SET utf8mb4
);

-- Bảng Cinema: Lưu thông tin rạp chiếu
CREATE TABLE Cinema (
    CinemaID INT AUTO_INCREMENT PRIMARY KEY,
    CinemaChainID INT,
    Address VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL,
    Province VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL,
    District VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL,
    Commune VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL,
    FOREIGN KEY (CinemaChainID) REFERENCES CinemaChain(CinemaChainID)
);

-- Bảng User: Lưu thông tin người dùng
CREATE TABLE User (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    AvatarLink VARCHAR(255),
    Role VARCHAR(20),
    Username VARCHAR(64) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Bio VARCHAR(255) CHARACTER SET utf8mb4,
    Email VARCHAR(64) NOT NULL UNIQUE,
    Fullname VARCHAR(64) CHARACTER SET utf8mb4,
    Birthday DATE,
    Address VARCHAR(255) CHARACTER SET utf8mb4,
    IsBanned BIT DEFAULT 0,
    PremiumTypeID INT,
    AccountBalance FLOAT DEFAULT 0,
    BonusPoint INT DEFAULT 0,
    Province VARCHAR(200) CHARACTER SET utf8mb4,
    District VARCHAR(200) CHARACTER SET utf8mb4,
    Commune VARCHAR(200) CHARACTER SET utf8mb4,
    Code VARCHAR(6),
    Status INT DEFAULT 0,
    FOREIGN KEY (PremiumTypeID) REFERENCES PremiumType(id)
);

-- Bảng Movie: Lưu thông tin phim
CREATE TABLE Movie (
    MovieID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL,
    Synopsis VARCHAR(255),
    DatePublished DATE,
    ImageURL VARCHAR(255),
    Rating FLOAT DEFAULT 0,
    Country VARCHAR(64),
    LinkTrailer VARCHAR(255),
    CinemaChainID INT,
    CinemaID INT,
    FOREIGN KEY (CinemaChainID) REFERENCES CinemaChain(CinemaChainID),
    FOREIGN KEY (CinemaID) REFERENCES Cinema(CinemaID)
);

-- Bảng MovieReview: Lưu đánh giá phim
CREATE TABLE MovieReview (
    MovieReviewID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    MovieID INT,
    Rating TINYINT,
    TimeCreated DATETIME,
    Content VARCHAR(255) CHARACTER SET utf8mb4,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
);

-- Bảng MovieInGenre: Lưu thông tin phim thuộc thể loại nào
CREATE TABLE MovieInGenre (
    MovieID INT,
    Genre VARCHAR(64) CHARACTER SET utf8mb4 NOT NULL,
    PRIMARY KEY (MovieID, Genre),
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
);

-- Bảng Room: Lưu thông tin phòng chiếu phim
CREATE TABLE Room (
    RoomID INT AUTO_INCREMENT PRIMARY KEY,
    RoomName VARCHAR(64) CHARACTER SET utf8mb4 NOT NULL,
    Capacity INT NOT NULL,
    ScreenType VARCHAR(250) CHARACTER SET utf8mb4,
    IsAvailable BOOLEAN DEFAULT TRUE,
    CinemaID INT,
    FOREIGN KEY (CinemaID) REFERENCES Cinema(CinemaID)
);

-- Bảng RoomType: Lưu các loại phòng chiếu (3D, 4DX, IMAX)
CREATE TABLE RoomType (
    RoomID INT,
    Type VARCHAR(64) CHARACTER SET utf8mb4,
    PRIMARY KEY (RoomID, Type),
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID)
);

-- Bảng Seat: Lưu thông tin chỗ ngồi trong phòng chiếu
CREATE TABLE Seat (
    SeatID INT AUTO_INCREMENT PRIMARY KEY,
    RoomID INT,
    Name VARCHAR(50) CHARACTER SET utf8mb4,
    CoordinateX INT,
    CoordinateY INT,
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID)
);

-- Bảng MovieSlot: Lưu thông tin suất chiếu
CREATE TABLE MovieSlot (
    MovieSlotID INT AUTO_INCREMENT PRIMARY KEY,
    RoomID INT,
    MovieID INT,
    StartTime DATETIME,
    EndTime DATETIME,
    Type VARCHAR(64),
    Price FLOAT,
    Discount FLOAT,
    Status VARCHAR(64) CHARACTER SET utf8mb4,
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID),
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
);

-- Các bảng còn lại tương tự như trong câu lệnh của bạn


-- Bảng FavoriteFilm: Lưu thông tin phim yêu thích của người dùng
CREATE TABLE FavoriteFilm (
    UserID INT,
    MovieID INT,
    FavoritedAt DATETIME,
    PRIMARY KEY (UserID, MovieID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
);

-- Bảng Order: Lưu thông tin đơn đặt hàng
CREATE TABLE `Order` (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
	MovieSlotID INT,
    TimeCreated DATETIME,
    PremiumTypeID INT,
    Status VARCHAR(64) ,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
	FOREIGN KEY (MovieSlotID) REFERENCES MovieSlot(MovieSlotID),
    FOREIGN KEY (PremiumTypeID) REFERENCES PremiumType(id)
);

-- Bảng Ticket: Lưu thông tin vé đã mua
CREATE TABLE Ticket (
    TicketID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT,
    SeatID INT,
    Status VARCHAR(64),
    FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID),
    FOREIGN KEY (SeatID) REFERENCES Seat(SeatID)
);

-- Bảng ApprovalRequest: Quản lý yêu cầu phê duyệt của người dùng
CREATE TABLE ApprovalRequest (
    UserID INT,
    CinemaChainID INT,
    Content VARCHAR(255) CHARACTER SET utf8mb4,
    Status VARCHAR(64),
    PRIMARY KEY (UserID, CinemaChainID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (CinemaChainID) REFERENCES CinemaChain(CinemaChainID)
);

-- Bảng CanteenItem: Lưu các sản phẩm của căng tin tại rạp chiếu phim
CREATE TABLE CanteenItem (
    CanteenItemID INT AUTO_INCREMENT PRIMARY KEY,
    CinemaChainID INT,
    Name VARCHAR(64) CHARACTER SET utf8mb4,
    Price FLOAT,
    Stock INT,
    Status VARCHAR(64),
    FOREIGN KEY (CinemaChainID) REFERENCES CinemaChain(CinemaChainID)
);

-- Bảng LikedMovieReview: Lưu thông tin người dùng thích các đánh giá phim
CREATE TABLE LikedMovieReview (
    UserID INT,
    MovieReviewID INT,
    PRIMARY KEY (UserID, MovieReviewID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (MovieReviewID) REFERENCES MovieReview(MovieReviewID)
);

-- Bảng Verify: Lưu mã xác thực của người dùng
CREATE TABLE Verify (
    UserID INT,
    Type VARCHAR(10),
    Code VARCHAR(255),
    Expiry DATETIME,
    PRIMARY KEY (UserID, Type),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Bảng MemberPremium: Lưu thông tin Premium của thành viên
CREATE TABLE MemberPremium (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    premium_type_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (premium_type_id) REFERENCES PremiumType(id),
    FOREIGN KEY (user_id) REFERENCES User(UserID)
);

-- Bảng MemberVIP: Lưu thông tin VIP của thành viên
CREATE TABLE MemberVIP (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    premium_type_id INT NOT NULL,
    cinema_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (premium_type_id) REFERENCES PremiumType(id),
    FOREIGN KEY (user_id) REFERENCES User(UserID),
    FOREIGN KEY (cinema_id) REFERENCES Cinema(CinemaID)
);

-- Thêm dữ liệu mẫu vào bảng CinemaChain
INSERT INTO CinemaChain (Name, Information) VALUES
('CGV', 'Chuỗi rạp chiếu phim lớn nhất Việt Nam'),
('Lotte Cinema', 'Chuỗi rạp chiếu phim của Hàn Quốc');

-- Thêm dữ liệu mẫu vào bảng Cinema
INSERT INTO Cinema (CinemaChainID, Address, Province, District, Commune) VALUES
(1, '54 Nguyễn Chí Thanh, Đống Đa, Hà Nội', 'Hà Nội', 'Đống Đa', 'Láng Thượng'),
(2, '54 Liễu Giai, Ba Đình, Hà Nội', 'Hà Nội', 'Ba Đình', 'Liễu Giai');

-- Thêm dữ liệu mẫu vào bảng Movie
INSERT INTO Movie (Title, Synopsis, DatePublished, ImageURL, Rating, Country) VALUES
('Avengers: Endgame', 'Biệt đội Avengers tập hợp một lần nữa để đánh bại Thanos', '2019-04-26', 'avengers_endgame.jpg', 8.4, 'USA'),
('Parasite', 'Một gia đình nghèo lên kế hoạch xâm nhập vào một gia đình giàu có', '2019-05-30', 'parasite.jpg', 8.6, 'South Korea');

-- Thêm dữ liệu mẫu vào bảng Room
INSERT INTO Room (RoomName, Capacity, ScreenType, IsAvailable, CinemaID) VALUES
('Phòng 1', 100, '2D', 1, 1),
('Phòng 2', 80, '3D', 1, 1),
('Phòng 1', 120, 'IMAX', 1, 2);

-- Thêm dữ liệu mẫu vào bảng MovieSlot
INSERT INTO MovieSlot (RoomID, MovieID, StartTime, EndTime, Type, Price, Discount, Status) VALUES
(1, 1, '2023-05-20 10:00:00', '2023-05-20 12:30:00', '2D', 100000, 0, 'Đang bán'),
(2, 1, '2023-05-20 13:00:00', '2023-05-20 15:30:00', '3D', 120000, 0, 'Đang bán'),
(3, 2, '2023-05-20 11:00:00', '2023-05-20 13:15:00', 'IMAX', 150000, 10000, 'Đang bán');
