package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.PartyDAO;

@WebServlet("/")
public class PratyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PratyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dopro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dopro(request, response);
	}

	protected void dopro (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getContextPath();
		String command = request.getServletPath();
		System.out.println("context:"+context+", " + "command:"+command);
		//리턴 받아서 파일명으로 바뀔거임
		String site = null;
		
		PartyDAO party = new PartyDAO();
		
		switch(command) {
		case "/home" :
			site = "index.jsp";
			break;
		//누른 값이 view면
		case "/view" :
			//site 값을 view.jsp로 바꿔준다
			site = party.view(request, response);
			break;
		case "/voteadd" :
			site = "voteadd.jsp";
			break;
		case "/voteaddlist":
			int result = party.voteAdd(request, response);
			response.setContentType("text/html; charset= UTF-8");
			PrintWriter out = response.getWriter();
			if (result == 1) {
				out.print("<script>");
				out.print("alert('투표하기 정보가 성공적으로 저장되었습니다.'); location.href= '" + context +  "' ;");
				out.print("</script>");
				out.flush();
			} else {
				out.print("<script>");
				out.print("alert('투표하기 정보 저장 실패!'); location.href= '" + context +  "' ;");
				out.print("</script>");
				out.flush();
			}
			break;
		case "/votescorelist" :
			site = party.voteScoreList(request, response);
			break;
		case "/memberlist" :
			site = party.memberlist(request, response);
			break;
		}
		getServletContext().getRequestDispatcher("/" + site).forward(request, response);
		
	}
}
