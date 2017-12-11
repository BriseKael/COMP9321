package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.JDBC;
import bean.*;

/**
 * Servlet implementation class SearchPageServlet
 */
@WebServlet({"/search", "/result"})
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    if (request.getServletPath().toLowerCase().equals("/search")) {

            request.getRequestDispatcher("/search.jsp").forward(request, response);
        } else if (request.getServletPath().toLowerCase().equals("/result")) {

            String nickname = request.getParameter("nickname");

            List<UserBean> userBeans = JDBC.getUsersByNickName(nickname);
            List<String> relationTypes = new ArrayList<>();
            
            for (UserBean userBean : userBeans) {
                relationTypes.add(JDBC.getUserRelationType(1, userBean.getUserId()));
            }

            request.setAttribute("userBeans", userBeans);
            request.setAttribute("relationTypes", relationTypes);

            String searchPattern = "nickname=" + nickname;

            request.setAttribute("searchPattern", searchPattern);

            request.getRequestDispatcher("/result.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
