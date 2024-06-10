package ProjetoOrdenaçãoEPesquisa;

public class PesquisaBinaria implements MetodoPesquisa {
    @Override
    public int pesquisar(String[] array, String valor) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = array[mid].compareTo(valor);

            if (comparison == 0) {
                return mid;
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Valor não encontrado
    }
}
