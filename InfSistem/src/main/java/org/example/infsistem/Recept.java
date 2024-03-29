package org.example.infsistem;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Recept {

    private static final String outputDirectory = "C:\\Users\\Strix\\Desktop\\Prog\\Java\\Projekat\\Java\\InfSistem\\src\\main\\resources\\Racuni";

    public static void generatePdfFromText(String text) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
            String outputFilePath = outputDirectory + "recept_" + timestamp + ".pdf";

            data.poslednjiRacun = outputFilePath;

            PdfWriter writer = new PdfWriter(outputFilePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Dodavanje teksta u dokument
            document.add(new Paragraph(text));

            // Zatvaranje dokumenta
            document.close();

            System.out.println("PDF datoteka je uspješno generirana na putanji: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Došlo je do greške prilikom generiranja PDF datoteke: " + e.getMessage());
        }
    }
}
