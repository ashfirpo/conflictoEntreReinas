package conflictoEntreReinas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Conflicto {
	
	private int dimensionTablero=0;
	private int cantReinas=0;
	private ArrayList<Reina> reinas;	
	private int [][] tablero;
	private ArrayList<ArrayList<Reina>> conflictos;
	
	public Conflicto(String path) throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(path));
		this.dimensionTablero=sc.nextInt();
		this.tablero= new int[this.dimensionTablero][this.dimensionTablero];
		
		for(int i=0;i<this.dimensionTablero;i++)
			Arrays.fill(this.tablero[i], 0);
		
		this.cantReinas=sc.nextInt();
		this.conflictos = new ArrayList<>();
		
		this.reinas = new ArrayList<>();
		int i=1;
		while(sc.hasNextLine() && i<this.cantReinas+1)
		{
			sc.nextLine();
			int fila=sc.nextInt();
			int columna=sc.nextInt();
			Reina r = new Reina(i, fila, columna);
			if(!reinas.contains(r))
			{
				reinas.add(r);
				this.tablero[fila-1][columna-1]=i;
			}
			i++;
		}		
		sc.close();
	}
	
	public void resolver()
	{
		int izq=0, der=0, up=0, down=0, diagUpIzq=0, diagUpDer=0, diagDownIzq=0, diagDownDer=0;
		int filaUp, filaDown, colIzq, colDer, tope;
		ArrayList<Reina> conf = null;
		
		for(Reina r:this.reinas) //voy mirando las reinas porque NxN > M
		{
			//Al final, no es tan largo todo esto porque el segundo loop va cortando a medida que encuentra conflictos
			//(o fin del tablero) en las líneas en las que se puede mover cada reina
			conf = new ArrayList<>();
			int fila = r.getFila()-1; //-1 para obtener los índices reales
			int columna = r.getColumna()-1;
			//Me posiciono en los casilleros que limitan con la reina
			filaUp=fila-1;
			filaDown=fila+1;
			colIzq=columna-1;
			colDer=columna+1;
			
			//Todo esto es para saber en qué forma tiene que moverse: divido el tablero en 4 y voy viendo
			//en qué cuadrado está posicionada la reina. Puede ser, por ejemplo, si tengo un tablero de 8x8,
			//miro el primer cuadrado: las filas del 0 al 3 y las columnas del 0 al 3
			//segundo cuadrado: filas del 0 al 3 y columnas del 4 al 8
			//tercer cuadrado: filas del 4 al 8 y columnas del 0 al 3
			//cuarto cuadrado: filas del 4 al 8 y columnas del 4 al 8
			if(fila <(this.dimensionTablero/2) && columna < (this.dimensionTablero/2))
				tope=filaDown==colDer?filaDown:(filaDown<colDer?filaDown:colDer);
			else if (fila >=(this.dimensionTablero/2) && columna < (this.dimensionTablero/2))
				tope=filaUp==colDer?this.dimensionTablero - filaUp-1:(this.dimensionTablero-filaUp-1<colDer?this.dimensionTablero-filaUp-1:colDer);
			else if (fila <(this.dimensionTablero/2) && columna >= (this.dimensionTablero/2))
				tope=filaDown<this.dimensionTablero-colIzq-1?filaDown:this.dimensionTablero-colIzq-1;
			else
				tope=filaUp==colIzq?this.dimensionTablero-filaUp-1:(this.dimensionTablero-filaUp-1<this.dimensionTablero-colIzq-1?this.dimensionTablero-filaUp-1:this.dimensionTablero-colIzq-1);
			
			while(tope>0 && tope<this.dimensionTablero) //solamente me muevo la cantidad maxima que necesito: siempre va a ser menor que el tablero
			{
				if(filaUp >= 0 && this.tablero[filaUp][columna]!=0 && up==0)
				{
					conf.add(this.reinas.get(this.tablero[filaUp][columna] -1));
					up=1;
				}
				
				if(filaDown < this.dimensionTablero && this.tablero[filaDown][columna]!=0 && down==0)
				{
					conf.add(this.reinas.get(this.tablero[filaDown][columna] -1));
					down=1;
				}
				
				if(colIzq >= 0 && this.tablero[fila][colIzq]!=0 && izq==0)
				{
					conf.add(this.reinas.get(this.tablero[fila][colIzq] -1));
					izq=1;
				}
				
				if(colDer < this.dimensionTablero && this.tablero[fila][colDer]!=0 && der==0)
				{
					conf.add(this.reinas.get(this.tablero[fila][colDer] -1));
					der=1;
				}
				//diagonales
				if((filaUp >=0 && colDer<this.dimensionTablero) && (this.tablero[filaUp][colDer]!=0 && diagUpDer==0))
				{
					conf.add(this.reinas.get(this.tablero[filaUp][colDer] -1));
					diagUpDer=1;
				}
				
				if((filaUp >=0 && colIzq >=0) && (this.tablero[filaUp][colIzq]!=0 && diagUpIzq==0))
				{
					conf.add(this.reinas.get(this.tablero[filaUp][colIzq] -1));
					diagUpIzq=1;
				}
				
				if((filaDown < this.dimensionTablero && colDer < this.dimensionTablero) && (this.tablero[filaDown][colDer]!=0 && diagDownDer==0))
				{
					conf.add(this.reinas.get(this.tablero[filaDown][colDer] -1));
					diagDownDer=1;
				}
				
				if((filaDown < this.dimensionTablero && colIzq >=0) && (this.tablero[filaDown][colIzq]!=0 && diagDownIzq==0))
				{
					conf.add(this.reinas.get(this.tablero[filaDown][colIzq] -1));
					diagDownIzq=1;
				}
				
				//me muevo para los lados que corresponda
				filaUp--;
				filaDown++;
				colIzq--;
				colDer++;
				
				tope++; //la función del tope es saber la cantidad de movimientos máximos que tengo que hacer:
						//básicamente, es el peor de los casos donde la reina está lo más alejada del borde del tablero
			}
			//reinicio los movimientos y agrego todos los conflictos que encontré
			izq=der=up=down=diagDownDer=diagDownIzq=diagUpIzq=diagUpDer=0;
			this.conflictos.add(conf);
		}
	}
	
	public void mostrarResultado()
	{
		for(ArrayList<Reina> conflictos : this.conflictos)
		{
			System.out.print(conflictos.size() + " ");
			conflictos.sort((r1, r2)-> r1.compareTo(r2)); //ordeno por id de reina (lo pide el enunciado)
			//otra forma de ordenar: conflictos.sort(new Reina()); para esto, tengo que implementar comparator en Reina
			for(Reina r : conflictos)
			{
				System.out.print(r.getId() + " ");
			}
			System.out.println();
		}
	}

}
