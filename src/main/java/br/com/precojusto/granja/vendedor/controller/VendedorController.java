package br.com.precojusto.granja.vendedor.controller;

import br.com.precojusto.granja.vendedor.dto.VendedorRQ;
import br.com.precojusto.granja.vendedor.dto.VendedorRS;
import br.com.precojusto.granja.vendedor.service.VendedorSrv;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorSrv vendedorSrv;

    @GetMapping
    public ResponseEntity<List<VendedorRS>> pesquisar(@RequestParam(value = "id", required = false) Integer id,
                                                      @RequestParam(value = "nmVendedor", required = false) String nmVendedor,
                                                      @RequestParam(value = "dsCpf", required = false) String dsCpf,
                                                      @RequestParam(value = "dsMatricula", required = false) String dsMatricula) {
        return ResponseEntity.ok(vendedorSrv.pesquisar(id, nmVendedor, dsCpf, dsMatricula));
    }

    @PostMapping
    public ResponseEntity<VendedorRS> inserir(@Valid @RequestBody VendedorRQ vendedorRQ) {
        VendedorRS vendedorRS = vendedorSrv.inserir(vendedorRQ);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedorRS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        vendedorSrv.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
