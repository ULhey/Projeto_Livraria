package Interfaces;

import java.sql.SQLException;

import Controler.ControleEstoque;
import DAO.EstoqueDao;
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

public class TelaEstoque implements Tela {

	private FlowPane principal = new FlowPane();
	
	private TextField txtCod = new TextField();
	private TextField txtCodLivro = new TextField();
	private TextField txtQuantidade = new TextField();
	
	private ControleEstoque cl = null;
	private Executor executor;

	public TelaEstoque(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void start() {
		try {	
			cl = new ControleEstoque();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		txtCod.setPrefWidth(290);
		txtCodLivro.setPrefWidth(290);
		txtQuantidade.setPrefWidth(290);

		VBox vbox = new VBox();
		
		GridPane grid = new GridPane();
		grid.add(new Label("Codigo: "), 0, 0);
		grid.add(txtCod, 1, 0);
		grid.add(new Label("Codigo livro: "), 0, 1);
		grid.add(txtCodLivro, 1, 1);
		grid.add(new Label("Quantidade: "), 0, 2);
		grid.add(txtQuantidade, 1, 2);
		
		ligacoes();
		
		grid.setHgap(10);
		grid.setVgap(10);
		
		HBox hbox = new HBox();
		Button btnCriar = new Button("Criar");
		btnCriar.setOnAction(a -> {
			if (Integer.parseInt(txtCod.getText()) == 0 || txtCodLivro.getText() == "") {
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
			if (Integer.parseInt(txtCod.getText()) == 0 || txtCodLivro.getText() == "") {
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
				EstoqueDao ldao = new EstoqueDao();
				txtQuery.setText("" + ldao.max());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		});
		Button bt2 = new Button("Count");
		bt2.setOnAction(a -> {
			try {
				EstoqueDao ldao = new EstoqueDao();
				txtQuery.setText("" + ldao.count());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		Button bt3 = new Button("Avg");
		bt3.setOnAction(a -> {
			try {
				EstoqueDao ldao = new EstoqueDao();
				txtQuery.setText("" + ldao.avg());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		Button bt4 = new Button("Min");
		bt4.setOnAction(a -> { 
			try {
				EstoqueDao ldao = new EstoqueDao();
				txtQuery.setText("" + ldao.min());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		});
		
		hbox2.setSpacing(10);
		hbox2.getChildren().addAll(bt1, bt2, bt3, bt4);
		
		vbox.getChildren().addAll(new Label("Estoque"), grid, hbox, new Label("Querys"), txtQuery, hbox2);
		vbox.setSpacing(10);
		
		principal.getChildren().addAll(vbox);
		principal.setAlignment(Pos.CENTER);
		principal.setMargin(grid, new Insets(10));
	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtCod.textProperty(), cl.codProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtCodLivro.textProperty(), cl.codLivroProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(txtQuantidade.textProperty(), cl.quantidadeProperty(), new NumberStringConverter());
	}
	
	public void limparCampos() {
		txtCod.setText("");
		txtCodLivro.setText("");
		txtQuantidade.setText("");
	}

	@Override
	public Pane render() {
		return principal;
	}

}
