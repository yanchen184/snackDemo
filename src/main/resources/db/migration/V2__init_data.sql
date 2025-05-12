-- Initial data population

-- Insert product classes
INSERT INTO product_class (name, description, created_by) VALUES 
('Snacks', 'Various snack items including chips, nuts, and dried fruits', 'system'),
('Beverages', 'Drinks including soda, juice, and water', 'system'),
('Candy', 'Sweet treats including chocolate, gummies, and hard candy', 'system'),
('Baked Goods', 'Fresh baked items including cookies, pastries, and bread', 'system'),
('Frozen Treats', 'Ice cream, popsicles, and other frozen desserts', 'system');

-- Insert colors
INSERT INTO color (name, hex_code, created_by) VALUES 
('Red', '#FF0000', 'system'),
('Blue', '#0000FF', 'system'),
('Green', '#00FF00', 'system'),
('Yellow', '#FFFF00', 'system'),
('Black', '#000000', 'system'),
('White', '#FFFFFF', 'system'),
('Purple', '#800080', 'system'),
('Orange', '#FFA500', 'system');

-- Insert members
INSERT INTO member (username, password, email, name, vip, created_by) VALUES 
('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin@example.com', 'Admin User', 'PLATINUM', 'system'), -- password: password
('user1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user1@example.com', 'Regular User', 'SILVER', 'system'),
('user2', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user2@example.com', 'Premium User', 'GOLD', 'system'),
('user3', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user3@example.com', 'New User', 'BRONZE', 'system'),
('user4', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'user4@example.com', 'Test User', 'SILVER', 'system');

-- Insert products
INSERT INTO product (name, picture, price, alive, product_class_id, created_by) VALUES 
('Potato Chips', 'https://example.com/images/potato_chips.jpg', 299, TRUE, 1, 'system'),
('Tortilla Chips', 'https://example.com/images/tortilla_chips.jpg', 349, TRUE, 1, 'system'),
('Pretzels', 'https://example.com/images/pretzels.jpg', 249, TRUE, 1, 'system'),
('Mixed Nuts', 'https://example.com/images/mixed_nuts.jpg', 599, TRUE, 1, 'system'),
('Cola', 'https://example.com/images/cola.jpg', 199, TRUE, 2, 'system'),
('Lemon Soda', 'https://example.com/images/lemon_soda.jpg', 199, TRUE, 2, 'system'),
('Orange Juice', 'https://example.com/images/orange_juice.jpg', 299, TRUE, 2, 'system'),
('Bottled Water', 'https://example.com/images/water.jpg', 149, TRUE, 2, 'system'),
('Chocolate Bar', 'https://example.com/images/chocolate.jpg', 249, TRUE, 3, 'system'),
('Gummy Bears', 'https://example.com/images/gummy_bears.jpg', 199, TRUE, 3, 'system'),
('Hard Candy Mix', 'https://example.com/images/hard_candy.jpg', 179, TRUE, 3, 'system'),
('Chocolate Chip Cookies', 'https://example.com/images/cookies.jpg', 349, TRUE, 4, 'system'),
('Croissant', 'https://example.com/images/croissant.jpg', 299, TRUE, 4, 'system'),
('Vanilla Ice Cream', 'https://example.com/images/vanilla_ice_cream.jpg', 499, TRUE, 5, 'system'),
('Chocolate Ice Cream', 'https://example.com/images/chocolate_ice_cream.jpg', 499, TRUE, 5, 'system'),
('Fruit Popsicle', 'https://example.com/images/popsicle.jpg', 249, TRUE, 5, 'system');

-- Insert member logs
INSERT INTO member_log (member_id, action, details) VALUES 
(1, 'LOGIN', 'Admin login from 192.168.1.1'),
(1, 'UPDATE_PROFILE', 'Updated email address'),
(2, 'LOGIN', 'User login from 192.168.1.2'),
(2, 'PURCHASE', 'Completed purchase #1001'),
(3, 'LOGIN', 'User login from 192.168.1.3'),
(3, 'PURCHASE', 'Completed purchase #1002'),
(4, 'REGISTER', 'New user registration'),
(4, 'LOGIN', 'User login from 192.168.1.4');

-- Insert bookings
INSERT INTO booking (member_id, status, total_price, booking_time, created_by) VALUES 
(2, 'COMPLETED', 1096, '2023-01-15 14:30:00', 'system'),
(3, 'COMPLETED', 1247, '2023-01-20 10:15:00', 'system'),
(2, 'PROCESSING', 698, '2023-02-01 16:45:00', 'system'),
(4, 'CANCELLED', 499, '2023-02-10 09:20:00', 'system'),
(5, 'COMPLETED', 847, '2023-02-15 13:10:00', 'system');

-- Insert booking details
INSERT INTO booking_detail (booking_id, product_id, quantity, price, created_by) VALUES 
(1, 1, 2, 299, 'system'),  -- 2 Potato Chips
(1, 5, 1, 199, 'system'),  -- 1 Cola
(1, 9, 1, 249, 'system'),  -- 1 Chocolate Bar
(1, 12, 1, 349, 'system'), -- 1 Chocolate Chip Cookies
(2, 4, 1, 599, 'system'),  -- 1 Mixed Nuts
(2, 7, 1, 299, 'system'),  -- 1 Orange Juice
(2, 10, 1, 199, 'system'), -- 1 Gummy Bears
(2, 13, 1, 299, 'system'), -- 1 Croissant
(3, 2, 1, 349, 'system'),  -- 1 Tortilla Chips
(3, 6, 1, 199, 'system'),  -- 1 Lemon Soda
(3, 11, 1, 179, 'system'), -- 1 Hard Candy Mix
(4, 14, 1, 499, 'system'), -- 1 Vanilla Ice Cream
(5, 3, 1, 249, 'system'),  -- 1 Pretzels
(5, 8, 1, 149, 'system'),  -- 1 Bottled Water
(5, 15, 1, 499, 'system'); -- 1 Chocolate Ice Cream

-- Insert product commits (reviews)
INSERT INTO product_commit (product_id, member_id, content, rating, action, created_by) VALUES 
(1, 2, 'Great chips, very crispy and flavorful!', 5, 'REVIEW', 'system'),
(1, 3, 'Good chips but a bit too salty for my taste.', 4, 'REVIEW', 'system'),
(5, 2, 'Classic cola taste, always refreshing.', 5, 'REVIEW', 'system'),
(9, 4, 'Delicious chocolate, smooth and rich.', 5, 'REVIEW', 'system'),
(12, 3, 'These cookies are amazing! Just like homemade.', 5, 'REVIEW', 'system'),
(14, 5, 'Creamy and delicious vanilla ice cream.', 4, 'REVIEW', 'system'),
(7, 2, 'Fresh orange juice, not too sweet.', 4, 'REVIEW', 'system'),
(4, 3, 'Great mix of nuts, very fresh.', 5, 'REVIEW', 'system');

-- Insert product commit reactions
INSERT INTO product_commit_react (product_commit_id, member_id, reaction) VALUES 
(1, 3, 'LIKE'),
(1, 4, 'LIKE'),
(2, 2, 'LIKE'),
(3, 3, 'LIKE'),
(3, 4, 'LIKE'),
(3, 5, 'LIKE'),
(4, 2, 'LIKE'),
(4, 3, 'LIKE'),
(5, 2, 'LIKE'),
(5, 4, 'LIKE'),
(5, 5, 'LIKE'),
(6, 2, 'LIKE'),
(6, 3, 'LIKE'),
(7, 3, 'LIKE'),
(8, 2, 'LIKE'),
(8, 4, 'LIKE'),
(8, 5, 'LIKE');