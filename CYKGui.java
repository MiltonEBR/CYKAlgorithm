
import javax.swing.JOptionPane;


public class CYKGui {
	
	
	public static void main(String[] args) {
		
		String [][] tablaGramatica;
		
		int n,
			m;
		
		n=Integer.parseInt(JOptionPane.showInputDialog("Cantidad de producciones"));
		String cadena = JOptionPane.showInputDialog("Ingrese cadena a evaluar");
		
		m=cadena.length()+1;
		
		tablaGramatica=new String[n][m];
		
		for (int i = 0; i < tablaGramatica.length; i++) {
			String tmp=JOptionPane.showInputDialog("Inserte la produccion "+i);
			int ind=tmp.indexOf("->");
			tablaGramatica[i][0]=tmp.substring(0,ind);
			
			//Checar si el generador es FNCh
			
			if(!CYKNodes.checkChomsky(0, tablaGramatica[i][0])) {
				JOptionPane.showMessageDialog(null, "No se ingresó en forma normal de chomsky", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			
			//Procesar los símbolos generados
			
			tmp=tmp.substring(ind+2,tmp.length());//String sin el generador
			//System.out.println(tmp);
			String[] tmpGenerados=tmp.split("\\|");
			
			//System.out.println(tmpGenerados.length);
			for (int j = 0; j < tmpGenerados.length; j++) {
				tablaGramatica[i][j+1]=tmpGenerados[j];
				//System.out.println(tmpGenerados[j]);
				//System.out.println(tablaGramatica[i][j+1]);
				if(!CYKNodes.checkChomsky(1, tablaGramatica[i][j+1])) {
					JOptionPane.showMessageDialog(null, "No se ingresó en forma normal de chomsky", "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
			
			//Fin de la revision de chomsky, las producciones ya se pasaron a matriz
		}
		
		
		System.out.println("Tabla de gramáticas:");
		CYKNodes.printTabla2D(tablaGramatica);
		
		System.out.println("Tabla generada por CYK");
		
		CYKNodes cyk=new CYKNodes(m,n,tablaGramatica);
		
		cyk.generaMatriz(cadena);
		
		String temp=cyk.printGenera(cadena);
		
		if(temp!=null) {
			JOptionPane.showMessageDialog(null, "La cadena puede ser generada\n\n"+temp,"Resultado",JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "La cadena no puede ser generada","Resultado",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	

}
