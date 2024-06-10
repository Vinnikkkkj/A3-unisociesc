package ordenacoes;

import java.util.Scanner;

public class EscolhaMetodo {

    public void ordenar(String[] array, Scanner scanner) {
        System.out.println("Choose the sorting method:");
        System.out.println("1. Insertion Sort");
        System.out.println("2. Bubble Sort");
        System.out.println("3. Quick Sort");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                InsertionSort.sort(array);
                break;
            case 2:
                BubbleSort.sort(array);
                break;
            case 3:
                QuickSort.sort(array);
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Insertion Sort.");
                InsertionSort.sort(array);
                break;
        }
    }

    public void pesquisar(String[] array, String valor, Scanner scanner) {
        System.out.println("Choose the search method:");
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");

        int escolha = scanner.nextInt();
        MetodoPesquisa metodoPesquisa;

        if (escolha == 1) {
            metodoPesquisa = new PesquisaLinear();
        } else if (escolha == 2) {
            metodoPesquisa = new PesquisaBinaria();
        } else {
            System.out.println("Invalid choice. Using Linear Search by default.");
            metodoPesquisa = new PesquisaLinear();
        }

        int index = metodoPesquisa.pesquisar(array, valor);

        if (index != -1) {
            System.out.println("Value found at index: " + index);
        } else {
            System.out.println("Value not found.");
        }
    }
}
