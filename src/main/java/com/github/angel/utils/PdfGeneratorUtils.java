/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.utils;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.github.angel.dto.ReportDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 *
 * @author aguero
 */
@Component
public class PdfGeneratorUtils {

    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
    private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
    private static final Font CONTENT_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
    private static final Font FOOTER_FONT = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY);

    public byte[] createPdfReport(ReportDTO data) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        addHeader(document);
        addReportContent(document, data);
        addFooter(document);

        document.close();
        return out.toByteArray();
    }

    private void addHeader(Document document) throws DocumentException {
        Paragraph title = new Paragraph("POS System Report", TITLE_FONT);
        title.setAlignment(Element.ALIGN_LEFT);
        document.add(title);

        Paragraph subtitle = new Paragraph("Purchase Report", SUBTITLE_FONT);
        subtitle.setAlignment(Element.ALIGN_LEFT);
        document.add(subtitle);

        document.add(Chunk.NEWLINE);
    }

    private void addReportContent(Document document, ReportDTO data) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);

        table.setWidths(new int[]{1, 2});

        addTableHeader(table);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        addTableRow(table, "Purchase Number", data.getReportId().toString());
        addTableRow(table, "Client Name", data.getCustomerName());
        addTableRow(table, "Product Name", data.getProductName());
        addTableRow(table, "Quantity", data.getQuantity().toString());
        addTableRow(table, "Price Per Unit", "$ " + data.getPricePerUnit());
        addTableRow(table, "Purchase Date", data.getPurchaseDate().format(formatter));
        addTableRow(table, "Payment Method", data.getPaymentMethod());

        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell header1 = new PdfPCell(new Phrase("Detail", HEADER_FONT));
        header1.setBackgroundColor(new BaseColor(0, 121, 107));
        header1.setBorderWidth(0);
        header1.setPadding(8);
        header1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(header1);

        PdfPCell header2 = new PdfPCell(new Phrase("Information", HEADER_FONT));
        header2.setBackgroundColor(new BaseColor(0, 121, 107));
        header2.setBorderWidth(0);
        header2.setPadding(8);
        header2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(header2);
    }

    private void addTableRow(PdfPTable table, String key, String value) {
        PdfPCell cell1 = new PdfPCell(new Phrase(key, CONTENT_FONT));
        cell1.setBorderWidth(0);
        cell1.setPadding(8);
        cell1.setBackgroundColor(new BaseColor(224, 242, 241));
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase(value, CONTENT_FONT));
        cell2.setBorderWidth(0);
        cell2.setPadding(8);
        cell2.setBackgroundColor(new BaseColor(240, 255, 255));
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell2);
    }

    private void addFooter(Document document) throws DocumentException {
        document.add(Chunk.NEWLINE);
        LineSeparator ls = new LineSeparator();
        ls.setLineColor(BaseColor.LIGHT_GRAY);
        document.add(ls);

        Paragraph footer = new Paragraph();
        footer.add(new Phrase("Generated by POS System\n", FOOTER_FONT));
        footer.add(new Phrase("Signature: Angel Aguero\n", FOOTER_FONT));
        footer.add(new Phrase("Project Code: ", FOOTER_FONT));
        footer.add(new Phrase("https://github.com/Angel-Raa/POS-Spring-Boot", FOOTER_FONT));
        footer.setAlignment(Element.ALIGN_RIGHT);

        document.add(footer);
    }
}
