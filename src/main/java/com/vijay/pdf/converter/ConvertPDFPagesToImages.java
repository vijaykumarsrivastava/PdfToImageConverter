package com.vijay.pdf.converter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Read the pdf file as a source file and convert it into image.
 * 
 * @author vijay
 *
 */
public class ConvertPDFPagesToImages {

	private static final String FILE_EXTENSION = "jpeg"; // jpg, jpeg, png, bmp, gif
	private static final ImageType IMAGE_TYPE = ImageType.RGB;
	// If you need small size file then use smaller number.
	private static final int DPI = 300;

	public static void main(String[] args) {

		// source pdf file.
		String sourceFile = "D:\\source_location\\pdf_file_name.pdf";
		// destination dir
		String destinationDir = "D:\\destination_location\\";

		process(sourceFile, destinationDir);
	}

	private static void process(String sourceFileLocation, String destinationDir) {
		try {

			File sourceFile = new File(sourceFileLocation);
			File destinationFile = new File(destinationDir);
			if (!destinationFile.exists()) {
				System.out.println("Destination folder is missing.");
				return;
			}
			if (!sourceFile.exists()) {
				System.err.println("Source file not exists");
				return;
			}
			PDDocument document = PDDocument.load(sourceFile);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			int numberOfPages = document.getNumberOfPages();
			System.out.println("Total number of page in source pdf file: " + numberOfPages);
			String fileName = sourceFile.getName().replace(".pdf", "");

			for (int i = 0; i < numberOfPages; i++) {
				File outPutFile = new File(destinationDir + fileName + "_" + (i+1) + "." + FILE_EXTENSION);
				BufferedImage bImage = pdfRenderer.renderImageWithDPI(i, DPI, IMAGE_TYPE);
				ImageIO.write(bImage, FILE_EXTENSION, outPutFile);
			}
			document.close();
			System.out.println("Done.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}