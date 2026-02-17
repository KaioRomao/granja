package br.com.precojusto.granja.venda.controller;

import br.com.precojusto.granja.venda.dto.RankingVendedorRS;
import br.com.precojusto.granja.venda.dto.VendaRQ;
import br.com.precojusto.granja.venda.dto.VendaRS;
import br.com.precojusto.granja.venda.service.VendaSrv;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaSrv vendaSrv;

    @PostMapping
    public ResponseEntity<VendaRS> inserir(@RequestBody VendaRQ vendaRQ) {
        VendaRS vendaRS = vendaSrv.inserir(vendaRQ);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaRS);
    }

    @GetMapping("/relatorio/excel")
    public ResponseEntity<byte[]> baixarRelatorioExcel(@RequestParam("dtInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtInicial,
                                                       @RequestParam("dtFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtFinal
    ) {
        byte[] bytes = vendaSrv.gerarRelatorioVendaExcel(dtInicial, dtFinal);

        String filename = "relatorio-venda.xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingVendedorRS>> rankingVendedores(@RequestParam("dtInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtInicial,
                                                                     @RequestParam("dtFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtFinal,
                                                                     @RequestParam(value = "statusVendaPato", required = false) String statusVendaPato,
                                                                     @RequestParam(value = "ordenacao", required = false) String ordenacao
    ) {
        return ResponseEntity.ok(vendaSrv.rankingVendedores(dtInicial, dtFinal, statusVendaPato, ordenacao));
    }
}