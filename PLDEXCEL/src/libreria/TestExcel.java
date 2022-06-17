/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author israel.garcia
 */
public class TestExcel {
    public static void main(String[] args) throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\israel.garcia\\Desktop\\X.xls")); //Pasamos el excel que vamos a leer
        Sheet sheet = workbook.getSheet(0); //Seleccionamos la hoja que vamos a leer
        String nombre;

        for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas
        for (int columna = 0; columna < sheet.getColumns(); columna++) { //recorremos las columnas
        nombre = sheet.getCell(columna, fila).getContents(); //setear la celda leida a nombre
        System.out.print(nombre + ""); // imprimir nombre
        }
        System.out.println("\n");
        System.out.println("????????????-");
        }
        }
        
}
