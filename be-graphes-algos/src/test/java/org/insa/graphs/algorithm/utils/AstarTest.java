package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public class AstarTest extends DijkstraTest{

	
	protected ShortestPathSolution calc(ShortestPathData data) {
		AStarAlgorithm AStarData = new AStarAlgorithm(data);
		return AStarData.run();
	}
	
	
	
	
}
