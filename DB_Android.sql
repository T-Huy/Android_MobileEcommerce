drop DATABASE app_dienthoai;

CREATE DATABASE app_dienthoai;

USE app_dienthoai;

CREATE TABLE `brand` (
    `brand_ID` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(200) NOT NULL,
    `logo` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `update_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `image_data` LONGBLOB,
    PRIMARY KEY (`brand_ID`)
); 

CREATE TABLE `color` (
    `color_ID` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `update_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`color_ID`)
);

CREATE TABLE `user` (
    `user_name` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `role` VARCHAR(15) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `update_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_name`)
);

CREATE TABLE `customer` (
    `user_name` VARCHAR(100) NOT NULL,
    `fullname` VARCHAR(200) DEFAULT NULL,
    `address` VARCHAR(200) DEFAULT NULL,
    `phonenumber` VARCHAR(11) DEFAULT NULL,
    `avatar` VARCHAR(200) DEFAULT NULL,
    PRIMARY KEY (`user_name`),
    CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`user_name`)
        REFERENCES `user` (`user_name`)
);

CREATE TABLE `product` (
  `product_ID` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `os` varchar(20) NOT NULL,
  `cpu` varchar(100) NOT NULL,
  `origin` varchar(200) NOT NULL,
  `brand_ID` int NOT NULL,
  `description` varchar(5000) NOT NULL,
  `battery` varchar(50) NOT NULL,
  `screen` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_ID`),
  KEY `brand_ID` (`brand_ID`),
  FULLTEXT KEY `product_name` (`product_name`,`os`,`cpu`),
  FULLTEXT KEY `search_product` (`product_name`,`os`,`cpu`),
  FULLTEXT KEY `product_name_2` (`product_name`,`os`,`cpu`),
  FULLTEXT KEY `search_product_2` (`product_name`,`os`,`cpu`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`brand_ID`) REFERENCES `brand` (`brand_ID`)
);

CREATE TABLE `options` (
  `options_ID` int NOT NULL AUTO_INCREMENT,
  `product_ID` int NOT NULL,
  `color_ID` int NOT NULL,
  `ram` varchar(100) NOT NULL,
  `rom` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`options_ID`),
  KEY `color_ID` (`color_ID`),
  KEY `product_ID` (`product_ID`),
  CONSTRAINT `options_ibfk_1` FOREIGN KEY (`color_ID`) REFERENCES `color` (`color_ID`),
  CONSTRAINT `options_ibfk_2` FOREIGN KEY (`product_ID`) REFERENCES `product` (`product_ID`)
);

CREATE TABLE `image` (
  `image_ID` int NOT NULL AUTO_INCREMENT,
  `options_ID` int NOT NULL,
  `path` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`image_ID`),
  KEY `options_ID` (`options_ID`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`options_ID`) REFERENCES `options` (`options_ID`)
);

CREATE TABLE `orders` (
  `order_ID` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `total_price` double DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_ID`),
  KEY `user_name` (`user_name`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `customer` (`user_name`)
);

CREATE TABLE `lineitem` (
  `lineitem_ID` int NOT NULL AUTO_INCREMENT,
  `order_ID` int DEFAULT NULL,
  `options_ID` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`lineitem_ID`),
  KEY `order_ID` (`order_ID`),
  KEY `options_ID` (`options_ID`),
  CONSTRAINT `lineitem_ibfk_1` FOREIGN KEY (`order_ID`) REFERENCES `orders` (`order_ID`),
  CONSTRAINT `lineitem_ibfk_2` FOREIGN KEY (`options_ID`) REFERENCES `options` (`options_ID`)
); 

CREATE TABLE `review` (
  `review_ID` int NOT NULL AUTO_INCREMENT,
  `product_ID` int NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `rate` int NOT NULL,
  `content` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_ID`),
  KEY `product_ID` (`product_ID`),
  KEY `user_name` (`user_name`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`product_ID`) REFERENCES `product` (`product_ID`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`user_name`) REFERENCES `customer` (`user_name`)
); 
-- USE app_dienthoai;
INSERT INTO `brand`(name, logo, created_at,update_at, image_data) VALUES 
('IPHONE','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/26/637340490193124614_iPhone-Apple@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('SAMSUNG','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/26/637340490904217021_Samsung@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('OPPO','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/26/637340491304997311_Oppo@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('XIAOMI','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2021/6/2/637582325361253577_Xiaomi@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),

('HONOR','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2024/6/29/638236346599640251_Honor.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('REALME','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/26/637340491898745716_Realme@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('TECNO','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2021/10/22/637705137962743415_Tecno@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('VIVO','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2022/8/22/637967768466618842_vivo-icon.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('NOKIA','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/26/637340493755614653_Nokia@2x.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01',NULL),
('MASSTEL','https://images.fpt.shop/unsafe/fit-in/108x40/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/26/637340491898901930_Masstel@2x.jpg','2024-04-08 17:27:01','2024-04-13 14:15:26',NULL);

INSERT INTO `color`(color_ID, name, created_at, update_at) VALUES 
(1,'BLACK','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(2,'WHILE','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(3,'GOLD','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(4,'GREY','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(5,'PURPLE','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(6,'BLUE','2024-04-08 17:27:01','2024-04-08 17:27:01');

INSERT INTO `user`(user_name, password, role, email, created_at, update_at) VALUES 
('admin','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_ADMIN','admin@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33'),
('manager','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_MANAGER','manager@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33'),
('shipper','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_SHIPPER','shipper@gmail.com','2024-04-15 16:33:23','2024-05-07 07:36:52'),
('user','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_USER','user@gmail.com','2024-04-15 16:33:23','2024-05-07 07:36:52'),

('lehuy099','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_USER','lehuy099@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33'),
('hoangphuc','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_USER','hoangphuc@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33'),
('thanhtin','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_USER','letin210303@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33'),
('phattran','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_USER','phattran1@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33'),
('quangvu','$2a$12$qm2JDMi93g4Y2CBId8sT2evV/g9MFzHebyWroSsxLDUAkffLK6eO6','ROLE_USER','quangvu@gmail.com','2024-04-08 17:36:33','2024-04-08 17:36:33');

INSERT INTO `customer`(user_name, fullname, address, phonenumber, avatar) VALUES 
('user','USER','số 1 Võ Văn Ngân, Linh Chiểu, Thủ Đức, TP.Hồ Chí Minh','123456789','paee44ca3-7a18-4568-a66e-915a1360cc72.jpg'),
('lehuy099','Lê Tấn Huy','Hưng Trị, Cát Thắng, Phù Cát, Bình Định','0123456789','paee44ca3-7a18-4568-a66e-915a1360cc72.jpg'),
('hoangphuc','Lê Hoàng Phúc','6 ben chuong duong','0123456789','paee44ca3-7a18-4568-a66e-915a1360cc72.jpg'),
('thanhtin','Lê Chánh Thành Tín','94 an duong vuong, Huế','0973910209','paee44ca3-7a18-4568-a66e-915a1360cc72.jpg'),
('phattran','Trần Tấn Phát','15 nguyen hoang','0212910209','paee44ca3-7a18-4568-a66e-915a1360cc72.jpg'),
('quangvu','Trần Quang Vũ','15 nguyen hue','0829391929','paee44ca3-7a18-4568-a66e-915a1360cc72.jpg');

INSERT INTO `product`(product_ID, product_name, os, cpu, origin, brand_ID, description, battery, screen, price, created_at, update_at) VALUES 
(1,'Samsung Galaxy S23 Ultra 5G','Android 13','Snapdragon 8 Gen 2','KOREAN',2,'Tự hào là điện thoại Galaxy đầu tiên sở hữu cảm biến tuyệt đỉnh 200MP, Samsung Galaxy S23 Ultra đưa người dùng vào không gian nhiếp ảnh tân tiến vượt trội','5000 mAh','6.80 inch',2100,'2024-04-08 17:27:01','2024-04-08 17:27:01'),
(2,'Xiaomi Redmi 10A','Android 11','Helio G25','CHINA',4,'Redmi 10A là sản phẩm giá rẻ tiếp theo Xiaomi đem đến cho người dùng','5000 mAh','6.53 inch',120,'2024-04-08 17:27:01','2024-04-08 17:27:01'),
(3,'iPhone 11','iOS 14','	Apple A13 Bionic','USA',1,'iPhone 11 với 6 phiên bản màu sắc, camera có khả năng chụp ảnh vượt trội, thời lượng pin cực dài và bộ vi xử lý mạnh','3110 mAh','6.10 inch',499,'2024-04-08 17:27:01','2024-04-08 17:27:01'),
(4,'OPPO A77s','Android 12','Snapdragon 680','CHINA',3,'Cuộc sống trở nên thú vị hơn khi có người bạn đồng hành trẻ trung, năng động – OPPO A77s. Chiếc điện thoại tầm trung với thiết kế hiện đại, tươi trẻ','5000 mAh','6.56 inch',315,'2024-04-08 17:27:01','2024-04-08 17:27:01'),
(5,'Samsung Galaxy S22','Android 12','Snapdragon 8 Gen 1','KOREAN',2,'Samsung Galaxy S22 là bước nhảy vọt trong công nghệ video trên thế hệ di động. Đồng thời, điện thoại cũng mở ra loạt cải tiến đột phá','3700 mAh','6.10 inch',520,'2024-04-08 17:27:01','2024-04-08 17:27:01'),
(6,'iPhone 12','iOS 14','Apple A14 Bionic','USA',1,'iPhone 12 ra mắt với vai trò mở ra một kỷ nguyên hoàn toàn mới. Tốc độ mạng 5G siêu tốc, bộ vi xử lý A14 Bionic','2815 mAh','6.10 inch',619,'2024-04-08 17:27:01','2024-04-08 17:27:01'),
(7,'iPhone 13', 'iOS', 'A15 Bionic', 'USA', 1, 'The iPhone 13 features a sleek design, advanced camera system, and powerful A15 Bionic chip.', '3095 mAh', '6.1 inches', 799.99, '2024-05-01 12:00:00', '2024-05-01 12:00:00'),
(8,'Galaxy S21', 'Android', 'Exynos 2100', 'South Korea', 2, 'The Galaxy S21 comes with a Dynamic AMOLED 2X display, versatile camera setup, and Exynos 2100 processor.', '4000 mAh', '6.2 inches', 699.99, '2024-05-01 12:00:00', '2024-05-01 12:00:00'),
(9,'OPPO Find X3', 'Android 12', 'Snapdragon 870', 'China', 3, 'OPPO Find X3 offers an impressive display, quad-camera setup, and powerful Snapdragon 870 chipset.', '4500 mAh', '6.7 inches', 649.99, '2024-05-01 12:00:00', '2024-05-01 12:00:00'),
(10,'Xiaomi Mi 11', 'Android 20', 'Snapdragon 888', 'China', 4, 'Xiaomi Mi 11 boasts a high-resolution display, excellent camera performance, and Snapdragon 888 chipset.', '4600 mAh', '6.81 inches', 749.99, '2024-05-01 12:00:00', '2024-05-01 12:00:00'),
(11,'VSmart Aris', 'Android 27', 'Snapdragon 730', 'Vietnam', 5, 'VSmart Aris features a solid build, decent performance, and Snapdragon 730 processor.', '5000 mAh', '6.39 inches', 349.99, '2024-05-01 12:00:00', '2024-05-01 12:00:00'),
(12,'iPhone 12', 'iOS', 'A14 Bionic', 'USA', 1, 'The iPhone 12 features a new design, 5G support, and an A14 Bionic chip.', '2815 mAh', '6.1 inches', 799.00, '2024-05-18 12:00:00', '2024-05-18 12:00:00'),
(13,'Samsung Galaxy S21', 'Android 16', 'Exynos 2100', 'South Korea', 2, 'The Samsung Galaxy S21 comes with a dynamic AMOLED display and the Exynos 2100 processor.', '4000 mAh', '6.2 inches', 799.99, '2024-05-18 12:00:00', '2024-05-18 12:00:00'),
(14,'Oppo Find X3 Pro', 'Android 15', 'Snapdragon 888', 'China', 3, 'Oppo Find X3 Pro features an innovative camera system and a sleek design.', '4500 mAh', '6.7 inches', 1149.00, '2024-05-18 12:00:00', '2024-05-18 12:00:00'),
(15,'Xiaomi Mi 11', 'Android 21', 'Snapdragon 888', 'China', 4, 'Xiaomi Mi 11 offers a high resolution AMOLED display and powerful performance.', '4600 mAh', '6.81 inches', 749.99, '2024-05-18 12:00:00', '2024-05-18 12:00:00'),
(16, 'Honor Magic 4', 'Android99', 'Snapdragon 888', 'China', 5, 'High-performance smartphone with advanced features', '4500mAh', '6.76 inches', 799.99, '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(17, 'Realme GT Neo', 'Android93', 'Dimensity 1200', 'China', 6, 'Affordable smartphone with high-end specs', '5000mAh', '6.43 inches', 599.99, '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(18, 'Tecno Phantom X2', 'Android98', 'Helio G95', 'China', 7, 'Feature-rich phone with excellent camera', '4700mAh', '6.7 inches', 499.99, '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(19, 'Vivo X70 Pro', 'Android', 'Exynos 1080', 'China', 7, 'Premium smartphone with sleek design', '4400mAh', '6.56 inches', 899.99, '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(20, 'Nokia G50', 'Android', 'Snapdragon 480', 'Finland', 9, 'Durable phone with long battery life', '5000mAh', '6.82 inches', 299.99, '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(21, 'Masstel Tab 10', 'Android', 'Unisoc SC9863A', 'Vietnam', 10, 'Affordable tablet with solid performance', '6000mAh', '10.1 inches', 199.99, '2024-04-08 17:27:01', '2024-04-13 14:15:26');
INSERT INTO `options`(options_ID, product_ID, color_ID, ram, rom, price, status) VALUES 
(1,1,1,'4','128',2100,1),
(2,1,3,'8','256',2200.5,1),
(3,1,5,'16','512',2419,1),
(4,2,4,'4','128',120,1),
(5,2,6,'8','256',125.5,1),
(6,2,2,'16','512',149,1),
(7,3,1,'4','64',499,1),
(8,3,2,'8','128',512,1),
(9,4,1,'8','128',315,1),
(10,4,6,'8','256',320,1),
(11,5,5,'5','128',520,1),
(12,5,1,'8','256',529.5,1),
(13,5,2,'12','256',531,1),
(14,6,6,'4','64',619,1),
(15,6,1,'8','128',639,1),
(16,6,5,'10','128',644.5,1),
(17, 7, 2, '4', '128', 650, 1),
(18, 8, 1, '4', '64', 300, 1),
(19, 8, 5, '12', '256', 350, 1),
(20, 9, 2, '6', '128', 540, 1),
(21, 10, 1, '4', '64', 400, 1),
(22, 11, 4, '16', '512', 340, 1),
(23, 12, 6, '8', '128', 720, 1),
(24, 12, 5, '12', '256', 740, 1),
(25, 13, 2, '4', '128', 800, 1),
(26, 13, 4, '16', '512', 840, 1),
(27, 14, 1, '4', '64', 600, 1),
(28, 14, 5, '12', '256', 640, 1),
(29, 15, 2, '4', '128', 900, 1),
(30, 15, 4, '16', '512', 940, 1),
(31, 16, 1, '16', '512', 940, 1),
(32, 17, 1, '16', '512', 940, 1),
(33, 18, 1, '16', '512', 940, 1),
(34, 19, 1, '16', '512', 940, 1),
(35, 20, 1, '16', '512', 940, 1),
(36, 21, 1, '16', '512', 940, 1);

INSERT INTO `image`(image_ID, options_ID, path, created_at, update_at) VALUES 
(1,1,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2024/2/2/638108941585078058_samsung-galaxy-s23-ultra-den-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(2,2,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2024/2/2/638108945003723972_samsung-galaxy-s23-ultra-kem-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(3,3,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2024/2/2/638108937882519991_samsung-galaxy-s23-ultra-tim-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(4,4,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/21/638072287029793818_Xiaomi-Redmi-10A-2GB-32GB-xam-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(5,5,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/21/638072287030090738_Xiaomi-Redmi-10A-2GB-32GB-xanh-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(6,6,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/21/638072287029391755_Xiaomi-Redmi-10A-2GB-32GB-bac-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(7,7,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059306728859551_iphone-11-den-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(8,8,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059309890101717_iphone-11-trang-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(9,9,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/10/5/638005897366049263_oppo-a77s-den-5.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(10,10,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/10/5/638005897366049263_oppo-a77s-xanh-5.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(11,11,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/7/23/637941779749495327_samsung-galaxy-s22-bora-purple-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(12,12,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/2/10/637800498484893706_samsung-galaxy-s22-den-12.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(13,13,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/2/10/637800500905379478_samsung-galaxy-s22-trang-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(14,14,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059236214534473_iphone-12-xanh-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(15,15,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059236214534473_iphone-12-xanh-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(16,16,'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059232363845825_iphone-12-tim-1.jpg','2024-04-08 17:27:01','2024-04-08 17:27:01'),
(17,17, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059242151239306_iphone-12-mini-do-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(18,18, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059232080173331_iphone-12-mini-trang-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(19,19, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059233772975683_iphone-12-mini-vang-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(20,20, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059242509670979_iphone-13-xanh-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(21,21, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059250903430359_iphone-13-den-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(22,22, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059239680436057_iphone-13-mini-vang-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(23,23, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059236718697681_iphone-13-pro-xanh-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(24,24, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059248900964697_iphone-13-pro-den-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(25,25, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059253690091713_iphone-13-pro-max-vang-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(26,26, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059254591246057_iphone-14-do-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(27,27, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059255758777306_iphone-14-trang-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(28,28, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059257694152106_iphone-14-vang-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(29,29, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059258908656995_iphone-14-pro-xanh-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(30,30, 'https://images.fpt.shop/unsafe/fit-in/800x800/filters:quality(90):fill(white):upscale()/fptshop.com.vn/Uploads/Originals/2022/12/6/638059260018209400_iphone-14-pro-den-1.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(31,31, 'https://cdn.chotot.com/IrI8F3000grnyxzqsOduFmDiG0UE9LJs2KYBdgzLOfM/preset:view/plain/229fb4e7f58e41fadcc9981d4ce45e94-2877683546500562377.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(32,32, 'https://viostore.vn/wp-content/uploads/2023/08/37-510x510.png', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(33,33, 'https://viostore.vn/wp-content/uploads/2023/08/37-510x510.png', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(34,34, 'https://cdn.tgdd.vn/Products/Images/42/249270/nokia-g50-pro-600x600.jpg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(35,35, 'https://cdn.viettelstore.vn/Images/Product/ProductImage/1400536094.jpeg', '2024-04-08 17:27:01', '2024-04-08 17:27:01'),
(36,36, 'https://cdn.viettelstore.vn/Images/Product/ProductImage/1400536094.jpeg', '2024-04-08 17:27:01', '2024-04-08 17:27:01');
INSERT INTO `orders`(order_ID, user_name, total_price, order_date, created_at, update_at) VALUES 
(25,'thanhtin',1347,'2024-04-30 14:03:32','2024-04-30 14:03:32','2024-04-30 14:03:32'),
(26,'phattran',3067,'2024-04-30 14:22:25','2024-04-30 14:22:26','2024-04-30 14:22:26'),
(27,'quangvu',1690,'2024-04-30 14:27:26','2024-04-30 14:27:27','2024-04-30 14:27:27'),
(28,'quangvu',6719.5,'2024-05-01 01:17:14','2024-05-01 01:17:15','2024-05-01 01:17:15'),
(29,'thanhtin',394.5,'2024-05-01 01:40:15','2024-05-01 01:40:15','2024-05-01 01:40:15'),
(30,'thanhtin',1147,'2024-05-01 01:43:13','2024-05-01 01:43:13','2024-05-01 01:43:13'),
(31,'thanhtin',1690,'2024-05-01 01:46:07','2024-05-01 01:46:08','2024-05-01 01:46:08');

INSERT INTO `lineitem`(lineitem_ID, order_ID, options_ID, quantity, created_at, update_at) VALUES 
(29,25,3,1,'2024-04-30 14:03:32','2024-04-30 14:03:32'),
(30,25,6,1,'2024-04-30 14:03:32','2024-04-30 14:03:32'),
(31,25,7,1,'2024-04-30 14:03:32','2024-04-30 14:03:32'),
(32,26,3,1,'2024-04-30 14:22:26','2024-04-30 14:22:26'),
(33,26,6,1,'2024-04-30 14:22:26','2024-04-30 14:22:26'),
(34,26,7,1,'2024-04-30 14:22:26','2024-04-30 14:22:26'),
(35,27,11,1,'2024-04-30 14:27:27','2024-04-30 14:27:27'),
(36,27,13,1,'2024-04-30 14:27:27','2024-04-30 14:27:27'),
(37,27,15,1,'2024-04-30 14:27:27','2024-04-30 14:27:27'),
(38,28,1,1,'2024-05-01 01:17:15','2024-05-01 01:17:15'),
(39,28,2,1,'2024-05-01 01:17:15','2024-05-01 01:17:15'),
(40,28,3,1,'2024-05-01 01:17:15','2024-05-01 01:17:15'),
(41,29,4,1,'2024-05-01 01:40:15','2024-05-01 01:40:15'),
(42,29,5,1,'2024-05-01 01:40:15','2024-05-01 01:40:15'),
(43,29,6,1,'2024-05-01 01:40:15','2024-05-01 01:40:15'),
(44,30,8,1,'2024-05-01 01:43:13','2024-05-01 01:43:13'),
(45,30,9,1,'2024-05-01 01:43:13','2024-05-01 01:43:13'),
(46,30,10,1,'2024-05-01 01:43:13','2024-05-01 01:43:13'),
(47,31,11,1,'2024-05-01 01:46:08','2024-05-01 01:46:08'),
(48,31,13,1,'2024-05-01 01:46:08','2024-05-01 01:46:08'),
(49,31,15,1,'2024-05-01 01:46:08','2024-05-01 01:46:08');

INSERT INTO `review`(review_ID, product_ID, user_name, rate, content, created_at, update_at) VALUES 
(1,1,'hoangphuc',4,'updatereview','2024-04-14 15:00:07','2024-04-14 15:47:07'),
(2,2,'quangvu',5,'sp tot','2024-04-14 15:00:28','2024-04-14 15:00:28'),
(3,4,'hoangphuc',5,'Test review','2024-04-14 15:01:39','2024-04-14 15:01:39'),
(4,6,'quangvu',3,'bad review','2024-04-14 15:02:00','2024-04-14 15:02:00'),
(6,5,'lehuy099',5,'good luck','2024-04-14 15:43:11','2024-04-14 15:43:11'),
(7,3,'phattran',5,'good luck','2024-04-14 15:43:18','2024-04-14 15:43:18'),
(8,1,'lehuy099',5,'test','2024-04-14 15:43:27','2024-04-14 15:43:27'),
(9,1,'thanhtin',5,'test test test','2024-04-14 15:44:14','2024-04-14 15:44:14');


-- SELECT SUM(total_price) 
-- FROM orders 
-- WHERE YEAR(update_at) = YEAR(CURDATE()) 
-- AND MONTH(update_at) = MONTH(CURDATE());

-- SELECT COUNT(*) 
-- FROM user
-- WHERE YEAR(created_at) = YEAR(CURDATE()) 
-- AND MONTH(created_at) = MONTH(CURDATE())