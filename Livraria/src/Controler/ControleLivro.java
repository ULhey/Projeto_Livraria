package Controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DAO.AutorDao;
import DAO.EditoraDao;
import DAO.LivroDao;
import Entidade.Autor;
import Entidade.Editora;
import Entidade.Livro;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControleLivro {
	ArrayList<Livro> livros = new ArrayList<Livro>();
	ArrayList<Editora> editoras = new ArrayList<Editora>();
	ArrayList<Autor> autores = new ArrayList<Autor>();
	
	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty titulo = new SimpleStringProperty("");
	private DoubleProperty valor = new SimpleDoubleProperty(0);
	private IntegerProperty codEditora = new SimpleIntegerProperty(0);
	private IntegerProperty codAutor = new SimpleIntegerProperty(0);
	
	public ControleLivro() throws ClassNotFoundException, SQLException {
		LivroDao ldao = new LivroDao();
		EditoraDao edao = new EditoraDao();
		AutorDao adao = new AutorDao();
		livros.addAll(ldao.buscaAllLivro());
		editoras.addAll(edao.buscaAllEditora());
		autores.addAll(adao.buscaAllAutor());
		ldao.desconectar();
		edao.desconectar();
		adao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {		
		if (!editoras.isEmpty() && !autores.isEmpty()) {
			LivroDao ldao = new LivroDao();

			Livro l = new Livro();
			l.setCod(cod.get());
			l.setTitulo(titulo.get());
			l.setValor(valor.get());
			
			EditoraDao edao = new EditoraDao();
			for (Editora e : editoras) {
				if (codEditora.get() == e.getCod()) {
					l.setEditora(e);
				}
			}
			edao.desconectar();
			AutorDao adao = new AutorDao();
			for (Autor a : autores) {
				if (codAutor.get() == a.getCod()) {
					l.setAutor(a);
					break;
				}
			}
			adao.desconectar();
			
			livros.add(l);
			ldao.inserirLivro(l);
			ldao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!editoras.isEmpty() && !autores.isEmpty()) {
			LivroDao ldao = new LivroDao();
			for (Livro l : livros) {
				if (l.getCod() == cod.get()) {
					Livro ll = ldao.buscaLivro(l);
					cod.set(ll.getCod());
					titulo.set(ll.getTitulo());
					valor.set(ll.getValor());
					codEditora.set(ll.getEditora().getCod());
					codAutor.set(ll.getAutor().getCod());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!editoras.isEmpty() && !autores.isEmpty()) {
			for (Livro l : livros) {
				if (l.getCod() == cod.get()) {
					LivroDao ldao = new LivroDao();
					l.setTitulo(titulo.get());
					l.setValor(valor.get());
					
					for (Editora e : editoras) {
						if (codEditora.get() == e.getCod()) {
							l.setEditora(e);
							break;
						}
					}
					
					for (Autor a : autores) {
						if (codAutor.get() == a.getCod()) {
							l.setAutor(a);
							break;
						}
					}
					
					ldao.atualizarLivro(l);
					ldao.desconectar();
					break;
				}
			}
		}
	}

	public void remove() throws ClassNotFoundException, SQLException {
		if (!editoras.isEmpty() && !autores.isEmpty()) {
			LivroDao ldao = new LivroDao();
			livros.remove(cod.get() - 1);
			ldao.excluirLivro(cod.get());
			ldao.desconectar();
		}
	}
	
	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty tituloProperty() {
		return titulo;
	}

	public DoubleProperty valorProperty() {
		return valor;
	}
	
	public IntegerProperty codEditoraProperty() {
		return codEditora;
	}
	
	public IntegerProperty codAutorProperty() {
		return codAutor;
	}
}