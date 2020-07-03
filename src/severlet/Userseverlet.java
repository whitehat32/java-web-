package severlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.myDB;
/**
 * Servlet implementation class Userseverlet
 */
@WebServlet("/Userseverlet")
public class Userseverlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Userseverlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String pid = (String) request.getParameter("pid");
		myDB db = new myDB();
		if(pid.equals("mgt")) {
			try {
				db.getConnection();
				List<Map<String , String>> userlist = db.getUserList();
				request.setAttribute("userlist", userlist);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/userList.jsp").forward(request, response);
		}
		else if(pid.equals("jin")) {
			String uid = (String) request.getParameter("uid");
			try {
				db.getConnection();
				String sql_ex = "update user set status = 0 where " + "id = " + "'" +uid + "'"; 
				db.executeSQL(sql_ex);
				List<Map<String , String>> userlist = db.getUserList();
				request.setAttribute("userlist", userlist);
				getServletContext().getRequestDispatcher("/userList.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(pid.equals("jjin")) {
			String uid = (String) request.getParameter("uid");
			try {
				db.getConnection();
				String sql_ex = "update user set status = 1 where " + "id = " + "'" +uid + "'"; 
				
				db.executeSQL(sql_ex);
				List<Map<String , String>> userlist = db.getUserList();
				request.setAttribute("userlist", userlist);
				getServletContext().getRequestDispatcher("/userList.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(pid.equals("shan")) {
			String uid = (String) request.getParameter("uid");
			try {
				db.getConnection();
				String sql_ex = "delete from user where " + "id = " + "'" +uid + "'"; 
				db.executeSQL(sql_ex);
				List<Map<String , String>> userlist = db.getUserList();
				request.setAttribute("userlist", userlist);
				getServletContext().getRequestDispatcher("/userList.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(pid.equals("shantie")) {
			String uid = (String) request.getParameter("uid");
			try {
				db.getConnection();
				String sql_ex = "delete from message where " + "id = " + "'" +uid + "'"; 
				db.executeSQL(sql_ex);
				getServletContext().getRequestDispatcher("/Contentseverlet").forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
		
			try {
				db.getConnection();
				List<Map<String , String>> subpage = db.getSubPage(pid);
				
				request.setAttribute("subpage", subpage);
				getServletContext().getRequestDispatcher("/userp.jsp").forward(request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(pid);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
