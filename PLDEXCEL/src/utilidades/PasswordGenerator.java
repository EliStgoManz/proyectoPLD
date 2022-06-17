/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Israel Osiris García
 */
public class PasswordGenerator {
    public static String NUMEROS = "123456789";
 
	public static String MAYUSCULAS = "ABCDEFGHJLMNPQRSTUVWXYZ";
 
	public static String MINUSCULAS = "abcdefghijmnopqrstuvwxyz";
 
	public static String ESPECIALES = "./*-";
 
	//
	public static String getPinNumber() {
		return getPassword(NUMEROS, 4);
	}
 
	public static String getPassword() {
		return getPassword(8);
	}
 
	public static String getPassword(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}
 
	public static String getPassword(String key, int length) {
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}
        
        
}
