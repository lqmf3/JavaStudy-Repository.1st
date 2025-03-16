CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    nickname VARCHAR(100),
    email VARCHAR(255) NOT NULL UNIQUE,
    region VARCHAR(100),
    age INT NOT NULL,
    gender VARCHAR(10),
    remark VARCHAR(255),
    is_deleted BOOLEAN
);

CREATE TABLE IF NOT EXISTS students_courses (
    id INT NOT NULL AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    end_date DATE DEFAULT NULL,
    student_id INT NOT NULL,
    PRIMARY KEY (id)
);
