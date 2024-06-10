package ordenacoes

public class InsertionSort {
    public static void sort(String[] array) {
        for (int i = 1; i < array.length; i++) {
            String current = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(current) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
    }
}
