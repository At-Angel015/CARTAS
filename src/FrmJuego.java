import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {
    private JPanel pnlJugador1, pnlJugador2;
    private Jugador jugador1 = new Jugador();
    private Jugador jugador2 = new Jugador();
    private JTabbedPane tpJugadores;

    public FrmJuego() {
        setSize(500, 300);
        setTitle("Juguemos al Apuntado!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 50, 470, 200);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(0, 255, 0));
        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));

        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);

        // eventos
        btnRepartir.addActionListener(e -> {
            jugador1.repartir();
            jugador2.repartir();
            jugador1.mostrar(pnlJugador1);
            jugador2.mostrar(pnlJugador2);
        });

        btnVerificar.addActionListener(e -> {
            verificar();
        });

    }

    private void verificar(){
        // Obtener el jugador actual según la pestaña seleccionada
        Jugador jugadorActual;
        String nombreJugador;
        
        if(tpJugadores.getSelectedIndex()==0){
            jugadorActual = jugador1;
            nombreJugador = "Martín Estrada Contreras";
        }
        else{
            jugadorActual = jugador2;
            nombreJugador = "Raúl Vidal";
        }
        
        // Mostrar resultados
        StringBuilder resultado = new StringBuilder();
        resultado.append("Jugador: ").append(nombreJugador).append("\n\n");
        
        // Grupos: Pares, Ternas, Cuartas, Quintas, Sextas, etc.
        resultado.append("=== GRUPOS (PARES, TERNAS, CUARTAS, QUINTAS, SEXTAS) ===\n");
        String grupos = jugadorActual.getGrupos();
        if(grupos.isEmpty() || grupos.trim().isEmpty()){
            resultado.append("No se encontraron grupos\n");
        } else {
            resultado.append(grupos);
        }
        resultado.append("\n");
        
        // Escalera de la misma pinta
        resultado.append("=== ESCALERA DE LA MISMA PINTA ===\n");
        resultado.append(jugadorActual.getEscaleraMismaPinta()).append("\n");
        
        // Puntaje
        resultado.append("=== PUNTAJE ===\n");
        resultado.append("Puntaje total: ").append(jugadorActual.getPuntaje());
        
        JOptionPane.showMessageDialog(null, resultado.toString());
    }

}