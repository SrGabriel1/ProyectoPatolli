package utileria;

import java.io.Serializable;

public class Dado implements Serializable{

    /**
     * Tira las cañas para el juego.
     *
     * @return Retorna un arreglo de 2 posiciones con el resultado de las cañas
     * donde la posición cero es las cañas con puntos y la posición uno son las
     * cañas con lisas.
     */
    public int[] tirarDados() {
        int[] resultados = new int[2];

        resultados[0] = generaNumeroAleatorio(0, 5);
        resultados[1] = Math.abs(resultados[0] - 5);

        return resultados;
    }

    /**
     * Genera un número aleatorio entre un minimo y un máximo.
     *
     * @param minimo Desde que número minimo se generan los numeros aleatorios.
     * @param maximo Hasta que número máximo se generan los numeros aleatorios.
     * @return retorna un número entero entre un minimo y un máximo dados.
     */
    private int generaNumeroAleatorio(int minimo, int maximo) {
        int num = (int) Math.floor(Math.random() * (maximo - minimo + 1) + (minimo));
        return num;
    }

}
