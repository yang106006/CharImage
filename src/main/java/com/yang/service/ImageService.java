package com.yang.service;

import java.io.FileInputStream;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	FileInputStream ChangToArrayImage(MultipartFile multipartFile);
}
