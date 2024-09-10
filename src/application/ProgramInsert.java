package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class ProgramInsert {

	public static void main(String[] args) {
		
		SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			/*
			 * No JDBC, eu não passo os valores diretamente. Passo com (?) que
			 * é o meu PLACE HOLDER.
			 */
			
			/*
			 * st = conn.prepareStatement(
					"INSERT INTO seller " +
					"(Name,Email,BirthDate,BaseSalary,DepartmentId) " +
                    "VALUES "  +
					"(?,?,?,?,?)");
			 */
			
			/*
			 * Se eu quiser inserir recuperando o ID inserido, eu sobrecarrego o 
			 * prepareStatement que passa a receber um novo parâmetro
			 */
			
			/*
			 * Eu posso também criar um outro prepareStatement que funciona da seguinte forma:
			 * 
			 * st = conn.prepareStatement("INSERT INTO department (Name) VALUES ('D1'),('D2')",
			 * Statement.RETURN_GENERATED_KEYS);
			 */
			st = conn.prepareStatement(
					"INSERT INTO seller " +
					"(Name,Email,BirthDate,BaseSalary,DepartmentId) " +
                    "VALUES "  +
					"(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			//Agora faço os comandos de troca do PLACEHOLDER
			//Lembrando que a contagem aqui começa com 1UM.
			st.setString(1, "Fabio Britto");
			st.setString(2, "fabio.tritono@gmail.com");
			//Quando vamos instanciar uma data com SQL, a importação é essa
			st.setDate(3, new java.sql.Date(stf.parse("14/09/1996").getTime()));
			st.setDouble(4, 3500.00);
			st.setInt(5, 1);
			
			//Pra executar esse comando eu chamo st.executeUpdate(). Ele retorna
			//o número de linhas alteradas
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				//O método getGeneratedKeys retorna um objeto do tipo ResultSet
				ResultSet rs = st.getGeneratedKeys();
				while(rs.next()) {
					//Nesse caso, não preciso passar como parâmetro o "Id". Como ele só irá me
					//retornar uma tabela auxiliar com apenas UMA coluna. O 1UM passado como parâmetro
					//informa que quero a primeira coluna
					int id = rs.getInt(1);
					System.out.println("Feito! ID: "  + id);
				}
			}
			else {
				System.out.println("Nenhuma linha alterada");
			}
			
			//System.out.println("Feito! " + rowsAffected + " linhas afetadas");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.getMessage();
		}
		
		finally {
			/*
			 * Como a classe PreparedStatement extends Statement, eu posso passar
			 * ela como parâmetro pro método já criado pra lidar com a classe mãe
			 */
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
	}
}
