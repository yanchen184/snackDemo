-- Initial data setup

-- Insert product classes
INSERT INTO product_class (name, description) VALUES 
('Snacks', 'Various snack items including chips, nuts, and crackers'),
('Beverages', 'Drinks including soda, juice, and water'),
('Candy', 'Sweet treats including chocolate, gummies, and hard candy'),
('Baked Goods', 'Fresh baked items including cookies, pastries, and bread');

-- Insert products
INSERT INTO product (name, picture, price, alive, product_class_id) VALUES 
('Potato Chips', '/images/products/chips.jpg', 299, TRUE, 1),
('Chocolate Cookies', '/images/products/cookies.jpg', 399, TRUE, 4),
('Cola', '/images/products/cola.jpg', 199, TRUE, 2),
('Mixed Nuts', '/images/products/nuts.jpg', 499, TRUE, 1),
('Fruit Juice', '/images/products/juice.jpg', 249, TRUE, 2),
('Gummy Bears', '/images/products/gummy.jpg', 349, TRUE, 3),
('Chocolate Bar', '/images/products/chocolate.jpg', 299, TRUE, 3),
('Croissant', '/images/products/croissant.jpg', 449, TRUE, 4);

-- Insert member data (password is 'password' - in a real system, these would be properly hashed)
INSERT INTO member (username, password, email, name, vip, role) VALUES 
('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin@snackoverflow.com', 'Admin User', 'vip4', 'ADMIN'),
('user1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user1@example.com', 'Regular User', 'normal', 'USER'),
('vipuser', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'vip@example.com', 'VIP User', 'vip2', 'USER');

-- Insert colors
INSERT INTO color (name, hex_code) VALUES 
('Red', '#FF0000'),
('Green', '#00FF00'),
('Blue', '#0000FF'),
('Yellow', '#FFFF00'),
('Black', '#000000'),
('White', '#FFFFFF');
