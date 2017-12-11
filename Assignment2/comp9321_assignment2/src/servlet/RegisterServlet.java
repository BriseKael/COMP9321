package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ServiceException;
import service.RegisterValidateService;
import bean.UserModel;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("-----r----" + action);
		RegisterValidateService service = new RegisterValidateService();
		if ("activate".equals(action)) {
			// 激活
			String email = request.getParameter("email");// 获取email
			String validateCode = request.getParameter("validateCode");// 激活码

			try {
				// 调用激活方法
				service.processActivate(email, validateCode);
				request.setAttribute("text", "激活成功！");
				request.getRequestDispatcher("/index.jsp").forward(request,
						response);
			} catch (ServiceException | ParseException e) {
				request.setAttribute("text", e.getMessage());
				request.getRequestDispatcher("/index.jsp").forward(request,
						response);
			}
		}else {
			request.setAttribute("text", action+" 不存在");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("-----r----" + action);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		System.out.println(basePath);

		if ("register".equals(action)) {
			// 注册
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String nickname = request.getParameter("nickname");
			String password = request.getParameter("password");
			UserModel userModel = new UserModel();
			userModel.setEmail(email);
			userModel.setName(username);
			userModel.setPassword(password);
			userModel.setRegisterTime(new Date());
			RegisterValidateService service = new RegisterValidateService();
			service.processregister(userModel, basePath);// 发邮箱激活
			request.setAttribute("text", "注册成功,请查看邮件进行激活！");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}else {
			request.setAttribute("text", action+" 不存在");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}
	}

}
