import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import java.util.List;

import com.sun.source.doctree.BlockTagTree;


public class CYKNodes {
	
	String tablaGramatica[][];
	int produc;
	Node tablaNodos[][][];
	
	//Constructor
	public CYKNodes(int m,int n, String[][] tabla) {
		this.tablaNodos=new Node[m][m][m];
		this.produc=n;
		this.tablaGramatica=tabla;
	}
	
	//Sirve para imprimir un arreglo bidimensional
	public static void printTabla2D(String[][] tabla) {
		for (String[] x : tabla)
		{
			System.out.print("[");
			for (String y : x)
			{
				if(y==null) {
					System.out.print(" , ");
				}else {
					System.out.print(y + ", ");
				}
				
			}
		   System.out.print("]");
		   System.out.println();
		}
	}
	
	
	public static void printTabla3DNodos(Node[][][] tabla) {
		for (Node[][] x : tabla)
		{
			System.out.print("[");
			int cont=0;
			for (Node[] y : x)
			{
				if(y==null) {
					continue;
				}
				
				for (Node z : y) {
					if(z==null) {
						System.out.print("");
					}else if(!z.getNombre().equals("-")){
						System.out.print(z.getNombre());
					}
					
				}
				if(cont<x.length-1) {
					System.out.print(", ");
				}
				
				cont++;
				
				
			}
		   System.out.print("]");
		   System.out.println();
		}
	}
	
	//Checa si lo ingresado esta en chomsky
	public static boolean checkChomsky(int lado,String a) {
		
		char[] cadena=a.toCharArray();
		
		//Simbolo generador
		if(lado==0) {
			if(a.length()==1 && cadena[0]>='A' && cadena[0]<='Z') {
				return true;
			}else {
				return false;
			}
		//Simbolos generados	
		}else if(lado==1) {
			if ( (a.length() == 1 && !(cadena[0]>='A' && cadena[0]<='Z')) ||
					(a.length()==2 && cadena[0]>='A' && cadena[0]<='Z' && cadena[1]>='A' && cadena[1]<='Z')){
				return true;
			}else {
				return false;
			}
		}
		
		return false;
		
	}
	
	//Junta sin que se repitan
	private static Node[] juntar(Node[] a, Node[] b) {
		if(a==null && b==null) {
			System.out.println("nullbb");
		}
		
		if(a==null) {
			//System.out.println("in");
			return b;
		}else if(b==null) {
			//System.out.println("in");
			return a;
		}
		
		String resultado="";
		for (Node node : a) {
			resultado+=node.getNombre();
		}
		String comparando="";
		for (Node node : b) {
			comparando+=node.getNombre();
		}
		//System.out.println(resultado+" junto "+comparando);
		
		LinkedList<Integer> indices=new LinkedList<Integer>();
		for (int i = 0; i < comparando.length(); i++) {
			String tmp=String.valueOf(comparando.charAt(i));
			if(!resultado.contains(tmp)) {
				indices.add(i);
			}
		}
		Node[] fin=new Node[a.length+indices.size()];
		for (int i = 0; i < a.length; i++) {
			fin[i]=a[i];
			
		}
		for (int i = 0; i < fin.length; i++) {
			if(indices.isEmpty()) {
				break;
			}
			fin[a.length+i]=b[indices.poll()];
		}
		//System.out.println(a+" junto "+b+" da: "+resultado);
		
//		System.out.println("_________");
//		for (Node node : fin) {
//			System.out.println(node.getNombre());
//		}
//		System.out.println("_________");
		return fin;
	}
	
	// Crea combinaciones posibles
	private Node[] generarCombi(Node[] a,Node[] b) {
		
		LinkedList<Node> nodos=new LinkedList<Node>();
		for (int i = 0; i < a.length; i++) {
			
			for (int j = 0; j < b.length; j++) {
				
				String tmp=a[i].getNombre()+b[j].getNombre();
				//System.out.println("Metele"+tmp);
				Node[] bp=buscarProduc(tmp);
				
				
				//System.out.println(a[i].getNombre()+" y "+b[j].getNombre()+" da: ");
//				for (Node node : bp) {
//					System.out.println(node.getNombre()+"x");
//				}
				
				for (int k = 0; k < bp.length; k++) {
					nodos.add(new Node(bp[k].getNombre(),a[i],b[j]));
					//System.out.print(bp[k].getNombre()+"Agregado");
				}
				//System.out.println();
				
				//nodos.add(new Node(tmp,a[i],b[j]));
				
				
			}
			
		}
		
		Node[] tmp=new Node[nodos.size()];
		
		int cont=0;
		while(!nodos.isEmpty()) {
			tmp[cont]=nodos.poll();
			cont++;
		}
		
		
		//System.out.println("------------");
		
		
		return tmp;
		
	}
	
	private Node[] buscarProduc(String a) {
		Node[] tmp=null;
		for (int i = 0; i < this.produc; i++) {
			int cont=1;
			//System.out.println("hi");
			while (this.tablaGramatica[i][cont]!=null) {
				//System.out.println(a);
				if(this.tablaGramatica[i][cont].equals(a)) {
					//System.out.println(this.tablaGramatica[i][0]+" genera "+tmp);
					Node[] tabla=new Node[1];
					tabla[0]=new Node(tablaGramatica[i][0],null,null);
					//System.out.println(tablaGramatica[i][0]+"ag");
					tmp=juntar(tmp, tabla);
				}
				cont++;
			}
			
		}
		
		if(tmp==null) {
			tmp=new Node[1];
			tmp[0]=new Node("-",null,null);
		}
		
		return tmp;
	}
	
	public void generaMatriz(String cadena) {
		
		char[] cdn=cadena.toCharArray();
		
		//Valor a la diagonal
		for (int i = 0; i < cadena.length(); i++) {
			
			Node[] acum=null;
			
			String cad=String.valueOf(cdn[i]);
			
			for (int j = 0; j < this.produc; j++) {
				
				int cont=1;
				while (tablaGramatica[j][cont]!=null) {
					//System.out.println(cad+"cad");
					
					if(tablaGramatica[j][cont].equals(cad)) {
						//tablaNodos[j][0].setProduc(acum,tablaGramatica[j][0]);
						Node[] tmp=new Node[1];
						tmp[0]=new Node(tablaGramatica[j][0],new Node(cad,null,null),null);
//						System.out.println("in");
//						for (Node node : tmp) {
//							System.out.println(node.getNombre()+" "+cad);
//						}
//						
						acum=juntar(acum,tmp);
						
						//acum+=tablaGramatica[j][0];
						
					}
					cont++;
					
				}
				
			}
			tablaNodos[i][i]=acum;
		}
		//printTabla2D(matriz);
		
		//Aqui empieza la parte superior de la matriz TERMINADO :D
		for (int i = 1; i < cadena.length(); i++) {
			
			for (int j = i; j < cadena.length(); j++) {

				Node[] acum=null;
				
				for (int k = j-i; k < j; k++) {
//					Node[] pop=new Node[2];
//					pop[0]=new Node("A",null,null);
//					pop[1]=new Node("B",null,null);
//					tablaNodos[0][0]=pop;
					
					//System.out.println("["+(j-i)+"]["+k+"] y ["+k+1+"]["+j+"]");
					
					Node[] prod=generarCombi(tablaNodos[j-i][k], tablaNodos[k+1][j]);
					
					//System.out.println("pipi"+prod);
					acum=juntar(acum,prod);
					//System.out.println(matriz[j-i][k]+" y "+matriz[k+1][j]+" g: "+acum);
				}
//				if(acum.equals("")) {
//					acum="-";
//				}
				
				tablaNodos[j-i][j]=acum;
			}
			
		}
		printTabla3DNodos(tablaNodos);
//		for (Node c : tablaNodos[0][4]) {
//			System.out.println(c.getNombre());
//			if(c.getNombre().equals("S")) {
//				System.out.println(c.getDer().getDer().getIzq().getNombre()+"1");
//				System.out.println(c.getDer().getDer().getIzq().getDer().getDer().getNombre()+"1");
//			}
//		}
		
	}
	
	public String printGenera(String cadena) {
		for (Node c : tablaNodos[0][cadena.length()-1]) {
			if(c.getNombre().equals("S")) {
				BTreePrinter.printNode(c);
				System.out.println("La cadena puede ser generada");
				System.out.println(BTreePrinter.global);
				return BTreePrinter.global;
			}
		}
		
		System.out.println("La cadena no puede ser generada");
		
		return null;
	}
	
//	public static void main(String[] args) {
//		String [][] tablaGramatica=new String[4][10];
//		
//		int n=4,
//			m=5;
//		
//		tablaGramatica[0][0]="S";
//		tablaGramatica[1][0]="A";
//		tablaGramatica[2][0]="B";
//		tablaGramatica[3][0]="C";
//		
//		tablaGramatica[0][1]="AB";
//		tablaGramatica[1][1]="BA";
//		tablaGramatica[2][1]="CC";
//		tablaGramatica[3][1]="AB";
//		
//		tablaGramatica[0][2]="BC";
//		tablaGramatica[1][2]="a";
//		tablaGramatica[2][2]="b";
//		tablaGramatica[3][2]="a";
//		
//		CYKNodes.printTabla2D(tablaGramatica);
//		
//		CYKNodes cyk=new CYKNodes(m,n,tablaGramatica);
//		
//		
//		String cadena = "baaba";
//		
//		cyk.generaMatriz(cadena);
////		cyk.printMatrizFinal(cadena);
////		
//		cyk.printGenera(cadena);
//	}
}

class BTreePrinter {
	
	static String global="";

    public static void printNode(Node root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
               // System.out.print(node.getNombre());
                global+=node.getNombre();
                newNodes.add(node.getIzq());
                newNodes.add(node.getDer());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                global+=" ";
                //System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        //System.out.println("");
        global+="\n";

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getIzq() != null) {
                   // System.out.print("/");
                	global+="/";
                }else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getDer() != null) {
                    //System.out.print("\\");
                    global+="\\";
                }else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }
            global+="\n";
            //System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) {
            //System.out.print(" ");
        	global+=" ";
        }
    }

    private static int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.getIzq()), BTreePrinter.maxLevel(node.getDer())) + 1;
    }

    private static boolean isAllElementsNull(List<Node> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}

class Node{
	
	private String nombre;
	
	private Node izq,der;
	
	public Node() {
		this.nombre=null;
		this.izq=this.der=null;
	}
	
	public Node(String noT, Node p1, Node p2) {
		this.nombre=noT;
		this.izq=p1;
		this.der=p2;
	}

	public Node getIzq() {
		return izq;
	}
	
	public Node getDer() {
		return der;
	}
	
	public String getNombre() {
		return nombre;
	}
}