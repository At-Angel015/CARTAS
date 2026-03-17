import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Carta {
    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        String nombreImagen = "imagenes/Carta" + indice + ".jpg";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(nombreImagen));

        JLabel lblCarta = new JLabel(imgCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());

        pnl.add(lblCarta);

        lblCarta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JOptionPane.showMessageDialog(null, getNombre() + " de " + getPinta());
            }
        });

    }

    public Pinta getPinta() {
        if (indice <= 13)
            return Pinta.TREBOL;
        if (indice <= 26)
            return Pinta.PICA;
        if (indice <= 39)
            return Pinta.CORAZON;
        return Pinta.DIAMANTE;
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0)
            residuo = 13;
        return NombreCarta.values()[residuo - 1];
    }

    /**
     * Retorna el valor numérico de la carta para el cálculo del puntaje.
     * AS, JACK, QUEEN, KING tienen valor de 10.
     * Las demás cartas tienen su valor numérico (2-10).
     */
    public int getValor() {
        int ordinal = getNombre().ordinal() + 1; // AS=1, DOS=2, ..., KING=13
        // AS, JACK, QUEEN, KING = 10 puntos
        if (ordinal == 1 || ordinal >= 11) {
            return 10;
        }
        return ordinal;
    }
}
