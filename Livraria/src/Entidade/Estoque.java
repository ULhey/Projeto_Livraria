package Entidade;

public class Estoque {
	private int cod;
	private Livro livro;
	private int quantidade;

	public int getCod() {
		return cod;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public int getquantidade() {
		return quantidade;
	}

	public void setquantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}