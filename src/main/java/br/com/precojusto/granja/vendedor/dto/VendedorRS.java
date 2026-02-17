package br.com.precojusto.granja.vendedor.dto;

import br.com.precojusto.granja.vendedor.entity.Vendedor;

import java.io.Serializable;
import java.util.UUID;

public record VendedorRS(Integer id,
                         String nmVendedor,
                         String dsCpf,
                         String dsmatricula) implements Serializable {


  public static VendedorRS vendedorToVendedorRS(Vendedor vendedor) {
    return new VendedorRS(
            vendedor.getId(),
            vendedor.getNmVendedor(),
            vendedor.getDsCpf(),
            vendedor.getDsMatricula()
    );
  }
}