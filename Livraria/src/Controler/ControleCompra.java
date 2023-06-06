package Controler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import DAO.CompraDao;
import DAO.LivroDao;
import Entidade.Compra;
import Entidade.Livro;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ControleCompra {
	ArrayList<Compra> compras = new ArrayList<Compra>();
	ArrayList<Livro> livros = new ArrayList<Livro>();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private IntegerProperty codLivro = new SimpleIntegerProperty(0);
	private IntegerProperty quantidade = new SimpleIntegerProperty(0);
	private DoubleProperty valor = new SimpleDoubleProperty(0);

	public ControleCompra() throws ClassNotFoundException, SQLException {
		CompraDao cdao = new CompraDao();
		LivroDao ldao = new LivroDao();
		livros.addAll(ldao.buscaAllLivro());
		compras.addAll(cdao.buscaAllCompra());
		cdao.desconectar();
		ldao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			CompraDao cdao = new CompraDao();

			Compra c = new Compra();
			c.setCod(cod.get());

			for (Livro l : livros) {
				if (l.getCod() == codLivro.get()) {
					c.setLivro(l);
					break;
				}
			}

			c.setQuantidade(quantidade.get());
			c.setValor(valor.get());

			compras.add(c);
			cdao.inserirCompra(c);
			cdao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			CompraDao cdao = new CompraDao();
			for (Compra c : compras) {
				if (c.getCod() == cod.get()) {
					Compra cc = cdao.buscaCompra(c);
					cod.set(cc.getCod());
					codLivro.set(cc.getLivro().getCod());
					quantidade.set(cc.getQuantidade());
					valor.set(cc.getValor());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			for (Compra c : compras) {
				if (c.getCod() == cod.get()) {
					CompraDao cdao = new CompraDao();

					for (Livro l : livros) {
						if (l.getCod() == codLivro.get()) {
							c.setLivro(l);
							break;
						}
					}
					c.setQuantidade(quantidade.get());
					c.setValor(valor.get());
					
					cdao.atualizarCompra(c);
					cdao.desconectar();
					break;
				}
			}
		}
	}

	public void remove() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			CompraDao cdao = new CompraDao();
			compras.remove(cod.get() - 1);
			cdao.excluirCompra(cod.get());
			cdao.desconectar();
		}
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public IntegerProperty codLivroProperty() {
		return codLivro;
	}

	public IntegerProperty quantidadeProperty() {
		return quantidade;
	}

	public DoubleProperty valorProperty() {
		return valor;
	}
	
}