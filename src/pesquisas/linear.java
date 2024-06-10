package ProjetoOrdenaçãoEPesquisa;

public class PesquisaLinear implements MetodoPesquisa {
    @Override
    public int pesquisar(String[] array, String valor) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(valor)) {
                return i;
            }
        }
        return -1; // Valor não encontrado
    }
}
