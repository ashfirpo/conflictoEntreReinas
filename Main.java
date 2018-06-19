package conflictoEntreReinas;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Conflicto c = new Conflicto("conflictos7.in");
		c.resolver();
		c.mostrarResultado();

	}

}
