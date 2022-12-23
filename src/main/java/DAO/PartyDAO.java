package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.MemberList;
import DTO.View;
import DTO.Vote;
import DTO.VoteScore;

public class PartyDAO {
	//db접속하는 애
	Connection conn = null;
	//명령어를 정의하고, 인수를 전달받고, 명령어를 실행하는데 사용
	//명령어 포맷을 준비하고 ?부분이 인수 역할을 하여 변경함
	//executeQuery() 메소드에 의해 실행됨
	PreparedStatement ps = null;
	//쿼리문이 실행되어 결과로 반환된 데이터들을 관리하는 클래스
	ResultSet rs = null;
	

	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sys1234");
		return con;
	}
	
	public String view (HttpServletRequest request, HttpServletResponse response) {
		ArrayList<View> viewList = new ArrayList<View>();
		try {
			conn = getConnection();
			//member 테이블과 party 테이블 사용해야함.
			String sql ="select m_no, m_name, p_name, DECODE(p_school,'1','고졸' ,'2','석사','학사')p_school, substr(T1.m_jumin,1,6)||'-'||substr(T1.m_jumin,7) m_jumin, m_city, p_tel1 ||'- '|| p_tel2||' - ' || DECODE(p_tel3, '0001', '1111','0002','2222','0003','3333','0004','4444','5555') p_tel1 from TBL_MEMBER_022005 T1 join TBL_PARTY_202005 T2 on(T1.P_code = T2.P_code)";
		
			ps = conn.prepareStatement(sql); //쿼리문 실행 준비
			rs = ps.executeQuery(); //쿼리문 실행
			
			while(rs.next()) {
				View view = new View();
				view.setM_no(rs.getString(1));
				view.setM_name(rs.getString(2));
				view.setP_name(rs.getString(3));
				view.setP_school(rs.getString(4));
				view.setM_jumin(rs.getString(5));
				view.setM_city(rs.getString(6));
				view.setP_tel1(rs.getString(7));
				
				viewList.add(view);
			}
			request.setAttribute("view", viewList);
			conn.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "view.jsp";
	}
	
	public int voteAdd (HttpServletRequest request, HttpServletResponse response) {
		String jumin = request.getParameter("jumin");
		String username = request.getParameter("username");
		String userselect = request.getParameter("userselect");
		String votetime = request.getParameter("votetime");
		String votenum = request.getParameter("votenum");
		String selectradio = request.getParameter("selectradio");
		int result = 0;
		
		try {
			conn = getConnection(); //디비
			String sql = "insert into TNL_VOTE_202005 values (?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, jumin);
			ps.setString(2, username);
			ps.setString(3, userselect);
			ps.setString(4, votetime);
			ps.setString(5, votenum);
			ps.setString(6, selectradio);
			
			result = ps.executeUpdate();
			conn.close();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public String voteScoreList(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<VoteScore> voteScoreList = new ArrayList<VoteScore>();
		
		try {
			conn = getConnection(); 
			String sql = "select v_name , '19'||substr(v_jumin, 1,2)||'년'||substr(v_jumin, 3,2)||'월'||substr(v_jumin,5,2)||'일생' v_jumin, trunc(months_between(sysdate, '19'||substr(v_jumin, 1,2)||'-02-02')/12) as age, case when substr(v_jumin,7 ,1) = 1 then '남자' else '여자' end as gender, m_no, substr(v_time,1, 2) || ':' || substr(v_time,3,2) v_time, case when v_confirm = 'Y' then '확인' else '미확인' end as voter from tnl_vote_202005";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		
		while (rs.next()) {
			VoteScore votescore = new VoteScore();
			
			votescore.setV_name(rs.getString(1));
			votescore.setV_jumin(rs.getString(2));
			votescore.setAge(rs.getString(3));
			votescore.setGender(rs.getString(4));
			votescore.setM_no(rs.getString(5));
			votescore.setV_time(rs.getString(6));
			votescore.setVoter(rs.getString(7));
			
			voteScoreList.add(votescore);
		}
		
		request.setAttribute("voteScoreList", voteScoreList);
			
		conn.close();
		ps.close();
		rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "votescorelist.jsp";
	}
	
	public String memberlist (HttpServletRequest request, HttpServletResponse response) {
			ArrayList<MemberList> memberList = new ArrayList<MemberList>();
		
		try {
			conn = getConnection();
			String sql = "select t1.v_confirm,  t2.m_no ,t2.m_name, count (t1.m_no) votenum from tnl_vote_202005 t1 join tbl_member_022005 t2 on(t1.m_no = t2.m_no) group by t2.m_name ,t2.m_no, t1.m_no, t1.v_confirm having t1.v_confirm = 'Y' order by count (t1.m_no) desc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				MemberList member = new MemberList();
				member.setV_confirm(rs.getString(1));
				member.setM_no(rs.getString(2));
				member.setM_name(rs.getString(3));
				member.setVotenum(rs.getString(4));
				
				memberList.add(member);
			}
			
			request.setAttribute("MemberList", memberList);
			
			conn.close();
			ps.close();
			rs.close();
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "memberlist.jsp";
	}
	

}
