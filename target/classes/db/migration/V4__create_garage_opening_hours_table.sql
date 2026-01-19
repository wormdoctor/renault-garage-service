CREATE TABLE garage_opening_hours (
    garage_id UUID NOT NULL,
    day_of_week VARCHAR(255) NOT NULL,
    opening_times_json TEXT,
    FOREIGN KEY (garage_id) REFERENCES garages(id)
);
