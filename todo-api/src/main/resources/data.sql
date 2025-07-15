INSERT INTO users (id, name, email, password) VALUES
(1, 'Alice Smith', 'alice@example.com', 'password123'),
(2, 'Bob Johnson', 'bob@example.com', 'securePass456'),
(3, 'Charlie Brown', 'charlie@example.com', 'abcXYZ789');

INSERT INTO tasks (id, user_id, name, description, due_date, due_time, status) VALUES
(1, 1, 'Buy groceries', 'Buy milk, eggs, and bread', '2025-07-13', '10:00:00', 'PENDING'),
(2, 1, 'Finish report', 'Complete the weekly sales report', '2025-07-12', '16:00:00', 'COMPLETED'),
(3, 2, 'Workout', 'Go to the gym for 1 hour', '2025-07-14', '07:00:00', 'PENDING'),
(4, 2, 'Team meeting', 'Attend the project team meeting on Zoom', '2025-07-14', '14:00:00', 'PENDING'),
(5, 3, 'Read a book', 'Finish reading chapter 5 of the book', '2025-07-16', '20:30:00', 'COMPLETED');
