package DAO;

import java.sql.*;
import java.util.*;
import DTO.*;


public class BoardDAO {
	
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
    //데이터 베이스와의 연결
    public Connection open() {
    	Connection conn = null;
    	try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return conn;
    }
    
    //게시판 리스트 가져오기
    public ArrayList<Board> getList() throws Exception {
    	Connection conn = open();
    	ArrayList<Board> boardList = new ArrayList<Board>(); //Board 객체를 저장할 리스트
    	String sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date, views from board";
    	PreparedStatement pstmt = conn.prepareStatement(sql); //쿼리문 컴파일 단계
    	ResultSet rs = pstmt.executeQuery(); //쿼리문 실행 -> 데이터 베이스 결과 저장
    	
    	//리소스 자동 닫기(try - with - resource)
    	
    	try (conn; pstmt; rs) {
    		while(rs.next()) {
    			Board b = new Board();
    			b.setBoard_no(rs.getInt(1));
    			b.setTitle(rs.getString(2));
    			b.setUser_id(rs.getString(3));
    			b.setReg_date(rs.getString(4));
    			b.setViews(rs.getInt(5));
    			
    			boardList.add(b);
    		}
    		
    		return boardList;
    		
    	}
    }
    
    //게시판 내용 가져오기
    public Board getView(int board_no) throws Exception {
    	Connection conn = open();
    	Board b = new Board();
    	
    	String sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date, views, content from board where board_no=?";
    	PreparedStatement pstmt = conn.prepareStatement(sql); //쿼리문 컴파일 단계
    	pstmt.setInt(1, board_no);
    	ResultSet rs = pstmt.executeQuery(); //쿼리문 실행 -> 데이터 베이스 결과 저장
    	
    	try(conn; pstmt; rs) {
    		while(rs.next()) {
    			b.setBoard_no(rs.getInt(1));
    			b.setTitle(rs.getString(2));
    			b.setUser_id(rs.getString(3));
    			b.setReg_date(rs.getString(4));
    			b.setViews(rs.getInt(5));
    			b.setContent(rs.getString(6));
    		}
    		return b;
    	}
    	
    	
    	
    }
    
    //조회수 
    public void updateViews(int board_no) throws Exception {
    	
    	Connection conn = open();
    	
    	String sql = "update board set views = (views + 1) where board_no = ?";
    	PreparedStatement pstmt = conn.prepareStatement(sql); //쿼리문 컴파일 단계
    
    	try (conn; pstmt) {
    		pstmt.setInt(1, board_no);
    		pstmt.executeUpdate();
    	}
    }
    
    
    public void insertBoard(Board b) throws Exception {
    	Connection conn = open();
    	String sql = "insert into board (board_no, user_id, title, content, reg_date, views) values(board_seq.nextval, ?, ?, ?, sysdate, 0)";
   
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	
		try (conn; pstmt) {
				pstmt.setString(1, b.getUser_id());
				pstmt.setString(2, b.getTitle());
				pstmt.setString(3, b.getContent());
				pstmt.executeUpdate();
			}
    }
    
    
}



