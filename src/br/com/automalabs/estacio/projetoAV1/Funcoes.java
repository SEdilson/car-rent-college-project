package br.com.automalabs.estacio.projetoAV1;

import java.util.Scanner;

public class Funcoes {
	
	//Em vários lugares eu declaro variáveis dentro de loops por ser mais conveniente.
	//Eu imaginei que poderia ser uma má prática, mas isto aqui me fez mudar de ideia:
	//https://stackoverflow.com/questions/4501482/java-declaring-variables-in-for-loops
	
	//Os métodos e atributos desta classe são declarados como static porque são chamados
	// a partir de main, que é declarado como static. Isso evita o erro:
	//Cannot make a static reference to the non-static method
	// Para evitar isso seria necessário instanciar um objeto desta classe
	// e chamar os métodos do objeto, que é a forma como operamos com Carro e Cliente
	//Em outras palavras, um método static pode ser chamado sem a necessidade de criar uma instância da classe.
	
	public static final String ARQ_VEICULOS="carros.txt";
	public static final String ARQ_CLIENTES="clientes.txt";
	public static final int MAX_VEICULOS=30; 
	public static final int MAX_CLIENTES=20; 
	public static int numClientesCadastrados=0;
	public static int numCarrosCadastrados=0;
	public static int numCarrosAlugados=0;
	
	public static void clrscr(){
	    //limpa a tela. Não funciona enquanto estiver rodando o programa no Eclipse
		System.out.println("Se você está vendo esta mensagem, este programa está rodando");
		System.out.println("no Eclipse. A rotina de apagamento de tela não funciona aqui.");
	    try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (java.io.IOException | InterruptedException ex) {}
	}	
	
	
	public static void waitForEnter(Scanner scan, boolean apagarTela) {

		System.out.println("Tecle ENTER para voltar ao menu...");
		scan.nextLine();
		if (apagarTela) clrscr(); 
	}
	
	static String getDataAtual() {
		java.util.Date date = java.util.Calendar.getInstance().getTime();
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String strDate = dateFormat.format(date);	
        return strDate;
	}
	
	
	public static void imprimirCarroPeloIndice(Carro carros[], int indice) {

		System.out.print("Carro ");
		System.out.print(carros[indice].getRegistroLoc()+" : ");
		System.out.print(carros[indice].getFabricante()+" ");
		System.out.print(carros[indice].getModelo()+" ");
		System.out.print(carros[indice].getMotor()+" ");
		System.out.print(carros[indice].getCor()+" ");
		System.out.print(carros[indice].getTipoCombustivel()+" ");
		System.out.println(carros[indice].getKmRodados()+"Km");
		}
	
	public static void imprimirFrotaDisponivel(Carro carros[], boolean todos) {
	    //Imprime a frota
		for (int i=0; i<(numCarrosCadastrados); i++) {

			boolean alugado= !(carros[i].getAlugadoPor().equalsIgnoreCase(""));
				if (todos || !alugado ) {
					imprimirCarroPeloIndice(carros, i);
				}
			
		}
		
	}

	public static void imprimirAlugados(Carro carros[], Cliente clientes[]) {

		for (int i=0; i<(numCarrosCadastrados); i++) {
			String alugadoPor=carros[i].getAlugadoPor();
			boolean alugado= !(alugadoPor.equalsIgnoreCase(""));
				if (alugado) {
					imprimirCarroPeloIndice(carros, i);
					System.out.print("Alugado para: ");
					System.out.println(clientes[Integer.parseInt(alugadoPor)].getNome());
					System.out.println("");
				}
		
		}
		
	}	
	
	public static void encerrarAluguel(Carro carros[], Cliente clientes[], Scanner scan) {
		imprimirAlugados(carros, clientes);
		
		String resposta="";
		do {
			System.out.println("Digite o código do carro que deseja devolver:");
			resposta=scan.nextLine();
			resposta=resposta.replaceAll("\\D",""); //removo tudo o que não for numérico
		} while (resposta.equals("")|| (Integer.parseInt(resposta)>numCarrosCadastrados)); 
		
		String veiculo= resposta;
		
		boolean achei=false;
		for (int i=0; i<(numCarrosCadastrados); i++) {
			String registroLoc=carros[i].getRegistroLoc();
			
		        if (registroLoc.equalsIgnoreCase(veiculo)) {
		        	
		        	if (!(carros[i].getAlugadoPor().equalsIgnoreCase(""))) {
		        		do {
		        			achei=true;
		        			System.out.println("Qual a kilometragem atual do veículo?");
		        			resposta=scan.nextLine();
		        			resposta=resposta.replaceAll("\\D",""); //removo tudo o que não for numérico
		        			
		        			if (Integer.parseInt(resposta)< carros[i].getKmRodados()) {
		        				System.out.println("Este número é menor que o anterior.");
		        				resposta="";
		        			}
		        		} while (resposta.equals("")); 		        		
		        		carros[i].setKmRodados(Integer.parseInt(resposta));
			        	carros[i].setAlugadoPor("");
		        	}
				}
		}
		if (achei) {
			System.out.println("Aluguel encerrado");
		}else {
			System.out.println("O código fornecido não é de um veículo atualmente alugado.");
		}
		
	}
	
	public static int imprimirCarroPeloRegistro(Carro carros[], String registro) {
	
		for (int i=0; i<(numCarrosCadastrados); i++) {
			String registroLoc=carros[i].getRegistroLoc();
			
		        if (registroLoc.equalsIgnoreCase(registro)) {
		        	imprimirCarroPeloIndice(carros, i);
		        	return i;
		        	
				}
		}
		return -1; //carro não encontrado
		
	}
	
	public static void imprimirClientes(Cliente c[], Carro v[]) {

		for (int i=0; i<(numClientesCadastrados); i++) {
			System.out.println("Nome: " + c[i].getNome());
			System.out.println("Endereço: "+ c[i].getEndereco());
			System.out.println("Telefone: "+ c[i].getTelefone());
			System.out.println("Idade: "+ c[i].getIdade()+" anos");
			System.out.println("CPF: "+ c[i].getCpf());
			System.out.println("RG: "+ c[i].getRg());
			
			
		    for (int j=0; j<numCarrosCadastrados; j++) {
	    	
				if (v[j].getAlugadoPor().equalsIgnoreCase(Integer.toString(i))) {
					System.out.print("Alugando: "); 
				    imprimirCarroPeloIndice(v,j);
				}
		    }				

			System.out.println("");
		}		
		
	}
	
	public static void imprimirClientesSimplificado(Cliente c[], Carro v[]) {

		for (int i=0; i<(numClientesCadastrados); i++) {
			System.out.print( i+1+ " - " + c[i].getNome()+", ");
			System.out.println("CPF: "+ c[i].getCpf());
			
		    for (int j=0; j<numCarrosCadastrados; j++) {
		    	
				if (v[j].getAlugadoPor().equalsIgnoreCase(Integer.toString(i))) {
					System.out.print("      Alugando: "); 
				    imprimirCarroPeloIndice(v,j);
				}
			
		    }				
						
		}		
		
	}
	
	public static String buscarClientePeloCPF(Cliente c[], String cpf) {
		
		String valFornecido=cpf.replaceAll("\\D",""); 
		for (int i=0; i<(numClientesCadastrados); i++) {
			String valCadastrado=c[i].getCpf().replaceAll("\\D",""); 
			if (valFornecido.contentEquals(valCadastrado)) {
			String texto= Integer.toString(i+1).concat(" - ").concat(c[i].getNome());	
			return texto; 
						
			}
		
	}
		return ""; //Se nada foi achado;
	}
	
	public static void alugarVeiculo(Carro carros[], Cliente clientes[], Scanner scan) {
		
		//TODO: Este trecho é idêntico ao de editarCliente. Incorporar em uma função
		imprimirClientesSimplificado(clientes, carros);
		
		String resposta="";
		do {
			System.out.println("Digite o número do cliente:");
			resposta=scan.nextLine();
			resposta=resposta.replaceAll("\\D",""); //removo tudo o que não for numérico
		} while (resposta.equals("")); 
		
		int cliente= Integer.parseInt(resposta)-1;
		//--------------------------------------------------------------------------------
		
		imprimirFrotaDisponivel(carros, false); //com false eu imprimo apenas os carros disponíveis

		resposta="";
		do {
			System.out.println("Digite o código do carro que deseja alugar:");
			resposta=scan.nextLine();
			resposta=resposta.replaceAll("\\D",""); //removo tudo o que não for numérico
		} while (resposta.equals("")|| (Integer.parseInt(resposta)>numCarrosCadastrados)); 
		
		String veiculo= resposta;

		System.out.println(clientes[cliente].getNome());		
		System.out.println("Você escolheu o veículo: ");
		int indexVeiculo=imprimirCarroPeloRegistro(carros, veiculo);
		System.out.println("Confirma o aluguel deste veículo? (s,n) ");
		String opcao=scan.nextLine();
		if (opcao.equalsIgnoreCase("s")) {
			clientes[cliente].setDataAluguel(getDataAtual());	
			//TODO: usar o indice do cliente vai cria problemas quando um cliente for apagado. 
			//O correto é usar um código de cliente
			carros[indexVeiculo].setAlugadoPor(Integer.toString(cliente));
			numCarrosAlugados++;
			System.out.println("Aluguel registrado.");	
		}else System.out.println("Aluguel cancelado.");	
	}
	
	public static void carregarVeiculos(Carro c[]) {
		
		try {
			java.io.FileReader reader = new java.io.FileReader(ARQ_VEICULOS);
		    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(reader);
		    numCarrosCadastrados=Integer.parseInt(bufferedReader.readLine());
		    
		    for (int i=0; i<numCarrosCadastrados; i++) {
		    	Carro car = new Carro();
		    	
		    	String fabricante=bufferedReader.readLine();
		    	String modelo=bufferedReader.readLine();
		    	String motor=bufferedReader.readLine();
		    	String cor=bufferedReader.readLine();
		    	String tipoCombustivel=bufferedReader.readLine();
		    	int kmRodados=Integer.parseInt(bufferedReader.readLine());
		    	String alugadoPor=bufferedReader.readLine();
		    	
				car.setRegistroLoc(Integer.toString(i+1));
				car.setFabricante(fabricante);
				car.setModelo(modelo);
				car.setMotor(motor);
				car.setCor(cor);
				car.setTipoCombustivel(tipoCombustivel);
				car.setKmRodados(kmRodados);
				car.setAlugadoPor(alugadoPor);	
				
				c[i]=car;
				
		    }		    
		    
		    reader.close();
		   } catch (java.io.IOException e) {
		    e.printStackTrace();
		    throw new Error("Erro ao carregar os veículos do arquivo.");
		   }	
	}
	
	public static void carregarClientes(Cliente c[]) {
		
		try {
			java.io.FileReader reader = new java.io.FileReader(ARQ_CLIENTES);
		    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(reader);
		    //A primeira linha do arquivo armazena o número de clientes a ler
		    numClientesCadastrados=Integer.parseInt(bufferedReader.readLine());
		    
		    for (int i=0; i<numClientesCadastrados; i++) {
		    	
		    	Cliente cl= new Cliente();

		    	String nome=bufferedReader.readLine();
		    	String endereco=bufferedReader.readLine();
		    	String telefone=bufferedReader.readLine();
		    	String dataNasc=bufferedReader.readLine();
		    	String cpf=bufferedReader.readLine();
		    	String rg=bufferedReader.readLine();
 
				cl.setNome(nome);
				cl.setEndereco(endereco);
				cl.setTelefone(telefone);
				cl.setDataNasc(dataNasc);
				cl.setCpf(cpf);
				cl.setRg(rg);
			    	
				c[i]=cl;	
		    }
		    reader.close();
		   } catch (java.io.IOException e) {
		    e.printStackTrace();
		    throw new Error("Erro ao carregar os clientes do arquivo.");
		   }	
	}	
	

	public static boolean adicionarCliente(Cliente c[], Scanner scan) {
		if (numClientesCadastrados==MAX_CLIENTES) {
			System.out.println("Já foi atingido o número máximo de clientes: "+MAX_CLIENTES);
			return false;
		}

	
		Cliente cl = new Cliente();
		                                 
		System.out.println("Nome: ");
		while(!cl.setNome(scan.nextLine())) {
			System.out.println("Nome inválido.");
			System.out.println("Nome: ");
		}	
	
		System.out.println("Endereço: ");
		while(!cl.setEndereco(scan.nextLine())) {
			System.out.println("Endereço inválido.");
			System.out.println("Endereço: ");
		}
		
		System.out.println("Telefone com DDD (exemplo: 81 987654321): ");
		while(!cl.setTelefone(scan.nextLine())) {
			System.out.println("Telefone inválido.");
			System.out.println("Telefone com DDD (exemplo: 81 987654321): ");
		}	
		
		System.out.println("Data de Nascimento (dd/mm/aaaa): ");
		while(!cl.setDataNasc(scan.nextLine())) {
			System.out.println("Data inválida.");
			System.out.println("Data de Nascimento (dd/mm/aaaa): ");
		}
		
		System.out.println("CPF: ");
		String resposta= scan.nextLine();
		boolean sucesso=false;
		do {
			String cliente=Funcoes.buscarClientePeloCPF(c, resposta);
			if (!cliente.isEmpty()) {
				
				System.out.print("Já existe um cliente cadastrado com este CPF: ");
				System.out.println(cliente);
				sucesso=false;
			} else sucesso=cl.setCpf(resposta);

		if (!sucesso) {
			System.out.println("CPF inválido.");
			System.out.println("CPF: ");
			resposta= scan.nextLine();	
		}
		}while (!sucesso);
	
		
		System.out.println("RG: ");
		while(!cl.setRg(scan.nextLine())) {
			System.out.println("RG inválido.");
			System.out.println("RG: ");
		}
		
		
		System.out.println("Confirma a inclusão deste cliente? (s,n) ");
		String opcao=scan.nextLine();
		if (opcao.equalsIgnoreCase("s")) {
			int indice= numClientesCadastrados;
			c[indice]=cl; //aplico o objeto inteiro na última posição livre do array
			
			System.out.println("Cliente Cadastrado.");
			
			numClientesCadastrados++;
			return true;
		}else {
			System.out.println("Cadastro cancelado.");
			return false;
		}
	}
	
	public static boolean editarCliente(Cliente c[], Carro car[], Scanner scan) {
	
		Cliente clEditado = new Cliente();
		
		//TODO: esta função recebe o array car[] apenas para poder repassar para a função seguinte
		//Existe um jeito de evitar isso?
		imprimirClientesSimplificado(c, car);
		
		String resposta="";
		do {
			System.out.println("Digite o número do cliente:");
			resposta=scan.nextLine();
			resposta=resposta.replaceAll("\\D",""); //removo tudo o que não for numérico
		} while (resposta.equals("")); 
		
		int numero= Integer.parseInt(resposta)-1;		
		
		System.out.println("O valor atual de cada campo será exibido entre parênteses");
		System.out.println("Se quiser deixar o campo inalterado basta teclar ENTER\n");
		
		String valorAtual= c[numero].getNome();
		System.out.println("Nome ( "+valorAtual+" ): ");
		resposta= scan.nextLine();
		if (resposta.isEmpty()) clEditado.setNome(valorAtual); //preservo o valor atual se o usuário respondeu com ENTER
		else {
			while(!clEditado.setNome(resposta)) {
				System.out.println("Nome inválido.");
				System.out.println("Nome: ");
				resposta= scan.nextLine();
			}
		}
	
		valorAtual= c[numero].getEndereco();
		System.out.println("Endereço: ( "+valorAtual+" ): ");
		resposta= scan.nextLine();
		if (resposta.isEmpty()) clEditado.setEndereco(valorAtual); //preservo o valor atual se o usuário respondeu com ENTER
		else {
			while(!clEditado.setEndereco(resposta)) {
				System.out.println("Endereço inválido.");
				System.out.println("Endereço: ");
				resposta= scan.nextLine();
			}
		}
		
		
		valorAtual= c[numero].getTelefone();
		System.out.println("Telefone: ( "+valorAtual+" ): ");
		resposta= scan.nextLine();
		if (resposta.isEmpty()) clEditado.setTelefone(valorAtual); //preservo o valor atual se o usuário respondeu com ENTER
		else {
			while(!clEditado.setTelefone(resposta)) {
				System.out.println("Telefone inválido.");
				System.out.println("Telefone com DDD (exemplo: 81 987654321): ");
				resposta= scan.nextLine();
			}
		}		
		
		
		valorAtual= c[numero].getDataNasc();
		System.out.println("Data de Nascimento (dd/mm/aaaa): ( "+valorAtual+" ): ");
		resposta= scan.nextLine();
		if (resposta.isEmpty()) clEditado.setDataNasc(valorAtual); //preservo o valor atual se o usuário respondeu com ENTER
		else {
			while(!clEditado.setDataNasc(resposta)) {
				System.out.println("Data inválida.");
				System.out.println("Data de Nascimento (dd/mm/aaaa): ");
				resposta= scan.nextLine();
			}
		}
		
		valorAtual= c[numero].getCpf();
		System.out.println("CPF: ( "+valorAtual+" ): ");
		resposta= scan.nextLine();
		if (resposta.isEmpty()) clEditado.setCpf(valorAtual); //preservo o valor atual se o usuário respondeu com ENTER
		else {
			boolean sucesso=false;
			do {
				String cliente=Funcoes.buscarClientePeloCPF(c, resposta);
				if (!cliente.isEmpty()) {
					
					System.out.print("Já existe um cliente cadastrado com este CPF: ");
					System.out.println(cliente);
					sucesso=false;
				} else sucesso=clEditado.setCpf(resposta);
				
			if (!sucesso) {
				System.out.println("CPF inválido.");
				System.out.println("CPF: ");
				resposta= scan.nextLine();	
			}
			}while (!sucesso);

		}		
		
		valorAtual= c[numero].getRg();
		System.out.println("RG: ( "+valorAtual+" ): ");
		resposta= scan.nextLine();
		if (resposta.isEmpty()) clEditado.setRg(valorAtual); //preservo o valor atual se o usuário respondeu com ENTER
		else {
			while(!clEditado.setRg(resposta)) {
				System.out.println("RG inválido.");
				System.out.println("RG: ");
				resposta= scan.nextLine();
			}
		}	
		
		System.out.println("Confirma as alterações feitas? (s,n) ");
		String opcao=scan.nextLine();
		if (opcao.equalsIgnoreCase("s")) {

			c[numero]=clEditado; //substituo o objeto inteiro 
			
			System.out.println("Alterações aplicadas.");
			return true;
		}else {
			System.out.println("Alterações descartadas.");
			return false;
		}
	}	
	
	/*
	public static int obterNovoCodCliente(Cliente[] c) {
		if (numClientesCadastrados==0) return 1;
		else
		{
			int codUltimoCliente= c[numClientesCadastrados-1].getCodCliente(); 
			return codUltimoCliente+1;
		}
		
		
	}
	*/
	public static void salvarClientes (Cliente[] c){
	  
	try {
	java.io.BufferedWriter outputWriter = null;
	  outputWriter = new java.io.BufferedWriter(new java.io.FileWriter(ARQ_CLIENTES));
	  outputWriter.write(Integer.toString(numClientesCadastrados));
	  outputWriter.newLine(); //A primeira linha registra o número de elementos
	  for (int i = 0; i < numClientesCadastrados; i++) {

	    outputWriter.write(c[i].getNome());
	    outputWriter.newLine();
	    outputWriter.write(c[i].getEndereco());
	    outputWriter.newLine();
	    outputWriter.write(c[i].getTelefone());
	    outputWriter.newLine();
	    outputWriter.write(c[i].getDataNasc());
	    outputWriter.newLine();
	    outputWriter.write(c[i].getCpf());
	    outputWriter.newLine();
	    outputWriter.write(c[i].getRg());
	    outputWriter.newLine();
	  }
	  outputWriter.flush();  
	  outputWriter.close();  
	   } catch (java.io.IOException e) {
		    e.printStackTrace();
		   }
	}


	public static void salvarVeiculos (Carro[] v){
	  
	try {
		java.io.BufferedWriter outputWriter = null;
	  outputWriter = new java.io.BufferedWriter(new java.io.FileWriter(ARQ_VEICULOS));
	  outputWriter.write(Integer.toString(numCarrosCadastrados));
	  outputWriter.newLine(); //A primeira linha registra o número de elementos
	  for (int i = 0; i < numCarrosCadastrados; i++) {

	    outputWriter.write(v[i].getFabricante());
	    outputWriter.newLine();
	    outputWriter.write(v[i].getModelo());
	    outputWriter.newLine();
	    outputWriter.write(v[i].getMotor());
	    outputWriter.newLine();
	    outputWriter.write(v[i].getCor());
	    outputWriter.newLine();
	    outputWriter.write(v[i].getTipoCombustivel());
	    outputWriter.newLine();
	    outputWriter.write(Integer.toString(v[i].getKmRodados()));
	    outputWriter.newLine();
	    outputWriter.write(v[i].getAlugadoPor());
	    outputWriter.newLine();
	  }
	  outputWriter.flush();  
	  outputWriter.close();  
	   } catch (java.io.IOException e) {
		    e.printStackTrace();
		   }
	}
}
