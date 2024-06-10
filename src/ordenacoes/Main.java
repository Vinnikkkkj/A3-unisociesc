package ordenacoes;

import java.util.Scanner;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Programa de Classificação e Pesquisa!");
        System.out.print("Digite o caminho para o arquivo: ");
        String path = scanner.nextLine();

        try {
            List<String> data = readStringsFromFile(path);
            String[] array = data.toArray(new String[0]);

            EscolhaMetodo escolhaMetodo = new EscolhaMetodo();
            escolhaMetodo.ordenar(array, scanner);

            System.out.println("Sorted data: " + Arrays.toString(array));

            System.out.print("Enter the value to search: ");
            String valor = scanner.next();
            escolhaMetodo.pesquisar(array, valor, scanner);

        } catch (IOException e) {
            System.out.println("Failed to read the file: " + e.getMessage());
        }
    }

    private static List<String> readStringsFromFile(String path) throws IOException {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.trim());
                }
            }
        }
        return data;
    }
}
