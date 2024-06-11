package ordenacoes;

import ordenacoes.EscolhaMetodo.ProgressCallback;

public class QuickSort {
    public static void sort(String[] array, ProgressCallback callback) {
        quickSort(array, 0, array.length - 1, callback);
    }

    private static void quickSort(String[] array, int low, int high, ProgressCallback callback) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1, callback);
            quickSort(array, pivotIndex + 1, high, callback);
            callback.onProgressUpdate(high);
        }
    }

    private static int partition(String[] array, int low, int high) {
        String pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) < 0) {
                i++;
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        String temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}
