package com.brightlightshop.client4.utils;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryUploader {
    private final Cloudinary cloudinary;

    public CloudinaryUploader() {
        cloudinary = new Cloudinary("cloudinary://138494379945565:MAMUSzniQXenEExILoIwuzxzik0@brightlightshopoop");
    }

    public String uploadImage(File file, String publicId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", publicId));
        return (String) uploadResult.get("secure_url");
    }



}
