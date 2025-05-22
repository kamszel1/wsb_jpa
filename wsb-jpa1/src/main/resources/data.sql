INSERT INTO address (id, city, address_line1, address_line2, postal_code)
VALUES (901, 'Warszawa', 'ul. Testowa 12', 'klatka A', '60-400');

INSERT INTO doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization)
VALUES (1, 'Kasia', 'Nowak', '123456789', 'kasia.nowak@test123.pl', 'DOC001', 'DERMATOLOGIST'),
       (2, 'Michal', 'Test', '987654321', 'michal123@test.pl', 'DOC002', 'GP'),
       (3, 'Pawel', 'Ziel', '555123456', 'pawel.z@test123.com', 'DOC003', 'SURGEON');

INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, registration_date)
VALUES (1, 'Piotr', 'Nowak', '789999999', 'tomek123@test.pl', 'PAT002', '1990-07-15', '2025-02-15'),
       (2, 'Jan', 'Kowalski', '123123123', 'ala.test@test.com', 'PAT002', '1970-02-12', '2024-11-15');

INSERT INTO visit (id, description, time, doctor_id, patient_id)
VALUES (1, 'Konsultacja a', '2023-11-01 10:00:00', 1, 1),
       (2, 'Leczenie x', '2023-11-15 14:30:00', 2, 2),
       (3, 'Pobieranie krwi', '2023-11-20 09:00:00', 1, 1);

INSERT INTO medical_treatment (id, visit_id, description, type)
VALUES (1, 1, 'Blood pressure check', 'EKG'),
       (2, 1, 'Physical examination', 'RTG');
