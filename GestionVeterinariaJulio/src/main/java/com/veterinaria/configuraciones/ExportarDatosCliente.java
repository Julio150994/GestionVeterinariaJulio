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
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.veterinaria.entidades.Citas;
import com.veterinaria.modelos.ModeloUsuarios;


public class ExportarDatosCliente {
	private static ModeloUsuarios datosCliente;
	private static List<Citas> datosCitasCliente;
	private static Font txtDatosCliente,txtLogo,txtCitasMascotaCliente;
	
	private static PdfPCell filaCliente = new PdfPCell(), filaCitas = new PdfPCell();
	
	public ExportarDatosCliente() {
		super();
	}
	
	public ExportarDatosCliente(ModeloUsuarios datosCliente, List<Citas> datosCitasCliente) {
		this.datosCliente = datosCliente;
		this.datosCitasCliente = datosCitasCliente;
	}

	/*--------------Mostramos los datos del cliente actual------------------------*/
	
	private void mostrarDatosCliente(PdfPTable tablaClientes) {
		filaCliente.setBackgroundColor(Color.decode("#437EB9"));
		filaCliente.setPadding(5);
		PdfPCell celdasCliente = new PdfPCell();
		celdasCliente.setBackgroundColor(Color.WHITE);
		celdasCliente.setPadding(5);
		
		
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setColor(Color.WHITE);
		
		filaCliente.setPhrase(new Phrase("Nombre",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		celdasCliente.setPhrase(new Phrase(datosCliente.getNombre()));
		celdasCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setPadding(10);
		tablaClientes.addCell(celdasCliente);
		tablaClientes.addCell(""); tablaClientes.addCell("");
		
		
		filaCliente.setPhrase(new Phrase("Apellidos",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		celdasCliente.setPhrase(new Phrase(datosCliente.getApellidos()));
		celdasCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setPadding(10);
		tablaClientes.addCell(celdasCliente);
		tablaClientes.addCell(""); tablaClientes.addCell("");
		
		
		filaCliente.setPhrase(new Phrase("Teléfono",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		celdasCliente.setPhrase(new Phrase(datosCliente.getTelefono()));
		celdasCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setPadding(10);
		tablaClientes.addCell(celdasCliente);
		tablaClientes.addCell(""); tablaClientes.addCell("");
		
		
		filaCliente.setPhrase(new Phrase("Username",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		tablaClientes.addCell(filaCliente);
		celdasCliente.setPhrase(new Phrase(datosCliente.getUsername()));
		celdasCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setPadding(10);
		tablaClientes.addCell(celdasCliente);
		tablaClientes.addCell(""); tablaClientes.addCell("");
	}
	
	
	/*------------------Mostrar las citas de la mascota de ese cliente---------------------------------*/
	private void mostrarCitasMascota(PdfPTable tablaCitasMascota) throws IOException {
		filaCitas.setBackgroundColor(Color.decode("#437EB9"));
		filaCitas.setPadding(5);
		
		txtCitasMascotaCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtCitasMascotaCliente.setColor(Color.WHITE);
		
		for(Citas cita: datosCitasCliente) {
			filaCitas.setPhrase(new Phrase("Nombre de veterinario",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getUsuario().getNombre());
			
			filaCitas.setPhrase(new Phrase("Apellidos de veterinario",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getUsuario().getApellidos());
			
			
			filaCitas.setPhrase(new Phrase("Teléfono de veterinario",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getUsuario().getTelefono());
			
			
			filaCitas.setPhrase(new Phrase("Username de veterinario",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getUsuario().getUsername());
			
			
			filaCitas.setPhrase(new Phrase("Fecha de cita",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(""+cita.getFecha());
			
			
			filaCitas.setPhrase(new Phrase("Motivo",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getMotivo());
			
			
			filaCitas.setPhrase(new Phrase("Informe",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getInforme());
			
			
			filaCitas.setPhrase(new Phrase("Nombre de mascota",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getMascota().getNombre());
			
			
			filaCitas.setPhrase(new Phrase("Tipo de mascota",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getMascota().getTipo());
			
			
			filaCitas.setPhrase(new Phrase("Raza de mascota",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(cita.getMascota().getRaza());
			
			
			filaCitas.setPhrase(new Phrase("Fecha de mascota",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			tablaCitasMascota.addCell(""+cita.getMascota().getFechaNacimiento());
			
			
			filaCitas.setPhrase(new Phrase("Foto de mascota",txtCitasMascotaCliente));
			filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
			filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
			filaCitas.setPadding(10);
			tablaCitasMascota.addCell(filaCitas);
			Image fotoMascota = Image.getInstance(cita.getMascota().getFoto());
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
		Paragraph tituloDatosCliente = new Paragraph("Datos de cliente",txtDatosCliente);
		tituloDatosCliente.setAlignment(Paragraph.ALIGN_CENTER);
		
		docCliente.add(tituloDatosCliente);
		
		//-----------Para el cliente actual------------------------
		PdfPTable tablaClientes = new PdfPTable(4);
		tablaClientes.setWidthPercentage(90f);
		tablaClientes.setWidths(new float[] {1.5f, 2.1f, 1f, 2f});// dimensiones para las 4 columnas
		tablaClientes.setSpacingBefore(10.6f);
		
		this.mostrarDatosCliente(tablaClientes);
		
		docCliente.add(tablaClientes);
		
		
		txtCitasMascotaCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtCitasMascotaCliente.setSize(19);
		txtCitasMascotaCliente.setColor(Color.BLACK);
		Paragraph tituloCitasMascota = new Paragraph("Lista de citas realizadas",txtCitasMascotaCliente);
		tituloCitasMascota.setAlignment(Paragraph.ALIGN_CENTER);
		tituloCitasMascota.setSpacingBefore(65.3f);
		
		docCliente.add(tituloCitasMascota);
		
		//--------------Para las citas de la mascota---------------
		PdfPTable tablaCitasMascota = new PdfPTable(12);
		tablaCitasMascota.setWidthPercentage(100f);
		tablaCitasMascota.setWidths(new float[] {1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f});// dimensiones para las 12 columnas
		tablaCitasMascota.setSpacingBefore(9.35f);
		
		this.mostrarCitasMascota(tablaCitasMascota);
		
		docCliente.add(tablaCitasMascota);
		
		docCliente.close();
	}
}
