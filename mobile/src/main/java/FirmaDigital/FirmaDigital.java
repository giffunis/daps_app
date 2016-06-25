package FirmaDigital;

/**
 * Created by drcaspa on 25/6/16.
 * email: giffunis@gmail.com
 */
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;


public class FirmaDigital {

    private static final int KEY_SIZE = 256;
    private PrivateKey privateKey_;
    private PublicKey publicKey_;
    private Signature firma_;


    public FirmaDigital() throws NoSuchAlgorithmException, InvalidKeyException {
        generadorClaves();
        crearFirma();
    }

    private void generadorClaves() throws NoSuchAlgorithmException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        keyGen.initialize(KEY_SIZE, random);
        KeyPair keypair = keyGen.generateKeyPair();
        privateKey_ = keypair.getPrivate();
        publicKey_ = keypair.getPublic();
    }

    private void crearFirma() throws NoSuchAlgorithmException, InvalidKeyException {
        firma_ = Signature.getInstance("SHA1withECDSA");
        firma_.initSign(privateKey_);
    }

    public String firmar(String cadena) throws UnsupportedEncodingException, SignatureException {
        Signature firma = firma_;

        // String to BASE64(String)
        byte[] strByte = cadena.getBytes("UTF-8");

        firma.update(strByte);
        byte[] realSig = firma.sign();
        String sign = Base64.encodeToString(realSig, Base64.DEFAULT);
        System.out.println("Signature en base64: " + sign);
        return sign;
    }

    public PrivateKey getPrivateKey(){
        return privateKey_;
    }

    public PublicKey getPublicKey() {
        return publicKey_;
    }

    public String getPrivateKeyBase64() {
        String [] aux;
        String cuerpo = "";
        String cabecera = "-----BEGIN PRIVATE KEY-----\n";
        String pie = "-----END PRIVATE KEY-----";

        // Transformación en Base64
        byte[] prueba2 = privateKey_.getEncoded();

        String clave2 = Base64.encodeToString(prueba2, Base64.DEFAULT);

        // Calculo el número filas;
        int filas = (int) Math.ceil((clave2.length() * 1.0) / 64);

        // Dividimos la clave en filas de 64 carácteres
        aux = splitByNumber(clave2, 64);

        // Guardamos las filas en el cuerpo
        for (int i = 0; i < filas; i++) {
            cuerpo = cuerpo + aux[i] + "\n";
        }
        return cabecera + cuerpo + pie;
    }

    public static String[] splitByNumber(String str, int size) {
        return (size<1 || str==null) ? null : str.split("(?<=\\G.{"+size+"})");
    }

    public String getPublicKeyBase64() {
        String [] aux;
        String cuerpo = "";
        String cabecera = "-----BEGIN PUBLIC KEY-----\n";
        String pie = "-----END PUBLIC KEY-----";

        // Transformación en Base64
        byte[] prueba2 = publicKey_.getEncoded();
        String clave2 = Base64.encodeToString(prueba2, Base64.DEFAULT);

        // Calculo el número filas;
        int filas = (int) Math.ceil((clave2.length() * 1.0) / 64);

        // Dividimos la clave en filas de 64 carácteres
        aux = splitByNumber(clave2, 64);

        // Guardamos las filas en el cuerpo
        for (int i = 0; i < filas; i++) {
            cuerpo = cuerpo + aux[i] + "\n";
        }
        return cabecera + cuerpo + pie;
    }

    public void imprimirBase64() {
        byte[] prueba = publicKey_.getEncoded();
        String clave = Base64.encodeToString(prueba, Base64.DEFAULT);

        System.out.println("Clave Pública en base64: " + clave);
        byte[] prueba2 = privateKey_.getEncoded();
        String clave2 = Base64.encodeToString(prueba2, Base64.DEFAULT);
        System.out.println("Clave Privada en base64: " + clave2);
    }

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException {
        FirmaDigital firmaDigital = new FirmaDigital();
        System.out.println("\nSignature en Base64:\n" + firmaDigital.firmar("Hola Mundo"));
        System.out.println("\nClave privada:\n\n" + firmaDigital.getPrivateKeyBase64());
        System.out.println("\nClave pública:\n\n" + firmaDigital.getPublicKeyBase64());

    }

}

