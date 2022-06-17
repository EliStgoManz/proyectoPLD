/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author israel.garcia
 */
public class Fecha {
    public static DateFormat hourdateFormat;

        /**
         * Regresa la fecha y hora del sistema con el formato ddMMyy_HHmm
         * @return 
         */
        public static String getFechaHoraSistema(){
            Date date = new Date();            
            hourdateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
            return hourdateFormat.format(date);
        }
        
        public static void main (String args[]){
            System.out.println( new Fecha().getFechaHoraSistema() );
        }
}
