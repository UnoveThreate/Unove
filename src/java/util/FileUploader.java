/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author nguyendacphong
 */
public class FileUploader {

    private final Cloudinary cloudinary;
    private final static String CLOUDINARY_URL = "cloudinary://483224641183916:0o4555j8ukzUqLpQfuzVFjVZFpw@dt7z1abo6";

    public FileUploader() {
        cloudinary = new Cloudinary(CLOUDINARY_URL);// hard-coded url
    }

    public String uploadAndReturnUrl(File uploadFile, String fileName, String uploadFolder) throws IOException {
        String result = "";
        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "public_id", fileName, // Set the desired name for the uploaded image
                "folder", uploadFolder // Specify the nested folder path
        );

        Map uploadResult = cloudinary.uploader().upload(uploadFile, uploadParams);

        result = (String) uploadResult.get("secure_url");
        return result;
    }
}
