package org.insa.graphs.model;

public class Label implements Comparable <Label>{
	
	private Node sommet_courant;
	private boolean marque;
	private int cout;
	private Arc pere;
	
	public Label(Node s){
		this.sommet_courant = s;
		this.cout = 1/0;
		this.pere = null;
	}
	
	public int getCost() {
		return this.cout;
	}
	
	public Node get_Som() {
		return this.sommet_courant;
	}
	
	public boolean getMark() {
		return this.marque;
	}
	
	public void Set_Som(Node n) {
		this.sommet_courant = n;
	}
	
	public void Set_Cost(int n) {
		this.cout = n;
	}
	
	public void Set_father(Arc p) {
		this.pere = p;
	}
	
	public int compareTo(Label l) {
		return Float.compare(this.cout, l.cout);
	}
	
}