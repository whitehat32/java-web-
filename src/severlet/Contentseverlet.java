package severlet;

import java.io.IOException;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DB.myDB;

/**
 * Servlet implementation class Contentseverlet
 */
@WebServlet("/Contentseverlet")
public class Contentseverlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Contentseverlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        int page = 1 , row = 40;
        myDB db = new myDB();
        if(request.getParameter("page") != null) {
        	page = Integer.parseInt(request.getParameter("page"));
        }
        
        if(request.getParameter("row") != null){
        	row = Integer.parseInt(request.getParameter("row"));
        }
        try {
        	  db.getConnection();
              List<Map<String , Object> > mainContents = db.getMainPage((page - 1) * row + 1, row);
              //计算当前页的帖子数量
              request.setAttribute("main", mainContents);
              Long count = db.getMainCount();
              //System.out.println("cnt = " + mainContents.size());
              String pageHtml = db.getPage(count , page , row);
              request.setAttribute("pageHtml", pageHtml);
              db.closeConn();
              getServletContext().getRequestDispatcher("/page.jsp").forward(request , response);
        }
        catch(SQLException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8"); 
		String type = request.getParameter("action");
		if(type.equals("fatie")) {
			HttpSession session = request.getSession();
			String userid = (String)session.getAttribute("id");
			String mainTitle = request.getParameter("mainTitle");
			String content = request.getParameter("content");
			//System.out.println("userid" + userid);
			myDB db = new myDB();
			try {
				db.getConnection();
				int status =  db.getStatus(userid);
				if(status == 1) {
					request.setAttribute("msg" , null);
					if(db.saveContent(content, mainTitle, userid) == 1) {
						doGet(request , response);
					}
					else {
						getServletContext().getRequestDispatcher("/error.jsp").forward(request , response);
					}
				}
				else  {
					request.setAttribute("msg", "您已经被管理员禁言,发帖失败");
					doGet(request , response);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type.equals("huitie")) {
			HttpSession session = request.getSession();
			String userid = (String)session.getAttribute("id");
			String mainTitle = request.getParameter("mainTitle");
			String content = request.getParameter("content");
			String hid = request.getParameter("hid");
			//System.out.println("userid" + userid);
			myDB db = new myDB();
			try {
				db.getConnection();
				int status =  db.getStatus(userid);
				if(status == 1) {
					request.setAttribute("msg" , null);
					if(db.saveHtContent(content, mainTitle, userid , hid) == 1) {
						doGet(request , response);
					}
					else {
						getServletContext().getRequestDispatcher("/error.jsp").forward(request , response);
					}
				}
				else  {
					request.setAttribute("msg", "您已经被管理员禁言");
					doGet(request , response);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else doGet(request , response);
		
	}

}
