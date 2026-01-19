CREATE TABLE accessories (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    type VARCHAR(255) NOT NULL,
    vehicle_id UUID NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);
