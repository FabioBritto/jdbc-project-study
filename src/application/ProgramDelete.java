package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class ProgramDelete {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			//NUNCA FAZER UMA DELEÇÃO SEM QUE HAJA UMA RESTRIÇÃO (WHERE)
			st = conn.prepareStatement(
					"DELETE FROM department " +
				    "WHERE " +
					"Id = ?");
			
			st.setInt(1, 2);

			//Lembrando que o método executeUpdate() me retorna um inteiro com
			//o número de linhas afetadas
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Feito! " + rowsAffected + " linhas afetadas");
		}
		catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
