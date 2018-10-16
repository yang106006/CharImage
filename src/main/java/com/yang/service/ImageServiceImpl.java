package com.yang.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageServiceImpl implements ImageService {

	@Override
	public FileInputStream ChangToArrayImage(MultipartFile uploadImage) {
		//替换的字符数组
		char[] charArr = { '$', '8', '+', '~', '-', '.' };
		System.out.println("Service");
		try {
			//文本，图片名 同一个UUID
			String uuid = UUID.randomUUID().toString();
			
			//将图片保存在服务器资源目录
			String origImgName = uploadImage.getOriginalFilename().toLowerCase();
			String overImgName = uuid+origImgName.substring(origImgName.lastIndexOf("."));
			
			
			File imgFile = new File("/usr/local/src/img/");
			if(!imgFile.exists()) {
				imgFile.mkdirs();
			}
			uploadImage.transferTo(new File("/usr/local/src/img/"+overImgName));
			
			//转换成字符画的文本路径及名称
			File textFile = new File("/usr/local/src/text/");
			if(!textFile.exists()) {
				textFile.mkdirs();
			}
			OutputStreamWriter outText = 
					new OutputStreamWriter(new FileOutputStream(textFile+uuid+".txt"));
			
			
			//用流读取文本文件，以便返回
			FileInputStream textInputStream = new FileInputStream(textFile);
			//校验图片,不是图片直接结束程序
			BufferedImage bufferedImage = ImageIO.read(uploadImage.getInputStream());

			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			if (height == 0 || width == 0) {
				System.out.println("不是图片");
				return null;
			}

			//利用YUV模型获得符合人眼的灰度值
			for (int y = 0; y < height; y += 10) {
				for (int x = 0; x < width; x += 10) {
					int rgb = bufferedImage.getRGB(x, y);
					int red = rgb >> 16 & 0xFF;
					int green = rgb >> 8 & 0xFF;
					int blue = rgb & 0xFF;
					int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
					
					//设置阈值,大于此值的填空格。
					//阈值越大,转成灰度图时,细节越多.(0-255)
					int threshold = 255;
					if (gray >= threshold) {
						gray = 255;
						outText.write(" "+" ");
						continue;
					}
					outText.write(charArr[gray / (threshold / 6 + 1)] + " ");
				}
				outText.write("\r\n");
			}
			outText.close();
			//返回处理好的文本
			System.out.println("OK");
			return textInputStream;				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
