package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import Entidade.Autor;
import Entidade.Compra;
import Entidade.Editora;
import Entidade.Livro;

public class CompraDao implements iCompraDao {
	private Connection c;

	public CompraDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirCompra(Compra cc) throws SQLException {
		String sql = "INSERT INTO estoque VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cc.getCod());
		ps.setInt(2, cc.getLivro().getCod());
		ps.setInt(3, cc.getQuantidade());
		ps.setDouble(4, cc.getValor());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarCompra(Compra cc) throws SQLException {
		String sql = "UPDATE estoque SET codLivro = ?, qtdComprada = ?, valor = ? WHERE cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cc.getLivro().getCod());
		ps.setInt(2, cc.getQuantidade());
		ps.setDouble(3, cc.getValor());
		ps.setInt(5, cc.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirCompra(int cod) throws SQLException {
		String sql = "DELETE estoque WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Compra buscaCompra(Compra cc) throws SQLException {
		String sql = "SELECT l.cod AS codL, l.titulo AS tituloL, l.valor AS valorL, l.codEditora AS codel, l.codAutor AS codaL, e.cod AS codE, e.nome AS nomeE, e.site AS siteE, a.cod AS codA, a.nome AS nomeA, a.biografia AS biograA, "
				+ "c.cod AS codcompra, c.codLivro as codcl, c.qtdComprada AS quantidade, c.valor AS valorc "
				+ "FROM livro l INNER JOIN editora e ON l.codEditora = e.cod INNER JOIN autor a ON l.codAutor = a.cod INNER JOIN compra c ON l.cod = c.codLivro WHERE c.cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cc.getCod());

		int cont = 0;
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cc.setCod(rs.getInt("codcompra"));

			Livro l = new Livro();
			l.setCod(rs.getInt("codcl"));
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

			cc.setLivro(l);

			cc.setQuantidade(rs.getInt("quantidade"));
			cc.setValor(rs.getDouble("valorc"));
			cont++;
		}

		if (cont == 0) {
			cc = new Compra();
		}

		rs.close();
		ps.close();
		return cc;
	}

	@Override
	public List<Compra> buscaAllCompra() throws SQLException {
		List<Compra> compras = new ArrayList<Compra>();

		String sql = "SELECT l.cod AS codL, l.titulo AS tituloL, l.valor AS valorL, l.codEditora AS codel, l.codAutor AS codaL, e.cod AS codE, e.nome AS nomeE, e.site AS siteE, a.cod AS codA, a.nome AS nomeA, a.biografia AS biograA, "
				+ "c.cod AS codcompra, c.codLivro as codcl, c.qtdComprada AS quantidade, c.valor AS valorc "
				+ "FROM livro l INNER JOIN editora e ON l.codEditora = e.cod INNER JOIN autor a ON l.codAutor = a.cod INNER JOIN compra c ON l.cod = c.codLivro";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Compra c = new Compra();
			c.setCod(rs.getInt("codcompra"));

			Livro l = new Livro();
			l.setCod(rs.getInt("codcl"));
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

			c.setLivro(l);

			c.setQuantidade(rs.getInt("quantidade"));
			c.setValor(rs.getDouble("valorc"));

			compras.add(c);
		}

		return compras;
	}
	
	public int max() throws SQLException {
		String sql = "select max(qtdComprada) as valormin from compra";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int min = 0;
		if (rs.next()) {
			min = rs.getInt("valormin");
		}
			
		return min;
	}
	
	public int min() throws SQLException {
		String sql = "select min(qtdComprada) as valormin from compra";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int min = 0;
		if (rs.next()) {
			min = rs.getInt("valormin");
		}
			
		return min;
	}
	
	public int avg() throws SQLException {
		String sql = "select avg(qtdComprada) as valoragv from compra";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valoragv");
		}
			
		return avg;
	}
	
	public int count() throws SQLException {
		String sql = "select count(qtdComprada) as valorcount from compra";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		int avg = 0;
		if (rs.next()) {
			avg = rs.getInt("valorcount");
		}
			
		return avg;
	}
}