package org.demo.shiro;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求转发到登录界面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   //获取参数
	   String username = request.getParameter("username");
	   String password = request.getParameter("password");
	   
	   //获取主题，或者是用户的概念
	   Subject subject = SecurityUtils.getSubject();
	   //创建用户名密码秘钥
	   UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	   
	   String emsg = null;
	   try {
		//登录
		subject.login(token);
	   } catch (UnknownAccountException e) {
		   emsg = "用户名错误";
	   }catch (IncorrectCredentialsException e) {
		   emsg = "用户密码错误";
	   }catch (AuthenticationException e) {
	      emsg = "其他异常："+e.getMessage();
	   }
	   if(emsg != null){
		  request.setAttribute("emsg", emsg);
		  request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	   }else{
		   response.sendRedirect(request.getContextPath()+"/");
	   }
	}

}
