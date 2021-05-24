package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import org.insa.graphs.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected Label[] InitCorresp(ShortestPathData data) {
    	Label[] Corresp = new Label[data.getGraph().size()];
    	
    	for(int i=0; i < data.getGraph().size(); i++) //Construction de Corresp
        {
        	Label label = new Label(data.getGraph().get(i));

        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        	}
        	
        	Corresp[label.get_Som().getId()] = label;
        	
        }
    	
    	return Corresp;
    	
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        boolean continuer = true;
        boolean no_sol = false;
        double new_cost;
        
        Label courant= null;
        
        Label[] Corresp = InitCorresp(data);
        
        BinaryHeap<Label> TAS = new BinaryHeap<Label>();

        TAS.insert(Corresp[data.getOrigin().getId()]);
        
        
        while(continuer)
        {
        	
        	if(TAS.isEmpty())
        	{
        	continuer = false;
        	no_sol = true;
        	}
        	else
        	{
        	TAS.findMin().Mark();
        	courant = TAS.deleteMin(); //on prend le plus petit
        	if(courant == Corresp[data.getDestination().getId()]) continuer = false;
        	
        	notifyNodeMarked(courant.get_Som());
        	
        	
        	for(Arc n: courant.get_Som().getSuccessors()) //mise a jour pour chaque successeur
        	{
        		if(data.isAllowed(n))
        		{
        			notifyNodeReached(n.getDestination());
        		
        			new_cost = data.getCost(n)+ courant.getCost();
	        		if(new_cost < Corresp[n.getDestination().getId()].getCost()) 
	        		{
	        			if(Corresp[n.getDestination().getId()].getCost()>=1.0f/0.0f)
	            		{
	        				Corresp[n.getDestination().getId()].Set_Cost(new_cost);
	        				Corresp[n.getDestination().getId()].Set_father(n);
	        			
	            			TAS.insert(Corresp[n.getDestination().getId()]);
	            			}
	        			else
	        			{
	        				TAS.remove(Corresp[n.getDestination().getId()]);
	        				Corresp[n.getDestination().getId()].Set_Cost(new_cost);
	        				Corresp[n.getDestination().getId()].Set_father(n);
	        				TAS.insert(Corresp[n.getDestination().getId()]);
	        				
	        			}
	        		}
        		}
        							
        	}
        	}
        	
        }
        
        List<Node> chemin = new ArrayList<Node>();
        
        if(no_sol)
        {
        	return new ShortestPathSolution(data, Status.INFEASIBLE, Path.createShortestPathFromNodes(data.getGraph(), chemin));
        }
        else
        {
        
        
        chemin.add(data.getDestination());
        
        
        while(chemin.get(chemin.size()-1) != data.getOrigin()) //retour sur le chemin
        {
        	
        	chemin.add(Corresp[chemin.get(chemin.size()-1).getId()].Get_father().getOrigin());
        }
        
        Collections.reverse(chemin);
        return new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(data.getGraph(), chemin));
       
        }
    }

}


