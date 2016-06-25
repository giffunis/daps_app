package FirmaDigital;

/**
 * Created by drcaspa on 25/6/16.
 * email: giffunis@gmail.com
 */
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Firmar {

    private static final String clavePrivada_ = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCA36tW9G38S3nsda0xu82FI4eiqfcmarCeZDKWP0u3ljw==";
    private static final String clavePublica_ = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEbANPZ/m6DDJKt3QFYMIzHOeGzoJ0avpVCdDv2JY3VOMoavbqxVk0aS/jOI5lUmt5k9sasYtFgQ9bqHYVTilmRQ==";


    public static String firmar(String mensaje) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        PrivateKey privateKey = obtenerPrivateKey();
        Signature firma = crearFirma(privateKey);
        String msgFirmado = mensajeFirmado(mensaje, firma);
        System.out.println("El mensaje es: \n" + mensaje);
        System.out.println("Mensaje firmado: \n" + msgFirmado);
        return msgFirmado;
    }

    private static PrivateKey obtenerPrivateKey() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] clear = Base64.decode(clavePrivada_.getBytes("UTF-8"),Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("EC");
        PrivateKey privateKey = fact.generatePrivate(keySpec);
        return privateKey;
    }

    private static PublicKey obtenerPublicKey() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encKey = Base64.decode(clavePublica_.getBytes("UTF-8"),Base64.DEFAULT);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
        return pubKey;
    }

    private static Signature crearFirma(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException{
        Signature firma = Signature.getInstance("SHA1withECDSA");
        firma.initSign(privateKey);
        return firma;
    }

    private static String mensajeFirmado(String mensaje, Signature firma) throws UnsupportedEncodingException, SignatureException{
        byte[] strByte = mensaje.getBytes("UTF-8");
        firma.update(strByte);
        byte[] realSig = firma.sign();
        String sign = Base64.encodeToString(realSig,Base64.DEFAULT);
        return sign;
    }

}