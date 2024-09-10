package application;

import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class ProgramTransaction {

	public static void main(String[] args) {
		Connection conn = null;
		
		
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			//Aqui eu delcaro que não é pra confirmar as operações automaticamente. Por padrão
			//todas as operações aguardarão confirmação do programador
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
			//Variável pra saber o número de linhas afetadas
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			/*
			 * Teste de erro no meio do programa.
			 * Nesse caso por exemplo, apenas metade do programa foi executado, porque
			 * no meio do código foi lançada uma execução. Preciso então, por meio de uma
			 * TRANSAÇÃO, proteger para que ou execute tudo ou nada
			 */
			/*
			 * int x = 1;
			 * if(x < 2) {
				throw new SQLException("Fake Error");
			}
			 */
			
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			//Aqui eu CONFIRMO A TRANSAÇÃO
			conn.commit();
			
			System.out.println("Rows1: " + rows1);
			System.out.println("Rows2: " + rows2);
		}
		catch(SQLException e) {
			//Caso a operação tenha parado no meio, eu crio uma lógica, pra voltar e tentar de novo
			try {
				//Se eu der o rollback(), é porque houve um erro e preciso informar para a aplicação
				conn.rollback();
				throw new DbException("Transação não concluída! O motivo foi: " + e.getMessage());
			}
			//Esse catch é pra caso eu tenha TENTADO VOLTAR pro rollback, mas deu jazz kkk
			catch(SQLException e1) {
				throw new DbException("Erro ao tentar voltar! O motivo foi: " + e1.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
