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
    
    /*public class Label implements Comparable <Label>{
    	
    	private Node sommet_courant;
    	private boolean marque;
    	private float cout;
    	private Arc pere;
    	
    	public Label(Node s){
    		this.sommet_courant = s;
    		this.cout = 1.0f/0.0f;
    		this.pere = null;
    	}
    	
    	public float getCost() {
    		return this.cout;
    	}
    	
    	public Node get_Som() {
    		return this.sommet_courant;
    	}
    	
    	public boolean getMark() {
    		return this.marque;
    	}
    	
    	public void Mark() {
    		this.marque = true;
    	}
    	public void Set_Som(Node n) {
    		this.sommet_courant = n;
    	}
    	
    	public void Set_Cost(float n) {
    		this.cout = n;
    	}
    	
    	public void Set_father(Arc p) {
    		this.pere = p;
    	}
    	
    	public Arc Get_father() {
    		return this.pere;
    	}
    	
    	public int compareTo(Label l) {
    		return Float.compare(this.cout, l.cout);
    	}
    	
    }*/
    
    
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
        //Label courant = null;
        boolean continuer = true;
        boolean no_sol = false;
        float new_cost;
        
        //Label[] labels = new Label[data.getGraph().size()];
        /*BinaryHeap<Label> TAS = new BinaryHeap<Label>();
        Label[] Corresp = new Label[data.getGraph().size()];
        float new_cost;
        
        for(int i=0; i < data.getGraph().size(); i++) //Construction de Corresp
        {
        	Label label = new Label(data.getGraph().get(i));
        	
        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        		TAS.insert(label);
        	}
        	
        	Corresp[label.get_Som().getId()] = label;
        	
        }*/
        
        Label courant= null;
        
        Label[] Corresp = InitCorresp(data);
        
        BinaryHeap<Label> TAS = new BinaryHeap<Label>();

        TAS.insert(Corresp[data.getOrigin().getId()]);
        

        
        int compte = 1;
        
        while(continuer)
        {
        	compte++;
        	
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
        	//System.out.println("etape :" + compte + " noeud : " + Corresp[courant.get_Som().getId()].get_Som().getId());
        	
        	
        	for(Arc n: courant.get_Som().getSuccessors()) //mise a jour pour chaque successeur
        	{
        		if(data.isAllowed(n))
        		{
        			notifyNodeReached(n.getDestination());
        		
        			new_cost = n.getLength() + courant.getCost();
        		//System.out.println(new_cost + " " + Corresp[n.getDestination().getId()].getCost());
	        		if(new_cost < Corresp[n.getDestination().getId()].getCost()) 
	        		{
	        			if(Corresp[n.getDestination().getId()].getCost()>=1.0f/0.0f)
	            		{
	        				Corresp[n.getDestination().getId()].Set_Cost(new_cost);
	        				Corresp[n.getDestination().getId()].Set_father(n);
	        			
	            			TAS.insert(Corresp[n.getDestination().getId()]);
	            			//System.out.println("ajout de  : " + Corresp[n.getDestination().getId()].get_Som().getId());
	            		}
	        			else
	        			{
	        				//System.out.println("ajout de  : " + Corresp[n.getDestination().getId()].get_Som().getId());
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
        	System.out.println("pas de solution");
        	return new ShortestPathSolution(data, Status.INFEASIBLE, Path.createShortestPathFromNodes(data.getGraph(), chemin));
        }
        else
        {
        
        
        chemin.add(data.getDestination());
        
        //System.out.println("avant boucle");
        while(chemin.get(chemin.size()-1) != data.getOrigin()) //retour sur le chemin
        {
        	//System.out.println("dans la boucle");
        	chemin.add(Corresp[chemin.get(chemin.size()-1).getId()].Get_father().getOrigin());
        }
        
        Collections.reverse(chemin);
        return new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(data.getGraph(), chemin));
        }
    }

}


