package study;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ScheduleDAO {
	
	private static final String DRIVER
	="com.mysql.cj.jdbc.Driver";
	private static final String URL
	="jdbc:mysql://localhost:3306/study_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER="root";
	private static final String PASS="root";
	
	
	public Vector select(){
		Vector data=new Vector();
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try{
			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL, USER, PASS);
			
			stmt=conn.createStatement();
			
			String sql="select pk_num,date,note from schedule order by date asc";
			rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				int pk_num=rs.getInt("pk_num");
				String date=rs.getString("date");
				String note=rs.getString("note");
				
				Vector row=new Vector();
				row.add(pk_num);
				row.add(date);
				row.add(note);
				
				data.add(row);
			}
			
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}catch(SQLException e){
			System.out.println(e);
		}finally{
			try{
				if(conn!=null&!conn.isClosed()){
					conn.close();
				}else if(stmt!=null&!stmt.isClosed()){
					stmt.close();
			}
		}catch(SQLException e){
			System.out.println(e);
		}}
		return data;
	}
	
	public int insert(ScheduleDTO sDto){
		
		Connection conn=null;
		PreparedStatement pstmt=null;

		int result = 0;
	
		
		try{

			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL,USER,PASS);
			
			String sql="insert into schedule (date,note) values (?,?)";
			pstmt=conn.prepareStatement(sql);
			System.out.println(1);
			pstmt.setString(1, sDto.getDate());
			System.out.println("2");
			pstmt.setString(2, sDto.getNote());
			System.out.println("3");
			
			result=pstmt.executeUpdate();

		}catch(ClassNotFoundException e){
			System.out.println(e);
		}catch(SQLException e){
			System.out.println(e);
		}finally{
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	
	public int update(ScheduleDTO sDto){
		int result=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
				
		try{
			 
			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL, USER, PASS);
			
			String sql="update schedule set note=? where pk_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, sDto.getNote());
			pstmt.setInt(2, sDto.getPk_num());
			result=pstmt.executeUpdate();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
		
	}
	
	public int deleteDialog(ScheduleDTO sDto){
		int result=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try{
			
			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL, USER, PASS);
			
			String sql="delete from schedule where pk_num=?";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, sDto.getPk_num());
			
			result=pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return result;
	}
}
