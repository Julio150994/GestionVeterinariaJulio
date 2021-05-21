package com.veterinaria.configuraciones;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private static Font txtDatosCliente,txtLogo,txtCitasMascotaCliente;
	private static SimpleDateFormat fechaSQL = new SimpleDateFormat("yyyy-MM-dd"),
			fechaNormal = new SimpleDateFormat("dd/MM/yyyy");
	private static Date fechaFormateada;
	
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
		filaCliente.setColspan(2);
		tablaClientes.addCell(filaCliente);
		celdasCliente.setPhrase(new Phrase(datosCliente.getNombre()));
		celdasCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setPadding(10);
		celdasCliente.setColspan(2);
		tablaClientes.addCell(celdasCliente);
		
		filaCliente.setPhrase(new Phrase("Apellidos",txtDatosCliente));
		filaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		filaCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		filaCliente.setPadding(10);
		filaCliente.setColspan(2);
		tablaClientes.addCell(filaCliente);
		celdasCliente.setPhrase(new Phrase(datosCliente.getApellidos()));
		celdasCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setVerticalAlignment(Element.ALIGN_CENTER);
		celdasCliente.setPadding(10);
		celdasCliente.setColspan(2);
		tablaClientes.addCell(celdasCliente);
		
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
	}
	
	public void exportarDatosCliente(HttpServletResponse resCliente) throws DocumentException, IOException {
		Document docCliente = new Document();
		docCliente.setPageSize(PageSize.A4);
		
		PdfWriter.getInstance(docCliente, resCliente.getOutputStream());
		docCliente.open();
		
		//----------Establecemos la imágen de logo de la clínica a la derecha del informe------------------
		txtLogo = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		txtLogo.setSize(12);
		txtLogo.setColor(Color.BLACK);
		Paragraph logo = new Paragraph("Informe de clínica DAM",txtLogo);
		logo.setAlignment(Paragraph.ALIGN_LEFT);
		docCliente.add(logo);
		
		Image logoClinica = Image.getInstance("logo.png");
		logoClinica.setAbsolutePosition(510,769);
		logoClinica.setAlignment(Element.ALIGN_RIGHT);
		docCliente.add(logoClinica);
		
		txtDatosCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtDatosCliente.setSize(19);
		txtDatosCliente.setColor(Color.BLACK);
		Paragraph tituloDatosCliente = new Paragraph("Datos de cliente",txtDatosCliente);
		tituloDatosCliente.setAlignment(Paragraph.ALIGN_CENTER);
		tituloDatosCliente.setSpacingBefore(8.45f);
		
		docCliente.add(tituloDatosCliente);
		
		//-----------Para el cliente actual------------------------
		PdfPTable tablaClientes = new PdfPTable(4);
		tablaClientes.setWidthPercentage(100f);
		tablaClientes.setSpacingBefore(4.36f);
		
		this.mostrarDatosCliente(tablaClientes);
		
		docCliente.add(tablaClientes);
		
		txtCitasMascotaCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		txtCitasMascotaCliente.setSize(19);
		txtCitasMascotaCliente.setColor(Color.BLACK);
		Paragraph tituloCitasMascota = new Paragraph("Lista de citas realizadas",txtCitasMascotaCliente);
		tituloCitasMascota.setAlignment(Paragraph.ALIGN_CENTER);
		tituloCitasMascota.setSpacingBefore(9.38f);
		docCliente.add(tituloCitasMascota);
		
		try {
			/*---------------Para mostrar todas las citas de la mascota------------------*/
			for(Citas cita: datosCitasCliente) {
				PdfPTable tablaCitasMascota = new PdfPTable(12);
				tablaCitasMascota.setWidthPercentage(86.85f);
				tablaCitasMascota.setSpacingBefore(14.36f);
				filaCitas.setBackgroundColor(Color.decode("#437EB9"));
				PdfPCell celdasCitasMascota = new PdfPCell();
				celdasCitasMascota.setBackgroundColor(Color.WHITE);
				
				txtCitasMascotaCliente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				txtCitasMascotaCliente.setColor(Color.WHITE);
				
				filaCitas.setPhrase(new Phrase("Nombre de veterinario",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getUsuario().getNombre()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Apellidos de veterinario",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getUsuario().getApellidos()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Teléfono de veterinario",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getUsuario().getTelefono()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Username de veterinario",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getUsuario().getUsername()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Fecha de cita",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				
				fechaFormateada = fechaSQL.parse(""+cita.getFecha());
				celdasCitasMascota.setPhrase(new Phrase(fechaNormal.format(fechaFormateada)));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Motivo",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getMotivo()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Informe",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getInforme()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Nombre de mascota",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getMascota().getNombre()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Tipo de mascota",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getMascota().getTipo()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Raza de mascota",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				celdasCitasMascota.setPhrase(new Phrase(cita.getMascota().getRaza()));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Fecha de mascota",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				
				fechaFormateada = fechaSQL.parse(""+cita.getMascota().getFechaNacimiento());
				celdasCitasMascota.setPhrase(new Phrase(fechaNormal.format(fechaFormateada)));
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setPadding(10);
				celdasCitasMascota.setColspan(6);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				
				filaCitas.setPhrase(new Phrase("Foto de mascota",txtCitasMascotaCliente));
				filaCitas.setHorizontalAlignment(Element.ALIGN_CENTER);
				filaCitas.setVerticalAlignment(Element.ALIGN_CENTER);
				filaCitas.setPadding(10);
				filaCitas.setColspan(6);
				tablaCitasMascota.addCell(filaCitas);
				Image fotoMascota = Image.getInstance(cita.getMascota().getFoto());
				fotoMascota.setAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setHorizontalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setVerticalAlignment(Element.ALIGN_CENTER);
				celdasCitasMascota.setColspan(6);
				celdasCitasMascota.setImage(fotoMascota);
				tablaCitasMascota.addCell(celdasCitasMascota);
				
				docCliente.add(tablaCitasMascota);// añadimos una tabla cada vez
				docCliente.newPage();// realizamos el salto de página
			}
			
			docCliente.close();
		}
		catch(ParseException | IOException ex) {
			ex.printStackTrace();
		}
	}
}
