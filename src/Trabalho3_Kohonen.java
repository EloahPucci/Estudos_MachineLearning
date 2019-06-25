import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Trabalho3_Kohonen {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		sc.useLocale(Locale.ENGLISH);
		Locale.setDefault(new Locale("en", "US"));
		
		double matrizW_pesos[][], matrizEntrada[][], matrizT[][], n_taxaDeAprendizado, d_j[], d_j_menor;
		int epocas = 0, raioVizinhanca, d_j_Vencedor;
		
		System.out.print("Digite a taxa de aprendizado ( 0 < n <= 1 ): ");
		n_taxaDeAprendizado = sc.nextDouble();
		
		System.out.print("Digite o raio de vizinhança (Número inteiro entre 0 e 3): ");
		raioVizinhanca = sc.nextInt();
		
		System.out.print("Serão quantas épocas? ");
		epocas = sc.nextInt();
		
		matrizW_pesos = new double[16][4];
		
		for(int y = 0; y < 16; y++) {
			for(int h = 0; h < 4; h++) {
				matrizW_pesos[y][h] = numeroAleatorio();
			}
		}
		
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
		
		while(epocas >= 0) {
			
			for(int i = 0; i < matrizEntrada.length; i++) {
				
				d_j = new double[] {0, 0, 0, 0};
				
				for(int j = 0; j < 4; j++) {
					//Fórmula para soma => D(J) = Σ ( W – X )2
					
					for(int a = 0; a < 16; a++) {
						d_j[j] += Math.pow((matrizW_pesos[a][j] - matrizEntrada[i][a]), 2);
					}
				}
				
				//======> Localizando a posição do D(J) vencedor:
				
				d_j_Vencedor = 0;
				d_j_menor = d_j[0];
				for(int t = 1; t < 4; t++) {
					if(d_j[t] < d_j_menor) {
						d_j_menor = d_j[t];
						d_j_Vencedor = t;
					}
				}
				
				//Calcula a quantidade de neurônios que serão alterados
				int neuroniosQueSeraoAlterados = raioVizinhanca * 2 + 1;
				
				//Altera o neurônio vencedor e seus vizinhos
				for(int r = d_j_Vencedor - raioVizinhanca; neuroniosQueSeraoAlterados > 0; r++) {
					
					if(r >= 0 && r < 4) {
						//Fórmula para alteração dos pesos ====> W = W + η ·( X – W )
						
						for(int q = 0; q < 16; q++) {
							matrizW_pesos[q][r] += n_taxaDeAprendizado * (matrizEntrada[i][q] - matrizW_pesos[q][r]);
						}
					}
					
					neuroniosQueSeraoAlterados--;
				}
			}
			
			if(n_taxaDeAprendizado > 0) {
				n_taxaDeAprendizado *= 0.5;
			}
			
			if(raioVizinhanca >= 0) {
				raioVizinhanca -= 1;
			}
			
			epocas--;
		}
		
		System.out.printf("\nTabela de pesos (W):\n");
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(matrizW_pesos[i][j] + "   ");
			}
			System.out.println();
		}
		
		System.out.println("______________________________________________________________________________________________________");
		System.out.println("FASE DE TESTE DO APRENDIZADO:");
		
		double vetorEntradaTeste[], d_j_Teste[];
		vetorEntradaTeste = new double[16];
		String resp = "";
		int posicoes[] = new int[4];

		for (int r = 0; r < 2; r++) {

			System.out.print("\nDigite o padrão de entrada " + (r + 1) + ": ");
			for (int i = 0; i < vetorEntradaTeste.length; i++) {
				vetorEntradaTeste[i] = sc.nextInt();
			}

			d_j_Teste = new double[] { 0, 0, 0, 0 };
			List<Double> list_d_j_Teste = new ArrayList<Double>();

			System.out.println("Vetor D(J) do padrão " + (r + 1) + ": ");
			for (int j = 0; j < 4; j++) {
				// Fórmula para soma => D(J) = Σ ( W – X )2

				for (int a = 0; a < 16; a++) {
					d_j_Teste[j] += Math.pow((matrizW_pesos[a][j] - vetorEntradaTeste[a]), 2);
				}
				
				list_d_j_Teste.add(d_j_Teste[j]);
				System.out.print(d_j_Teste[j] + " ");
			}
			
			Collections.sort(list_d_j_Teste);
			
			System.out.println();
			
			for(int a = 0; a < 4; a++) {
				for(int b = 0; b < 4; b++) {
					if(d_j_Teste[b] == list_d_j_Teste.get(a)) {
						posicoes[b] = a+1;
					}
				}
			}
			
			System.out.println("\nImprimindo o list teste " + (r + 1) + ": ");
			for(double aux: list_d_j_Teste) {
				System.out.print(aux + " ");
			}
			
			System.out.println();
			
			System.out.println("\nImprimindo as ordens " + (r + 1) + ": ");
			for(int c = 0; c < 4; c++) {
				System.out.print(posicoes[c] + "° ");
			}
			
			System.out.println("\n_____________________________________");
		}
	}

	public static double numeroAleatorio() {

		Random rand = new Random();
		double randomNum = rand.nextDouble() * 2 - 1;

		return randomNum;
	}
}

