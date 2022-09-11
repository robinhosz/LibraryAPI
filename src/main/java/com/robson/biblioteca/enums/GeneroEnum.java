package com.robson.biblioteca.enums;

public enum GeneroEnum {

	COMEDIA(0, "Comedia"), TERROR(1, "Terror"), ACAO(2, "Ação"), EMPREENDEDORISMO(3, "Empreendedorismo"),
	FILOSOFIA(4, "Filosofia"), HISTORIA(5, "Historia");

	private Integer cod;
	private String key;

	private GeneroEnum(Integer cod, String key) {
		this.cod = cod;
		this.key = key;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
