package Controler;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.AutorDao;
import Entidade.Autor;
//import DAO.AutorDao;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControleAutor {
	ArrayList<Autor> autores = new ArrayList<Autor>();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty biografia = new SimpleStringProperty("");
	
	public ControleAutor() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		autores.addAll(adao.buscaAllAutor());
		adao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();

		Autor a = new Autor();
		a.setCod(cod.get());
		a.setNome(nome.get());
		a.setbiografia(biografia.get());

		autores.add(a);
		adao.inserirAutor(a);
		adao.desconectar();
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		for (Autor a : autores) {
			if (a.getCod() == cod.get()) {
				Autor aa = adao.buscaAutor(a);
				nome.set(a.getNome());
				biografia.set(a.getbiografia());
				break;
			}
		}
		adao.desconectar();
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		for (Autor a : autores) {
			if (a.getCod() == cod.get()) {
				a.setNome(nome.get());
				a.setbiografia(biografia.get());
				adao.atualizarAutor(a);
				break;
			}
		}
		adao.desconectar();
	}

	public void remove() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		for (Autor a : autores) {
			if (cod.get() == a.getCod()) {
				autores.remove(a.getCod() - 1);
				adao.excluirAutor(a.getCod());
				break;
			}
		}
		adao.desconectar();
	}
	
	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty biografiaProperty() {
		return biografia;
	}
}