package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		//Objeto pra Conexão do Banco
		Connection conn = null;
		//Objeto pra realizar a busca no banco de dados
		Statement st = null;
		//O resultado desta consulta eu guardo em rs
		ResultSet rs = null;
		
		//Como todas essas operações são pra acessar recursos externos (banco de dados) pode gerar exceções
		try {
			conn = DB.getConnection();
			
			//Pra instanciar um STATEMENT, posso chamar a variável de conexão "conn"
			st = conn.createStatement();
			
			//Método pra que recebe como parâmetro uma instrução SQL
			//Aqui, eu uso o st para realizar a busca, e guardo seu resultado em rs
			rs = st.executeQuery("SELECT * FROM department");
			/*
			 * Por padrão, o resultado em tabela do "rs" me traz uma tabela que começa em 0ZERO,
			 * que seria o cabeçalho. Ou seja, antes de começar os dados
			 * Quando eu fizer o rs.next() pela primeira vez, estarei na posição 1UM
			 */
			while(rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}
		}
		/*
		 * ----------------------ATENÇÃO-----------------------------
		 * Como os recursos "conn,st,rs" são recursos externos e não controlados pela JVM
		 * preciso fechar manualmente pra evitar que haja vazamento de memória
		 */
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			/*
			 * Cada uma dessas duas próximas operações, podem lançar uma SQLEXCEPTION, portanto,
			 * preciso na classe DB acrescentar métodos auxiliares para fechamento dos objetos
			 */
			DB.closeRestultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
