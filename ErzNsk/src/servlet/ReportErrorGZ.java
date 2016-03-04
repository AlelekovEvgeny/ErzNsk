package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/reporterrorgz")
public class ReportErrorGZ extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
		
		// get absolute path
		String path = request.getServletContext().getRealPath("/uploads");
		//System.out.println("absoluteDiskPath "+ absoluteDiskPath);
		
		// we need only one file in this folder (on my rules in this folder only one file-report)
		File doc = new File(path); 
		int i = 0;
		File []f = doc.listFiles();
		if(f.length ==1) 
		{
				path = f[0].getAbsolutePath();
				doc = new File(path);
				ServletOutputStream stream = null;
				BufferedInputStream buf = null;
				try {
					stream = response.getOutputStream();
					
					response.setCharacterEncoding("application/msexcel");
					response.addHeader("Content-Disposition", "attachment; filename=" + path);
					response.setContentLength((int)doc.length());
					FileInputStream input = new FileInputStream(doc);
					buf = new BufferedInputStream(input);
					int readBytes = 0;
					while((readBytes = buf.read()) != -1) { stream.write(readBytes); }
				} catch (IOException ioe) {
					throw new ServletException(ioe.getMessage());
				} finally {
					if(stream != null) { stream.close(); }
					if(buf != null) { buf.close(); }
				}
		}
		else 
		{
			// 
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
