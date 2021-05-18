package com.veterinaria.configuraciones;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloUsuarios;


public class ExportarDatosCliente {
	private static ModeloUsuarios datosCliente;
	private static List<Citas> datosCitasCliente;
	private static Font txtDatosCliente,txtLogo;
	private static PdfPCell filaCliente = new PdfPCell();
	
	public ExportarDatosCliente() {
		super();
	}
	
	public ExportarDatosCliente(ModeloUsuarios datosCliente, List<Citas> datosCitasCliente) {
		this.datosCliente = datosCliente;
		this.datosCitasCliente = datosCitasCliente;
	}

	/*--------------Mostramos los datos del cliente actual------------------------*/
	
	private void headClientes(PdfPTable tablaClientes) {
		filaCliente.setBackgroundColor(Color.decode("#437EB9"));
		filaCliente.setPadding(5);
		
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setColor(Color.WHITE);
		
		filaCliente.setPhrase(new Phrase("Nombre",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		
		filaCliente.setPhrase(new Phrase("Apellidos",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		
		filaCliente.setPhrase(new Phrase("Teléfono",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		
		filaCliente.setPhrase(new Phrase("Username",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
	}
	
	private void bodyClientes(PdfPTable tablaClientes) {
		tablaClientes.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		tablaClientes.addCell(datosCliente.getNombre());
		tablaClientes.addCell(datosCliente.getApellidos());
		tablaClientes.addCell(datosCliente.getTelefono());
		tablaClientes.addCell(datosCliente.getUsername());
	}
	
	
	/*------------------Mostrar las citas de la mascota de ese cliente---------------------------------*/
	private void headCitasMascota(PdfPTable tablaCitasMascota) {
		PdfPCell filaCitas = new PdfPCell();
		filaCitas.setBackgroundColor(Color.decode("#437EB9"));
		filaCitas.setPadding(5);
		
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setColor(Color.WHITE);
		
		filaCitas.setPhrase(new Phrase("Nombre de veterinario"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Apellidos de veterinario"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Teléfono de veterinario"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Username de veterinario"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Fecha de cita"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Motivo"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Informe"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Nombre de mascota"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Tipo de mascota"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Raza de mascota"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Fecha de mascota"));
		tablaCitasMascota.addCell(filaCitas);
		
		filaCitas.setPhrase(new Phrase("Foto de mascota"));
		tablaCitasMascota.addCell(filaCitas);
	}
	
	private void bodyCitasMascota(PdfPTable tablaCitasMascota) throws IOException {
		for(Citas cita: datosCitasCliente) {
			tablaCitasMascota.addCell(cita.getUsuario().getUsername());
			tablaCitasMascota.addCell(""+cita.getFecha());// comprobar esto
			tablaCitasMascota.addCell(cita.getMotivo());
			tablaCitasMascota.addCell(cita.getInforme());
			
			tablaCitasMascota.addCell(cita.getMascota().getNombre());
			tablaCitasMascota.addCell(cita.getMascota().getTipo());
			tablaCitasMascota.addCell(cita.getMascota().getRaza());
			tablaCitasMascota.addCell(""+cita.getMascota().getFechaNacimiento());// comprobar esto
			
			Image fotoMascota = Image.getInstance(cita.getMascota().getFoto());// visualizamos las fotos en el PDF
			fotoMascota.setAlignment(Element.ALIGN_CENTER);
			tablaCitasMascota.addCell(fotoMascota);
		}
	}
	
	
	public void exportarDatosCliente(HttpServletResponse resCliente) throws DocumentException, IOException {
		Document docCliente = new Document();
		docCliente.setPageSize(PageSize.A3);
		
		PdfWriter.getInstance(docCliente, resCliente.getOutputStream());
		
		docCliente.open();
		
		//----------Establecemos la imágen de logo de la clínica a la derecha del informe------------------
		Image logoClinica = Image.getInstance("logo.png");
		logoClinica.setAlignment(Element.ALIGN_RIGHT);
		docCliente.add(logoClinica);
		
		txtLogo = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		txtLogo.setSize(12);
		txtLogo.setColor(Color.BLACK);
		Paragraph logo = new Paragraph("Informe de clínica DAM",txtLogo);
		logo.setAlignment(Paragraph.ALIGN_LEFT);
		
		docCliente.add(logo);
		
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setSize(19);
		txtDatosCliente.setColor(Color.BLACK);
		Paragraph titulo = new Paragraph("Datos de cliente",txtDatosCliente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		
		docCliente.add(titulo);
		
		//-----------Para el cliente actual------------------------
		PdfPTable tablaClientes = new PdfPTable(4);
		tablaClientes.setWidthPercentage(90f);
		tablaClientes.setWidths(new float[] {1.6f, 3.6f, 2.8f, 1.5f});// dimensiones para las 4 columnas
		tablaClientes.setSpacingBefore(10);
		
		this.headClientes(tablaClientes);
		this.bodyClientes(tablaClientes);
		
		docCliente.add(tablaClientes);
		
		//--------------Para las citas de la mascota---------------
		PdfPTable tablaCitasMascota = new PdfPTable(12);
		tablaCitasMascota.setWidthPercentage(100f);
		tablaCitasMascota.setWidths(new float[] {1.3f,2f,1.33f,1.5f,1.15f,1.8f,1.8f,1.65f,1.5f,1.5f,1.13f,1.5f});// dimensiones para las 12 columnas con proporción 1/3 y 2/3
		tablaCitasMascota.setSpacingBefore(63.8f);
		
		this.headCitasMascota(tablaCitasMascota);
		this.bodyCitasMascota(tablaCitasMascota);
		
		docCliente.add(tablaCitasMascota);
		
		docCliente.close();
	}
}
