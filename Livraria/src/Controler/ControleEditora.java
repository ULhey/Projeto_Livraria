package Controler;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.EditoraDao;
import Entidade.Editora;
//import DAO.EditoraDao;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControleEditora {
	ArrayList<Editora> editoras = new ArrayList<Editora>();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty site = new SimpleStringProperty("");

	public ControleEditora() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();
		editoras.addAll(edao.buscaAllEditora());
		edao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();

		Editora e = new Editora();
		e.setCod(cod.get());
		e.setNome(nome.get());
		e.setSite(site.get());

		editoras.add(e);
		edao.inserirEditora(e);
		edao.desconectar();
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();

		for (Editora e : editoras) {
			if (e.getCod() == cod.get()) {
				Editora ee = edao.buscaEditora(e);
				nome.set(ee.getNome());
				site.set(ee.getSite());

				break;
			}
		}
		edao.desconectar();
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();
		for (Editora e : editoras) {
			if (e.getCod() == cod.get()) {
				e.setNome(nome.get());
				e.setSite(site.get());
				edao.atualizarEditora(e);
				break;
			}
		}
		edao.desconectar();
	}

	public void remove() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();
		for (Editora e : editoras) {
			if (cod.get() == e.getCod()) {
				editoras.remove(e.getCod() - 1);
				edao.excluirEditora(e.getCod());
				break;
			}
		}
		edao.desconectar();
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty siteProperty() {
		return site;
	}
}