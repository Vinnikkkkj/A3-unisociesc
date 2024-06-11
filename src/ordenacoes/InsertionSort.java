package ordenacoes;

import ordenacoes.EscolhaMetodo.ProgressCallback;

public class InsertionSort {
    public static void sort(String[] array, ProgressCallback callback) {
        for (int i = 1; i < array.length; i++) {
            String key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
            callback.onProgressUpdate(i);
        }
    }
}
