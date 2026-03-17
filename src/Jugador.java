import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN_SUPERIOR = 10;
    private final int MARGEN_IZQUIERDA = 10;
    private final int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.setLayout(null);
        pnl.removeAll();
        int posicion = MARGEN_IZQUIERDA + DISTANCIA * (TOTAL_CARTAS - 1);
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN_SUPERIOR);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos(){
        int[] contadores=new int[NombreCarta.values().length];

        for(Carta carta:cartas){
            contadores[carta.getNombre().ordinal()]++;
        }

        String resultado="";
        for(int i=0;i<contadores.length;i++){
            if(contadores[i]>=2){
                resultado+=Grupo.values()[contadores[i]]+" de "+NombreCarta.values()[i]+"\n";
            }
        }

        return resultado;
    }

    /**
     * Ejercicio 1: Detecta escaleras de la misma pinta (secuencias de cartas consecutivas
     * del mismo palo). Por ejemplo: 10, J, Q, K de pica.
     * @return String con las escaleras encontradas o mensaje indicando que no hay
     */
    public String getEscaleraMismaPinta() {
        // Crear un arreglo de valores numéricos y pintas para ordenar
        int[] valores = new int[cartas.length];
        Pinta[] pintas = new Pinta[cartas.length];
        
        for (int i = 0; i < cartas.length; i++) {
            valores[i] = cartas[i].getNombre().ordinal() + 1; // AS=1, ..., KING=13
            pintas[i] = cartas[i].getPinta();
        }

        // Ordenar burbuja por valor
        for (int i = 0; i < valores.length - 1; i++) {
            for (int j = 0; j < valores.length - i - 1; j++) {
                if (valores[j] > valores[j + 1]) {
                    // Intercambiar valores
                    int tempVal = valores[j];
                    valores[j] = valores[j + 1];
                    valores[j + 1] = tempVal;
                    // Intercambiar pintas correspondientemente
                    Pinta tempPinta = pintas[j];
                    pintas[j] = pintas[j + 1];
                    pintas[j + 1] = tempPinta;
                }
            }
        }

        // Buscar escaleras: 3 o más cartas consecutivas de la misma pinta
        StringBuilder resultado = new StringBuilder();
        
        for (Pinta pinta : Pinta.values()) {
            // Obtener los valores de esta pinta
            java.util.List<Integer> valoresPinta = new java.util.ArrayList<>();
            
            for (int i = 0; i < valores.length; i++) {
                if (pintas[i] == pinta) {
                    valoresPinta.add(valores[i]);
                }
            }
            
            // Ordenar los valores de esta pinta
            java.util.Collections.sort(valoresPinta);
            
            // Buscar secuencias consecutivas
            if (valoresPinta.size() >= 3) {
                int secuenciaInicio = 0;
                int secuenciaActual = 1;
                
                for (int i = 1; i < valoresPinta.size(); i++) {
                    if (valoresPinta.get(i) == valoresPinta.get(i - 1) + 1) {
                        secuenciaActual++;
                    } else {
                        // Fin de una secuencia, verificarla
                        if (secuenciaActual >= 3) {
                            resultado.append("Escalera de ").append(secuenciaActual).append(" cartas de ").append(pinta).append(": ");
                            for (int k = 0; k < secuenciaActual; k++) {
                                resultado.append(NombreCarta.values()[valoresPinta.get(secuenciaInicio + k) - 1]);
                                if (k < secuenciaActual - 1) {
                                    resultado.append(", ");
                                }
                            }
                            resultado.append("\n");
                        }
                        secuenciaInicio = i;
                        secuenciaActual = 1;
                    }
                }
                
                // Verificar la última secuencia
                if (secuenciaActual >= 3) {
                    resultado.append("Escalera de ").append(secuenciaActual).append(" cartas de ").append(pinta).append(": ");
                    for (int k = 0; k < secuenciaActual; k++) {
                        resultado.append(NombreCarta.values()[valoresPinta.get(secuenciaInicio + k) - 1]);
                        if (k < secuenciaActual - 1) {
                            resultado.append(", ");
                        }
                    }
                    resultado.append("\n");
                }
            }
        }

        if (resultado.length() == 0) {
            return "No se encontraron escaleras de la misma pinta";
        }
        
        return resultado.toString();
    }

    /**
     * Ejercicio 2: Calcula el puntaje del jugador considerando únicamente las cartas
     * que NO conforman figuras (grupos). 
     * - AS, JACK, QUEEN, KING tienen valor de 10 puntos
     * - Las demás cartas conservan su valor numérico
     * @return El puntaje total
     */
    public int getPuntaje() {
        // Contar cuántas veces aparece cada nombre de carta (para identificar figuras)
        int[] contadores = new int[NombreCarta.values().length];
        
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // Identificar qué cartas están en figuras (aparecen 2 o más veces)
        boolean[] enFigura = new boolean[cartas.length];
        for (int i = 0; i < cartas.length; i++) {
            int ordinal = cartas[i].getNombre().ordinal();
            enFigura[i] = contadores[ordinal] >= 2;
        }

        // Sumar el valor de las cartas que NO están en figuras
        int puntaje = 0;
        for (int i = 0; i < cartas.length; i++) {
            if (!enFigura[i]) {
                puntaje += cartas[i].getValor();
            }
        }

        return puntaje;
    }
}
