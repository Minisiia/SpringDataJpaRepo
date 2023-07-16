CREATE DATABASE itvdn;
USE itvdn;
CREATE TABLE Employee(
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         position VARCHAR(255) NOT NULL,
                         phone VARCHAR(255) NOT NULL
);

INSERT INTO Employee (id, name, position, phone)
VALUES
    (1, 'John Doe', 'Manager', '123-456-7890'),
    (2, 'Jane Smith', 'Sales Representative', '987-654-3210'),
    (3, 'Michael Johnson', 'Software Engineer', '555-555-5555'),
    (4, 'Emily Davis', 'Marketing Specialist', '111-222-3333'),
    (5, 'Robert Wilson', 'Financial Analyst', '444-444-4444'),
    (6, 'Jennifer Brown', 'Human Resources Manager', '777-777-7777'),
    (7, 'David Thompson', 'Operations Supervisor', '888-888-8888'),
    (8, 'Sarah Taylor', 'Customer Service Representative', '999-999-9999'),
    (9, 'Daniel Miller', 'Research Scientist', '666-666-6666');