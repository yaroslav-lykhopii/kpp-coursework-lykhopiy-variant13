DROP DATABASE IF EXISTS hotel_tickets;
CREATE DATABASE hotel_tickets CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotel_tickets;

CREATE TABLE room (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      number VARCHAR(20) NOT NULL UNIQUE,
                      floor INT NOT NULL,
                      type VARCHAR(60) NOT NULL,
                      status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE'
);

CREATE TABLE specialist (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            full_name VARCHAR(160) NOT NULL,
                            specialization VARCHAR(120) NOT NULL,
                            phone VARCHAR(40)
);

CREATE TABLE ticket (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        room_id BIGINT NOT NULL,
                        specialist_id BIGINT NULL,
                        title VARCHAR(200) NOT NULL,
                        description VARCHAR(1000) NOT NULL,
                        state VARCHAR(30) NOT NULL,
                        f1 DOUBLE NOT NULL,
                        f2 DOUBLE NOT NULL,
                        f3 DOUBLE NOT NULL,
                        impact_index DOUBLE NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (room_id) REFERENCES room(id),
                        FOREIGN KEY (specialist_id) REFERENCES specialist(id)
);

CREATE TABLE work_record (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             ticket_id BIGINT NOT NULL,
                             performed_at VARCHAR(20) NOT NULL,
                             comment VARCHAR(500) NOT NULL,
                             hours_spent DOUBLE NOT NULL,
                             FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);

INSERT INTO room(number, floor, type, status) VALUES
                                                  ('101', 1, 'Standard', 'AVAILABLE'),
                                                  ('102', 1, 'Standard', 'OCCUPIED'),
                                                  ('201', 2, 'Suite',    'AVAILABLE');

INSERT INTO specialist(full_name, specialization, phone) VALUES
                                                             ('Іваненко Петро', 'Сантехнік', '+380501112233'),
                                                             ('Сидоренко Оксана', 'Електрик', '+380502223344');

INSERT INTO ticket(room_id, specialist_id, title, description, state, f1, f2, f3, impact_index) VALUES
                                                                                                    (1, NULL, 'Протікає кран', 'У ванній кімнаті протікає кран', 'NEW', 50, 60, 40, 51.00),
                                                                                                    (2, 1,    'Не працює ТБ',  'Відсутній сигнал',              'ASSIGNED', 30, 20, 10, 21.50);