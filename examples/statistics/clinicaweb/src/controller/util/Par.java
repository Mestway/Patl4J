package controller.util;

public class Par<A, B> {

	/** Primeiro membro do par */
	private A first;

	/** Segundo membro do par */
	private B second;

	/**
	 * Inicia um par vazio
	 */
	public Par() {
		this(null, null);
	}

	/**
	 * Inicia um par indicando seus elementos
	 * 
	 * @param first o primeiro elemento
	 * @param second o segundo elemento
	 */
	public Par(A first, B second) {
		this.setFirst(first);
		this.setSecond(second);
	}

	/**
	 * @return the first
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(A first) {
		this.first = first;
	}

	/**
	 * @return the second
	 */
	public B getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public void setSecond(B second) {
		this.second = second;
	}

	/**
	 * Retorna uma String representando o par
	 * 
	 * @param uma string da forma ( X , Y ) representando o par
	 */
	public String toString() {
		return "( " + this.getFirst() + " , " + this.getSecond() + " )";
	}

	public boolean equals(Par<A, B> par) {
		if (getFirst().equals(par.getFirst()) && getSecond().equals(par.getSecond())) 
			return true;

		return false;

	}

}