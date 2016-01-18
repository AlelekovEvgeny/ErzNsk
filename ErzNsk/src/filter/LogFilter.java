package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LogFilter implements Filter {

	public void init(FilterConfig filterConfig) {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String[] exsistUrl = new String[] {
				"http://asu-paa:8080/ErzNsk/",
				"http://asu-paa:8080/ErzNsk/index.jsp",
				"http://asu-paa:8080/ErzNsk/login.jsp",
				"http://asu-paa:8080/ErzNsk/readme.jsp",
				"http://asu-paa:8080/ErzNsk/successfull.jsp",
				"http://asu-paa:8080/ErzNsk/un404.jsp",
				"http://asu-paa:8080/ErzNsk/unSuccessfull.jsp",
				"http://asu-paa:8080/ErzNsk/unSuccessfullLogin.jsp",
				"http://asu-paa:8080/ErzNsk/login",
				"http://asu-paa:8080/ErzNsk/logout",
				"http://asu-paa:8080/ErzNsk/loadZu2",
				"http://asu-paa:8080/ErzNsk/loadZp",
				"http://asu-paa:8080/ErzNsk/loadError",
				"http://asu-paa:8080/ErzNsk/loadAppak",
				"http://asu-paa:8080/ErzNsk/message",
				"http://asu-paa:8080/ErzNsk/excelEdit",
				"http://asu-paa:8080/ErzNsk/wsAnswer",
				"http://asu-paa:8080/ErzNsk/progressBar",
				"http://asu-paa:8080/ErzNsk/css/styles.css",
				"http://asu-paa:8080/ErzNsk/script/checkNumber.js",
				"http://asu-paa:8080/ErzNsk/script/checkNumberLogin.js",
				"http://asu-paa:8080/ErzNsk/script/showProgressBar.js",
				"http://asu-paa:8080/ErzNsk/script/checkNumberIndex.js",
				"http://asu-paa:8080/ErzNsk/script/webSocketAnswer.js",
				"http://asu-paa:8080/ErzNsk/script/jquery-1.11.0.min.js",
				"http://asu-paa:8080/ErzNsk/ponomarev.xls",
				"http://asu-paa:8080/ErzNsk/CountFfomsWorkData.xls",
		}; 
		boolean exsist = false;
		for (int i = 0; i < exsistUrl.length; i++) {
			if (exsistUrl[i].equals(((HttpServletRequest) request).getRequestURL().toString())) {
				exsist = true;
			};
		}
		if (!exsist) { request.getRequestDispatcher("/un404.jsp").forward(request, response); }
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
