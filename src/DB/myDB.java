	package DB;
	
	import java.sql.*;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	
	public class myDB {
		private Connection conn;
		private String name , id , usertype;
		public myDB() {
			conn = null;
		}
		public void getConnection() throws ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			String sqlurl = "jdbc:mysql://127.0.0.1:3306/webdb?characterEncoding=utf8&amp;useSSL=false";
			String user = "root";
			String ps = "123456";
			conn = DriverManager.getConnection(sqlurl , user , ps );
		}
		
		public boolean checkLogin(String id , String password) throws SQLException {
			Statement stmt = conn.createStatement();
			String sqlq = "select * from user where id=\"" + id + "\"and password=\"" + password + "\"";
			ResultSet rs = stmt.executeQuery(sqlq);
			boolean t = rs.next();
			if(t) {
				name = rs.getString("username");
				usertype = rs.getString("usertype");
			}
			rs.close();	
			stmt.close();
			conn.close();
			return t;
		}
		public String getName() {
			return name;
		}
		public String getUserType() {
			return usertype;
		}
		public int getStatus(String id) throws SQLException {
			Statement stmt = conn.createStatement();
			String sqlq = "select status from user where " + "id = " + "'" + id + "'";
			ResultSet rs = stmt.executeQuery(sqlq);
			if(rs.next()) {
				return rs.getInt(1);
			}
			else return 0;
		}
		
		public int saveContent(String content , String title , String userid) throws SQLException {
			Statement stmt = conn.createStatement();
			String sqlq = "select * from message";
			ResultSet rs = stmt.executeQuery(sqlq);
			int num = 1;
			while(rs.next()) {
				if(rs.getInt("id") != num)break;
				else num++;
			}
			String sql_save = "insert into message"
			+ "(id , title , userid , content , createtime)"
			+ "values(? , ? , ? , ? , ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql_save);
			pstmt.setString(1 , "000" + num);
			pstmt.setString(2, title);
			pstmt.setString(3, userid);
			pstmt.setString(4, content);
			Timestamp now = new Timestamp((new Date()).getTime());
			pstmt.setTimestamp(5, now);
			int t =  pstmt.executeUpdate();
			conn.close();
			pstmt.close();
			stmt.close();
			rs.close();
			return t;
		}
		
		public int saveHtContent(String content , String title , String userid , String hid) throws SQLException {
			Statement stmt = conn.createStatement();
			String sqlq = "select * from message";
			ResultSet rs = stmt.executeQuery(sqlq);
			int num = 1;
			while(rs.next()) {
				if(rs.getInt("id") != num)break;
				else num++;
			}
			String sql_save = "insert into message"
			+ "(id , title , userid , content , createtime)"
			+ "values(? , ? , ? , ? , ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql_save);
			pstmt.setString(1 , "000" + num);
			pstmt.setString(2, title);
			pstmt.setString(3, userid);
			pstmt.setString(4, content);
			Timestamp now = new Timestamp((new Date()).getTime());
			pstmt.setTimestamp(5, now);
			int t =  pstmt.executeUpdate();
			pstmt.close();
			sql_save = "insert into ht (id,hid) values(?,?)";
			pstmt = conn.prepareStatement(sql_save);
			pstmt.setString(1 , "000" + num);
			pstmt.setString(2, hid);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
			stmt.close();
			rs.close();
			return t;
		}
		
		public List<Map<String , String> > getUserList() throws SQLException {
			List records = new ArrayList();
			String sql_select = "select * from user";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_select);
			rs.last();
			int x = rs.getRow();
			ResultSetMetaData rsmd = rs.getMetaData();  
	        int fcnt = rsmd.getColumnCount();  
			Map<String, String> valueMap = null;
			if(rs.absolute(1)) {
				for(int i = 1 ; i <= x ; i++) {
					valueMap = new HashMap();
					for(int j = 1 ; j <= fcnt ; j++) {
						String colName = rsmd.getColumnName(j);
						String value = rs.getString(j);
						valueMap.put(colName , value);
					}
					records.add(valueMap);
					if(!rs.next())break;
				}
			}
			stmt.close();
			rs.close();
			return records;
		}
		
		public List<Map<String , Object> > getMainPage(int row , int offset) throws SQLException {
			List records = new ArrayList();
			String sql_select = "select message.id as id, username , title , message.createtime , userid as createtime from message , user where message.userid=user.id order by message.createtime";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_select);
			rs.last();
			int x = rs.getRow();
			ResultSetMetaData rsmd = rs.getMetaData();  
	        int fcnt = rsmd.getColumnCount();  
			if(row < 1 || row > x) {
				System.out.println("row = " + row);
				return records;
				
			}
			Map<String, String> valueMap = null;
			if(row + offset > x) {
				offset = x - row + 1;
			}
			if(rs.absolute(row)) {
				for(int i = row ; i < row + offset ; i++) {
					valueMap = new HashMap();
					for(int j = 1 ; j <= fcnt ; j++) {
						String colName = rsmd.getColumnName(j);
						String value = rs.getString(j);
						valueMap.put(colName , value);
					}
					records.add(valueMap);
					if(!rs.next())break;
				}
			}
			stmt.close();
			rs.close();
			return records;
		}
		public List<Map<String , String>> getSubPage(String pid) throws SQLException {
			//System.out.println("pid=" + pid);
			String sql_select = "select username , title , content , DATE_FORMAT(message.createtime , '%Y年%m月%d日  %h点%i分%s秒')"
					 			+"as sec_createtime from message , user where message.id = '" 
					 			+ pid + "'" + "and message.userid=user.id";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_select);
			ResultSetMetaData rsmd = rs.getMetaData();  
	        int fcnt = rsmd.getColumnCount();  
	        List records = new ArrayList();
	        Map<String, String> valueMap = null;
	        if(!rs.next()) {
	        	//System.out.println("!!!");
	        	rs.close();
	        	return records;
	        }
	        //System.out.println("????");
	        valueMap = new HashMap();
			for(int j = 1 ; j <= fcnt ; j++) {
				String colName = rsmd.getColumnName(j);
				String value = rs.getString(j);
				valueMap.put(colName , value);
			}
			valueMap.put("id" , pid);
			records.add(valueMap);
			rs.close();
			stmt.close();
			sql_select = "select username , title , content , DATE_FORMAT(message.createtime , '%Y年%m月%d日  %h点%i分%s秒')"
		 			+"as sec_createtime from message , ht , user where ht.hid = '" + pid + "'" + 
					" and message.id=ht.id" + " and message.userid=user.id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql_select);
			rsmd = rs.getMetaData(); 
			while(rs.next()) {
				valueMap = new HashMap();
				for(int j = 1 ; j <= fcnt ; j++) {
					String colName = rsmd.getColumnName(j);
					String value = rs.getString(j);
					valueMap.put(colName , value);
				}
				records.add(valueMap);
			}
			rs.close();
			stmt.close();
			return records;
		}
		public Long getMainCount() throws SQLException {
			String sql_select = "select count(id) as count from message";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_select);
			if(rs.next()) {
				Long t = rs.getLong("count");
				stmt.close();
				rs.close();
				return t;
			}
			else return (long) 0;
		}
		public void closeConn() throws SQLException {
			conn.close();
		}
		public String getPage (Long count,Integer currentPage,Integer offset){
			//数据
			Long currentLong = Long.parseLong(currentPage+"");
			Long countPage = 0L;
			//这里计算总页数
			if(count%offset!=0){
				countPage = count/offset+1; 
			}else{
				countPage = count/offset;
			}
			//使用StringBuffer拼接字符串
			StringBuffer sb = new StringBuffer();
			//前一页判断，判断当前页数大于1则存在前一页，否则不存在前一页
			if (currentPage> 1){
				sb.append("<span class=\"page\"> <a href=\"?page="+(currentPage-1));
				sb.append("\"> «</a> </span> ");
			}else{
				sb.append("<span class=\"page\"> <a href=\"?page=1");
				sb.append("\"> «</a> </span> ");
			}
			sb.append("<span class=\"page\" style=\"width: 50px !important;\"> ");
			sb.append("<a href=\"?page=1");
			sb.append("\"> start</a> ");
			sb.append("</span> ");
			
			//中间页数导航，中间最多显示5页，这里的计算有些复杂，判断了三次
			//第一次判断总页数减去当前页数加1大于等于5，证明向后存在5页
			//假设我们当前页数为2，那么我们中间导航显示为2、3、4、5、6
			if ((countPage-currentLong+1) >=5){
				for (Long i = currentLong ; i<currentPage+5;i++){
					sb.append("<span class=\"page\"> ");
					sb.append("<a href=\"?page="+i);
					sb.append("\"> "+i+"</a> ");
					sb.append("</span> ");
				}
			//第二次判断，基于上一次的判断不成立，那么证明当前页数向后不足5页
			//这时候判断总页数减4，判断中间导航是否能够支撑5页，假设总页数为10
			//当前页数为7，7向后不足5页，那么判断总页数是否够支撑5页，用总页数-4
			//如果够5页，那么得出一个结论是当前页数向后不够5页，总页数大于或等于5页
			//当前页数包含在最后5页，那么中间导航显示的就是6、7、8、9、10
			}else if (countPage-4 >  0){
				for (long i = countPage-4 ; i<= countPage;i++){
					sb.append("<span class=\"page\"> ");
					sb.append("<a href=\"?page="+i);
					sb.append("\"> "+i+"</a> ");
					sb.append("</span> ");
				}
			//经过上面两轮的判断，可以直接得出结论，总页数不足支撑5页
			//那么从1开始到总页数结束
			}else{
				for (long i = 1 ; i<= countPage;i++){
					sb.append("<span class=\"page\"> ");
					sb.append("<a href=\"?page="+i);
					sb.append("\"> "+i+"</a> ");
					sb.append("</span> ");
				}
			}
			//判断最后一页，最后一页等于总页数，这里只要判断是否存在1页，不存在最后一页设为1
			sb.append("<span class=\"page\" style=\"width: 40px !important;\"> ");
			sb.append("<a href=\"?page="+(countPage==0?1:countPage));
			sb.append("\"> end</a> ");
			sb.append("</span> ");
			//判断是否存在下一页，当前页数小于总页数，那么存在最后一页
			if (currentLong<countPage){
				sb.append("<span class=\"page\"> ");
				sb.append("<a href=\"?page="+currentLong+1);
				sb.append("\"> »</a> ");
				sb.append("</span> ");
			}else{
				sb.append("<span class=\"page\"> ");
				sb.append("<a href=\"?page="+currentLong);
				sb.append("\"> »</a> ");
				sb.append("</span> ");
			}
			//输出总页数
			sb.append("<span> ");
			sb.append("共"+countPage+"页");
			sb.append("</span> ");
			
			return sb.toString();
		}
		public Long countUser(String sid) throws SQLException {
			String sql_select = "select count(id) as count from user where id = " + "'" + sid + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql_select);
			if(!rs.next())return (long) 0;
			Long t = rs.getLong("count");
			//System.out.println("t = " + t);
			stmt.close();
			rs.close();
			return t;
		}
		public int insertUser(String password , String sid , String classname , String username) throws SQLException {
			String sql_save = "insert into user"
					+ "(id , password , classname , username , status , createtime , usertype)"
					+ "values(? , ? , ? , ? , ? , ? , ? )";
				PreparedStatement pstmt = conn.prepareStatement(sql_save);
				pstmt.setString(1 , sid);
				pstmt.setString(2, password);
				pstmt.setString(3, classname);
				pstmt.setString(4, username);
				pstmt.setInt(5, 1);
				Timestamp now = new Timestamp((new Date()).getTime());
				pstmt.setTimestamp(6, now);
				pstmt.setString(7, "normal");
				int t = pstmt.executeUpdate();
				pstmt.close();
				return t;
		}
		public int executeSQL(String sql_ex) throws SQLException {
			Statement stmt = conn.createStatement();
			return stmt.executeUpdate(sql_ex);
		}
	}
