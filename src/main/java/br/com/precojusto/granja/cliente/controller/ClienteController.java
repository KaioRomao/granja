package br.com.precojusto.granja.cliente.controller;

import br.com.precojusto.granja.cliente.dto.ClienteRQ;
import br.com.precojusto.granja.cliente.dto.ClienteRS;
import br.com.precojusto.granja.cliente.service.ClienteSrv;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteSrv clienteSrv;

    @GetMapping
    public ResponseEntity<List<ClienteRS>> pesquisar(@RequestParam(value = "id", required = false) Integer id,
                                                     @RequestParam(value = "nmPato", required = false) String nmPato,
                                                     @RequestParam(value = "stValidoDesconto", required = false) boolean stValidoDesconto) {
        return ResponseEntity.ok(clienteSrv.pesquisar(id, nmPato, stValidoDesconto));
    }

    @PostMapping
    public ResponseEntity<ClienteRS> inserir(@Valid @RequestBody ClienteRQ clienteRQ) {
        ClienteRS clienteRS = clienteSrv.inserir(clienteRQ);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRS);
    }

}
