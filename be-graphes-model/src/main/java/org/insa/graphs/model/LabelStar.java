package org.insa.graphs.model;
import Label.java;
import Node;

public class LabelStar extends Label implements Comparable <LabelStar>{
	
	private float Estimated_Cost;
	
	public LabelStar(Node O, Node D)
	{
		super(Node O);
		this.Estimated_Cost = Math.sqrt(Node.getPoint.getLattitude * Node.getPoint.getLattitude + Node.getPoint.getLontitude * Node.getPoint.getLontitude); 
	}
	
	public float getTotalCost ()
	{
		return this.cout + this.Estimated_Cost;
	}
	
	public int compareTo(LabelStar l) {
		if(Float.compare(this.cout + this.Estimated_Cost, l.cout + this.Estimated_Cost))
			{return Float.compare(this.cout + this.Estimated_Cost, l.cout + this.Estimated_Cost);}
		else
		{
			return Float.compare(this.Estimated_Cost, this.Estimated_Cost);
		}
		
	}
}