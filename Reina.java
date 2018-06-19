package conflictoEntreReinas;

public class Reina {
	
	private int id;
	private int fila;
	private int columna;
	
	public Reina(int id, int fila, int columna)
	{
		this.id=id;
		this.fila=fila;
		this.columna=columna;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getFila()
	{
		return this.fila;
	}
	
	public int getColumna()
	{
		return this.columna;
	}
	
	public int compareTo(Reina r)
	{
		return this.id - r.id;
	}
	
	@Override
	public boolean equals(Object r)
	{
		Reina param = (Reina)r;
		return (this.fila==param.fila && this.columna==param.columna);
	}
	

}
