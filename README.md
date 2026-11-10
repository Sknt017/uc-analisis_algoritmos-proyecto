**Entrega proyecto final de algoritmos de ordenamiento para Analisis de Algoritmos**

la aplicacion se diseñó bajo el patrón de diseño **Strategy** (un patrón de diseño de comportamiento que  permite definir una familia de algoritmos, poner cada uno en su propia clase y hacer que sus objetos sean intercambiables.) para encapsular cada algoritmo y facilitar la adición de futuros métodos de ordenamiento.

## Estructura de interfaz: 

```
┌────────────────────────────────────────────────────────────────────────┐
│                        ComparadorOrdenamiento                          │
│                               (JFrame)                                 │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ Contiene JTabbedPane
       ┌────────────────────────────┼────────────────────────────┐
       ▼                            ▼                            ▼
┌──────────────┐            ┌──────────────┐             ┌──────────────┐
│ Pestaña 1:   │            │ Pestaña 2:   │             │ Pestaña 3:   │
│ Información y│            │ Benchmarking │             │ Calidad de   │
│ Pruebas      │            │ y Gráficas   │             │ Software     │
└──────────────┘            └──────────────┘             └──────────────┘
                                    │ Usa
                                    ▼
                      ┌───────────────────────────┐
                      │    SortingAlgorithm       │ (Interfaz)
                      └─────────────┬─────────────┘
          ┌─────────────────────────┼─────────────────────────┐
          ▼                         ▼                         ▼
┌──────────────────┐      ┌──────────────────┐      ┌──────────────────┐
│  SelectionSort   │      │  InsertionSort   │      │    ShellSort     │
└──────────────────┘      └──────────────────┘      └──────────────────┘

```


## Ejecucion 

1. clonar el repo
2. Ejecutar el archivo `run.bat` en un computador que tenga instalado java (JDK, JRE, Server VM)