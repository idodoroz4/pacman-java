package models;


import java.security.SecureRandom;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private Key _aesKey;
    private Cipher _cipher;
    private String Encryption_method = "AES";

    public Encryption(){

        try {
            // create 128 bit key
            SecureRandom random = new SecureRandom();
            byte[] _key = new byte[16]; // 128 bits are converted to 16 bytes;
            random.nextBytes(_key);
            _aesKey = new SecretKeySpec(_key, Encryption_method);
            _cipher = Cipher.getInstance(Encryption_method);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public byte[] encrypt (String password){
        byte[] encrypted = null;
        try {
            _cipher.init(Cipher.ENCRYPT_MODE, _aesKey);
            encrypted = _cipher.doFinal(password.getBytes());
            System.err.println();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return encrypted;
    }

    public String decrypt (byte[] encrypted_pass){

        String decrypted = "";
        try {
            _cipher.init(Cipher.DECRYPT_MODE, _aesKey);
            decrypted = new String(_cipher.doFinal(encrypted_pass));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return decrypted;
    }
}
