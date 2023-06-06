package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidade.Autor;
import Entidade.Editora;
import Entidade.Livro;

public class LivroDao implements iLivroDao {
	private Connection c;

	public LivroDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirLivro(Livro l) throws SQLException {
		String sql = "INSERT INTO livro VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, l.getCod());
		ps.setString(2, l.getTitulo());
		ps.setDouble(3, l.getValor());
		ps.setInt(4, l.getEditora().getCod());
		ps.setInt(5, l.getAutor().getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarLivro(Livro l) throws SQLException {
		String sql = "UPDATE livro SET titulo = ?, valor = ?, codEditora = ?, codAutor = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, l.getTitulo());
		ps.setDouble(2, l.getValor());
		ps.setInt(3, l.getEditora().getCod());
		ps.setInt(4, l.getAutor().getCod());
		ps.setInt(5, l.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirLivro(int cod) throws SQLException {
		String sql = "DELETE livro WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Livro buscaLivro(Livro l) throws SQLException {
		String sql = "SELECT l.cod AS codL, l.titulo AS tituloL, l.valor AS valorL, l.codEditora AS codel, l.codAutor AS codaL, e.cod AS codE, "
				+ "e.nome AS nomeE, e.site AS siteE, a.cod AS codA, a.nome AS nomeA, a.biografia AS biograA FROM livro l "
				+ "INNER JOIN editora e ON l.codEditora = e.cod "
				+ "INNER JOIN autor a ON l.codAutor = a.cod WHERE l.cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, l.getCod());
		
		ResultSet rs = ps.executeQuery();
		int cont = 0;
		if (rs.next()) {
			l.setCod(rs.getInt("codL"));
			l.setTitulo(rs.getString("tituloL"));
			l.setValor(rs.getDouble("valorL"));
			
			Autor a = new Autor();
			a.setCod(rs.getInt("codA"));
			a.setNome(rs.getString("nomeA"));
			a.setbiografia(rs.getString("biograA"));
			l.setAutor(a);
			
			Editora e = new Editora();
			e.setCod(rs.getInt("codE"));
			e.setNome(rs.getString("nomeE"));
			e.setSite(rs.getString("siteE"));
			l.setEditora(e);

			cont++;
		}
		
		return l;
	}

	@Override
	public List<Livro> buscaAllLivro() throws SQLException {
		List<Livro> livros = new ArrayList<Livro>();
		
		String sql = "SELECT l.cod AS codL, l.titulo AS tituloL, l.valor AS valorL, l.codEditora AS codel, l.codAutor AS codaL, e.cod AS codE, "
				+ "e.nome AS nomeE, e.site AS siteE, a.cod AS codA, a.nome AS nomeA, a.biografia AS biograA FROM livro l "
				+ "INNER JOIN editora e ON l.codEditora = e.cod "
				+ "INNER JOIN autor a ON l.codAutor = a.cod";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Livro l = new Livro();
			l.setCod(rs.getInt("codL"));
			l.setTitulo(rs.getString("tituloL"));
			l.setValor(rs.getDouble("valorL"));
			
			Autor a = new Autor();
			a.setCod(rs.getInt("codA"));
			a.setNome(rs.getString("nomeA"));
			a.setbiografia(rs.getString("biograA"));
			l.setAutor(a);
			
			Editora e = new Editora();
			e.setCod(rs.getInt("codE"));
			e.setNome(rs.getString("nomeE"));
			e.setSite(rs.getString("siteE"));
			l.setEditora(e);
			
			livros.add(l);
		}
		
		return livros;
	}
	
	public int max() throws SQLException {
		String sql = "select max(valor) as valormax from livro";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int max = 0;
		if (rs.next()) {
			max = rs.getInt("valormax");
		}
			
		return max;
	}
	
	public int min() throws SQLException {
		String sql = "select min(valor) as valormin from livro";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int min = 0;
		if (rs.next()) {
			min = rs.getInt("valormin");
		}
			
		return min;
	}
	
	public int avg() throws SQLException {
		String sql = "select avg(valor) as valoragv from livro";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valoragv");
		}
			
		return avg;
	}
	
	public int count() throws SQLException {
		String sql = "select count(valor) as valorcount from livro";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valorcount");
		}
			
		return avg;
	}
}
