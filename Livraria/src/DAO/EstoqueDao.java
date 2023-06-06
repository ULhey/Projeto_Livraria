package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidade.Autor;
import Entidade.Editora;
import Entidade.Estoque;
import Entidade.Livro;


public class EstoqueDao implements iEstoqueDao{
	private Connection c;

	public EstoqueDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirEstoque(Estoque ee) throws SQLException {
		String sql = "INSERT INTO estoque VALUES (?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ee.getCod());
		ps.setInt(2, ee.getLivro().getCod());
		ps.setInt(3, ee.getquantidade());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarEstoque(Estoque ee) throws SQLException {
		String sql = "UPDATE estoque SET codLivro = ?, quantidade = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ee.getLivro().getCod());
		ps.setInt(2, ee.getquantidade());
		ps.setInt(3, ee.getCod());
		
		ps.execute();
		ps.close();
	}

	@Override
	public void excluirEstoque(int cod) throws SQLException {
		String sql = "DELETE editora WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Estoque buscaEstoque(Estoque ee) throws SQLException {
		String sql = "SELECT l.cod AS codL, l.titulo AS tituloL, l.valor AS valorL, l.codEditora AS codel, l.codAutor AS codaL, e.cod AS codE, e.nome AS nomeE, e.site AS siteE, a.cod AS codA, a.nome AS nomeA, a.biografia AS biograA, ee.cod AS codee, ee.codLivro as codeel, ee.quantidade AS quantidade\r\n"
				+ "FROM livro l INNER JOIN editora e ON l.codEditora = e.cod INNER JOIN autor a ON l.codAutor = a.cod INNER JOIN estoque ee ON l.cod = ee.codLivro WHERE ee.cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ee.getCod());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			ee.setCod(rs.getInt("codee"));
			
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
			
			ee.setLivro(l);
			
			ee.setquantidade(rs.getInt("quantidade"));
		}
		
		return ee;
	}

	@Override
	public List<Estoque> buscaAllEstoque() throws SQLException {
		List<Estoque> estoques = new ArrayList<Estoque>();
		String sql = "SELECT l.cod AS codL, l.titulo AS tituloL, l.valor AS valorL, l.codEditora AS codel, l.codAutor AS codaL, e.cod AS codE, e.nome AS nomeE, e.site AS siteE, a.cod AS codA, a.nome AS nomeA, a.biografia AS biograA, ee.cod AS codee, ee.codLivro as codeel, ee.quantidade AS quantidade\r\n"
				+ "FROM livro l INNER JOIN editora e ON l.codEditora = e.cod INNER JOIN autor a ON l.codAutor = a.cod INNER JOIN estoque ee ON l.cod = ee.codLivro";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Estoque ee = new Estoque();
			ee.setCod(rs.getInt("codee"));
			
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
			
			ee.setLivro(l);
			
			ee.setquantidade(rs.getInt("quantidade"));
			
			estoques.add(ee);
		}
		
		return estoques;
	}
	
	public int max() throws SQLException {
		String sql = "select max(quantidade) as valormax from estoque";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int max = 0;
		if (rs.next()) {
			max = rs.getInt("valormax");
		}
			
		return max;
	}
	
	public int min() throws SQLException {
		String sql = "select min(quantidade) as valormin from estoque";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int min = 0;
		if (rs.next()) {
			min = rs.getInt("valormin");
		}
			
		return min;
	}
	
	public int avg() throws SQLException {
		String sql = "select avg(quantidade) as valoragv from estoque";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valoragv");
		}
			
		return avg;
	}
	
	public int count() throws SQLException {
		String sql = "select count(quantidade) as valorcount from estoque";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valorcount");
		}
			
		return avg;
	}
}
