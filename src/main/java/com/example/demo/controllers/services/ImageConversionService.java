package com.example.demo.controllers.services;

import java.util.Base64;

public class ImageConversionService {
	
	public static String convertToBase64(byte[] blob) {
		return Base64.getEncoder().encodeToString(blob);
	}
	
	public static byte[] convertToBlob(String base64Photo) {
		return Base64.getDecoder().decode(base64Photo);
	}
}