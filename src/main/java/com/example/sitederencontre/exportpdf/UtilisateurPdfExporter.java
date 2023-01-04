package com.example.sitederencontre.exportpdf;

import com.example.sitederencontre.entities.Utilisateur;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UtilisateurPdfExporter extends AbstractExporter {

    public void export(List<Utilisateur> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("Liste des utilisateurs", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 1.7f});

        writeTableHeader(table);
        writeTableData(table, listUsers);

        document.add(table);

        document.close();

    }

    private void writeTableData(PdfPTable table, List<Utilisateur> listUsers) {
        for (Utilisateur user : listUsers) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getPrenom());
            table.addCell(user.getLocalisation());
            table.addCell(String.valueOf(user.isPrivilege()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prenom", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Localisation ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Privilege", font));
        table.addCell(cell);
    }

}