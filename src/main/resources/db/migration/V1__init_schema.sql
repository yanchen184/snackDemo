-- Initial schema setup

-- Product class table
CREATE TABLE IF NOT EXISTS product_class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_product_class_name (name)
);

-- Product table
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    picture VARCHAR(500) NOT NULL,
    price INT NOT NULL,
    alive BOOLEAN NOT NULL DEFAULT TRUE,
    product_class_id BIGINT NOT NULL,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_class FOREIGN KEY (product_class_id) REFERENCES product_class(id),
    INDEX idx_product_name (name),
    INDEX idx_product_class (product_class_id)
);

-- Member table
CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    name VARCHAR(100),
    vip VARCHAR(20),
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_member_username (username),
    INDEX idx_member_email (email)
);

-- Member log table
CREATE TABLE IF NOT EXISTS member_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_member_log FOREIGN KEY (member_id) REFERENCES member(id),
    INDEX idx_member_log_member (member_id),
    INDEX idx_member_log_action (action)
);

-- Booking table
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_price INT,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_member FOREIGN KEY (member_id) REFERENCES member(id),
    INDEX idx_booking_member (member_id),
    INDEX idx_booking_status (status)
);

-- Booking detail table
CREATE TABLE IF NOT EXISTS booking_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_detail_booking FOREIGN KEY (booking_id) REFERENCES booking(id),
    CONSTRAINT fk_booking_detail_product FOREIGN KEY (product_id) REFERENCES product(id),
    INDEX idx_booking_detail_booking (booking_id),
    INDEX idx_booking_detail_product (product_id)
);

-- Product commit table (comments/reviews)
CREATE TABLE IF NOT EXISTS product_commit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    rating INT,
    action VARCHAR(20),
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_commit_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_product_commit_member FOREIGN KEY (member_id) REFERENCES member(id),
    INDEX idx_product_commit_product (product_id),
    INDEX idx_product_commit_member (member_id)
);

-- Product commit reactions table
CREATE TABLE IF NOT EXISTS product_commit_react (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_commit_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    reaction VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_commit_react_commit FOREIGN KEY (product_commit_id) REFERENCES product_commit(id),
    CONSTRAINT fk_product_commit_react_member FOREIGN KEY (member_id) REFERENCES member(id),
    UNIQUE KEY uk_member_commit_reaction (member_id, product_commit_id),
    INDEX idx_product_commit_react_commit (product_commit_id)
);

-- Color table (for additional product attributes)
CREATE TABLE IF NOT EXISTS color (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    hex_code VARCHAR(7) NOT NULL,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_color_name (name),
    UNIQUE KEY uk_color_hex_code (hex_code)
);
