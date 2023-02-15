package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import dao.MemberDao;
import db.DBConnection;
import dto.BbsDto;
import dto.MemberDto;
import net.sf.json.JSONObject;

// Servlet이 Controller
@WebServlet("/bbs")
public class BbsController extends HttpServlet{

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
		
		String param = req.getParameter("param");
		
		// MemberController에서 param=bbslist로 BbsController로 값을 넘겨줌(이동)
		// bbslist.jsp에서 검색 버튼을 눌렀을 때 choice, search 값을 넘겨줌(이동)
		if(param.equals("bbslist")) { 
			
			// bbslist.jsp에서 검색 버튼 부분
			String choice = req.getParameter("choice");
			String search = req.getParameter("search");
			if(choice == null || choice.equals("") || search == null) {
				choice="";
				search="";
			}
			
			BbsDao dao = BbsDao.getInstance();
			
//			// 글의 목록
//			List<BbsDto> list = dao.getBbsSearchList(choice, search);
			
			// 글의 총 수
			int count = dao.getAllBbs(choice, search);
			
			// 페이지의 총 수
			int pageBbs = count / 10;         // 10개씩 글이 나타나도록
			if( (count % 10) > 0 ){			  // 총 32개의 글이면, 2가 남으므로 
				pageBbs = pageBbs + 1;	      // pageBbs는 3 + 1 = 4가 된다.
			}
			// 현재 페이지 번호
			String sPageNumber = req.getParameter("pageNumber");
			int pageNumber = 0;
			if(sPageNumber != null && sPageNumber.equals("") == false){
				pageNumber = Integer.parseInt(sPageNumber);
			}
			// 글의 목록
			List<BbsDto> list = dao.getBbsPageList(choice, search,pageNumber);
			
			// 보내줄 값들
			req.setAttribute("pageNumber", pageNumber);
			req.setAttribute("pageBbs", pageBbs);
			req.setAttribute("bbslist", list);
			req.setAttribute("choice", choice);
			req.setAttribute("search", search);
			
			// 보내준다.
			forward("bbslist.jsp", req, resp);
		} 
		else if(param.equals("bbswrite")) {
			resp.sendRedirect("bbswrite.jsp");
		}
		else if(param.equals("bbswriteAf")) {
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			boolean isS = BbsDao.getInstance().writeBbs(new BbsDto(id, title, content));
			String bbswrite = "";
			if(isS) {
				bbswrite = "BBS_ADD_OK";
			} else {
				bbswrite = "BBS_ADD_NO";
			}
			
			req.setAttribute("bbswrite", bbswrite);
			forward("message.jsp", req, resp);
		}
		else if(param.equals("bbsdetail")) { // bbslist.jsp에서 제목(<a>)을 클릭
			int seq = Integer.parseInt(req.getParameter("seq"));
			BbsDao dao = BbsDao.getInstance();
			// 조회수 증가
			dao.readcount(seq);
			
			// seq에 따른 글에 대한 정보를 담은 객체 dto 생성 by getBbs 메서드
			BbsDto dto = dao.getBbs(seq);
			
			req.setAttribute("bbsdto", dto);
			forward("bbsdetail.jsp", req, resp);
		}
		else if(param.equals("answer")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			BbsDao dao = BbsDao.getInstance();
			// seq에 따른 글에 대한 정보를 담은 객체 dto 생성 by getBbs 메서드
			BbsDto dto = dao.getBbs(seq);
			
			req.setAttribute("bbsdto", dto);
			forward("answer.jsp", req, resp);
			
		}
		else if(param.equals("answerAf")) {
			int seq = Integer.parseInt( req.getParameter("seq") );
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao dao = BbsDao.getInstance();
			boolean isS = dao.answer(seq, new BbsDto(id, title, content));
			String answer = "BBS_ANSWER_OK";
			if(!isS) {				
				answer = "BBS_ANSWER_NO";
			}
			
			req.setAttribute("seq", seq);		
			req.setAttribute("answer", answer);			
			forward("message.jsp", req, resp);
		}
		else if(param.equals("bbsupdate")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDao dao = BbsDao.getInstance();
			BbsDto dto = dao.getBbs(seq);

			req.setAttribute("bbsdto", dto);
			forward("bbsupdate.jsp", req, resp);
			
		}
		else if(param.equals("bbsupdateAf")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao dao = BbsDao.getInstance();
			boolean isS = dao.updateBbs(seq, title, content);
			String updateBbs = "BBS_UPDATE_OK";
			if(!isS) {
				updateBbs = "BBS_UPDATE_NO";
			}
			
			req.setAttribute("seq", seq);		
			req.setAttribute("updateBbs", updateBbs);
			forward("message.jsp", req, resp);
		}
		else if(param.equals("bbsdelete")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			BbsDao dao = BbsDao.getInstance();
			boolean isS = dao.deleteBbs(seq);
			String deleteBbs = "BBS_DELETE_OK";
			if(!isS) {
				deleteBbs = "BBS_DELETE_NO";
			}
			
			req.setAttribute("deleteBbs", deleteBbs);
			forward("message.jsp", req, resp);
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
