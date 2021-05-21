package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
//import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm.Label;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;


public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
     
    /*private class LabelStar extends Label implements Comparable <Label>{
    	
	    private float Estimated_Cost;
	    	
	    public LabelStar(Node O, Node D)
	    {
	    	super(O);
	    	
	    	float Dlat = (O.getPoint().getLatitude() - D.getPoint().getLatitude()) * (O.getPoint().getLatitude() - D.getPoint().getLatitude());
	    	float Dlong = (O.getPoint().getLongitude() - D.getPoint().getLongitude()) * (O.getPoint().getLongitude() - D.getPoint().getLongitude());
	    	
	    	this.Estimated_Cost = (float) Math.sqrt(Dlat + Dlong); 
	    }
	    	
	    public float getCost()
	    {
	    	return this.cout + this.Estimated_Cost;
	    }
	    
	    public float getEstimated()
	    {
	    	return this.Estimated_Cost;
	    }
	    	
	    public int compareTo(LabelStar l) {
	    	if(Float.compare(this.getCost(), l.getCost()) == 0)
	    		{return Float.compare(this.getCost(), l.getCost());}
	    	else
	    	{
	    		return Float.compare(this.getEstimated(), l.getEstimated());
	    	}
	    		
	    }*/
    
    /*protected LabelStar InitCourant() {
    	LabelStar courant = null;
    	return courant;
    }
    
    protected BinaryHeap<Label> Init_TAS(ShortestPathData data){
    	
    	BinaryHeap<Label> TAS = new BinaryHeap<Label>();
    	
    	for(int i=0; i < data.getGraph().size(); i++) //Construction de Corresp
        {
        	LabelStar label = new LabelStar(data.getGraph().get(i), data.getDestination());
        	 
        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        		TAS.insert(label);
        	}
        	
        }
    	
    	return TAS;
    }*/
    
    
    protected Label[] InitCorresp(ShortestPathData data) {
    	Label[] Corresp = new LabelStar[data.getGraph().size()];
    	
    	for(int i=0; i < data.getGraph().size(); i++) //Construction de Corresp
        {
        	LabelStar label = new LabelStar(data.getGraph().get(i), data.getDestination());

        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        	}
        	
        	Corresp[label.get_Som().getId()] = label;
        	
        }
    	
    	return Corresp;
    	
    }

}
