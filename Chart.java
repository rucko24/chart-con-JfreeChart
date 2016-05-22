package chart;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart extends JFrame {
	
	private JFreeChart grafica;
	private XYSeriesCollection datos = new XYSeriesCollection();
	
	//constantes 
	private final static int LINEA = 1;
	private final static int POLAR = 2;
	private final static int DISPERSION = 3;
	private final static int AREA = 4;
	private final static int LOGARITMICA = 5;
	private final static int SERIETIEMPO = 6;
	private final static int PASO = 7;
	private final static int PASOAREA = 8;
	
	private double x[];
	private double y[];
	
	private String titulo;
	private String tX;
	private String tY;
	
	public Chart(int tipo, String titulo) {
		this.titulo = titulo;
		tipoGrafica(tipo);
	}
	
	public void tipoGrafica(int tipo) {
		switch(tipo) {
		case LINEA:
			grafica = ChartFactory.createXYLineChart(titulo, tX, tY , datos, PlotOrientation.VERTICAL, true,true,true);
			break;
		case POLAR:
			grafica = ChartFactory.createPolarChart(titulo, datos, true,true,true);
			break;
		case DISPERSION:
			grafica = ChartFactory.createScatterPlot(titulo, tX, tY, datos, PlotOrientation.VERTICAL, true, true, true);
			break;
		case AREA:
			grafica = ChartFactory.createXYAreaChart(titulo, tX, tY, datos, PlotOrientation.VERTICAL, true, true, true); 
			break;
			
		case LOGARITMICA:
			grafica = ChartFactory.createXYLineChart(titulo, tX, tY, datos, PlotOrientation.VERTICAL, true ,true, true);
			//una variable para recojer los eje de la grafica
			XYPlot eje = grafica.getXYPlot();
			//creando la escala logaritmica
			NumberAxis rango = new LogarithmicAxis(tY);
			//asignar al eje en el rango, cambiando la escala logaritmica
			eje.setRangeAxis(rango);
			//cambia las escalas de las Y
			break;
		case SERIETIEMPO:
			grafica = ChartFactory.createTimeSeriesChart(titulo, tX, tY, datos, true, true, true);
			break;
		case PASO: 
			grafica = ChartFactory.createXYStepAreaChart(titulo, tX, tY, datos, PlotOrientation.VERTICAL, true, true, true);
			break;
		case PASOAREA:
			grafica = ChartFactory.createXYAreaChart(titulo, tX, tY, datos, PlotOrientation.VERTICAL, true, true, true);
			break;
		}

		x = rango(1,360,0.5);
		//llenando los valores de y con respecto al los de x
		//donde pasamos el vector x, y regresara el SENO
		y = f(x);
		
		agregarGrafica("sin(x)", x, y);
		
		JPanel panel = getPanel();
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.setVisible(true);
		
	}
	
	public void agregarGrafica(String id, double x[], double y[]) {
		//pasando id de cada uno de las graficas
		XYSeries s = new XYSeries(id);
		//asignar valores que nos esten llegando
		int n = x.length; //numeros de registros que se van a asignar;
		//para ir asignando cada uno de los valores
		//asigano valores de x y y
		for( int f=0; f<n; f++) {
			s.add(x[f], y[f]);
		}
		//asignado series a los datos, para almacernarlos y sumando
		datos.addSeries(s);
	}
	
	public JPanel getPanel() {
		return new ChartPanel(grafica);
	}
	
	//metodo para tener los datos y add a la chart
	//2 vectores 
	public double[] rango(double xInicial, double xFinal, double d) {
		
		//genereando una series de datos
		//contar numero de datos
		int n = (int) ((xFinal - xInicial)/d);
		//arreglo para regresar los datos
		double r[] = new double[n];
		//for para generar todos los datos
		
		for( int f=0; f<n; f++) {
			//d = incrementos, hasta llegar al ultimo valor
			r[f] = xInicial + d * f;
		}
		
		return r;
	}
	
	public double[] f(double[] x) {
		//revibiendo vertor con todas las X
		//y aqui generamos todas las Y
		int n = x.length;
		//
		double[] y = new double[n];
		//
		for( int f=0; f<n; f++) {
			//EL seno(PERO EN radianes)necesitamos pasar grados 
			//dividirlo entre 180 para lograr la 
			//conversion,,,la funsion seno recibe radianes
			double radianes = x[f] * Math.PI / 180;
			y[f] = Math.sin(radianes);
		}
		
		return y;
	}
	
	public static void main(String[] args) {
		
		Chart myChart = new Chart(Chart.POLAR, "Charts");
		
		
	}
}
