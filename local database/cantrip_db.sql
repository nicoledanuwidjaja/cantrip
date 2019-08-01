DROP DATABASE IF EXISTS cantrip;
CREATE DATABASE cantrip;

USE cantrip;

CREATE TABLE trip_object
(trip_id INT PRIMARY KEY AUTO_INCREMENT,
start_location VARCHAR(200),
end_location VARCHAR(200),
start_date DATE,
end_date DATE
);

CREATE TABLE itinerary_item
(plan_id INT PRIMARY KEY AUTO_INCREMENT,
trip_id INT NOT NULL,
start_time DATE NOT NULL,
end_time DATE,
CONSTRAINT fk_trip_id
FOREIGN KEY (trip_id) REFERENCES trip_object(trip_id)
);
