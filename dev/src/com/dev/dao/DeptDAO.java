package com.dev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev.dao.ConnectionManager;
import com.dev.vo.DeptBeans;
import com.dev.vo.DeptSearchVO;

/*
 * DAO : Data Access Object (DB처리)
 */
public class DeptDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//싱글톤 : static 필드
	static DeptDAO instance;
	public static DeptDAO getInstance() {
		if(instance==null)
			instance=new DeptDAO();
			return instance;		
	}
	
	//등록
	public void deptInsert(DeptBeans vo) {		
		try {
			conn = ConnectionManager.getConnnect();
			//트랙잭션 모드 변경(autocommit 해제)
			conn.setAutoCommit(false);
			String sql = " insert into hr.departments(department_id, "
				 	+    " department_name, manager_id, location_id)"
					+    " values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getDepartment_id());
			pstmt.setString(2, vo.getDepartment_name());
			pstmt.setString(3, vo.getManager_id());
			pstmt.setString(4, vo.getLocation_id());
			
			pstmt.executeUpdate();

			conn.commit();  //정상실행이면 커밋
			
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();  //에러나면 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//수정
	public void deptUpdate(DeptBeans vo) {		
		try {
			conn = ConnectionManager.getConnnect();
			String sql = " UPDATE hr.departments set      "
				 	+    " department_name = ?, "
				 	+    "        manager_id = ?,      "
				 	+    "        location_id = ?      "
				 	+    "  WHERE department_id = ?    ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getDepartment_name());
			pstmt.setString(2, vo.getManager_id());
			pstmt.setString(3, vo.getLocation_id());
			pstmt.setString(4, vo.getDepartment_id());
			pstmt.executeUpdate();			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//5. 연결해제 
			ConnectionManager.close(conn);
		}
	}
	
	//삭제
	public void deptDelete(String department_id) {		
		try {
			conn = ConnectionManager.getConnnect();
			String sql = " DELETE FROM hr.departments  "
				 	+    "  WHERE department_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_id);
			pstmt.executeUpdate();			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//5. 연결해제 
			ConnectionManager.close(conn);
		}
	}
	
	//단건조회
	public DeptBeans getDept(String department_id) {
		DeptBeans vo = null;
		try {
			conn = ConnectionManager.getConnnect();
			String sql ="SELECT department_id, department_name, manager_id, location_id "
					+   "  FROM hr.departments"
					+   " WHERE department_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new DeptBeans();
				vo.setDepartment_id(rs.getString("department_id"));
				vo.setDepartment_name(rs.getString("department_name"));
				vo.setManager_id(rs.getString("manager_id"));
				vo.setLocation_id(rs.getString("location_id"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			//5. 연결해제 
			ConnectionManager.close(conn);
		}  
		return vo;
	}
	//전체조회
	public ArrayList<DeptBeans> selectAll() {
		ArrayList<DeptBeans> list = new ArrayList<DeptBeans>();
		DeptBeans bean = null;
		try {
			conn = ConnectionManager.getConnnect();		
			String sql="select location_id,"
					       + " manager_id,"
					       + " department_name,"
					       + " department_id"
					       + " from hr.departments "
					       + " order by department_id ";
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next() ) {
				bean = new DeptBeans();
				bean.setLocation_id(rs.getString(1));
				bean.setManager_id(rs.getString(2));
				bean.setDepartment_name(rs.getString(3));
				bean.setDepartment_id(rs.getString(4));
				list.add(bean);
			}
		} catch(Exception e) {
			System.out.print("selectAll: " + e.getMessage());
		} finally {
			//5. 연결해제 
			ConnectionManager.close(conn);
		}
		return list;
	}

	//전체조회
	public List<Map<String,Object>> selectAllMap() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			conn = ConnectionManager.getConnnect();			
			String sql = "select * from hr.departments";
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);			
			while(rs.next()) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("departmentId", rs.getInt("department_id"));
				map.put("departmentName", rs.getString("department_name"));
				map.put("managerId", rs.getInt("manager_id"));
				map.put("locationId", rs.getBigDecimal("location_id"));
				list.add(map);
			}			
			//4. disconnect
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//5. 연결해제 
			ConnectionManager.close(conn);
		}
		return list;
	}
	
	//검색조회
	public List<DeptBeans> getDeptList(DeptSearchVO searchVO) {
		List<DeptBeans> list = new ArrayList<DeptBeans>();
		DeptBeans vo = null;
		String whereCondition = " where 1 = 1 ";
		if(searchVO.getLocation_id() != null && !searchVO.getLocation_id().equals("")) {
			whereCondition += " and location_id = ? ";
		}
		if(searchVO.getManager_id() != null && !searchVO.getManager_id().equals("")) {
			whereCondition += " and manager_id = ? ";
		}
		try {			
			conn = ConnectionManager.getConnnect();			
			String sql = "select b.*  from( select a.*, rownum rn  from ( "
			        +   "SELECT department_id, department_name, manager_id, location_id "
					+   "  FROM hr.departments"
			        +   whereCondition					
					+   " ORDER BY department_id "
					+   " ) a   ) b  where rn between ? and ?	";
			pstmt = conn.prepareStatement(sql);
			int pos = 1;
			if(searchVO.getLocation_id() != null && !searchVO.getLocation_id().equals("")) {
				pstmt.setString(pos++, searchVO.getLocation_id());
			}
			if(searchVO.getManager_id() != null && !searchVO.getManager_id().equals("")) {
				pstmt.setString(pos++, searchVO.getManager_id());
			}
			pstmt.setInt(pos++, searchVO.getStart());
			pstmt.setInt(pos++, searchVO.getEnd());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new DeptBeans();
				vo.setDepartment_id(rs.getString("department_id"));
				vo.setDepartment_name(rs.getString("department_name"));
				vo.setManager_id(rs.getString("manager_id"));
				vo.setLocation_id(rs.getString("location_id"));
				list.add(vo);
			}
		} catch(Exception e) {	e.printStackTrace();
		} finally{
			ConnectionManager.close(conn);
		}
		return list;
	}	
	
	//레코드 건수 조회
	public int count(DeptSearchVO searchVO) {
		int cnt = 0;
		try {
			conn = ConnectionManager.getConnnect();
			//동적 조건 추가
			String whereCondition = " where 1 = 1 ";
			if(searchVO.getLocation_id() != null && !searchVO.getLocation_id().equals("")) {
				whereCondition += " and location_id = ? ";
			}
			if(searchVO.getManager_id() != null && !searchVO.getManager_id().equals("")) {
				whereCondition += " and manager_id = ? ";
			}
			
			String sql = "select count(*) from departments" + whereCondition;
			pstmt = conn.prepareStatement(sql);

			// 조건값 셋팅
			int pos = 1;
			if(searchVO.getLocation_id() != null && !searchVO.getLocation_id().equals("")) {
				pstmt.setString(pos++, searchVO.getLocation_id());
			}
			if(searchVO.getManager_id() != null && !searchVO.getManager_id().equals("")) {
				pstmt.setString(pos++, searchVO.getManager_id());
			}
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.close(conn);
		}
		return cnt;
	}
}
