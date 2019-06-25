import java.util.Locale;
import java.util.Scanner;

public class Trabalho_2_Perceptron {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		sc.useLocale(Locale.ENGLISH);
		Locale.setDefault(new Locale("en", "US"));
		
		double matrizW_pesos[][], matrizEntrada[][], matrizT[][], n_taxaDeAprendizado, limiar, yEnt;
		int contador, f, epocas = 0;
		boolean validacao = true;
		
		System.out.print("Digite a taxa de aprendizado ( 0 < n <= 1 ): ");
		n_taxaDeAprendizado = sc.nextDouble();
		System.out.print("Digite o limiar: ");
		limiar = sc.nextDouble();
		
		matrizW_pesos = new double[][] {
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 },
			{ 0, 0, 0, 0 }
		};
		
		matrizEntrada = new double[][] {
			{ 1, 1, 1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, 1, 1, 1 },
			{ -1, 1, -1, 1, 1, -1, -1, 1, -1, -1, 1, -1, 1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, 1, -1, 1, 1, -1, -1, 1, 1, 1, 1, 1 },
			{ 1, -1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1, 1 },
			{ 1, 1, 1, 1, -1, -1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1 },
			{ 1, -1, -1, 1, -1, -1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, 1 },
			{ 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1 }
		};
		
		matrizT = new double[][] {
			{ 1, -1, -1, -1 },
			{ -1, 1, -1, -1 },
			{ -1, -1, 1, -1 },
			{ -1, -1, -1, 1 },
			{ 1, 1, -1, -1 },
			{ -1, 1, 1, -1 },
			{ -1, -1, 1, 1 },
			{ 1, 1, 1, -1 },
			{ -1, 1, 1, 1 },
			{ 1, -1, -1, 1 }
		};
		
		int vetorF[] = new int[4];
		
		while (validacao) {
			contador = 0;

			for (int i = 0; i < matrizEntrada.length; i++) {

				for (int a = 0; a < 4; a++) {

					yEnt = 0;

					for (int j = 0; j < 16; j++) {
						yEnt += matrizEntrada[i][j] * matrizW_pesos[j][a];
					}

					if (yEnt > limiar) {
						vetorF[a] = 1;
					} else if ((-1 * limiar) <= yEnt && yEnt <= limiar) {
						vetorF[a] = 0;
					} else {
						vetorF[a] = -1;
					}					
				}				
//				
//				TESTE 01 => FALHOU! (MEXENDO OS PESOS POR NEURONIO) ==============================>
//				
//				for (int g = 0; g < 4; g++) {
//					
//					if (vetorF[g] != matrizT[i][g]) {
//						// DeltaW = n . [ T – f(Yent) ] . X => Fórmula de variação dos pesos
//
//						for (int x = 0; x < 16; x++) {
//							matrizW_pesos[x][g] += n_taxaDeAprendizado * (matrizT[i][g] - vetorF[g]) * matrizEntrada[i][x];
//						}
//
//					} else {
//						contador++;
//					}
//					
//				}
	
				if (vetorF[0] == matrizT[i][0] && vetorF[1] == matrizT[i][1] && vetorF[2] == matrizT[i][2]
						&& vetorF[3] == matrizT[i][3]) {

					contador++;
				} else {

					for (int t = 0; t < 4; t++) {
						for (int x = 0; x < 16; x++) {
							matrizW_pesos[x][t] += n_taxaDeAprendizado * (matrizT[i][t] - vetorF[t])
									* matrizEntrada[i][x];
						}
					}
				}
			}

			if (contador == 10) {
				validacao = false;
			}

			epocas++;
			System.out.println(epocas);
		}

		for (int q = 0; q < 16; q++) {
			for (int t = 0; t < 4; t++) {
				System.out.print(matrizW_pesos[q][t] + " ");
			}
			System.out.println();
		}

		System.out.println("\nQuantidade de épocas: " + epocas);

		System.out.println("________________________________________________________________");
		System.out.println("FASE DE TESTE DO APRENDIZADO:");

		double vetorEntradaTeste[], yentTeste = 0;
		vetorEntradaTeste = new double[16];
		int vetorFTeste[] = new int[4];

		for (int i = 0; i < vetorEntradaTeste.length; i++) {
			vetorEntradaTeste[i] = sc.nextInt();
		}

		for (int j = 0; j < 4; j++) {
			yentTeste = 0;
			
			for (int r = 0; r < 16; r++) {
				yentTeste += vetorEntradaTeste[r] * matrizW_pesos[r][j];
			}

			if (yentTeste > limiar) {
				vetorFTeste[j] = 1;
			} else if ((-1 * limiar) <= yentTeste && yentTeste <= limiar) {
				vetorFTeste[j] = 0;
			} else {
				vetorFTeste[j] = -1;
			}
		}
		
		imprimeResultado(vetorFTeste);
	}
	
	public static void imprimeResultado(int vetorSaida[]) {
		if(vetorSaida[0] == 1 && vetorSaida[1] == -1 && vetorSaida[2] == -1 && vetorSaida[3] == -1) {
			System.out.println("Padrão do dígito 0.");
		} else if(vetorSaida[0] == -1 && vetorSaida[1] == 1 && vetorSaida[2] == -1 && vetorSaida[3] == -1) {
			System.out.println("Padrão do dígito 1.");
		} else if(vetorSaida[0] == -1 && vetorSaida[1] == -1 && vetorSaida[2] == 1 && vetorSaida[3] == -1) {
			System.out.println("Padrão do dígito 2.");
		} else if(vetorSaida[0] == -1 && vetorSaida[1] == -1 && vetorSaida[2] == -1 && vetorSaida[3] == 1) {
			System.out.println("Padrão do dígito 3.");
		} else if(vetorSaida[0] == 1 && vetorSaida[1] == 1 && vetorSaida[2] == -1 && vetorSaida[3] == -1) {
			System.out.println("Padrão do dígito 4.");
		} else if(vetorSaida[0] == -1 && vetorSaida[1] == 1 && vetorSaida[2] == 1 && vetorSaida[3] == -1) {
			System.out.println("Padrão do dígito 5.");
		} else if(vetorSaida[0] == -1 && vetorSaida[1] == -1 && vetorSaida[2] == 1 && vetorSaida[3] == 1) {
			System.out.println("Padrão do dígito 6.");
		} else if(vetorSaida[0] == 1 && vetorSaida[1] == 1 && vetorSaida[2] == 1 && vetorSaida[3] == -1) {
			System.out.println("Padrão do dígito 7.");
		} else if(vetorSaida[0] == -1 && vetorSaida[1] == 1 && vetorSaida[2] == 1 && vetorSaida[3] == 1) {
			System.out.println("Padrão do dígito 8.");
		} else if(vetorSaida[0] == 1 && vetorSaida[1] == -1 && vetorSaida[2] == -1 && vetorSaida[3] == 1) {
			System.out.println("Padrão do dígito 9.");
		} else {
			System.out.println("Não bateu com nenhuma das saídas.");
			System.out.println(vetorSaida[0] + " " + vetorSaida[1] + " " + vetorSaida[2] + " " + vetorSaida[3]);
		}
	}
}
