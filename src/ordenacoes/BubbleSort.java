package ordenacoes;

import ordenacoes.EscolhaMetodo.ProgressCallback;

public class BubbleSort {
    public static void sort(String[] array, ProgressCallback callback) {
        boolean swapped;
        int n = array.length;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1].compareTo(array[i]) > 0) {
                    String temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                    swapped = true;
                }
            }
            n--;
            callback.onProgressUpdate(array.length - n);
        } while (swapped);
    }
}
