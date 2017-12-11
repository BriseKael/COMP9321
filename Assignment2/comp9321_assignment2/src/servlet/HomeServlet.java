package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.*;
import database.*;

/**
 * Servlet implementation class HomePageServlet
 */
@WebServlet("/homepage")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // upload folder
    // already setted. under the same folder with the webapp
    private static final String UPLOAD_DIRECTORY = "comp9321_ass2_upload";

    // upload size
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String action = request.getParameter("action");

        if (action == null || action.equals("")) {
            // handle notification
            List<NotificationBean> notificationBeans = new ArrayList<>();
            notificationBeans = JDBC.getNotificationsBySubjectiveUserId(1);

            request.setAttribute("notificationBeans", notificationBeans);
            
            // handle wall post
            List<PostBean> postBeans = new ArrayList<>();
            postBeans = JDBC.getFriendPostsByUserId(1);
            
            
            request.setAttribute("postBeans", postBeans);
            
            // handle nicknames 
            List<String> nicknames = new ArrayList<>();
            for (PostBean postBean : postBeans)
            {
                String nickname = JDBC.getUser(postBean.getAuthorId()).getNickname();
                nicknames.add(nickname);
            }
            request.setAttribute("nicknames", nicknames);
            

            // forward to homepage
            request.getRequestDispatcher("/homepage.jsp").forward(request, response);

        } else if (action.equals("sendMessage")) {

            if (!ServletFileUpload.isMultipartContent(request)) {
                PrintWriter writer = response.getWriter();
                writer.println("Error: form must contain enctype=multipart/form-data");
                writer.flush();
                return;
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);

            // maximum file size
            upload.setFileSizeMax(MAX_FILE_SIZE);

            // maximum request size
            upload.setSizeMax(MAX_REQUEST_SIZE);

            // handle chinese character
            upload.setHeaderEncoding("UTF-8");

            // realpath is the realpath for webapp.
            // get it parent, and that is the upload path and folder
            String realPath = request.getServletContext().getRealPath("./");
            String uploadPath = new File(realPath).getParent() + File.separator + UPLOAD_DIRECTORY;

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            try {
                List<FileItem> formItems = upload.parseRequest(request);

                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            String fileName = new File(item.getName()).getName();
                            String filePath = uploadPath + File.separator + fileName;
                            File storeFile = new File(filePath);

                            System.out.println(fileName + "\n" + filePath);

                            item.write(storeFile);
//                            request.setAttribute("filename", fileName);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }

            // forward to homepage
//            request.getRequestDispatcher("/homepage.jsp").forward(request, response);
            response.sendRedirect("./homepage");
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
