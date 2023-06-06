package Interfaces;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application implements Executor {

	private Tela Editora, Autor, Livro, Estoque, Compra;
	private BorderPane principal = new BorderPane();

	@Override
	public void start(Stage stage) throws Exception {
		Editora = new TelaEditora(this);
		Editora.start();
		Autor = new TelaAutor(this);
        Autor.start();
        Livro = new TelaLivro(this);
        Livro.start();
        Estoque = new TelaEstoque(this);
        Estoque.start();
        Compra = new TelaCompra(this);
        Compra.start();
		
		MenuBar mnuBar = new MenuBar();
		Menu mnuCadastro = new Menu("Cadastro");
		
		mnuBar.getMenus().addAll(mnuCadastro);
		
		Tab abaEditora = new Tab("Editora");        
		MenuItem mnuEditora = new MenuItem("Editora");
		mnuEditora.setOnAction( e-> {
			abrir("Editora"); });
		
		Tab abaAutor = new Tab("Autor");
        MenuItem mnuAutor = new MenuItem("Autor");
        mnuAutor.setOnAction( e -> {
            abrir("Autor"); });
        
        Tab abaLivro = new Tab("Livro");
        MenuItem mnuLivro = new MenuItem("Livro");
        mnuLivro.setOnAction( e -> {
            abrir("Livro"); });
        
        Tab abaEstoque = new Tab("Estoque");
        MenuItem mnuEstoque = new MenuItem("Estoque");
        mnuEstoque.setOnAction( e -> {
            abrir("Estoque"); });
        
        Tab abaCompra = new Tab("Compra");
        MenuItem mnuCompra = new MenuItem("Compra");
        mnuCompra.setOnAction( e -> {
            abrir("Compra"); });
        
		mnuCadastro.getItems().addAll(mnuEditora, mnuAutor, mnuLivro, mnuEstoque, mnuCompra);
		
		principal.setTop(mnuBar);
	
		Scene scn = new Scene(principal, 500, 400);
		
		
		stage.setScene(scn);
		stage.setResizable(false);
		stage.setTitle("Sistema de Livraria");
		stage.show();
	}

	@Override	
	public void abrir(String cmd) {
        if ("Editora".equals(cmd)) { 
            principal.setCenter(Editora.render());
        } else if ("Autor".equals(cmd)) {
            principal.setCenter(Autor.render());
        } else if ("Livro".equals(cmd)) {
        	principal.setCenter(Livro.render());
        } else if ("Estoque".equals(cmd)) {
        	principal.setCenter(Estoque.render());
        } else if ("Compra".equals(cmd)) {
        	principal.setCenter(Compra.render());
        }
    }
	public static void main(String[] args) {
		Application.launch();
	}

}
