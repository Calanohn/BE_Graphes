package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.*;


public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }    
    
    protected Label[] InitCorresp(ShortestPathData data) {
    	Label[] Corresp = new LabelStar[data.getGraph().size()];
    	
    	for(int i=0; i < data.getGraph().size(); i++) //Construction of Corresp
        {
        	LabelStar label = new LabelStar(data.getGraph().get(i), data.getDestination(), data);

        	if(data.getGraph().get(i) == data.getOrigin())
        	{
        		label.Set_Cost(0);
        	}
        	
        	Corresp[label.get_Som().getId()] = label;
        	
        }
    	
    	return Corresp;
    	
    }

}
