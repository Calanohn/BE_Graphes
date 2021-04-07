package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;
import java.util.ArrayList;
import org.insa.graphs.algorithm.utils.BinaryHeap;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Label courant;
        
        //Label[] labels = new Label[data.getGraph().size()];
        BinaryHeap<Label> TAS = new BinaryHeap();
        
        for(int i=0; i < data.getGraph().size(); i++)
        {
        	Label label = new Label(data.getGraph().get(i));
        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        		courant = label;
        	}
        	
        	TAS.insert(label);
        	
        }
        
        ArrayList<Node> Fini = new ArrayList<Node>(data.getGraph().getNodes());
        List<Arc> recherche;
        
        for(int i = 0; i<data.graph.size();i++)
        {
        	recherche = courant.get_Som().getSuccessors();
        	
        	for(Arc recherche : n )
        	{
        		
        		
        		
        		
        		
        		
        	}
        	courant = null;
        }
        
        
        
        return solution;
    }

}
