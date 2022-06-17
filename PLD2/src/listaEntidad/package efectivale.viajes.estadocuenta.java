package efectivale.viajes.estadocuenta;

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

import com.evsa.pedido.login.Bitacora;

public class Consumos {

	private Bitacora bitacora = null;

	private Map<?, ?> post = null;
	private String xperiodoDesde = null;
	private String xperiodoHasta = null;

	// Variables post
	private String xtarjeta = null;
	private Date periodoDesde;
	private Date periodoHasta;
	private Connection cn = null;

	private HSSFWorkbook libro;

	public void doProceso(Bitacora bitacora, Map<?, ?> post) {
		
		// Bitacora y post disponibles a la clase
		this.bitacora = bitacora;
		this.post = post;

		try {

			cn = bitacora.getConnection("pdviajes");
			// Intercambio inicial
			if (bitacora.intercambioID == -1) {
				bitacora.bitacoraDialogo = getDialogoCaptura();
				bitacora.intercambioID = 1;
				return;
			}

			if (bitacora.intercambioID == 1) {
				if (post.containsKey("xoCancelar")) {
					bitacora.bitacoraNotificacion = "Se abandona Resumen de Consumos";
					return;
				}
				if (post.containsKey("xoExcel")) {
					if (isPostValido() && doReporteExcel()) {
						bitacora.isDescargaExcel = true;
						bitacora.libroExcel = libro;
						return;	}
				}

				bitacora.bitacoraDialogo = getDialogoCaptura();
				return;
			}
		} catch (Exception e) {
			bitacora.bitacoraErrorCraso = e.toString();
			bitacora.closeConnection(cn);
		} finally {
			bitacora.closeConnection(cn);
		}
		return;
	}

	private String getDialogoCaptura() {

		bitacora.dialogoDatoEnfocado = "periodoDesde";

		recuperaDatosCapturados();

		// Eliminamos nulos
		if (xperiodoDesde == null)
			xperiodoDesde = new SimpleDateFormat("yyyy-MM").format(bitacora.getBitacoraFecha()) + "-01";
		if (xperiodoHasta == null)
			xperiodoHasta = new SimpleDateFormat("yyyy-MM-dd").format(bitacora.getBitacoraFecha());

		if (xtarjeta == null || xtarjeta.equals("0"))
			xtarjeta = "";

		String dialogo = "	<link type='text/css' href='resources/bootstrap.min.css' rel='stylesheet'>"
				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"DT\">" + "<tr>"
				+ "<td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" + "<tr class=\"DE0\">"
				+ "<td colspan=\"2\"><li class=\"list-group-item list-group-item-info\">Reporte Consumos</li></td>"
				+ "</tr>" + "<tr class=\"DFC2\">" + "<td class=\"DFC22\">Del:</td>"
				+ "<td class=\"DFC22\"><input name=\"periodoDesde\" type=\"text\" id=\"periodoDesde\" value=\""
				+ xperiodoDesde + "\" />" + "&nbsp;&nbsp;AAAA-MM-DD </td>" + "</tr>" + "<tr class=\"DFC2\">"
				+ "<td class=\"DFC22\">Al:</td>"
				+ "<td class=\"DFC22\"><input name=\"periodoHasta\" type=\"text\" id=\"periodoHasta\" value=\""
				+ xperiodoHasta + "\" />" + "&nbsp;&nbsp;AAAA-MM-DD</td>" + "</tr>"

				+ "<tr class=\"DFC2\">" + "<td class=\"DFC22\">Tarjeta:</td>"
				+ "<td class=\"DFC22\"><input name=\"tarjeta\" type=\"text\" id=\"tarjeta\" value=\"" + xtarjeta
				+ "\" />" + "</td>" + "</tr>" + "" + "</table></td>" + "</tr>" + "<tr class=\"DB\">" + "</tr>"
				+ "</table>" + "</td>" + "</tr>" + "<tr class=\"DB\">"
				+ "<td align=\"center\" class=\"DB\"><input name=\"xoExcel\" type=\"submit\" id=\"xoExcel\" value=\"Aceptar\" accesskey=\"A\" />"
				+ "<input name=\"xoCancelar\" type=\"submit\" id=\"xoCancelar\" value=\"Cancelar\" accesskey=\"C\" />"
				+ "</tr>" + "</table>";
		return dialogo;
	}

	private void recuperaDatosCapturados() {
		if (post.containsKey("periodoDesde")) {
			xperiodoDesde = ((String[]) post.get("periodoDesde"))[0].trim();
		}
		if (post.containsKey("periodoHasta")) {
			xperiodoHasta = ((String[]) post.get("periodoHasta"))[0].trim();
		}
		if (post.containsKey("tarjeta")) {
			xtarjeta = ((String[]) post.get("tarjeta"))[0].trim();
		}

	}

	private boolean isPostValido() {
		if (!isPostValidoPeriodo())
			return false;

		if (post.containsKey("tarjeta")) {
			xtarjeta = ((String[]) post.get("tarjeta"))[0].trim();
		}
		if (xtarjeta == null || xtarjeta.trim().length() == 0) {
			xtarjeta = "";
		} else if(xtarjeta.length() != 16){
			bitacora.bitacoraErrorCraso = "Tarjeta debe tener 16 caracteres.";
			return false;
		}
		if (xtarjeta.length() != 16)
			xtarjeta = "";

		if (!Bitacora.isDigitos(xtarjeta)) {
			bitacora.bitacoraErrorCraso = "Tarjeta debe tener solo numeros.";
			return false;
		}
		return true;
	}

	private boolean isPostValidoPeriodo() {

		// Extraemos del post
		if (post.containsKey("periodoDesde"))
			xperiodoDesde = ((String[]) post.get("periodoDesde"))[0].trim();
		if (post.containsKey("periodoHasta"))
			xperiodoHasta = ((String[]) post.get("periodoHasta"))[0].trim();

		// Validamos
		if (xperiodoDesde == null || xperiodoDesde.length() == 0) {
			bitacora.bitacoraErrorCraso = "Debes proporcionar el inicio del periodo";
			return false;
		}
		if (Bitacora.getFecha(xperiodoDesde) == null) {
			bitacora.bitacoraErrorCraso = "El inicio del periodo debe ser una fecha en formato AAAA-MM-DD";
			return false;
		}
		if (xperiodoHasta == null || xperiodoHasta.length() == 0) {
			bitacora.bitacoraErrorCraso = "Debes proporcionar el fin del periodo";
			return false;
		}
		if (Bitacora.getFecha(xperiodoHasta) == null) {
			bitacora.bitacoraErrorCraso = "El fin del periodo debe ser una fecha en formato AAAA-MM-DD";
			return false;
		}

		// Convertimos Strings
		periodoDesde = Bitacora.getFecha(xperiodoDesde);
		periodoHasta = Bitacora.getFecha(xperiodoHasta);

		return true;
	}

	private boolean doReporteExcel(){

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

			// Fecha
			row = hoja.createRow(0);
			col = row.createCell(6);

			col.setCellValue(new HSSFRichTextString(
					new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bitacora.getBitacoraFecha()) + " Hrs"));
			col.setCellStyle(cellRight);

			// Nombre corporativo
			row = hoja.createRow(1);
			col = row.createCell(2);
			col.setCellValue(new HSSFRichTextString(bitacora.getClienteNombre()));
			col.setCellStyle(cellBold);

			// Nombre reporte
			row = hoja.createRow(3);
			col = row.createCell(2);
			col.setCellValue(new HSSFRichTextString("Reporte de Consumos del " + periodoDesde + " al " + periodoHasta));
			col.setCellStyle(cellBold);

			// Encabezado
			row = hoja.createRow(5);

			col = row.createCell(0);
			col.setCellValue(new HSSFRichTextString("Tarjeta"));
			col.setCellStyle(cellHeader);

			col = row.createCell(1);
			col.setCellValue(new HSSFRichTextString("Num. Empleado"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(2);
			col.setCellValue(new HSSFRichTextString("Empleado"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(3);
			col.setCellValue(new HSSFRichTextString("Fecha y hora "));
			col.setCellStyle(cellHeader);

			col = row.createCell(4);
			col.setCellValue(new HSSFRichTextString("Comercio"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(5);
			col.setCellValue(new HSSFRichTextString("País Comercio"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(6);
			col.setCellValue(new HSSFRichTextString("Tipo de movimiento"));
			col.setCellStyle(cellHeader);
			
			col = row.createCell(7);
			col.setCellValue(new HSSFRichTextString("Importe MXN"));
			col.setCellStyle(cellHeader);
			
			
			col = row.createCell(8);
			col.setCellValue(new HSSFRichTextString("Importe origen USD"));
			col.setCellStyle(cellHeader);

			col = row.createCell(9);
			col.setCellValue(new HSSFRichTextString("Concepto"));
			col.setCellStyle(cellHeader);

			String query = "select numeroTarjeta,e.tnuec,e.tnoem || ' ' || e.tappa || ' ' || e.tapma nombre,fechaTransaccion ||' '||horaTransaccion fecha,"
					+ " descripcioncomercio,descripcionpais,concepto,importe,importeusd,claveTransaccion,claveRespuesta "
					+ " from bm_tbitact bm"
					+ " join ttarj t on bm.numeroTarjeta= t.tnuta "
					+ " join tmemp e on t.tidem=e.tidem where e.tnucl=? and e.tnuco=? and claveRespuesta='000' and fechatransaccion BETWEEN ? and ? ";

			if (xtarjeta.length() == 16)
				query += "and bm.numeroTarjeta = ?";

			query += " order by bm.numeroTarjeta,bm.fechaTransaccion";
			t = 5;
			// Recuperamos Movimientos
			ps = cn.prepareStatement(query);

			ps.setInt(1, bitacora.getClienteID());
			ps.setInt(2, bitacora.getConsignatarioID());
			ps.setTimestamp(3, java.sql.Timestamp.valueOf(xperiodoDesde + " 00:00:00.000"));
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(xperiodoHasta + " 23:59:59.999"));

			if (xtarjeta.length() == 16) {
				ps.setString(t, xtarjeta);
				t++;
			}
			
			rs = ps.executeQuery();
			if (!rs.next()) {
				bitacora.bitacoraErrorCraso = "No existen Registros de Consumos Para ese Periodo.";
				return false;
			}

			// Presentamos cada fila
			int nrow = 6;
			do {
				row = hoja.createRow(nrow++);

				String tarjetaID = rs.getString("numeroTarjeta");
				String tnuec = rs.getString("tnuec");
				String nombre = rs.getString("nombre");
				String fecha = rs.getString("fecha");
				String comercio = rs.getString("descripcioncomercio");
				String pais = rs.getString("descripcionpais");
				String concepto =  rs.getString("concepto");
				double importe = rs.getDouble("importe");
				double importeusd = rs.getDouble("importeusd");
				String clave = "N/D";
				String transaccion = rs.getString("claveTransaccion").trim();
				String val = "01";

				if (transaccion.equals(val)) {
					clave = "Venta";
				}

				val = "02";
				if (transaccion.equals(val)) {
					clave = "Disposicion de Efectivo";
				}

				val = "21";
				if (transaccion.equals(val)) {
					clave = "Devolucion";
				}

				col = row.createCell(0);
				col.setCellValue(new HSSFRichTextString(tarjetaID));
				col.setCellStyle(cellDetailCentrado);

				col = row.createCell(1);
				col.setCellValue(new HSSFRichTextString(tnuec));
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
				
				col = row.createCell(9);
				col.setCellValue(new HSSFRichTextString(clave));
				col.setCellStyle(cellDetailCentrado);

			} while (rs.next());

			// Ancho columnas
			for (int x = 0; x <= 14; x++) {
				hoja.autoSizeColumn(x);
			}

		} catch (Exception e) {
			bitacora.bitacoraErrorCraso = e.toString();
			bitacora.closeConnection(ps, rs);
		} finally {
			bitacora.closeConnection(ps, rs);
		}
		return true;
	}
}