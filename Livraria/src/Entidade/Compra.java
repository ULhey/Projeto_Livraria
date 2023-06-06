package Entidade;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class Compra {
	private int cod;
	private Livro livro;
	private int quantidade;
	
	public Livro getLivro() {
		return livro;
	}
	private double valor;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
}