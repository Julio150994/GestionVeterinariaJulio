package com.veterinaria.configuraciones;

import java.awt.Color;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.veterinaria.modelos.ModeloUsuarios;


public class ExportarDatosCliente {
	private static ModeloUsuarios datosCliente;
	private static Font txtDatosCliente;
	
	
	public ExportarDatosCliente() {
		super();
	}
	
	public ExportarDatosCliente(ModeloUsuarios datosCliente) {
		this.datosCliente = datosCliente;
	}

	
	private void headClientes(PdfPTable tablaClientes) {
		PdfPCell filaCliente = new PdfPCell();
		filaCliente.setBackgroundColor(Color.GRAY);
		filaCliente.setPadding(5);
		
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setColor(Color.BLACK);
		
		filaCliente.setPhrase(new Phrase("Nombre"));
		tablaClientes.addCell(filaCliente);
		
		filaCliente.setPhrase(new Phrase("Apellidos"));
		tablaClientes.addCell(filaCliente);
		
		filaCliente.setPhrase(new Phrase("Teléfono"));
		tablaClientes.addCell(filaCliente);
		
		filaCliente.setPhrase(new Phrase("Username"));
		tablaClientes.addCell(filaCliente);
	}
	
	private void bodyClientes(PdfPTable tablaClientes) {
		tablaClientes.addCell(datosCliente.getNombre());
		tablaClientes.addCell(datosCliente.getApellidos());
		tablaClientes.addCell(datosCliente.getTelefono());
		tablaClientes.addCell(datosCliente.getUsername());
	}
	
	
	public void exportarDatosCliente(HttpServletResponse resCliente) throws DocumentException, IOException {
		Document docCliente = new Document(PageSize.A4);
		PdfWriter.getInstance(docCliente, resCliente.getOutputStream());
		
		docCliente.open();
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setSize(19);
		txtDatosCliente.setColor(Color.BLACK);
		
		Paragraph titulo = new Paragraph("Datos de cliente",txtDatosCliente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		
		docCliente.add(titulo);
		
		PdfPTable tablaClientes = new PdfPTable(4);
		tablaClientes.setWidthPercentage(83f);
		tablaClientes.setWidths(new float[] {1.5f, 3.5f, 2.7f, 1.4f});// dimensiones para las 4 columnas
		tablaClientes.setSpacingBefore(10);
		
		this.headClientes(tablaClientes);
		this.bodyClientes(tablaClientes);
		
		docCliente.add(tablaClientes);
		docCliente.close();
	}
}
