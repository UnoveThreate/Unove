/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Random;
import javax.servlet.ServletException;

/**
 *
 * @author PC
 */
public class Util {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String QR_CODE_IMAGE_PATH = "/tmp/qrcode.png";

    public static String getRanDom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String generateActivationCodeOrder() {

        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static String generateQRCodeAndUpload(String qrCodeText, String fileName, String uploadFolder) throws IOException, ServletException {
        try {
            // Generate QR code and save it as an image file
            generateQRCodeImage(qrCodeText, 250, 250, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            throw new ServletException(e);
        }

        // Upload the image to the cloud and get the URL
        FileUploader cloudinaryUploader = new FileUploader();
        File qrCodeFile = new File(QR_CODE_IMAGE_PATH);
        return cloudinaryUploader.uploadAndReturnUrl(qrCodeFile, fileName, uploadFolder);
    }

    /**
     * Checks if the provided string is null or empty.
     *
     * @param str the string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Hashes the password using SHA-256.
     *
     * @param password the plaintext password
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }
}
