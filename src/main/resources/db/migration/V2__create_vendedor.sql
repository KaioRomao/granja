CREATE TABLE vendedor (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nm_vendedor VARCHAR(255) NOT NULL,
                          ds_cpf VARCHAR(14) NOT NULL,
                          ds_matricula CHAR(36) NOT NULL,
                          CONSTRAINT uk_vendedor_cpf UNIQUE (ds_cpf),
                          CONSTRAINT uk_vendedor_matricula UNIQUE (ds_matricula)
) ENGINE=InnoDB;