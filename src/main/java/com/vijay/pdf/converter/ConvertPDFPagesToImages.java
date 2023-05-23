package com.vijay.pdf.converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Read the pdf file as a source file and convert it into image.
 * 
 * @author vijay
 *
 */
public class ConvertPDFPagesToImages {

	private static final String TYPE = "jpeg"; // jpg, jpeg, png, bmp, gif
	private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
	// If you need small size file then use smaller number.
	private static final int RESOLUTION = 72 * 2 * 2;

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
			@SuppressWarnings("unchecked")
			List<PDPage> list = document.getDocumentCatalog().getAllPages();

			String fileName = sourceFile.getName().replace(".pdf", "");
			int pageNumber = 1;
			for (PDPage page : list) {
				BufferedImage image = page.convertToImage(IMAGE_TYPE, RESOLUTION);
				File outputfile = new File(
						destinationDir + fileName + "_" + pageNumber + "_" + IMAGE_TYPE + "." + TYPE);
				ImageIO.write(image, TYPE, outputfile);
				System.out.println("Image has created at " + outputfile.getAbsolutePath());
				pageNumber++;
			}
			document.close();
			System.out.println("Done.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}