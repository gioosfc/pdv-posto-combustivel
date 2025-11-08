package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.domain.entity.Venda;
import com.br.pdvpostocombustivel.domain.entity.VendaItem;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class VendaPdfService {

    private final VendaService vendaService;

    public VendaPdfService(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    public byte[] gerarPdf(Long vendaId) {
        // ✅ Corrigido: método correto do service
        Venda v = vendaService.buscarOuThrow(vendaId);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, out);
            doc.open();

            Font h1 = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 12);

            Paragraph title = new Paragraph("Comprovante de Venda - Posto de Combustível", h1);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph("Data/Hora: " + v.getDataHora(), normal));

            // ✅ Corrigido: pega o nome do operador a partir de Acesso
            String operador = (v.getAcesso() != null && v.getAcesso().getUsuario() != null)
                    ? v.getAcesso().getUsuario()
                    : "N/A";
            doc.add(new Paragraph("Operador: " + operador, normal));

            if (v.getPlaca() != null && !v.getPlaca().isBlank()) {
                doc.add(new Paragraph("Placa: " + v.getPlaca(), normal));
            }

            doc.add(new Paragraph("Forma de Pagamento: " +
                    (v.getFormaPagamento() != null ? v.getFormaPagamento() : "-"), normal));
            doc.add(new Paragraph(" "));

            // ===== Tabela =====
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell(cab("Bomba"));
            table.addCell(cab("Produto"));
            table.addCell(cab("Preço/L"));
            table.addCell(cab("Litros"));
            table.addCell(cab("Subtotal"));

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            for (VendaItem i : v.getItens()) {
                table.addCell(txt(i.getBombaNome() != null ? i.getBombaNome() : "-"));
                table.addCell(txt(i.getProduto().getNome()));
                table.addCell(txt(nf.format(i.getPrecoUnitario())));
                table.addCell(txt(i.getQuantidade().setScale(3, RoundingMode.HALF_UP).toPlainString() + " L"));
                table.addCell(txt(nf.format(i.getSubtotal())));
            }

            doc.add(table);
            doc.add(new Paragraph(" "));

            Paragraph totalP = new Paragraph("TOTAL: " + nf.format(v.getTotal()), h1);
            totalP.setAlignment(Element.ALIGN_RIGHT);
            doc.add(totalP);

            doc.add(new Paragraph("\nObrigado pela preferência!", normal));

            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Falha ao gerar PDF da venda " + vendaId, e);
        }
    }

    private PdfPCell cab(String s) {
        PdfPCell c = new PdfPCell(new Phrase(s, new Font(Font.HELVETICA, 12, Font.BOLD)));
        c.setGrayFill(0.9f);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        return c;
    }

    private PdfPCell txt(String s) {
        PdfPCell c = new PdfPCell(new Phrase(s));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        return c;
    }
}
