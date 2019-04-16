package com.dev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dev.vo.MemberVO;

public class LoginDAO {

	//싱글톤
	private static LoginDAO dao = new LoginDAO();
	public static LoginDAO getInstance() {
		return dao;
	}
	//등록
	public void loginInsert(Connection conn, MemberVO member) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("insert into login(id, passwd) values(?,?)");
		pstmt.setString(1, member.getId());
		pstmt.setString(2, member.getPasswd());
		pstmt.executeUpdate();
	}
	
	//검색
	public MemberVO loginSearch(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;

		pstmt = conn.prepareStatement("select * from login where id=?");
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			member = new MemberVO();
			member.setId(rs.getString(1));
			member.setPasswd(rs.getString(2));
		}
		return member;
	}
	
	//수정
	public void loginUpdate(Connection conn, MemberVO member) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("update login set passwd=? where id=?");
		pstmt.setString(1, member.getPasswd());
		pstmt.setString(2, member.getId());
		pstmt.executeUpdate();
	}
	
	//삭제
	public void loginDelete(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("delete from login where id=?");
		pstmt.setString(1, id);
		pstmt.executeUpdate();

	}
	
	//전체 조회
	public ArrayList<MemberVO> loginList(Connection conn) throws SQLException{

		ArrayList<MemberVO> list = new ArrayList<MemberVO>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		MemberVO member = null;

			pstmt = conn.prepareStatement("select * from member");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new MemberVO();
				member.setId(rs.getString(1));
				member.setPasswd(rs.getString(2));
				list.add(member);
			}

		return list;
	}
	
}
