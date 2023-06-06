package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidade.Autor;

public class AutorDao implements iAutorDao {
	private Connection c;

	public AutorDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirAutor(Autor a) throws SQLException {
		String sql = "INSERT INTO autor VALUES (?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getCod());
		ps.setString(2, a.getNome());
		ps.setString(3, a.getbiografia());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarAutor(Autor a) throws SQLException {
		String sql = "UPDATE autor SET nome = ?, biografia = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, a.getNome());
		ps.setString(2, a.getbiografia());
		ps.setInt(3, a.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirAutor(int cod) throws SQLException {
		String sql = "DELETE autor WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Autor buscaAutor(Autor a) throws SQLException {
		String sql = "SELECT cod, nome, biografia FROM autor WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getCod());

		int cont = 0;
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			a.setNome(rs.getString("nome"));
			a.setbiografia(rs.getString("biografia"));
			cont++;
		}

		if (cont == 0) {
			a = new Autor();
		}

		rs.close();
		ps.close();
		return a;
	}

	@Override
	public List<Autor> buscaAllAutor() throws SQLException {
		String sql = "SELECT cod, nome, biografia FROM autor";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		List<Autor> autores = new ArrayList<Autor>();

		while (rs.next()) {
			Autor a = new Autor();
			a.setCod(rs.getInt("cod"));
			a.setNome(rs.getString("nome"));
			a.setbiografia(rs.getString("biografia"));

			autores.add(a);
		}

		rs.close();
		ps.close();

		return autores;
	}
	
	public int max() throws SQLException {
		String sql = "select max(len(nome)) as valormin from autor";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		int min = 0;
		if (rs.next()) {
			min = rs.getInt("valormin");
		}

		return min;
	}
	
	public int min() throws SQLException {
		String sql = "select min(len(nome)) as valormin from autor";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		int min = 0;
		if (rs.next()) {
			min = rs.getInt("valormin");
		}

		return min;
	}

	public int avg() throws SQLException {
		String sql = "select avg(len(nome)) as valoragv from autor";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valoragv");
		}

		return avg;
	}

	public int count() throws SQLException {
		String sql = "select count(len(nome)) as valorcount from autor";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valorcount");
		}

		return avg;
	}
}