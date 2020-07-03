package severlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.*;

import DB.myDB;

/**
 * Servlet implementation class Myseverlet
 */
@WebServlet("/myseverlet")
public class Myseverlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Myseverlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8"); 
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Contentseverlet");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String url = "/index.jsp";
		String type = request.getParameter("action");
		String id = "";
		
		request.setCharacterEncoding("UTF-8"); 
		if(type.equals("login")) {
			id = request.getParameter("id");
			String password = request.getParameter("password");
			myDB mdb = new myDB();
			try {
				mdb.getConnection();
				if(mdb.checkLogin(id, password)) {
					url = "/Contentseverlet";
					HttpSession session = request.getSession();
					session.setAttribute("name", mdb.getName());
					session.setAttribute("id", id);
					if(mdb.getUserType().equals("admin")) {
						session.setAttribute("flag", 1);//表示是否有管理员权限
					}
				}
				else {
					HttpSession session = request.getSession();
					request.setAttribute("msg", "<script>alert(\"您登录失败\")</script>");
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			//System.out.println("id = " + id + "password = " + password);
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("register")) {
			//request.setCharacterEncoding("UTF-8"); 
			String password = request.getParameter("password");
			String sid = request.getParameter("sid");
			String classname = new String(request.getParameter("classname").getBytes("ISO-8859-1") , "utf-8");
			String username =new String(request.getParameter("username").getBytes("ISO-8859-1") , "utf-8");
			myDB db = new myDB();
			try {
				db.getConnection();
				int flag = 0;
				if(db.countUser(sid) == 0) {
					//System.out.println("ok");
					flag = db.insertUser(password , sid , classname , username);
					if(flag == 1)request.setAttribute("msg","<script>alert(\"注册成功，您可以用账号登录了\")</script>");
				}
				if(flag == 0){
					request.setAttribute("msg", "<script>alert(\"注册失败,您的学生号可能已被注册\")</script>");
				}
				db.closeConn();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}

}
