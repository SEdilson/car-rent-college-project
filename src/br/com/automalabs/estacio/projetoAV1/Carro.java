package br.com.automalabs.estacio.projetoAV1;

public class Carro {
	/*adicionar qualquer atributo aqui requer alterações também em carros.txt
	  e nas funções que salvam e carregam os dados do arquivo
	*/
	private String registroLoc;
	private String fabricante;
	private String modelo;
	private String cor;
	private String motor;
	private String tipoCombustivel;
	private int kmRodados;
	private String alugadoPor; 	
	
    Carro(){
		this.fabricante="";
		this.modelo="";		
		this.cor="";
		this.motor="";
		this.tipoCombustivel="";
		this.kmRodados=0;
		this.alugadoPor="";
    }
    
	String getAlugadoPor() {
		return this.alugadoPor;
	}	
	String getRegistroLoc() {
		return this.registroLoc; 
	}	
	String getFabricante() {
		return this.fabricante; 
	}
	String getModelo() {
		return this.modelo; 
	}
	String getTipoCombustivel() {
		return this.tipoCombustivel;
	}
	
	String getMotor() {
		return this.motor;
	}
	
	String getCor() {
		return this.cor;
	}
	
	int getKmRodados() {
		return this.kmRodados;
	}	
	
	void setCor(String cor) {
		this.cor=cor;
	}
	
	void setAlugadoPor(String cliente) {
		this.alugadoPor=cliente;
	}
	
	void setRegistroLoc(String registroLoc) {
		this.registroLoc=registroLoc;
	}	
	void setFabricante(String fabricante) {
		this.fabricante=fabricante;
	}
	void setModelo(String modelo) {
		this.modelo=modelo;
	}
	void setMotor(String motor) {
		this.motor=motor;
	}
	void setTipoCombustivel(String tipoCombustivel) {
		this.tipoCombustivel=tipoCombustivel;
	}	
	void setKmRodados(int kmRodados) {
		this.kmRodados=kmRodados;
	}
}

