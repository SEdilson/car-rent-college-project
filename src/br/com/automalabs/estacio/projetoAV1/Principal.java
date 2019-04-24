package br.com.automalabs.estacio.projetoAV1;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args){

		
		Cliente clientes[] = new Cliente[Funcoes.MAX_CLIENTES]; //aloco a memória necessária
		Funcoes.carregarClientes(clientes);	
		
		Carro carros[] = new Carro[Funcoes.MAX_VEICULOS]; //aloco a memória necessária
		Funcoes.carregarVeiculos(carros);	
		
		//DEBUG
		//Retorna o local onde deveriam estar os arquivos carro.txt e cliente.txt
		//System.out.println(new File(".").getAbsoluteFile());

		String opcao="";
		Scanner scan = new Scanner(System.in);
		do{
			System.out.println("\nDigite a opcao desejada seguida de ENTER:\n");
			System.out.println("1 - Listar frota");
			System.out.println("2 - Listar clientes com os respectivos carros alugados - versão completa");
			System.out.println("3 - Listar clientes com os respectivos carros alugados - versão curta");
			System.out.println("4 - Adicionar cliente");
			System.out.println("5 - Editar cliente");
			System.out.println("6 - Procurar cliente pelo CPF");
			System.out.println("7 - Alugar veículo");
			System.out.println("8 - Listar veículos alugados");
			System.out.println("9 - Encerrar aluguel");				
			System.out.println("0 - Sair do programa");

		String opcoesValidas="0123456789";	
		opcao = scan.nextLine();
		
		//Se eu usar a opção "default" do switch para isso o menu vai ter
		//que ser redesenhado a cada erro
		while (opcoesValidas.indexOf(opcao) == -1) {
			System.out.print("Opção inválida. As opções válidas são ");
			System.out.println(opcoesValidas + ". Tente novamente.");	
			opcao = scan.nextLine();
		}
		
		switch(opcao){
		case "0":			
			System.out.println("Encerrado pelo usuário.");
			break;
		case "1":
			Funcoes.imprimirFrotaDisponivel(carros, true);
			Funcoes.waitForEnter(scan, true);
			break;
		case "2":
			Funcoes.imprimirClientes(clientes, carros);
			Funcoes.waitForEnter(scan, true);
			break;
		case "3":
			Funcoes.imprimirClientesSimplificado(clientes, carros);
			Funcoes.waitForEnter(scan, true);
			break;			
		case "4":
			if(Funcoes.adicionarCliente(clientes, scan)) Funcoes.salvarClientes(clientes);
			Funcoes.waitForEnter(scan, true);
			break;
		case "5":
			if(Funcoes.editarCliente(clientes, carros, scan)) Funcoes.salvarClientes(clientes);
			Funcoes.waitForEnter(scan, true);
			break;	
		case "6":
			String resposta="";
			do {
				System.out.println("Digite o CPF:");
				resposta=scan.nextLine();
				resposta=resposta.replaceAll("\\D",""); //removo tudo o que n�o for num�rico
			} while (resposta.equals("")); 
			String cliente=Funcoes.buscarClientePeloCPF(clientes, resposta);
			if (cliente.isEmpty())System.out.println("Não há cliente cadastrado com este CPF");
			else System.out.println(cliente);
			
			Funcoes.waitForEnter(scan, true);
			break;				
		case "7":
			Funcoes.alugarVeiculo(carros, clientes, scan);
			Funcoes.salvarVeiculos (carros);
			Funcoes.waitForEnter(scan, true);
			break;	
		case "8":
			Funcoes.imprimirAlugados(carros, clientes);
			Funcoes.waitForEnter(scan, true);
			break;	
		case "9":
			Funcoes.encerrarAluguel(carros, clientes, scan);
			Funcoes.salvarVeiculos (carros);			
			Funcoes.waitForEnter(scan, true);
			break;	

		}
		}while(!opcao.contentEquals("0"));
		
		System.out.println("Programa encerrado.");
		scan.close();
		
		}
}