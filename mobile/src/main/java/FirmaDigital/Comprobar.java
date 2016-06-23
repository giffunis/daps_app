package FirmaDigital;

/**
 * Created by drcaspa on 24/6/16.
 * email: giffunis@gmail.com
 */
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Comprobar {
    private static String clavePublicaServidor_ = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEBH8Z/WHOHm/ZbDDoFJGy2xobkc5vqssP/iIngDj2gcC751zvKkffEVCMCVvyNzcwfeQOOblwQrKTI5eM3ucuuQ==";



    public static Boolean comprobarFirma(String clavePublica, String mensaje, String signature) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        byte[] encKey = Base64.decode(clavePublica.getBytes("UTF-8"),Base64.DEFAULT);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

        byte[] sigToVerify = Base64.decode(signature.getBytes("UTF-8"),Base64.DEFAULT);
        Signature sig = Signature.getInstance("SHA1withECDSA");
        sig.initVerify(pubKey);

        sig.update(mensaje.getBytes("UTF-8"));
        boolean verifies = sig.verify(sigToVerify);
        return verifies;
    }

    public static Boolean comprobarFirma2(String mensaje, String signature) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        byte[] encKey = Base64.decode(clavePublicaServidor_.getBytes("UTF-8"),Base64.DEFAULT);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

        byte[] sigToVerify = Base64.decode(signature.getBytes("UTF-8"),Base64.DEFAULT);
        Signature sig = Signature.getInstance("SHA1withECDSA");
        sig.initVerify(pubKey);

        sig.update(mensaje.getBytes("UTF-8"));
        boolean verifies = sig.verify(sigToVerify);
        return verifies;
    }
}
