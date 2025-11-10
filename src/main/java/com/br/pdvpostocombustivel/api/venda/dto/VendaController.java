package com.br.pdvpostocombustivel.api.venda;

import com.br.pdvpostocombustivel.api.venda.dto.ResumoProdutoDTO;
import com.br.pdvpostocombustivel.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivel.api.venda.dto.VendaService;
import com.br.pdvpostocombustivel.domain.entity.Venda;
import com.br.pdvpostocombustivel.domain.repository.VendaRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private final VendaService vendaService;
    private final VendaRepository vendaRepository;

    public VendaController(VendaService vendaService, VendaRepository vendaRepository) {
        this.vendaService = vendaService;
        this.vendaRepository = vendaRepository;
    }

    /** ✅ Criar nova venda */
    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequest vendaRequest) {
        Venda criada = vendaService.criarVenda(vendaRequest);
        return ResponseEntity.ok(criada);
    }



    /** ✅ Listar todas as vendas */
    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        try {
            return ResponseEntity.ok(vendaService.listarVendas());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /** ✅ Buscar venda por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        try {
            Venda venda = vendaService.buscarPorId(id);
            if (venda == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(venda);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /** ✅ Gerar comprovante PDF */
    @GetMapping(value = "/{id}/comprovante", produces = MediaType.APPLICATION_PDF_VALUE)
    public void gerarComprovante(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Venda venda = vendaService.buscarPorIdComItens(id);
        if (venda == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venda não encontrada");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=comprovante-venda-" + id + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            gerarPdfComprovante(venda, out);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erro ao gerar PDF: " + e.getMessage());
        }
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<Venda>> relatorio(
            @RequestParam String inicio,            // formato: yyyy-MM-dd
            @RequestParam String fim,               // formato: yyyy-MM-dd
            @RequestParam(required = false) String forma,
            @RequestParam(required = false) String placa
    ) {
        try {
            Date di = parseInicio(inicio);
            Date df = parseFim(fim);
            List<Venda> lista = vendaService.relatorio(di, df, forma, placa);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /** 📊 Resumo por produto no período */
    @GetMapping("/resumo-produtos")
    public ResponseEntity<List<ResumoProdutoDTO>> resumoProdutos(
            @RequestParam String inicio,            // formato: yyyy-MM-dd
            @RequestParam String fim,               // formato: yyyy-MM-dd
            @RequestParam(required = false) String forma,
            @RequestParam(required = false) String placa
    ) {
        try {
            Date di = parseInicio(inicio);
            Date df = parseFim(fim);
            List<ResumoProdutoDTO> lista = vendaService.resumoProdutos(di, df, vaziar(forma), vaziar(placa));
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /** 🧰 Utilitários locais do controller */
    private Date parseInicio(String s) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(s);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private Date parseFim(String s) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(s);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    private String vaziar(String s) {
        return (s == null || s.isBlank() || "TODAS".equalsIgnoreCase(s)) ? null : s;
    }

    /** ✅ Método utilitário para gerar PDF */
    private void gerarPdfComprovante(Venda venda, OutputStream out) throws Exception {
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, out);
        doc.open();

        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        Paragraph titulo = new Paragraph("COMPROVANTE DE VENDA", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);
        doc.add(new Paragraph("Data: " + LocalDateTime.now(), normalFont));
        doc.add(new Paragraph("Forma de Pagamento: " + venda.getFormaPagamento(), normalFont));
        if (venda.getPlaca() != null && !venda.getPlaca().isBlank()) {
            doc.add(new Paragraph("Placa: " + venda.getPlaca(), normalFont));
        }
        doc.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 3, 2, 2});
        table.addCell(cabecalho("Bomba"));
        table.addCell(cabecalho("Produto"));
        table.addCell(cabecalho("Litros"));
        table.addCell(cabecalho("Subtotal"));

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        venda.getItens().forEach(item -> {
            table.addCell(celula(
                    item.getBombaId() != null ? String.valueOf(item.getBombaId()) : "-", normalFont));
            table.addCell(celula(
                    item.getProduto() != null ? item.getProduto().getNome() : "-", normalFont));
            table.addCell(celula(
                    item.getQuantidade() != null ? item.getQuantidade().setScale(2, RoundingMode.HALF_UP) + " L" : "-", normalFont));
            table.addCell(celula(
                    item.getSubtotal() != null ? nf.format(item.getSubtotal()) : "-", normalFont));
        });

        doc.add(table);
        doc.add(new Paragraph("\nTotal: " + nf.format(venda.getTotal()), boldFont));
        doc.add(new Paragraph("\nObrigado pela preferência!", normalFont));

        doc.close();
    }

    /** ✅ Cabeçalho do PDF */
    private PdfPCell cabecalho(String texto) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        return cell;
    }

    /** ✅ Célula do PDF */
    private PdfPCell celula(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
