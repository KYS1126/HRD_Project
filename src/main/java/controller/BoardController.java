package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import DAO.BoardDAO;
import DTO.Board;

@WebServlet("/")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardDAO dao;
	private ServletContext ctx;
	
	
    //처음에 서블릿이 생성될떄 딱 한번 생성된다.
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//init은 서블렛 객체 생성시 딱 한번만 실행하므로 객체를 한번만 생성해 공유할수있다.
		dao = new BoardDAO();
		ctx = getServletContext(); //웹 어플리케이션 자원 관리
	}
    
    
	public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //한글 깨짐 방지
		doPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //한글 깨짐 방지
		doPro(request, response);
	}
	
	protected void doPro (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getContextPath();
		String command = request.getServletPath();
		String site = null;
		
		//경로 라우팅(경로를 찾아줌)
		
		switch (command) {
		case "/list" :
			site = getList(request);
			break;
		case "/view" :
			site = getView(request);
			break;
		case "/write": //글쓰기 화면을 보여줌
			site = "write.jsp";
			break;
		case "/insert": //insert 기능
			site = insertBoard(request);
			break;
		}
		
		/*
		 redirect : URL의 변화가 O(), 객체의 재사용성 X(request,response객체), 새로고침시 정보가 사라짐
		 		- DB에 변화가 생기는 요청에 사용(글쓰기, 회원가입 등) insert, update, delete 조심
		 
		 forward : URL의 변화가 X(보안),객체의 재사용성 O(request,response객체), 새로고침 해도 정보가 남아있음
		 		- 단순 조회(리스트 보기, 검색) select
		 */
		
		if(site.startsWith("redirect:/")) {  //redirect - 
			String rview = site.substring("redirect:/".length());
			System.out.println(rview);
			response.sendRedirect(rview);
		}else { //forward
			ctx.getRequestDispatcher("/"+site).forward(request, response);
		}
		
	}
		
		
	
	public String getList(HttpServletRequest request) {
		List<Board> list;
		
		try {
			list = dao.getList();
			request.setAttribute("boardList", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("게시판 목록 생성 과정에서 문제 발생");
			//사용자 한테 보여주기 위해 저장
			request.setAttribute("error", "게시판 목록이 정상적으로 처리되지 않았습니다.");
		}
		
		return "index.jsp";
	}
	
	
	public String getView(HttpServletRequest request) {
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		
		try {
			Board b = dao.getView(board_no);
			request.setAttribute("board", b);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("게시글을 가져오는 과정에서 문제 발생");
			//사용자 한테 보여주기 위해 저장
			request.setAttribute("error", "게시글을 정상적으로 처리되지 않았습니다.");
		}
		
		return "view.jsp";
	}
	
	public String insertBoard (HttpServletRequest request) {
		Board b = new Board();
		
		/*
		b.setUser_id(request.getParameter("user_id"));
		request.getParameter("title");
		request.getParameter("content");
		*/
		
		try {
			BeanUtils.populate(b, request.getParameterMap());
			dao.insertBoard(b);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("추가 과정에서 문제 발생");
			request.setAttribute("error", "게시글을 정상적으로 등록되지 않았습니다.");
			return getList(request);
		}
		
		return "redirect:/list";
		
	}
	
}



