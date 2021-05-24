package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
	
	private double Estimated_Cost;
	
	
	public LabelStar(Node O, Node D, ShortestPathData data)
	{
		super(O);
        switch (data.getMode()) {
            case LENGTH:
                this.Estimated_Cost = O.getPoint().distanceTo(D.getPoint());
                System.out.println("Cout estimé en distance : " + this.Estimated_Cost);
                break;

            case TIME:
                this.Estimated_Cost =  O.getPoint().distanceTo(D.getPoint()) * 3600.0 / (data.getGraph().getGraphInformation().getMaximumSpeed() * 1000.0);
                
            default:
                break;
        }
	}
	
	 public double getTotalCost()
	    {
	    	return (double) this.cout + this.Estimated_Cost;
	    }
	    
	 public double getEstimated()
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