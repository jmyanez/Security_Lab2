
import java.io.*;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.*;

class Decrypt {

    public static void main(String[] args) {
        // This program reads a private key from a file
        // and an encrypted message, decrypts the message
        // and prints it.
        // Written by Luc Longpre for Computer Security, Spring 2018

        File file;
        PrivateKey privKey;
        Cipher cipher;
        byte[] decryptedByteArray;
        String encryptedString, decryptedString;

        // get the private key from file
        privKey = PemUtils.readPrivateKey("JoseYServerCertprivateKey.pem");

        // get the encrypted Message
        try {
            file = new File("encryptedMessage.txt");
            Scanner input = new Scanner(file);
            encryptedString = input.nextLine();
            System.out.println("The encrypted string is: " + encryptedString);
        } catch (Exception e) {
            System.out.println("Could not open encryptedMessage file");
            return;
        }
        decryptedByteArray = decrypt(privKey, Base64.getDecoder().decode(encryptedString));
        decryptedString = new String(decryptedByteArray);
        System.out.println("The decrypted string is: " + decryptedString);
    }

    public static byte[] decrypt(PrivateKey privKey, byte[] encryptedByteArray) {
        // decrypts a byte array using a private key
        // and returns the decryption as a byte array
       
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            return cipher.doFinal(encryptedByteArray);      
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("error while decrypting the message");
            return null;
        }
    }

// decrypt2 is created to be able to call this from EchoClientSkeleton so we do no have to
// copy the code to decrypt over there and make the code look cleaner
    public byte[] decrytp2(byte[] encryptedBytes){
        PrivateKey pk;
        Cipher cipher;
        byte[] myArray;
        // get the private key from file
        pk = PemUtils.readPrivateKey("JoseYServerCertprivateKey.pem");
        System.out.println("The encrypted bytes are: " + encryptedBytes[1]);

        try {

            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pk);
            //System.out.println(encryptedBytes);
            myArray = cipher.doFinal(encryptedBytes);
            return myArray;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("error while decrypting the message");
            return null;
        }
    }


}
