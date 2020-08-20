package com.zrq.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConstants;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.w3c.dom.Document;

/**
 * Copyright © 2020 cjkj. All rights reserved.
 * @Description: word文件转换成html
 * @author: zrq
 * @date: 2020年8月20日
 * @version: 1.0
 */
public class WordToHtml {
	
	/**
	 * word2007转换成html文件用于在线浏览
	 * @param path
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String Word2007ToHtml(String path,String file) throws Exception {
		
		String nweHtml = "";
		InputStream is = new FileInputStream(new File(path+file));
		XWPFDocument xdDocument = new XWPFDocument(is);
        if (file.endsWith(".docx") || file.endsWith(".DOCX")) {  
              
        	// 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)  
            File imageFolderFile = new File(path+"\\images\\");  
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));  
            options.setExtractor( new FileImageExtractor(imageFolderFile));  
            options.setIgnoreStylesIfUnused(false);  
            options.setFragment(true);  
            
            //也可以使用字符数组流获取解析的内容,显示在html页面中
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            XHTMLConverter.getInstance().convert(xdDocument, baos, options);  
            String string2 = baos.toString();
            
            //设置HTML样式
            String substring = string2.substring(string2.indexOf("<p"), string2.lastIndexOf("</div>"));
            nweHtml = "<body style='margin:0px auto;width:auto;'><div style='margin:auto;text-align:center;'><div style='margin:0 auto;width:95%;text-align:left;line-height:25px;'>"
            		          +substring+"</div></div></body>";
            baos.close();
            System.out.println(nweHtml);
        } else {  
            System.out.println("Enter only MS Office 2007+ files");  
        }  
		return nweHtml;
	} 
	
	/**
	 * word2003转换成html文件
	 * @param path
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String Word2003ToHtml(String path ,String file) throws Exception {
		
		//创建文件输入流，读取word文件
		InputStream input = new FileInputStream(path + file);
		//使用poi接口实现word文档
		HWPFDocument wordDocument = new HWPFDocument(input);
		
		//word文件转存成html
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
		DocumentBuilderFactory.newInstance().newDocumentBuilder()
		      .newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] content, PictureType pictureType,
		     String suggestedName, float widthInches, float heightInches) {
		    return suggestedName;
		   }
		  });
		wordToHtmlConverter.processDocument(wordDocument);
		//存储word图片
		List pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
		   for (int i = 0; i < pics.size(); i++) {
			   Picture pic = (Picture) pics.get(i);
			   try {
				   pic.writeImageContent(new FileOutputStream(path+"\\image\\"
						   + pic.suggestFullFileName()));
			   } catch (FileNotFoundException e) {
				   e.printStackTrace();
			   }
		   }
		}
		//获取HTML文档
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		//转换成html文件内容
		serializer.transform(domSource, streamResult);
		outStream.close();
		String content = new String(outStream.toByteArray());
		
		//设置HTML的样式
		String substring = content.substring(content.indexOf("<p"), content.lastIndexOf("</body>"));
        String nweHtml = "<body style='margin:0px auto;width:auto;'><div style='font-family:"+"仿宋"+";font-size:14.0pt;margin:auto;text-align:center;'><div style='margin:0 auto;width:95%;text-align:left;line-height:25px;'>"
        		          +substring+"</div></div></body>";
		return nweHtml; 
	}
	
	
	public static void main(String[] args) throws Exception {
		
		final  String path = "C:\\Users\\lenovo\\Desktop\\";
		final  String file = "2_梳理舆情关键词.doc";
		Word2003ToHtml(path,file);
//		Word2007ToHtml(path,file);
	}
	
	

}
