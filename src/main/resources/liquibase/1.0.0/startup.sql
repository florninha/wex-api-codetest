CREATE TABLE purchase_transaction (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    transaction_date DATE NOT NULL,
    amount_USD VARCHAR(25) NOT NULL
);