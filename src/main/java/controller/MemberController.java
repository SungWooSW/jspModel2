package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import db.DBConnection;
import dto.MemberDto;
import net.sf.json.JSONObject;

// Servlet이 Controller
@WebServlet("/member")
public class MemberController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	// doGet과 doPost를 하나의 doProc()으로 사용한다.
	public void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBConnection.initConnection();
		req.setCharacterEncoding("utf-8"); // post의 방식으로 보내는 값이 '한글'의 경우 반드시 인코딩을 해줘야 한다.
		
		// 다른 jsp 파일들에서 넘겨받은 param
		String param = req.getParameter("param");
		
		// 넘겨받은 param의 값에 따라 jsp 파일 간 이동!
		if(param.equals("login")) { // 로그인 화면
			resp.sendRedirect("login.jsp");
		} 
		else if(param.equals("regi")) { // 회원가입 화면
			resp.sendRedirect("regi.jsp");
		} 
		else if(param.equals("idcheck")) { // id확인 화면
			String id = req.getParameter("id"); 
			
			// DB에 접근
			MemberDao dao = MemberDao.getInstance();
			boolean b = dao.getId(id);
			// dao.getId(id)의 반환값이 true면 이미 사용중인 아이디가 있다는 것
			
			// 다시 전송해줄 문자열
			String str = "NO";
			if( b == false ) {
				str = "YES"; // id 사용이 가능하다.(사용중인 id가 없음)
			}
			
			JSONObject obj = new JSONObject(); // JSON 객체를 생성
			// JSON 객체에 데이터(str) 저장
			obj.put("str", str);  // obj.put("name", value)
			
			resp.setContentType("application/x-json;charset=utf-8"); // 화면에 나타나는 문자가 깨지지 않도록
			resp.getWriter().print(obj); // 스트림에 텍스트를 기록하고자 함
		}
		else if(param.equals("regiAf")) {	// 회원가입
			
			// parameter 값
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			
			System.out.println(id + " " + pwd + " " + name + " " + email);
			
			// db에 저장
			MemberDao dao = MemberDao.getInstance();
			boolean isS = dao.addMember(new MemberDto(id, pwd, name, email, 0));
			
			String message = "";
			if(isS) {
				message = "MEMBER_YES";
			} else {
				message = "MEMBER_NO";
			}
			req.setAttribute("message", message);
//			req.getRequestDispatcher("message").forward(req, resp);
			forward("message.jsp", req, resp);
		}
		else if(param.equals("loginAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			MemberDao dao = MemberDao.getInstance();
			MemberDto login = dao.login(id, pwd);
			
			// session에 저장
		
			if(login != null) {
				req.getSession().setAttribute("login", login);
				resp.sendRedirect("bbs?param=bbslist"); // MemberController에서 BbsController로 보냄
			}
		}
		
	}
	
	public void forward(String linkName, HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher dispatcher = req.getRequestDispatcher(linkName);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
