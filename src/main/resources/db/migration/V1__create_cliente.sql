CREATE TABLE cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nm_cliente VARCHAR(255) NOT NULL,
                         st_valido_desconto BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB;