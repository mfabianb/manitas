package com.manitas.domain.service.impl;

import com.manitas.domain.service.EncodeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Log4j2
public class EncodeServiceImpl implements EncodeService {

    @Value("${config.public-key}")
    public String publicKeyString;

    @Value("${config.private-key}")
    public String privateKeyString;

    @Override
    @Scheduled(cron = "${crud.generate.cron}")
    public void generateKeys() throws NoSuchAlgorithmException{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        try(FileOutputStream fosPublic = new FileOutputStream(publicKeyString);
            FileOutputStream fosPrivate = new FileOutputStream(privateKeyString)){

            fosPublic.write(publicKey.getEncoded());
            fosPrivate.write(privateKey.getEncoded());
        }catch (Exception e){
            log.info(e);
        }

        log.info("LLAVES ESCRITAS EL: {}", LocalDateTime.now());

    }

    @Override
    public String encode(String message){
        try{
            PublicKey publicKey = getPublic();

            Cipher encryptCipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] secretMessageBytes = message.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        }catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | IOException
                | InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            log.info(e);
            return null;
        }
    }

    @Override
    public String decode(String message){
        try{
            byte[] text = Base64.getDecoder().decode(message.getBytes());
            PrivateKey privateKey =  getPrivate();
            Cipher decryptCipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(text);
            return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | IOException | NoSuchAlgorithmException
                | InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            log.info(e);
            return null;
        }
    }

    private PrivateKey getPrivate() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File privateKeyFile = new File("private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    private PublicKey getPublic() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File("public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }
}
