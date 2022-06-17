/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author gibran.matias
 */
public class comprime {
    private static final int BUFFER_SIZE = 1024;
            public void Zippear(String pFile, String pZipFile) throws Exception {
// objetos en memoria
                FileInputStream fis = null;
                FileOutputStream fos = null;
                ZipOutputStream zipos = null; 
// buffer
                byte[] buffer = new byte[BUFFER_SIZE];
                try {// fichero a comprimir
                    fis = new FileInputStream(pFile);// fichero contenedor del zip
                    fos = new FileOutputStream(pZipFile);// fichero comprimido
                    zipos = new ZipOutputStream(fos);
                    ZipEntry zipEntry = new ZipEntry(pFile);zipos.putNextEntry(zipEntry);
                    int len = 0;
// zippear
                    while ((len = fis.read(buffer, 0, BUFFER_SIZE))!= -1)
                        zipos.write(buffer, 0, len);// volcar la memoria al disco
                        zipos.flush();
                } catch (Exception e) {
                	System.out.println("comprime.java Zippear() "+e.toString());
                	throw e;
                } 
                finally {// cerramos los files
                    zipos.close();
                    fis.close();
                    fos.close();} // end try} // end
    
    }
    
    
    public static void main(String[]args) throws IOException, Exception{
        comprime c=new comprime();
        c.Zippear("C:\\Users\\gibran.matias\\Documents\\REGALOS_OPCIONES.pdf","C:\\Users\\gibran.matias\\Documents\\REGALOS_OPCIONES.zip" );
    }
  
}
