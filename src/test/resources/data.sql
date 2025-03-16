INSERT INTO students (name, nickname, email, region, age, gender, is_deleted)
VALUES
('AoyamaHaruka', 'Haru', 'Haruka@example.com', 'Tokyo', 31, 'Female', false),
('SasakiNatsu', 'Natsu', 'Natu@example.com', 'Shizuoka', 29, 'Male', false),
('NatsukawaRimi', 'Rimi', 'natsukawaR@example.com', 'Aichi', 25, 'Female', false),
('SaitoAki', 'Aki', 'aki_sR@example.com', NULL, 32, 'Other', false),
('YamadaTaro', 'Taro', 'taro@example.com', 'Kumamoto', 0, 'Male', false),
('YanagisawaSakura', 'Sakura', 'sakura@example.com', 'Ehime', 0, 'Female', false);

INSERT INTO students_courses (student_id, course_name, start_date, end_date)
VALUES
(1, 'Introduction to SQL', '2024-01-15 00:00:00', '2024-03-15'),
(2, 'Advanced Java Programming', '2024-02-01 00:00:00', '2024-06-01'),
(5, 'Data Analysis with Python', '2024-03-10 00:00:00', '2024-05-10'),
(7, 'Web Development Basics', '2024-04-01 00:00:00', '2024-09-01'),
(1, 'Machine Learning Basics', '2024-05-20 00:00:00', '2024-08-20'),
(12, 'Introduction to SQL', '2025-01-15 19:28:19', '2026-01-15'),
(12, 'Data Analysis with Python', '2025-01-21 00:00:00', '2025-03-21'),
(13, 'Advanced Java Programming', '2025-01-22 22:09:54', '2026-01-22');
