package com.yang.controller;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yang.service.ImageService;

@Controller
public class MainController {
	
	@Autowired
	private ImageService imageService;
	
	//转向首页
	@RequestMapping("/")
	public String doFileUpload(){
		System.out.println("111111");
		return "index.jsp";
	}
	
	//上传图片处理后并下载文件
	@RequestMapping("/doDealAndDownLoad")
	public ResponseEntity<byte[]> doChangToCharImage(MultipartFile uploadImage){
		try {
			byte[] body = null;
			
			//读取文件
			FileInputStream textFile = imageService.ChangToArrayImage(uploadImage); 
			if(textFile==null){
				throw new Exception("发生错误");
			}
			//获取文件大小，设置数组的长度
			body = new byte[textFile.available()];
			
			//把文件读取到数组
			textFile.read(body);
			//设置响应头，响应实体
			HttpHeaders headers = new HttpHeaders();
		    headers.add(
		    		"Content-Disposition",
		    		"attchement;filename=" + uploadImage.getOriginalFilename().hashCode()+".txt");
		    HttpStatus statusCode = HttpStatus.OK;
		    ResponseEntity<byte[]> entity = 
		    		new ResponseEntity<byte[]>(body, headers, statusCode);
		    textFile.close();
		    return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}



