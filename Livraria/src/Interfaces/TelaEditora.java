package Interfaces;

import java.sql.SQLException;

import Controler.ControleEditora;
import DAO.EditoraDao;
import DAO.LivroDao;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class TelaEditora implements Tela {
	private FlowPane principal = new FlowPane();

	private TextField txtCod = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtSite = new TextField();

	private ControleEditora cl = null;
	private Executor executor;

	public TelaEditora(Executor executor) {
		this.executor = executor;
	}

	public void start() {
		try {
			cl = new ControleEditora();
		}  catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		txtCod.setPrefWidth(290);
		txtNome.setPrefWidth(290);
		txtSite.setPrefWidth(290);

		VBox vbox = new VBox();

		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Nome: "), 0, 1);
		grid.add(txtNome, 1, 1);
		grid.add(new Label("Site: "), 0, 2);
		grid.add(txtSite, 1, 2);

		ligacoes();

		grid.setHgap(10);
		grid.setVgap(10);

		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtNome.getText() == "" || txtSite.getText() == "") {
				Alert m = new Alert(AlertType.ERROR, "Campos em branco ou codigo zerado", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.criar();
					limparCampos();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		Button btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtNome.getText() == "" || txtSite.getText() == "") {
				Alert m = new Alert(AlertType.ERROR, "Campos em branco ou codigo zerado", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.atualizar();
					limparCampos();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0) {
				Alert m = new Alert(AlertType.ERROR, "Codigo não pode ser zero", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.leitura();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		Button btnExcluir = new Button("Excluir");
		btnExcluir.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0) {
				Alert m = new Alert(AlertType.ERROR, "Codigo não pode ser zero", ButtonType.OK);
				m.showAndWait();
			} else {
				try {
					cl.remove();
					limparCampos();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		TextField txtQuery = new TextField();
		HBox hbox2 = new HBox();
		
		Button bt1 = new Button("Max");
		bt1.setOnAction(a -> { 
			try {
				EditoraDao ldao = new EditoraDao();
				txtQuery.setText("" + ldao.max());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		});
		Button bt2 = new Button("Count");
		bt2.setOnAction(a -> {
			try {
				EditoraDao ldao = new EditoraDao();
				txtQuery.setText("" + ldao.count());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		Button bt3 = new Button("Avg");
		bt3.setOnAction(a -> {
			try {
				EditoraDao ldao = new EditoraDao();
				txtQuery.setText("" + ldao.avg());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		Button bt4 = new Button("Min");
		bt4.setOnAction(a -> { 
			try {
				EditoraDao ldao = new EditoraDao();
				txtQuery.setText("" + ldao.min());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		});
		hbox2.setSpacing(10);
		hbox2.getChildren().addAll(bt1, bt2, bt3, bt4);
		
		vbox.getChildren().addAll(new Label("Editora"), grid, hbox, new Label("Querys"), txtQuery, hbox2);
		vbox.setSpacing(10);

		principal.getChildren().addAll(vbox);
		principal.setAlignment(Pos.CENTER);
		principal.setMargin(grid, new Insets(10));

	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtCod.textProperty(), cl.codProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtNome.textProperty(), cl.nomeProperty());
		Bindings.bindBidirectional(txtSite.textProperty(), cl.siteProperty());
	}

	public void limparCampos() {
		txtCod.setText("");
		txtNome.setText("");
		txtSite.setText("");

	}

	@Override
	public Pane render() {
		return principal;
	}
}
