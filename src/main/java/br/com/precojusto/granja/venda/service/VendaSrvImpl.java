package br.com.precojusto.granja.venda.service;

import br.com.precojusto.granja.cliente.entity.Cliente;
import br.com.precojusto.granja.cliente.repository.ClienteRepository;
import br.com.precojusto.granja.config.exception.GranjaException;
import br.com.precojusto.granja.config.exception.utils.ExcelUtil;
import br.com.precojusto.granja.pato.entity.Pato;
import br.com.precojusto.granja.pato.repository.PatoRepository;
import br.com.precojusto.granja.venda.dto.RankingVendedorRS;
import br.com.precojusto.granja.venda.dto.VendaRQ;
import br.com.precojusto.granja.venda.dto.VendaRS;
import br.com.precojusto.granja.venda.entity.Venda;
import br.com.precojusto.granja.venda.repository.VendaRepository;
import br.com.precojusto.granja.vendedor.entity.Vendedor;
import br.com.precojusto.granja.vendedor.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendaSrvImpl implements VendaSrv {

    private static final BigDecimal DESCONTO_20 = new BigDecimal("0.20");

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final PatoRepository patoRepository;

    @Override
    public VendaRS inserir(VendaRQ vendaRQ) {

        Cliente cliente = clienteRepository.findById(vendaRQ.idCliente())
                .orElseThrow(() -> new GranjaException("Cliente não encontrado: " + vendaRQ.idCliente()));

        Vendedor vendedor = vendedorRepository.findById(vendaRQ.idVendedor())
                .orElseThrow(() -> new GranjaException("Vendedor não encontrado: " + vendaRQ.idVendedor()));

        List<Pato> patos = patoRepository.findAllById(vendaRQ.idPatoList());
        if (patos.size() != vendaRQ.idPatoList().size()) {
            throw new GranjaException("Um ou mais patos informados não foram encontrados.");
        }

        boolean stPatoInvalido = patos.stream().anyMatch(p -> !p.isStDisponivel());
        if (stPatoInvalido) {
            throw new GranjaException("Não é possível vender: existe pato indisponível na lista.");
        }

        BigDecimal totalBruto = patos.stream().map(this::calcularPrecoPato).reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean stAplicaDesconto = cliente.isStValidoDesconto();
        BigDecimal desconto = stAplicaDesconto ? totalBruto.multiply(DESCONTO_20) : BigDecimal.ZERO;

        BigDecimal totalLiquido = totalBruto.subtract(desconto);

        Venda venda = new Venda(null, cliente, vendedor, new ArrayList<>(), new Date(), totalBruto, desconto, totalLiquido, stAplicaDesconto);

        patos.forEach(pato -> {
            pato.setStDisponivel(false);
            pato.setVendaId(venda);
        });

        venda.getPatoList().addAll(patos);

        Venda salva = vendaRepository.save(venda);
        return VendaRS.vendaToVendaRS(salva);
    }

    private BigDecimal calcularPrecoPato(Pato pato) {
        int filhos = (pato.getPatoFilhoList() == null) ? 0 : pato.getPatoFilhoList().size();

        if (filhos == 0) {
            return new BigDecimal("70.00");
        } else if (filhos == 1) {
            return new BigDecimal("50.00");
        }
        return new BigDecimal("25.00");
    }


    @Override
    @Transactional(readOnly = true)
    public byte[] gerarRelatorioVendaExcel(LocalDate dtInicial, LocalDate dtFinal) {
        if (dtInicial == null || dtFinal == null) {
            throw new GranjaException("dtInicial e dtFinal são obrigatórios.");
        }
        if (dtFinal.isBefore(dtInicial)) {
            throw new GranjaException("dtFinal não pode ser menor que dtInicial.");
        }

        List<Pato> patos = patoRepository.listarParaRelatorioVenda();

        Date ini = Date.from(dtInicial.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(dtFinal.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Pato> filtrados = patos.stream()
                .filter(p -> {
                    if (p.getVendaId() == null || p.getVendaId().getDtVenda() == null) return true;
                    Date dt = p.getVendaId().getDtVenda();
                    return !dt.before(ini) && dt.before(fim);
                })
                .toList();

        try (SXSSFWorkbook wb = new SXSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ExcelUtil excel = new ExcelUtil(wb);
            Sheet sheet = wb.createSheet("RELATORIO");

            int rowIdx = 0;

            SXSSFRow r0 = (SXSSFRow) sheet.createRow(rowIdx++);
            excel.addCellTituloStyle(0, r0, "RELATÓRIO DE VENDA");

            String geradoEm = "Gerado em: " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(java.time.LocalDateTime.now());
            excel.addCellTituloStyle(6, r0, geradoEm);

            SXSSFRow r1 = (SXSSFRow) sheet.createRow(rowIdx++);
            excel.addCellTituloFontBold(8, r1, "Data inicial");
            excel.addCellTituloFontBold(9, r1, dtInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            excel.addCellTituloFontBold(10, r1, "Data final");
            excel.addCellTituloFontBold(11, r1, dtFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            SXSSFRow header = (SXSSFRow) sheet.createRow(rowIdx++);
            excel.addCellTituloStyle(0, header, "Nome");
            excel.addCellTituloStyle(1, header, "Status");
            excel.addCellTituloStyle(2, header, "Cliente");
            excel.addCellTituloStyle(3, header, "Tipo do Cliente");
            excel.addCellTituloStyle(4, header, "Valor");
            excel.addCellTituloStyle(5, header, "Data/hora");
            excel.addCellTituloStyle(6, header, "Vendedor");

            for (Pato p : filtrados) {
                SXSSFRow row = (SXSSFRow) sheet.createRow(rowIdx++);

                String status = (p.getVendaId() == null) ? "Disponível" : "Vendido";
                String cliente = (p.getVendaId() == null) ? "-" : p.getVendaId().getCliente().getNmCliente();
                String tipoCliente = (p.getVendaId() == null) ? "-" : (p.getVendaId().getCliente().isStValidoDesconto() ? "Com Desconto" : "Sem Desconto");

                BigDecimal valorPato = calcularPrecoPato(p);

                String dataHora = (p.getVendaId() == null || p.getVendaId().getDtVenda() == null)
                        ? "-"
                        : new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(p.getVendaId().getDtVenda());

                String vendedor = (p.getVendaId() == null) ? "-" : p.getVendaId().getVendedor().getNmVendedor();

                excel.addCellStyle(0, row, p.getNmPato());
                excel.addCellStyle(1, row, status);
                excel.addCellStyle(2, row, cliente);
                excel.addCellStyle(3, row, tipoCliente);
                excel.addCellStyle(4, row, "R$ " + valorPato.toPlainString());
                excel.addCellStyle(5, row, dataHora);
                excel.addCellStyle(6, row, vendedor);
            }

            for (int c = 0; c <= 6; c++) {
                sheet.setColumnWidth(c, 20 * 256);
            }
            sheet.setColumnWidth(0, 32 * 256);

            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new GranjaException("Erro ao gerar relatório Excel: " + e.getMessage());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<RankingVendedorRS> rankingVendedores(LocalDate dtInicial,
                                                     LocalDate dtFinal,
                                                     String statusVendaPato,
                                                     String ordenacao) {
        if (dtInicial == null || dtFinal == null) {
            throw new GranjaException("dtInicial e dtFinal são obrigatórios.");
        }
        if (dtFinal.isBefore(dtInicial)) {
            throw new GranjaException("dtFinal não pode ser menor que dtInicial.");
        }

        Boolean stDisponivel = parseStatusVendaPato(statusVendaPato);

        Date ini = Date.from(dtInicial.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(dtFinal.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()); // exclusivo

        List<Object[]> rows = vendaRepository.rankingVendedores(ini, fim, stDisponivel);

        List<RankingVendedorRS> ranking = rows.stream()
                .map(r -> new RankingVendedorRS(
                        (Integer) r[0],
                        (String) r[1],
                        (Long) r[2],
                        (BigDecimal) r[3]
                ))
                .toList();

        Comparator<RankingVendedorRS> cmp = comparatorOrdenacao(ordenacao);
        return ranking.stream()
                .sorted(cmp)
                .toList();
    }

    private Comparator<RankingVendedorRS> comparatorOrdenacao(String ordenacao) {
        String ord = (ordenacao == null) ? "VALOR" : ordenacao.trim().toUpperCase();

        if ("QTDE".equals(ord) || "QTD".equals(ord) || "VENDAS".equals(ord)) {
            return Comparator.comparingLong(RankingVendedorRS::qtVendas).reversed()
                    .thenComparing(RankingVendedorRS::vlTotalVendido, Comparator.reverseOrder());
        }

        //ordenar por maior valor total vendido
        return Comparator.comparing(RankingVendedorRS::vlTotalVendido, Comparator.reverseOrder())
                .thenComparingLong(RankingVendedorRS::qtVendas).reversed();
    }

    private Boolean parseStatusVendaPato(String statusVendaPato) {
        if (statusVendaPato == null || statusVendaPato.isBlank()) {
            return null; // sem filtro
        }
        String s = statusVendaPato.trim().toUpperCase();
        return switch (s) {
            case "VENDIDO" -> Boolean.FALSE;
            case "DISPONIVEL", "DISPONÍVEL" -> Boolean.TRUE;
            default -> throw new GranjaException("statusVendaPato inválido. Use VENDIDO ou DISPONIVEL.");
        };
    }

}