CREATE TABLE vehicles (
    id UUID PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    year_of_manufacture INT NOT NULL,
    fuel_type VARCHAR(255) NOT NULL,
    model VARCHAR(255),
    garage_id UUID NOT NULL,
    FOREIGN KEY (garage_id) REFERENCES garages(id)
);
