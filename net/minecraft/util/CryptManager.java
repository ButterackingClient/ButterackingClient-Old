/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CryptManager
/*     */ {
/*  31 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey createNewSharedKey() {
/*     */     try {
/*  38 */       KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
/*  39 */       keygenerator.init(128);
/*  40 */       return keygenerator.generateKey();
/*  41 */     } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*  42 */       throw new Error(nosuchalgorithmexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair() {
/*     */     try {
/*  51 */       KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
/*  52 */       keypairgenerator.initialize(1024);
/*  53 */       return keypairgenerator.generateKeyPair();
/*  54 */     } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*  55 */       nosuchalgorithmexception.printStackTrace();
/*  56 */       LOGGER.error("Key pair generation failed!");
/*  57 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) {
/*     */     try {
/*  66 */       return digestOperation("SHA-1", new byte[][] { serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
/*  67 */     } catch (UnsupportedEncodingException unsupportedencodingexception) {
/*  68 */       unsupportedencodingexception.printStackTrace();
/*  69 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] digestOperation(String algorithm, byte[]... data) {
/*     */     try {
/*  78 */       MessageDigest messagedigest = MessageDigest.getInstance(algorithm); byte b; int i;
/*     */       byte[][] arrayOfByte;
/*  80 */       for (i = (arrayOfByte = data).length, b = 0; b < i; ) { byte[] abyte = arrayOfByte[b];
/*  81 */         messagedigest.update(abyte);
/*     */         b++; }
/*     */       
/*  84 */       return messagedigest.digest();
/*  85 */     } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*  86 */       nosuchalgorithmexception.printStackTrace();
/*  87 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKey decodePublicKey(byte[] encodedKey) {
/*     */     try {
/*  96 */       EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
/*  97 */       KeyFactory keyfactory = KeyFactory.getInstance("RSA");
/*  98 */       return keyfactory.generatePublic(encodedkeyspec);
/*  99 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */     
/* 101 */     } catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */ 
/*     */ 
/*     */     
/* 105 */     LOGGER.error("Public key reconstitute failed!");
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted) {
/* 113 */     return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encryptData(Key key, byte[] data) {
/* 120 */     return cipherOperation(1, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptData(Key key, byte[] data) {
/* 127 */     return cipherOperation(2, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] cipherOperation(int opMode, Key key, byte[] data) {
/*     */     try {
/* 135 */       return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
/* 136 */     } catch (IllegalBlockSizeException illegalblocksizeexception) {
/* 137 */       illegalblocksizeexception.printStackTrace();
/* 138 */     } catch (BadPaddingException badpaddingexception) {
/* 139 */       badpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 142 */     LOGGER.error("Cipher data failed!");
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Cipher createTheCipherInstance(int opMode, String transformation, Key key) {
/*     */     try {
/* 151 */       Cipher cipher = Cipher.getInstance(transformation);
/* 152 */       cipher.init(opMode, key);
/* 153 */       return cipher;
/* 154 */     } catch (InvalidKeyException invalidkeyexception) {
/* 155 */       invalidkeyexception.printStackTrace();
/* 156 */     } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/* 157 */       nosuchalgorithmexception.printStackTrace();
/* 158 */     } catch (NoSuchPaddingException nosuchpaddingexception) {
/* 159 */       nosuchpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 162 */     LOGGER.error("Cipher creation failed!");
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cipher createNetCipherInstance(int opMode, Key key) {
/*     */     try {
/* 171 */       Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 172 */       cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
/* 173 */       return cipher;
/* 174 */     } catch (GeneralSecurityException generalsecurityexception) {
/* 175 */       throw new RuntimeException(generalsecurityexception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\CryptManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */