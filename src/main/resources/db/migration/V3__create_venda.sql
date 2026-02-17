CREATE TABLE venda (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       cliente_id INT NOT NULL,
                       vendedor_id INT NOT NULL,
                       dt_venda DATETIME NOT NULL,
                       vl_total_bruto DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                       vl_desconto DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                       vl_total_liquido DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                       st_desconto_aplicado BOOLEAN DEFAULT FALSE,

                       CONSTRAINT fk_venda_cliente
                           FOREIGN KEY (cliente_id) REFERENCES cliente(id)
                               ON DELETE RESTRICT ON UPDATE CASCADE,

                       CONSTRAINT fk_venda_vendedor
                           FOREIGN KEY (vendedor_id) REFERENCES vendedor(id)
                               ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_venda_dt_venda ON venda(dt_venda);
CREATE INDEX idx_venda_vendedor_id ON venda(vendedor_id);