package br.com.precojusto.granja.pato.controller;

import br.com.precojusto.granja.pato.dto.PatoRQ;
import br.com.precojusto.granja.pato.dto.PatoRS;
import br.com.precojusto.granja.pato.dto.PatoVendidoRS;
import br.com.precojusto.granja.pato.service.PatoSrv;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patos")
@RequiredArgsConstructor
public class PatoController {

    private final PatoSrv patoSrv;

    @GetMapping
    public ResponseEntity<List<PatoRS>> pesquisar(@RequestParam(value = "id", required = false) Integer id,
                                                  @RequestParam(value = "nmPato", required = false) String nmPato,
                                                  @RequestParam(value = "idPatoMae", required = false) Integer idPatoMae) {
        return ResponseEntity.ok(patoSrv.pesquisar(id, nmPato, idPatoMae));
    }

    @GetMapping("/vendidos")
    public ResponseEntity<List<PatoVendidoRS>> listarPatosVendidos() {
        return ResponseEntity.ok(patoSrv.listarPatoVendido());
    }

    @PostMapping
    public ResponseEntity<PatoRS> inserir(@Valid @RequestBody PatoRQ patoRQ) {
        PatoRS patoRS = patoSrv.inserir(patoRQ);
        return ResponseEntity.status(HttpStatus.CREATED).body(patoRS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatoRS> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(patoSrv.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatoRS> atualizar(@PathVariable Integer id,
                                            @Valid @RequestBody PatoRQ patoRQ) {
        return ResponseEntity.ok(patoSrv.atualizar(id, patoRQ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Integer id) {
        patoSrv.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
