package ordenacoes;

import pesquisas.*;

public class EscolhaMetodo {
	
	public interface ProgressCallback {
        void onProgressUpdate(int progress);
    }

	public void ordenar(String[] array, String sortType, ProgressCallback callback) {
        switch (sortType) {
            case "Insertion Sort":
                InsertionSort.sort(array, callback);
                break;
            case "Quick Sort":
                QuickSort.sort(array, callback);
                break;
            case "Bubble Sort":
                BubbleSort.sort(array, callback);
                break;
            default:
                throw new IllegalArgumentException("Tipo de ordenação desconhecido: " + sortType);
        }
    }

	public String pesquisar(String[] array, String valor, String sortType) {
	    MetodoPesquisa metodoPesquisa;

	    if ("Linear".equals(sortType)) {
	        metodoPesquisa = new Linear();
	    } else if ("Binaria".equals(sortType)) {
	        metodoPesquisa = new Binaria();
	    } else {
	        System.out.println("Invalid choice. Using Linear Search by default.");
	        metodoPesquisa = new Linear();
	    }

	    int index = metodoPesquisa.pesquisar(array, valor);

	    if (index != -1) {
	        return "Valor encontrado no index " + index;
	    } else {
	        return "Valor não encontrado no arquivo informado";
	    }
	}

}
