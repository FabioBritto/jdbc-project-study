package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class ProgramUpdate {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			//NUNCA FAZER UMA ATUALIZAÇÃO SEM QUE HAJA UMA RESTRIÇÃO (WHERE)
			st = conn.prepareStatement(
					"UPDATE seller " +
					//Aqui é pra caso eu queira incrementar o salário por exemplo
			        "SET BaseSalary = BaseSalary + ? " +
				    "WHERE " +
			        "(DepartmentId = ?)");
			
			st.setDouble(1, 900.95);
			st.setInt(2, 2);

			//Lembrando que o método executeUpdate() me retorna um inteiro com
			//o número de linhas afetadas
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Feito! " + rowsAffected + " linhas afetadas");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
