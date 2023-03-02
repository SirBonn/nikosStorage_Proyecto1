/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author sirbon
 */
public class PasswordEncoder {
    
    public static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // creamos un objeto MessageDigest con el algoritmo SHA-256
            byte[] hash = md.digest(password.getBytes()); // ciframos la contraseña utilizando el método digest de MessageDigest
            StringBuilder hexString = new StringBuilder(); // creamos un StringBuilder para convertir los bytes del hash en una cadena de texto
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b); // convertimos cada byte del hash a su valor hexadecimal
                if (hex.length() == 1) {
                    hexString.append('0'); // añadimos un cero al principio si el valor hexadecimal es de un solo dígito
                }
                hexString.append(hex); // añadimos el valor hexadecimal al StringBuilder
            }
            return hexString.toString(); // devolvemos la cadena de texto resultante
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
