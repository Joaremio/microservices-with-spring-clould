CREATE TABLE exchange (
                          id BIGSERIAL PRIMARY KEY,
                          from_currency CHAR(3) NOT NULL,
                          to_currency CHAR(3) NOT NULL,
                          conversion_factor NUMERIC(65,2) NOT NULL
);