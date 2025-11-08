package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.domain.entity.Venda;
import com.br.pdvpostocombustivel.domain.repository.VendaRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Controlador REST para vendas e geração de comprovantes em PDF
 */
@RestController
@RequestMapping("/api/v1/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaRepository vendaRepository;

    /** Criar nova venda */
    @PostMapping
    @Transactional
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        Venda criada = vendaService.criarVenda(venda);
        return ResponseEntity.ok(criada);
    }

    /** Listar todas as vendas */
    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }

    /** Buscar venda por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarOuThrow(id)); // ✅ corrigido
    }

    /** Gerar comprovante PDF da venda */
    @GetMapping(value = "/{id}/comprovante", produces = MediaType.APPLICATION_PDF_VALUE)
    public void gerarComprovante(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Venda venda = vendaService.buscarOuThrow(id);
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

    /** Método utilitário para gerar PDF */
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
        doc.add(new Paragraph("Operador: " +
                (venda.getAcesso() != null && venda.getAcesso().getUsuario() != null
                        ? venda.getAcesso().getUsuario()
                        : "N/A"), normalFont));
        doc.add(new Paragraph("Forma de Pagamento: " + venda.getFormaPagamento(), normalFont));
        if (venda.getPlaca() != null && !venda.getPlaca().isBlank()) {
            doc.add(new Paragraph("Placa: " + venda.getPlaca(), normalFont));
        }
        doc.add(new Paragraph("\n"));

        // Tabela com itens
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 3, 2, 2});
        table.addCell(cabecalho("Bomba"));
        table.addCell(cabecalho("Produto"));
        table.addCell(cabecalho("Litros"));
        table.addCell(cabecalho("Subtotal"));

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        venda.getItens().forEach(item -> {
            table.addCell(celula(String.valueOf(item.getBombaId()), normalFont));
            table.addCell(celula(item.getProduto().getNome(), normalFont));
            table.addCell(celula(item.getQuantidade().setScale(2, RoundingMode.HALF_UP).toString() + " L", normalFont));
            table.addCell(celula(nf.format(item.getSubtotal()), normalFont));
        });

        doc.add(table);
        doc.add(new Paragraph("\nTotal: " + nf.format(venda.getTotal()), boldFont));
        doc.add(new Paragraph("\nObrigado pela preferência!", normalFont));

        doc.close();
    }

    private PdfPCell cabecalho(String texto) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        return cell;
    }

    private PdfPCell celula(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
