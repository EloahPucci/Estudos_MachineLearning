import java.util.Locale;
import java.util.Scanner;

public class RedesNeurais_Perceptron {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		sc.useLocale(Locale.ENGLISH);
		Locale.setDefault(new Locale("en", "US"));

		double vetorW_pesos[], tabelaVerdadeAnd[][], vetorT_resultadoEsperado[], n_taxaDeAprendizado, yEnt, limiar;
		int f, contador, contEpoca = 0, vetorF[];
		boolean validacao = true;

		System.out.print("Digite a taxa de aprendizado ( 0 < n <= 1 ): ");
		n_taxaDeAprendizado = sc.nextDouble();
		System.out.print("Digite o limiar: ");
		limiar = sc.nextDouble();		

		vetorW_pesos = new double[] { 0, 0, 0, 0 };
		tabelaVerdadeAnd = new double[][] {
			{ 1, 1, 1, 1 },
			{ 1, 1, 0, 1 },
			{ 1, 0, 1, 1 },
			{ 1, 0, 0, 1 },
			{ 0, 1, 1, 1 },
			{ 0, 1, 0, 1 },
			{ 0, 0, 1, 1 },
			{ 0, 0, 0, 1 }
		};
		vetorT_resultadoEsperado = escolhaDaOperacao();
		vetorF = new int[8];

		while (validacao) {			
			contador = 0;

			for (int i = 0; i < tabelaVerdadeAnd.length; i++) {
				yEnt = 0;

				for (int j = 0; j < 4; j++) {
					yEnt += tabelaVerdadeAnd[i][j] * vetorW_pesos[j];
				}

				if (yEnt > limiar) {
					f = 1;
					vetorF[i] = f;
				} else if ((-1 * limiar) <= yEnt && yEnt <= limiar) {
					f = 0;
					vetorF[i] = f;
				} else {
					f = -1;
					vetorF[i] = f;
				}

				if (f != vetorT_resultadoEsperado[i]) {
					// DeltaW = n . [ T – f(Yent) ] . X => Fórmula de variação dos pesos

					for (int a = 0; a < vetorW_pesos.length; a++) {
						vetorW_pesos[a] += n_taxaDeAprendizado * (vetorT_resultadoEsperado[i] - f)
								* tabelaVerdadeAnd[i][a];
					}
				} else {
					contador++;
				}
			}

			if (contador == 8) {
				validacao = false;

				System.out.print("Pesos corretos: ");
				for (double aux : vetorW_pesos) {
					System.out.print(aux + " ");
				}
				System.out.println();

				for (int aux : vetorF) {
					System.out.print(aux + " ");
				}
			}
			contEpoca++;
		}
		System.out.println("\n");
		System.out.println("Épocas (quantidade de vezes que o while rodou): " + contEpoca);
		
		int vetorXteste[];
		double yentTeste = 0;
		
		System.out.println();
		System.out.println("___________________________________________________");
		System.out.println("Teste:\nDigite as entradas (0 ou 1):");

		vetorXteste = new int[4];
		
		for(int i = 0; i < vetorXteste.length; i++) {
			if(i == 3) {
				vetorXteste[i] = 1;
			} else {
				System.out.print("Digite a entrada " + (i + 1) + ":");
				vetorXteste[i] = sc.nextInt();
			}
		}
		
		for(int i = 0; i < vetorW_pesos.length; i++) {
			yentTeste += vetorXteste[i] * vetorW_pesos[i];
		}
		
		if (yentTeste > limiar) {
			System.out.println("Resultado: " + 1);
		} else if ((-1 * limiar) <= yentTeste && yentTeste <= limiar) {
			System.out.println("Resultado: " + 0);
		} else {
			System.out.println("Resultado: " + -1);
		}
		
		sc.close();
	}

	public static double[] AND() {

		double vetor[] = new double[] { 1, 0, 0, 0, 0, 0, 0, 0 };
		return vetor;
	}
	
	public static double[] NAND() {

		double vetor[] = new double[] { 0, 1, 1, 1, 1, 1, 1, 1 };
		return vetor;
	}

	public static double[] OR() {

		double vetor[] = new double[] { 1, 1, 1, 1, 1, 1, 1, 0 };
		return vetor;
	}
	
	public static double[] NOR() {

		double vetor[] = new double[] { 0, 0, 0, 0, 0, 0, 0, 1 };
		return vetor;
	}
	
//	public static double[] XOR() {
//
//		double vetor[] = new double[] { 1, 0, 0, 1, 0, 1, 1, 0 };
//		return vetor;
//	}

	public static double[] escolhaDaOperacao() {
		Scanner sc = new Scanner(System.in);

		int opcao;

		System.out.print("\nQual operação lógica você deseja executar? Digite:\n1-AND 2-OR 3-NAND " + "4-NOR\n=>");
		opcao = sc.nextInt();

		switch (opcao) {
		case 1:
			return AND();
		case 2:
			return OR();
		case 3:
			return NAND();
		case 4:
			return NOR();
//		case 5:
//			return XOR();
		default:
			return null;
		}
	}
}
