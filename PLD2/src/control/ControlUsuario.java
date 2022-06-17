/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosUsuarioRatos;

/**
 *
 * @author Israel Osiris Garcia
 */
public class ControlUsuario {
    
    /**
     * Obtiene el próximo usuario de PLD asignado
     * @return El próximo usuario de PLD asignado
     */
    
    public String getUsuarioAsignado(){
        String usuarioAsignado = ""; 
        String[] listaUsuarios = new DatosUsuarioRatos().getListaUsuariosPLD();
        String ultimoUsuario = new DatosUsuarioRatos().getUltimoUsuarioAsignado();
        
        if ( listaUsuarios != null ){ //si no existen usuarios pld se aborta el metodo
            if ( !ultimoUsuario.isEmpty()){ // si hay ultimo usuario asignado
                for ( int i = 0; i<listaUsuarios.length; i++){
                    if ( listaUsuarios[i].equals(ultimoUsuario)){
                        if ( i+1 == listaUsuarios.length){ //si se llego al final de la lista toma el primero
                            try{
                                usuarioAsignado = listaUsuarios[0];
                                new DatosUsuarioRatos().actualizaUltimoUsuarioAsignado(usuarioAsignado);                                
                                break;  
                            } catch (Exception es ){
                            	System.out.println("ControlUsuario.java actualizaUltimoUsuarioAsignado "+es.toString());
                                
                                break;
                            }
                                    
                        } else {
                            try{
                                usuarioAsignado = listaUsuarios[i+1];
                                new DatosUsuarioRatos().actualizaUltimoUsuarioAsignado(usuarioAsignado);
                                break;
                            } catch ( Exception es ){
                            	System.out.println("ControlUsuario.java actualizaUltimoUsuarioAsignado "+es.toString());
                                
                                break;
                            }
                        }
                    }
                }
                if (usuarioAsignado.compareTo("") == 0) {
                    try{
                        usuarioAsignado = listaUsuarios[0];
                        new DatosUsuarioRatos().actualizaUltimoUsuarioAsignado(usuarioAsignado);                                
                    } catch (Exception es ){
                    	System.out.println("ControlUsuario.java actualizaUltimoUsuarioAsignado "+es.toString());
                        
                    }
                }
            } else {
                try{
                    usuarioAsignado  = listaUsuarios[0];
                    new DatosUsuarioRatos().actualizaUltimoUsuarioAsignado(usuarioAsignado);
                } catch( Exception es){
                	System.out.println("ControlUsuario.java actualizaUltimoUsuarioAsignado "+es.toString());
                    
                }
            }//if isEmpty
        } //if listusuarios
        
        return usuarioAsignado;
    }//fin metodo getUsuarioAsignado
    

}
