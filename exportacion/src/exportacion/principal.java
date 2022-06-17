package exportacion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class principal {
	
	private HSSFWorkbook libro;
	private Connection cn = null;
	
	private String doReporteExcel(String Cliente){

		PreparedStatement ps = null;
		ResultSet rs = null;
		int t = 0;
		try {
			libro = new HSSFWorkbook();
			HSSFSheet hoja = libro.createSheet();

			// Negritas
			HSSFFont fontBold = libro.createFont();
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle cellBold = libro.createCellStyle();
			cellBold.setFont(fontBold);

			// Alineado derecha
			HSSFCellStyle cellRight = libro.createCellStyle();
			cellRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

			// Alineado derecha negrita
			HSSFCellStyle cellRightBold = libro.createCellStyle();
			cellRightBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cellRightBold.setFont(fontBold);

			// Encabezado Firma
			HSSFCellStyle cellEncabezadoFirma = libro.createCellStyle();
			cellEncabezadoFirma.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// Firma
			HSSFCellStyle cellFirma = libro.createCellStyle();
			cellFirma.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellFirma.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);

			// Encabezado
			HSSFCellStyle cellHeader = libro.createCellStyle();
			cellHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellHeader.setFont(fontBold);

			// Detalle
			HSSFCellStyle cellDetail = libro.createCellStyle();
			cellDetail.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cellDetail.setBorderBottom(HSSFCellStyle.BORDER_THIN);

			// Detalle Centrado
			HSSFCellStyle cellDetailCentrado = libro.createCellStyle();
			cellDetailCentrado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellDetailCentrado.setBorderBottom(HSSFCellStyle.BORDER_THIN);

			// Detalle Entero
			HSSFCellStyle cellDetailEntero = libro.createCellStyle();
			cellDetailEntero.setBorderBottom(HSSFCellStyle.BORDER_THIN);

			// Detalle Decimal
			HSSFDataFormat formatDecimal = libro.createDataFormat();
			HSSFCellStyle cellDetailDecimal = libro.createCellStyle();
			cellDetailDecimal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellDetailDecimal.setDataFormat(formatDecimal.getFormat("###,###,###,##0.00"));

			// Detalle Importe
			HSSFDataFormat formatImporte = libro.createDataFormat();
			HSSFCellStyle cellDetailImporte = libro.createCellStyle();
			cellDetailImporte.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellDetailImporte.setDataFormat(formatImporte.getFormat("$###,###,###,##0.00"));

			// Total
			HSSFCellStyle cellTotal = libro.createCellStyle();
			cellTotal.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			cellTotal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellTotal.setDataFormat(formatImporte.getFormat("$###,###,###,##0.00"));
			cellTotal.setFont(fontBold);

			// row y col de maniobra
			HSSFRow row = null;
			HSSFCell col = null;

//			// Fecha
//			row = hoja.createRow(0);
//			col = row.createCell(6);

//			col.setCellValue(new HSSFRichTextString(
//					new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bitacora.getBitacoraFecha()) + " Hrs"));
//			col.setCellStyle(cellRight);

			// Nombre corporativo
			row = hoja.createRow(1);
			col = row.createCell(2);
			col.setCellValue(new HSSFRichTextString(Cliente));
			col.setCellStyle(cellBold);

			// Nombre reporte
			row = hoja.createRow(3);
			col = row.createCell(2);
			col.setCellValue(new HSSFRichTextString("Informacion obtenida sobre cliente."));
			col.setCellStyle(cellBold);

			// Encabezado
			row = hoja.createRow(5);

			col = row.createCell(0);
			col.setCellValue(new HSSFRichTextString("Cliente_id"));
			col.setCellStyle(cellHeader);

			col = row.createCell(1);
			col.setCellValue(new HSSFRichTextString("Num. Interno"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(2);
			col.setCellValue(new HSSFRichTextString("Tipo Persona"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(3);
			col.setCellValue(new HSSFRichTextString("Nombre"));
			col.setCellStyle(cellHeader);

			col = row.createCell(4);
			col.setCellValue(new HSSFRichTextString("Apellido Paterno"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(5);
			col.setCellValue(new HSSFRichTextString("Apellido ¨Materno"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(6);
			col.setCellValue(new HSSFRichTextString("Razon Social"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(7);
			col.setCellValue(new HSSFRichTextString("Calle"));
			col.setCellStyle(cellHeader);
			
			
			col = row.createCell(8);
			col.setCellValue(new HSSFRichTextString("Num. exterior"));
			col.setCellStyle(cellHeader);

			col = row.createCell(9);
			col.setCellValue(new HSSFRichTextString("Colonia"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(10);
			col.setCellValue(new HSSFRichTextString("Cod. Postal"));
			col.setCellStyle(cellHeader);

			col = row.createCell(11);
			col.setCellValue(new HSSFRichTextString("Telefono"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(12);
			col.setCellValue(new HSSFRichTextString("Email"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(13);
			col.setCellValue(new HSSFRichTextString("Fecha Nacimiento"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(14);
			col.setCellValue(new HSSFRichTextString("CURP"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(15);
			col.setCellValue(new HSSFRichTextString("RFC"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(16);
			col.setCellValue(new HSSFRichTextString("Actividad Eco."));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(17);
			col.setCellValue(new HSSFRichTextString("Giro Emp."));
			col.setCellStyle(cellHeader);

			String query = " select cliente_id, idcliente, "
+" (select tipopersona from avcliente where cliente_id=idcliente) as tipopersona, "
+" CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'F' )  "
+" THEN ((select nombre from avpersonafisica where cliente_id=idcliente)) "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'M' ) "
+ "+  THEN ((select rlnombre from avpersonamoral where cliente_id=idcliente))  "
+" END as nombre, "
+" CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'F' )  "
+"   THEN ((select apellidopaterno from avpersonafisica where cliente_id=idcliente)) "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'M' ) "
+"   THEN ((select rlapellidopaterno from avpersonamoral where cliente_id=idcliente))  "
+" END as apellidopaterno, "
+" CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'F' )  "
+"   THEN ((select apellidomaterno from avpersonafisica where cliente_id=idcliente)) "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'M' ) "
+"   THEN ((select rlapellidomaterno from avpersonamoral where cliente_id=idcliente))  "
+" END as apellidomaterno, "
+" razonsocial, "
+"  CASE  "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'N' )  "
+"   THEN ((select calle from avdomicilionac where cliente_id=idcliente)) "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'E' ) "
+"   THEN ((select calle from avdomicilioext where cliente_id=idcliente))  "
+" END as calle, "
+" CASE  "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'N' )  "
+"   THEN ((select numexterior from avdomicilionac where cliente_id=idcliente)) "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'E' ) "
+"   THEN ((select numexterior from avdomicilioext where cliente_id=idcliente))  "
+" END as numexterior, "
+" CASE  "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'N' )  "
+"   THEN ((select numinterior from avdomicilionac where cliente_id=idcliente)) "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'E' ) "
+"   THEN ((select numinterior from avdomicilioext where cliente_id=idcliente))  "
+" END as numinterior,  "
+" CASE  "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'N' )  "
+"   THEN ((select colonia from avdomicilionac where cliente_id=idcliente)) "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'E' ) "
+"   THEN ((select colonia from avdomicilioext where cliente_id=idcliente))  "
+" END as colonia, "
+" CASE  "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'N' )  "
+"   THEN ((select codpostal from avdomicilionac where cliente_id=idcliente)) "
+" WHEN (  (select tipodomicilio from avcliente where cliente_id=idcliente) = 'E' ) "
+"   THEN ((select codpostal from avdomicilioext where cliente_id=idcliente))  "
+" END as codpostal, "
+" (select telefono from avcliente where cliente_id=idcliente) as telefonp, "
+" email, "
+" CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'F' )  "
+"   THEN ((select fechanacimiento from avpersonafisica where cliente_id=idcliente)) "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'M' ) "
+"   THEN ((select rlfechanacimiento from avpersonamoral where cliente_id=idcliente))  "
+" END as fechanacimiento, "
+"  CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'F' )  "
+"   THEN ((select curp from avpersonafisica where cliente_id=idcliente)) "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'M' ) "
+"   THEN ((select rlcurp from avpersonamoral where cliente_id=idcliente))  "
+" END as curp, "
+" rfc as rfcCliente, "
+"  CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'F' )  "
+"   THEN ((select actividad_id from avpersonafisica where cliente_id=idcliente)) "
+" END as actividadEco, "
+"  CASE  "
+" WHEN (  (select tipopersona from avcliente where cliente_id=idcliente) = 'M' ) "
+"   THEN ((select giro_id from avpersonamoral where cliente_id=idcliente))  "
+" END as giro "
+" from varusuariotransitorio  where cliente_id=? ";


			t = 5;
			// Recuperamos Movimientos
			ps = cn.prepareStatement(query);

			ps.setString(1, Cliente);
			
			rs = ps.executeQuery();
			if (!rs.next()) {
				System.out.println("No existen Registros.");
//				return false;
			}

			// Presentamos cada fila
			int nrow = 6;
			do {
				row = hoja.createRow(nrow++);

				String Cliente_id = rs.getString("cliente_id");
				String Idcliente = rs.getString("idcliente");
				String Tipopersona = rs.getString("tipopersona");
				String Nombre = rs.getString("nombre");
				String Apellidopaterno = rs.getString("apellidopaterno");
				String Apellidomaterno = rs.getString("apellidomaterno");
				String Razonsocial =  rs.getString("razonsocial");
				
				
				double importe = rs.getDouble("importe");
				double importeusd = rs.getDouble("importeusd");


				col = row.createCell(0);
				col.setCellValue(new HSSFRichTextString(Cliente_id));
				col.setCellStyle(cellDetailCentrado);

				col = row.createCell(1);
				col.setCellValue(new HSSFRichTextString(Idcliente));
				col.setCellStyle(cellDetailCentrado);

				col = row.createCell(2);
				col.setCellValue(new HSSFRichTextString(nombre));
				col.setCellStyle(cellDetailCentrado);
			
				col = row.createCell(3);
				col.setCellValue(new HSSFRichTextString(fecha));
				col.setCellStyle(cellDetailCentrado);

				col = row.createCell(4);
				col.setCellValue(new HSSFRichTextString(comercio));
				col.setCellStyle(cellDetailCentrado);
				
				col = row.createCell(5);
				col.setCellValue(new HSSFRichTextString(pais));
				col.setCellStyle(cellDetailCentrado);
				
				col = row.createCell(6);
				col.setCellValue(new HSSFRichTextString(concepto));
				col.setCellStyle(cellDetailCentrado);
				
				col = row.createCell(7);
				col.setCellValue(new HSSFRichTextString(importe + ""));
				col.setCellStyle(cellDetailCentrado);

				col = row.createCell(8);
				col.setCellValue(new HSSFRichTextString(importeusd + ""));
				col.setCellStyle(cellDetailCentrado);
				
//				col = row.createCell(9);
//				col.setCellValue(new HSSFRichTextString(clave));
//				col.setCellStyle(cellDetailCentrado);

			} while (rs.next());

			// Ancho columnas
			for (int x = 0; x <= 14; x++) {
				hoja.autoSizeColumn(x);
			}

		} catch (Exception e) {
//			bitacora.bitacoraErrorCraso = e.toString();
//			bitacora.closeConnection(ps, rs);
		} finally {
//			bitacora.closeConnection(ps, rs);
		}
//		return true;
		return Cliente; //Se agrego cuando genere conexion
	}
	
	
	
	
	
	
	
}