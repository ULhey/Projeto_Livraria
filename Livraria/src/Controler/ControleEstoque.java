package Controler;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.EstoqueDao;
import DAO.LivroDao;
import Entidade.Estoque;
import Entidade.Livro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ControleEstoque {
	ArrayList<Estoque> estoques = new ArrayList<Estoque>();
	ArrayList<Livro> livros = new ArrayList<Livro>();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private IntegerProperty codLivro = new SimpleIntegerProperty(0);
	private IntegerProperty quantidade = new SimpleIntegerProperty(0);

	public ControleEstoque() throws ClassNotFoundException, SQLException {
		EstoqueDao eedao = new EstoqueDao();
		LivroDao ldao = new LivroDao();
		estoques.addAll(eedao.buscaAllEstoque());
		livros.addAll(ldao.buscaAllLivro());
		eedao.desconectar();
		ldao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			EstoqueDao eedao = new EstoqueDao();

			Estoque ee = new Estoque();
			ee.setCod(cod.get());
			for (Livro l : livros) {
				if (l.getCod() == codLivro.get()) {
					ee.setLivro(l);
				}
			}
			ee.setquantidade(quantidade.get());

			estoques.add(ee);
			eedao.inserirEstoque(ee);
			eedao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			EstoqueDao edao = new EstoqueDao();
			for (Estoque e : estoques) {
				if (e.getCod() == cod.get()) {
					Estoque ee = edao.buscaEstoque(e);
					cod.set(ee.getCod());
					codLivro.set(ee.getLivro().getCod());
					quantidade.set(ee.getquantidade());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			for (Estoque e : estoques) {
				if (e.getCod() == cod.get()) {
					EstoqueDao edao = new EstoqueDao();

					for (Livro l : livros) {
						if (l.getCod() == codLivro.get()) {
							e.setLivro(l);
							break;
						}
					}

					e.setquantidade(quantidade.get());

					edao.atualizarEstoque(e);
					edao.desconectar();
					break;
				}
			}
		}
	}

	public void remove() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty()) {
			EstoqueDao edao = new EstoqueDao();
			estoques.remove(cod.get() - 1);
			edao.excluirEstoque(cod.get());
			edao.desconectar();
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
}
