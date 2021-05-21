package org.insa.graphs.model;

public class LabelStar extends Label {
	
	private float Estimated_Cost;
	
	
	public LabelStar(Node O, Node D)
	{
		super(O);
    	this.Estimated_Cost = (float)O.getPoint().distanceTo(D.getPoint());
	}
	
	 public float getTotalCost()
	    {
	    	return this.cout + this.Estimated_Cost;
	    }
	    
	 public float getEstimated()
	    {
	    	return this.Estimated_Cost;
	    }
	    	
	 /*public int compareTo(LabelStar l) {
	    	if(Float.compare(this.getTotalCost(), ((LabelStar) l).getTotalCost()) != 0)
	    		{return Float.compare(this.getTotalCost(), ((LabelStar) l).getTotalCost());}
	    	else
	    	{
	    		return Float.compare(this.getEstimated(), ((LabelStar) l).getEstimated());
	    	}
	    }*/
}