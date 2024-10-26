/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encriptar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author march
 */
public class SHA1 {
    private MessageDigest md;
    private byte[] buffer, digest;
    private String hash = "";

    public String getHash(String message) throws NoSuchAlgorithmException {
        buffer = message.getBytes();
        md = MessageDigest.getInstance("SHA1");
        md.update(buffer);
        digest = md.digest();

        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
     return hash;
    }
    
    public String cript(String pass){
    SHA1 s = new SHA1();
//    "basura"(cadena de texto aleatorio) la cual la añado en la misma cadena 
//    para encriptar y para liar aun mas vuelvo a encriptar el resultado mas la cadena
    String basura="rwr24t5yt25y543td32ty6";
        try {
            return s.getHash(s.getHash(pass+basura)+basura);
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    
    return "0";
    }
}
