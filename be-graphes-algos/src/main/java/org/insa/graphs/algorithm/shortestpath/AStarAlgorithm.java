package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Path solution = null;
        Label courant = null;
        boolean continuer = true;
        
        //Label[] labels = new Label[data.getGraph().size()];
        BinaryHeap<LabelStar> TAS = new BinaryHeap();
        LabelStar[] Corresp = new LabelStar[data.getGraph().size()];
        float new_cost;
        
        for(int i=0; i < data.getGraph().size(); i++) //Construction de Corresp
        {
        	LabelStar label = new LabelStar(data.getGraph().get(i), data.getGraph.getDestination);
        	
        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        		TAS.insert(label);
        	}
        	
        	Corresp[label.get_Som().getId()] = label;
        	
        }
        
        
        
        
        System.out.println("creation du tas check");
        
        //ArrayList<Node> Fini = new ArrayList<Node>(data.getGraph().getNodes());
        //List<Arc> recherche = new ArrayList<Arc>();
        
        
        
        //Corresp[data.getOrigin().getId()].Set_Cost(0);
    	//TAS.insert(Corresp[data.getOrigin().getId()]);
        
        //for(int i = 0; i<data.getGraph().size()/2 ;i++) //passage sur tous les points du graphes
        //for(int i = 0; i<4;i++)
        int compte = 1;
        boolean nouveau = false;
        
        while(continuer)
        {
        	compte++;
        	
        	TAS.findMin().Mark();
        	courant = TAS.deleteMin(); //on prend le plus petit
        	//courant.Mark(); //on le marque
        	
        	notifyNodeMarked(courant.get_Som());
        	System.out.println("etape :" + compte + " noeud : " + Corresp[courant.get_Som().getId()].get_Som().getId());
        	
        	
        	//System.out.println("Thread 4 ?");
        	for(Arc n: courant.get_Som().getSuccessors()) //mise a jour pour chaque successeur
        	{
        		if(data.isAllowed(n))
        		{
        		notifyNodeReached(n.getDestination());
        		
        		new_cost = n.getLength() + courant.getCost();
        		//System.out.println(new_cost + " " + Corresp[n.getDestination().getId()].getCost());
        		if(new_cost < Corresp[n.getDestination().getId()].getCost()) 
        		{
        			if(Corresp[n.getDestination().getId()].getCost()>=1.0f/0.0f) nouveau = true;
        				
        			Corresp[n.getDestination().getId()].Set_Cost(new_cost);
        			Corresp[n.getDestination().getId()].Set_father(n);
        			
        			if(n.getDestination() == data.getDestination()) continuer = false;
        			
        			if(nouveau)
            		{
        				nouveau = false;
            			TAS.insert(Corresp[n.getDestination().getId()]);
            			System.out.println("ajout de  : " + Corresp[n.getDestination().getId()].get_Som().getId());
            		}
        			else
        			{
        				System.out.println("ajout de  : " + Corresp[n.getDestination().getId()].get_Som().getId());
        				TAS.remove(Corresp[n.getDestination().getId()]);
        				TAS.insert(Corresp[n.getDestination().getId()]);
        				
        			}
        		}
        		}
        							
        	}

        	
        }
        
        
        List<Node> chemin = new ArrayList<Node>();
        
        chemin.add(data.getDestination());
        
        //System.out.println("avant boucle");
        while(chemin.get(chemin.size()-1) != data.getOrigin()) //retour sur le chemin
        {
        	//System.out.println("dans la boucle");
        	chemin.add(Corresp[chemin.get(chemin.size()-1).getId()].Get_father().getOrigin());
        }
        
        Collections.reverse(chemin);
        System.out.println("gg");
        return new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(data.getGraph(), chemin));
        
    }

}
