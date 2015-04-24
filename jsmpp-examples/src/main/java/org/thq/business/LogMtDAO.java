/**
 * 
 */
package org.thq.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.thq.database.ConnectionPool;

/**
 * @author tuanhq
 *
 */
public class LogMtDAO {
	private static final Logger logger = Logger.getLogger(LogMtDAO.class);
	public boolean insertLogMt(LogMtDto logDto) throws ClassNotFoundException, SQLException {

		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		String strSQL = null;
		try {
			strSQL = "INSERT INTO log_mt" + "(sub_id, mt, date)" + " values(?,?,?)";
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(strSQL);
			stmt.setString(1, logDto.getSubId());
			stmt.setString(2, logDto.getMt());
			stmt.setString(3, logDto.getDate());			
			result = stmt.executeUpdate() >= 0;
		} catch (Exception e) {
			logger.error("insert: Errior executing " + strSQL + " >>> " + e.toString());
			e.printStackTrace();

		} finally {
			ConnectionPool.putConnection(conn);
			stmt.close();
		}
		return result;

	}
	public static void main(String[] args) {
		LogMtDto dto =new LogMtDto();
		dto.setMt("helo");
		dto.setSubId("1243556");
		dto.setDate("2015");
		try {
			for(int i =0;i <10;i ++){
				new LogMtDAO().insertLogMt(dto);	
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
