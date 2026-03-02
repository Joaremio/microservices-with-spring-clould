CREATE TABLE book (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      author TEXT,
                      launch_date TIMESTAMP(6) NOT NULL,
                      price NUMERIC(65,2) NOT NULL,
                      title TEXT
);