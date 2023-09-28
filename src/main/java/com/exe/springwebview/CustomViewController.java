package com.exe.springwebview;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomViewController {

	@RequestMapping(value = "/simpleCustomView.action")
	public ModelAndView customView() {
		
		ModelAndView mav = new ModelAndView();
		
		//mav.setViewName("simpleCustomView");view를 jsp파일로 쓴거임
		mav.setView(new SimpleCustomView());//jsp가 아닌 그냥 클래스면 view
		mav.addObject("message","SimpleCustomView Class입니다");
		return mav;
		
	}
	
	@RequestMapping(value = "/pdfView.action")
	public ModelAndView getPdfView() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setView(new CustomPdfView());//jsp가 아닌 그냥 클래스면 view pdf //itext maven으로
		mav.addObject("message","PDF입니다");
		return mav;
		
	}
	
	@RequestMapping(value = "/excelView.action")
	public ModelAndView getExcelView() {//poi/jxl maven으로 다운
		
		ModelAndView mav = new ModelAndView();
		
		mav.setView(new CustomExcelView());//jsp가 아닌 그냥 클래스면 view
		
		return mav;
		
	}
	
	@RequestMapping(value = "/upload.action",method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request) throws Exception {//io, fileupload maven으로 다운

		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/files");
		//C:\sts-bundle\work\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\SpringWebView\WEB-INF
		
		MultipartFile file = request.getFile("upload");
		
		if(file!=null&&file.getSize()>0) {
			
			try {
				
				InputStream is = file.getInputStream();
				
				FileOutputStream fos = new FileOutputStream(path + "/" + file.getOriginalFilename());
				
//				int data;
//				byte[] buffer = new byte[4096];
//				while((data=is.read(buffer,0,buffer.length))!=-1) {
//					
//					fos.write(buffer,0,data);
//					
//				}
				
				FileCopyUtils.copy(is,fos);//is 읽고 fos로 내보내라
				
				is.close();
				fos.close();
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
		
		return "uploadResult";
	}
	
	@RequestMapping(value = "/download.action")
	public ModelAndView download() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setView(new DownloadView());
		
		return mav;
	}
	
}
