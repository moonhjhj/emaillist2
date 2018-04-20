package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.EmaillistDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.EmailVo;

@WebServlet("/el") // 접속받는 곳 주소
public class EmaillistController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("controller");

		String actionName = request.getParameter("a");

		// "form".equals(actionName) =>actionName.equals("form")이면 actionName에서 a값이 없는
		// 경우 오류가 남.
		if ("form".equals(actionName)) {
			// form 관련
			// form.jsp를 web-inf로 넣으면 외부에서 접근은 불가능하지만 내부에서는 접근 가능하게 할 수 있음.(forward는 다
			// 수정해줘야함)
			// RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/form.jsp");

			/************************* static으로 변환 *************************/
			WebUtil.forward(request, response, "/WEB-INF/form/jsp");
			// RequestDispatcher rd = request.getRequestDispatcher("form.jsp");
			// rd.forward(request, response);

		} else if ("insert".equals(actionName)) {
			// insert 관련
			String lastName = request.getParameter("ln");
			String firstName = request.getParameter("fn");
			String email = request.getParameter("email");

			EmailVo vo = new EmailVo(lastName, firstName, email);

			EmaillistDao dao = new EmaillistDao();
			dao.insert(vo);

			// 리스트 리다이렉트
//			response.sendRedirect("/emaillist2/el");// 리스트로 돌아감. url형태여야 함.
			/*********************** static으로 변환(WebUtil.java) ************************/
			WebUtil.redirect(request, response, "/emaillist2/el");

		} else {
			// list 관련
			EmaillistDao dao = new EmaillistDao();
			List<EmailVo> list = dao.getList();

			request.setAttribute("list", list);
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");

			/****************** 외부에서 접속 불가능 *****************/
			// RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");

			/****************** static으로 만들어버림(WebUtil.java) *****************/
			// RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
			// rd.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
