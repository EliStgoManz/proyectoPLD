/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author israel.garcia
 */
public class Archivos {
    /**
     * Method to <b>copy</b> a file from a source origin (<code>fromFile</code>)
     * to a destination(<code>toFile</code>). Método para <b>copiar</b> un
     * fichero desde un origen (<code>fromFile</code>) a un destino
     * (<code>toFile</code>).
     *
     * @param fromFile <code>String</code> source file path (ruta del fichero
     * origen).
     * @param toFile <code>String</code> destination file path (ruta del fichero
     * destino).
     * @return <code>boolean</code> It returns true if they could copy the file
     * false on error (devuelve true si se pude copiar el fichero false en caso
     * de error).
     */
    public boolean copyFile(String fromFile, String toFile) {
        File origin = new File(fromFile);
        File destination = new File(toFile);
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                // We use a buffer for the copy (Usamos un buffer para la copia).
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    
    
    public static boolean deleteSubDirectorys(File path, File rootPath) {
    if( path.exists() ) {
      File[] files = path.listFiles();
      
      for(int i=0; i<files.length ; i++) {
         File x = files[i];
         if ( files[i] != rootPath){
            if(files[i].isDirectory()) {
              deleteSubDirectorys(files[i], rootPath);
            }
            else {
              files[i].delete();
            }
         }
      }
    }
    
        if ( rootPath != path ){
            return path.delete();
        } else {
            return true;
        }
    
    
    }
}
