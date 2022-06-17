/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Salvador Valenzuela
 */
public class Cadenas {
 
     public static String getExtension(String filename) {
            int index = filename.lastIndexOf('.');
            if (index == -1) {
                  return "";
            } else {
                  return filename.substring(index + 1).trim();
            }
    }

    public static String convert(String value, String fromEncoding, String toEncoding) {
        try {
            return new String (value.getBytes(fromEncoding), toEncoding);
        } catch (Exception e) {
            return value;
        }
    }
    
    public static String charset(String value, String charsets[]) {
        String probe = StandardCharsets.UTF_8.name();
        for(String c : charsets) {
            Charset charset = Charset.forName(c);
            if(charset != null) {
                String uno = convert(value, charset.name(), probe);
                String dos = convert(uno,  probe, charset.name());
                System.out.println(value + " - " + uno + " / " + dos);
                if(value.equals(convert(convert(value, charset.name(), probe), probe, charset.name()))) {
                    System.out.println(c);                    
                    return c;
                }
            }
        }
        return StandardCharsets.UTF_8.name();
    }    
    
    public static String corrigeEncode (String cadena) {
        /*String[] Encodings = new String[] {"ISO-8859-1", "UTF-8"};
        if (!(charset(cadena, Encodings).compareTo("UTF-8") == 0)) {
            return cadena;
        } else {
            return convert(cadena, "UTF-8", "ISO-8859-1");
        }*/
        return cadena;        
    }
}
