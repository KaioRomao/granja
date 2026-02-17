CREATE TABLE pato (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nm_pato VARCHAR(25) NOT NULL,
                      st_disponivel BOOLEAN DEFAULT TRUE,
                      pato_mae_id INT NULL,
                      venda_id INT NULL,

                      CONSTRAINT uk_pato_nome UNIQUE (nm_pato),

                      CONSTRAINT fk_pato_mae
                          FOREIGN KEY (pato_mae_id) REFERENCES pato(id)
                              ON DELETE SET NULL ON UPDATE CASCADE,

                      CONSTRAINT fk_pato_venda
                          FOREIGN KEY (venda_id) REFERENCES venda(id)
                              ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_pato_mae_id ON pato(pato_mae_id);
CREATE INDEX idx_pato_venda_id ON pato(venda_id);