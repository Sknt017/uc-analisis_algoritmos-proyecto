import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// ==========================================
// 1. CLASE CONTENEDORA DE MÉTRICAS DE EJECUCIÓN
// ==========================================
class ExecutionMetrics {
    private final String algorithmName;
    private final long executionTimeNs;
    private final long comparisonCount;
    private final long swapCount;
    private final long memoryUsedBytes;
    private final int arraySize;

    public ExecutionMetrics(String algorithmName, long executionTimeNs, long comparisonCount, long swapCount, long memoryUsedBytes, int arraySize) {
        this.algorithmName = algorithmName;
        this.executionTimeNs = executionTimeNs;
        this.comparisonCount = comparisonCount;
        this.swapCount = swapCount;
        this.memoryUsedBytes = memoryUsedBytes;
        this.arraySize = arraySize;
    }

    public String getAlgorithmName() { return algorithmName; }
    public long getExecutionTimeNs() { return executionTimeNs; }
    public double getExecutionTimeMs() { return executionTimeNs / 1_000_000.0; }
    public long getComparisonCount() { return comparisonCount; }
    public long getSwapCount() { return swapCount; }
    public long getMemoryUsedBytes() { return memoryUsedBytes; }
    public int getArraySize() { return arraySize; }
}

// ==========================================
// 2. INTERFAZ Y CLASES DE ALGORITMOS (STRATEGY)
// ==========================================
abstract class SortingAlgorithm {
    public abstract String getName();
    public abstract String getHistory();
    public abstract String getCharacteristics();
    public abstract String getBigO();
    public abstract String getPseudocode();
    public abstract String getSoftwareQualityDetails();
    
    // Método abstracto para ordenar
    public abstract ExecutionMetrics sort(int[] inputArr);

    protected void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

// --- SELECTION SORT ---
class SelectionSort extends SortingAlgorithm {
    @Override
    public String getName() { return "Selection Sort (Selección)"; }

    @Override
    public String getHistory() {
        return "Historia:\n" +
               "Es uno de los algoritmos de ordenamiento intuitivos más antiguos, derivado de prácticas de\n" +
               "clasificación manual de datos anteriores a la era de la computación digital. Fue formalizado\n" +
               "en los primeros textos de ciencias de la computación en la década de 1950.";
    }

    @Override
    public String getCharacteristics() {
        return "Características:\n" +
               "- Tipo: Basado en comparaciones (In-place).\n" +
               "- Estabilidad: No estable por defecto (puede alterar orden relativo de duplicados).\n" +
               "- Ventaja: Garantiza un número de intercambios mínimo (máximo N-1 swaps).\n" +
               "- Inconveniente: Realiza siempre el mismo número de comparaciones independientemente de la entrada.";
    }

    @Override
    public String getBigO() {
        return "Rendimiento Big-O:\n" +
               "- Peor Caso: O(N²)\n" +
               "- Caso Promedio: O(N²)\n" +
               "- Mejor Caso: O(N²)\n" +
               "- Espacio Auxiliar: O(1)";
    }

    @Override
    public String getPseudocode() {
        return "PROCEDIMIENTO SelectionSort(A : Arreglo, N : Tamaño)\n" +
               "  PARA i = 0 HASTA N - 2 HACER\n" +
               "    minIdx = i\n" +
               "    PARA j = i + 1 HASTA N - 1 HACER\n" +
               "      SI A[j] < A[minIdx] ENTONCES\n" +
               "        minIdx = j\n" +
               "      FIN SI\n" +
               "    FIN PARA\n" +
               "    SI minIdx != i ENTONCES\n" +
               "      Intercambiar(A[i], A[minIdx])\n" +
               "    FIN SI\n" +
               "  FIN PARA\n" +
               "FIN PROCEDIMIENTO";
    }

    @Override
    public String getSoftwareQualityDetails() {
        return "Calidad de Software (Selection Sort):\n" +
               "- Mantenibilidad / Complejidad Ciclomática: Muy baja (V(G) = 3). Código simple de probar.\n" +
               "- Eficiencia de Memoria: Excelente (O(1) auxiliar).\n" +
               "- Eficiencia de CPU: Pobre para N elevado debido a comparaciones cuadráticas inevitables.";
    }

    @Override
    public ExecutionMetrics sort(int[] inputArr) {
        int[] arr = inputArr.clone();
        long comparisons = 0;
        long swaps = 0;

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, i, minIdx);
                swaps++;
            }
        }
        long endTime = System.nanoTime();

        return new ExecutionMetrics(getName(), endTime - startTime, comparisons, swaps, 0, n);
    }
}

// --- INSERTION SORT ---
class InsertionSort extends SortingAlgorithm {
    @Override
    public String getName() { return "Insertion Sort (Inserción)"; }

    @Override
    public String getHistory() {
        return "Historia:\n" +
               "Inspirado en la manera natural en que los humanos ordenan cartas de una baraja.\n" +
               "Fue analizado formalmente por John Mauchly en 1946 durante el desarrollo de los primeros\n" +
               "programas para la computadora ENIAC.";
    }

    @Override
    public String getCharacteristics() {
        return "Características:\n" +
               "- Tipo: Basado en comparaciones e in-place.\n" +
               "- Estabilidad: Estable (preserva orden de elementos con llaves iguales).\n" +
               "- Adaptativo: Muy rápido O(N) para arreglos parcialmente o totalmente ordenados.\n" +
               "- Utilidad Práctica: Algoritmo base para arreglos pequeños en librerías estándar (ej. Dual-Pivot Timsort).";
    }

    @Override
    public String getBigO() {
        return "Rendimiento Big-O:\n" +
               "- Peor Caso: O(N²)\n" +
               "- Caso Promedio: O(N²)\n" +
               "- Mejor Caso: O(N) (Arreglo ya ordenado)\n" +
               "- Espacio Auxiliar: O(1)";
    }

    @Override
    public String getPseudocode() {
        return "PROCEDIMIENTO InsertionSort(A : Arreglo, N : Tamaño)\n" +
               "  PARA i = 1 HASTA N - 1 HACER\n" +
               "    clave = A[i]\n" +
               "    j = i - 1\n" +
               "    MIENTRAS j >= 0 Y A[j] > clave HACER\n" +
               "      A[j + 1] = A[j]\n" +
               "      j = j - 1\n" +
               "    FIN MIENTRAS\n" +
               "    A[j + 1] = clave\n" +
               "  FIN PARA\n" +
               "FIN PROCEDIMIENTO";
    }

    @Override
    public String getSoftwareQualityDetails() {
        return "Calidad de Software (Insertion Sort):\n" +
               "- Mantenibilidad / Complejidad Ciclomática: Baja (V(G) = 3).\n" +
               "- Testabilidad: Alta. Comportamiento predecible con vectores ordenados/inversos.\n" +
               "- Robustez y Desempeño Adaptativo: Alto desempeño cuando la entropía de datos es baja.";
    }

    @Override
    public ExecutionMetrics sort(int[] inputArr) {
        int[] arr = inputArr.clone();
        long comparisons = 0;
        long swaps = 0; // Se consideran desplazamientos

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    swaps++; // Movimiento de elemento
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
        long endTime = System.nanoTime();

        return new ExecutionMetrics(getName(), endTime - startTime, comparisons, swaps, 0, n);
    }
}

// --- SHELL SORT ---
class ShellSort extends SortingAlgorithm {
    @Override
    public String getName() { return "Shell Sort"; }

    @Override
    public String getHistory() {
        return "Historia:\n" +
               "Inventado por Donald Shell en 1959. Fue el primer algoritmo que rompió la barrera\n" +
               "cuadrática O(N²), demostrando que era posible realizar ordenamientos subcuadráticos\n" +
               "mediante comparaciones a larga distancia con intervalos (gaps).";
    }

    @Override
    public String getCharacteristics() {
        return "Características:\n" +
               "- Generalización de Insertion Sort con saltos decrecientes.\n" +
               "- Tipo: In-place.\n" +
               "- Estabilidad: No estable.\n" +
               "- El rendimiento depende fuertemente de la secuencia de brechas (Gap sequence: Shell, Knuth, Sedgewick).";
    }

    @Override
    public String getBigO() {
        return "Rendimiento Big-O:\n" +
               "- Peor Caso: O(N²) [secuencia original] u O(N^(3/2))\n" +
               "- Caso Promedio: O(N^1.25) a O(N^1.5)\n" +
               "- Mejor Caso: O(N log N)\n" +
               "- Espacio Auxiliar: O(1)";
    }

    @Override
    public String getPseudocode() {
        return "PROCEDIMIENTO ShellSort(A : Arreglo, N : Tamaño)\n" +
               "  gap = N / 2\n" +
               "  MIENTRAS gap > 0 HACER\n" +
               "    PARA i = gap HASTA N - 1 HACER\n" +
               "      temp = A[i]\n" +
               "      j = i\n" +
               "      MIENTRAS j >= gap Y A[j - gap] > temp HACER\n" +
               "        A[j] = A[j - gap]\n" +
               "        j = j - gap\n" +
               "      FIN MIENTRAS\n" +
               "      A[j] = temp\n" +
               "    FIN PARA\n" +
               "    gap = gap / 2\n" +
               "  FIN MIENTRAS\n" +
               "FIN PROCEDIMIENTO";
    }

    @Override
    public String getSoftwareQualityDetails() {
        return "Calidad de Software (Shell Sort):\n" +
               "- Mantenibilidad / Complejidad Ciclomática: Moderada (V(G) = 4).\n" +
               "- Eficiencia Temporal: Significativamente superior a Selection e Insertion en N > 1,000.\n" +
               "- Escalabilidad: Alta capacidad de procesamiento para conjuntos de datos medianos.";
    }

    @Override
    public ExecutionMetrics sort(int[] inputArr) {
        int[] arr = inputArr.clone();
        long comparisons = 0;
        long swaps = 0;

        long startTime = System.nanoTime();
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        swaps++;
                        j -= gap;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }
        long endTime = System.nanoTime();

        return new ExecutionMetrics(getName(), endTime - startTime, comparisons, swaps, 0, n);
    }
}

// ==========================================
// 3. PANEL PERSONALIZADO PARA GRÁFICAS (Graphics2D)
// ==========================================
class BarChartPanel extends JPanel {
    private List<ExecutionMetrics> metricsList = new ArrayList<>();
    private String chartTitle = "Comparación de Tiempo de Ejecución (ms)";

    public void setMetrics(List<ExecutionMetrics> metrics, String title) {
        this.metricsList = metrics;
        this.chartTitle = title;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 60;

        // Fondo y Título
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        g2.setColor(new Color(40, 40, 40));
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString(chartTitle, padding, 30);

        if (metricsList == null || metricsList.isEmpty()) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2.drawString("Presione 'Ejecutar Comparativa' para generar los datos.", width / 4, height / 2);
            return;
        }

        // Determinar valor máximo para escalar barras
        double maxVal = 0;
        for (ExecutionMetrics m : metricsList) {
            double val = m.getExecutionTimeMs();
            if (val > maxVal) maxVal = val;
        }
        if (maxVal == 0) maxVal = 1.0; // Evitar división por cero

        // Dibujar Ejes
        int chartBottom = height - padding;
        int chartTop = 50;
        int chartLeft = padding + 20;
        int chartRight = width - padding;
        int availableHeight = chartBottom - chartTop;

        g2.setColor(Color.GRAY);
        g2.drawLine(chartLeft, chartBottom, chartRight, chartBottom); // Eje X
        g2.drawLine(chartLeft, chartBottom, chartLeft, chartTop);    // Eje Y

        // Dibujar Barras
        int barCount = metricsList.size();
        int barWidth = (chartRight - chartLeft) / (barCount * 2);
        Color[] colors = {new Color(52, 152, 219), new Color(46, 204, 113), new Color(155, 89, 182)};

        for (int i = 0; i < barCount; i++) {
            ExecutionMetrics m = metricsList.get(i);
            double val = m.getExecutionTimeMs();
            int barHeight = (int) ((val / maxVal) * (availableHeight - 20));

            int x = chartLeft + (i * 2 + 1) * barWidth;
            int y = chartBottom - barHeight;

            g2.setColor(colors[i % colors.length]);
            g2.fillRect(x, y, barWidth, barHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, barWidth, barHeight);

            // Valores sobre la barra
            String valStr = String.format("%.3f ms", val);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.drawString(valStr, x - 5, y - 5);

            // Nombre de la etiqueta
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString(m.getAlgorithmName().split(" ")[0], x, chartBottom + 20);
        }
    }
}

// ==========================================
// 4. VENTANA PRINCIPAL DE LA APLICACIÓN
// ==========================================
public class ComparadorOrdenamiento extends JFrame {

    private final List<SortingAlgorithm> algorithms = new ArrayList<>();
    private int[] generatedData = new int[0];

    // Componentes pestaña 1
    private JComboBox<String> comboAlgoritmos;
    private JTextArea txtHistoria, txtCaracteristicas, txtBigO, txtPseudocode;
    private JTextArea txtResultadoUnitario;

    // Componentes pestaña 2 (Comparativa)
    private JTextField txtTamanoArray;
    private BarChartPanel chartPanel;
    private JTextArea txtResultadoComparativa;

    // Componentes pestaña 3 (Calidad de Software)
    private JTextArea txtCalidadSoftware;

    public ComparadorOrdenamiento() {
        // Registrar Algoritmos
        algorithms.add(new SelectionSort());
        algorithms.add(new InsertionSort());
        algorithms.add(new ShellSort());

        // Configuración JFrame
        setTitle("Programa Didáctico: Comparador de Algoritmos de Ordenamiento");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("1. Algoritmos y Teoría", crearPanelTeoriaYUnico());
        tabbedPane.addTab("2. Comparativa y Gráficas", crearPanelComparativa());
        tabbedPane.addTab("3. Calidad de Software", crearPanelCalidad());

        add(tabbedPane);
    }

    // --- PESTAÑA 1: TEORÍA Y PRUEBA INDIVIDUAL ---
    private JPanel crearPanelTeoriaYUnico() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Selector
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(new JLabel("Seleccionar Algoritmo:"));
        comboAlgoritmos = new JComboBox<>();
        for (SortingAlgorithm algo : algorithms) {
            comboAlgoritmos.addItem(algo.getName());
        }
        comboAlgoritmos.addActionListener(e -> cargarInformacionAlgoritmo());
        panelTop.add(comboAlgoritmos);

        // Área central dividida (Teoría a la izq, Pseudocódigo y Prueba a la der)
        JTabbedPane tabTeoria = new JTabbedPane();

        txtHistoria = crearTextAreaLectura();
        txtCaracteristicas = crearTextAreaLectura();
        txtBigO = crearTextAreaLectura();
        txtPseudocode = crearTextAreaLectura();

        tabTeoria.addTab("Historia", new JScrollPane(txtHistoria));
        tabTeoria.addTab("Características", new JScrollPane(txtCaracteristicas));
        tabTeoria.addTab("Rendimiento Big-O", new JScrollPane(txtBigO));
        tabTeoria.addTab("Pseudocódigo", new JScrollPane(txtPseudocode));

        // Panel de ejecución individual
        JPanel panelEjecucion = new JPanel(new BorderLayout(5, 5));
        panelEjecucion.setBorder(BorderFactory.createTitledBorder("Prueba Individual Instantánea (N=1,000)"));
        JButton btnEjecutarUnico = new JButton("Probar Algoritmo Seleccionado");
        txtResultadoUnitario = crearTextAreaLectura();

        btnEjecutarUnico.addActionListener(e -> ejecutarPruebaUnica());
        panelEjecucion.add(btnEjecutarUnico, BorderLayout.NORTH);
        panelEjecucion.add(new JScrollPane(txtResultadoUnitario), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabTeoria, panelEjecucion);
        splitPane.setDividerLocation(320);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);

        cargarInformacionAlgoritmo();
        return panel;
    }

    // --- PESTAÑA 2: BENCHMARKING Y GRÁFICAS ---
    private JPanel crearPanelComparativa() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Controles de entrada
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelControles.add(new JLabel("Tamaño del Arreglo (N):"));
        txtTamanoArray = new JTextField("5000", 8);
        JButton btnEjecutarBenchmark = new JButton("Ejecutar Comparativa");

        panelControles.add(txtTamanoArray);
        panelControles.add(btnEjecutarBenchmark);

        // Centro: Gráfica y Reporte de métricas
        chartPanel = new BarChartPanel();
        chartPanel.setPreferredSize(new Dimension(400, 300));

        txtResultadoComparativa = crearTextAreaLectura();
        JScrollPane scrollTabla = new JScrollPane(txtResultadoComparativa);

        JSplitPane splitCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanel, scrollTabla);
        splitCenter.setDividerLocation(480);

        btnEjecutarBenchmark.addActionListener(e -> ejecutarBenchmarkCompleto());

        panel.add(panelControles, BorderLayout.NORTH);
        panel.add(splitCenter, BorderLayout.CENTER);

        return panel;
    }

    // --- PESTAÑA 3: CALIDAD DE SOFTWARE ---
    private JPanel crearPanelCalidad() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        txtCalidadSoftware = crearTextAreaLectura();
        actualizarTextoCalidad();

        panel.add(new JLabel("Evaluación de Calidad de Software (Métricas y Buenas Prácticas):"), BorderLayout.NORTH);
        panel.add(new JScrollPane(txtCalidadSoftware), BorderLayout.CENTER);

        return panel;
    }

    // --- LÓGICA DE NEGOCIO Y EVENTOS ---
    private void cargarInformacionAlgoritmo() {
        int idx = comboAlgoritmos.getSelectedIndex();
        if (idx >= 0) {
            SortingAlgorithm algo = algorithms.get(idx);
            txtHistoria.setText(algo.getHistory());
            txtCaracteristicas.setText(algo.getCharacteristics());
            txtBigO.setText(algo.getBigO());
            txtPseudocode.setText(algo.getPseudocode());
        }
    }

    private void ejecutarPruebaUnica() {
        int idx = comboAlgoritmos.getSelectedIndex();
        if (idx < 0) return;

        SortingAlgorithm algo = algorithms.get(idx);
        int[] dataset = generarDatasetAleatorio(1000);
        ExecutionMetrics metric = algo.sort(dataset);

        txtResultadoUnitario.setText(String.format(
            "Resultados de prueba para: %s\n" +
            "--------------------------------------------------\n" +
            "Tamaño del Arreglo (N) : %d elementos\n" +
            "Tiempo de Ejecución    : %.4f ms (%d nanosegundos)\n" +
            "Número de Comparaciones: %,d\n" +
            "Intercambios/Swaps     : %,d\n" +
            "Complejidad Espacial   : O(1) Auxiliar In-Place",
            metric.getAlgorithmName(), metric.getArraySize(),
            metric.getExecutionTimeMs(), metric.getExecutionTimeNs(),
            metric.getComparisonCount(), metric.getSwapCount()
        ));
    }

    private void ejecutarBenchmarkCompleto() {
        int n;
        try {
            n = Integer.parseInt(txtTamanoArray.getText().trim());
            if (n <= 0 || n > 100000) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un tamaño numérico válido (entre 1 y 100,000).", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // dataset idéntico para justa competencia
        int[] baseDataset = generarDatasetAleatorio(n);
        List<ExecutionMetrics> resultados = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE CALIDAD Y RENDIMIENTO ===\n");
        sb.append(String.format("Tamaño del dataset evaluado: N = %,d\n\n", n));
        sb.append(String.format("%-18s | %-12s | %-15s | %-12s\n", "Algoritmo", "Tiempo (ms)", "Comparaciones", "Swaps/Movs"));
        sb.append("-------------------------------------------------------------------\n");

        for (SortingAlgorithm algo : algorithms) {
            ExecutionMetrics m = algo.sort(baseDataset);
            resultados.add(m);

            sb.append(String.format("%-18s | %-12.3f | %-15d | %-12d\n",
                    algo.getName().split(" ")[0],
                    m.getExecutionTimeMs(),
                    m.getComparisonCount(),
                    m.getSwapCount()));
        }

        txtResultadoComparativa.setText(sb.toString());
        chartPanel.setMetrics(resultados, "Tiempo de Ejecución para N = " + n + " elementos");
    }

    private void actualizarTextoCalidad() {
        StringBuilder sb = new StringBuilder();
        sb.append("MÉTRICAS DE CALIDAD DE SOFTWARE EVALUADAS (Estándar ISO/IEC 25010)\n\n");
        sb.append("1. Eficiencia de Comportamiento Temporal:\n");
        sb.append("   - Mide la latencia de respuesta frente a N elementos. Insertion Sort sobresale en datos ordenados, mientras que Shell Sort domina en escala masiva.\n\n");
        sb.append("2. Utilización de Recursos (Memoria Auxiliar):\n");
        sb.append("   - Los 3 algoritmos probados cumplen con el principio In-place con espacio auxiliar O(1), maximizando la calidad en gestión de memoria RAM.\n\n");
        sb.append("3. Mantenibilidad y Modularidad (Patrón Strategy):\n");
        sb.append("   - Cada algoritmo está aislado formalmente. Modificar o extender la lista (ej. agregar QuickSort o MergeSort) no impacta el diseño de la GUI.\n\n");
        sb.append("-------------------------------------------------------------------\n");
        sb.append("DETALLES ESPECÍFICOS POR ALGORITMO:\n\n");

        for (SortingAlgorithm algo : algorithms) {
            sb.append(algo.getSoftwareQualityDetails()).append("\n\n");
        }

        txtCalidadSoftware.setText(sb.toString());
    }

    private int[] generarDatasetAleatorio(int size) {
        Random rand = new Random(42); // Semilla fija para reproducibilidad de pruebas
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(10000);
        }
        return data;
    }

    private JTextArea crearTextAreaLectura() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComparadorOrdenamiento().setVisible(true));
    }
}