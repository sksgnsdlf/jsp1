package com.dev.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dev.dao.ConnectionManager;
import com.dev.dao.LoginDAO;
import com.dev.dao.MemberDAO;
import com.dev.vo.MemberVO;

public class MemberService {

	private static MemberService service = new MemberService();
	public MemberDAO dao = MemberDAO.getInstance();

	private MemberService() {
	}

	public static MemberService getInstance() {
		return service;
	}

	// 회원등록
	public void memberInsert(MemberVO member) {
		//트랜잭션처리
		Connection conn= ConnectionManager.getConnnect();
		try {
			conn.setAutoCommit(false);
			//member 등록
			dao.memberInsert(conn, member);
			//login 등록
			LoginDAO.getInstance().loginInsert(conn, member);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.close(conn);
		}
	}

	public MemberVO memberSearch(String id) {
		Connection conn = ConnectionManager.getConnnect();
		MemberVO member = null ;
		try {
			conn.setAutoCommit(false);
			member = dao.memberSearch(conn, id);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionManager.close(conn);
		}
		return member;
	}

	public void memberUpdate(MemberVO member) {
		//트랜잭션 처리
		Connection conn = ConnectionManager.getConnnect();
		try {
			conn.setAutoCommit(false);
			dao.memberUpdate(conn, member);	
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn);
		}
	}

	public void memberDelete(String id) {
		Connection conn= ConnectionManager.getConnnect();
		
		try {
			conn.setAutoCommit(false);
			dao.memberDelete(conn, id);
			LoginDAO.getInstance().loginDelete(conn, id);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn);
		}
	}

	public ArrayList<MemberVO> memberList() {
		Connection conn = ConnectionManager.getConnnect();
		ArrayList<MemberVO> list = null;
		try {
			list = dao.memberList(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn);
		}
		return list;
	}

}
