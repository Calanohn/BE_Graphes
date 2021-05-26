package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.*;

import java.util.*;

import java.io.*;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;


import org.insa.graphs.model.*;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;

public class DijkstraTest {

    /* List of Graphs to be used in the tests */
	/* List of Graphs to be used in the tests */

	private static Graph graphRun = null ;

	protected ShortestPathSolution calc(ShortestPathData data) {
		DijkstraAlgorithm DijkstraData = new DijkstraAlgorithm(data);
		return DijkstraData.run();
	}

	
	public static void initAll() throws IOException  {
		final String mapRun = "C:\\Users\\User\\Desktop\\INSA\\3A\\BE_Graphes\\Maps\\Reunion\\reunion.mapgr";
		final GraphReader readerRun = new BinaryGraphReader(
	            new DataInputStream(new BufferedInputStream(new FileInputStream(mapRun))));
		
		DijkstraTest.graphRun = readerRun.read();
	}

	
	/* chemin valide */
	

	@Test
	public void testIsValid() {
		final ShortestPathData dataRun = new ShortestPathData(graphRun,graphRun.get(1), graphRun.get(1000), ArcInspectorFactory.getAllFilters().get(0) );
		ShortestPathSolution solutionRun = calc(dataRun);
		assertTrue(solutionRun.getPath().isValid());
	}
	
	private ShortestPathData invalidData = new ShortestPathData(
		graphRun, graphRun.get(52105), graphRun.get(5), ArcInspectorFactory.getAllFilters().get(0));

	@Test
	public void testNoPath() {
		ShortestPathSolution invalidSolution = calc(invalidData);
		assertFalse(invalidSolution.isFeasible());
	}

	@Test
	public void LengthComparaison() {
		ShortestPathData data;
		Random random=new Random();
		int O;
		int D;
		int max=graphRun.size();
		
		for (int i = 0 ; i<10 ; i++) {
			O = random.nextInt(max);
			D = random.nextInt(max);
			data=new ShortestPathData(graphRun, graphRun.get(O), graphRun.get(D), ArcInspectorFactory.getAllFilters().get(0));
			BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
			ShortestPathSolution solBellman = Bellman.run();
			ShortestPathSolution solDjikstra = calc(data);
			
			if (solDjikstra.isFeasible() && solBellman.isFeasible()) {
				assertEquals(solDjikstra.getPath().getLength(), solBellman.getPath().getLength(),1e-5);
			} else {
				assertFalse(solDjikstra.isFeasible());
				assertFalse(solBellman.isFeasible());
			}	
		}
	}


	@Test
	public void TimeComparaison() {
		ShortestPathData data ;
		Random random=new Random();
		int O=0;
		int D=0;
		int max=graphRun.size();

		for (int i = 0 ; i<10 ; i++) {
			O = random.nextInt(max);
			D = random.nextInt(max);
			data=new ShortestPathData(graphRun,graphRun.get(O), graphRun.get(D), ArcInspectorFactory.getAllFilters().get(2) );
			BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
			ShortestPathSolution solBellman = Bellman.run();
			ShortestPathSolution solDjikstra = calc(data);
			
			if (solDjikstra.isFeasible() && solBellman.isFeasible()) {
				assertEquals(solDjikstra.getPath().getMinimumTravelTime(), solBellman.getPath().getMinimumTravelTime(),1e-5);
			} else {
				assertFalse(solDjikstra.isFeasible());
				assertFalse(solBellman.isFeasible());
			}
		}
	}

	
	@Test
	public void VoitureSeulement() {
		ShortestPathData data ;
		Random random=new Random();
		int O=0;
		int D=0;
		int max=graphRun.size();

		for (int i = 0 ; i<10 ; i++) {
			O = random.nextInt(max);
			D = random.nextInt(max);
			data=new ShortestPathData(graphRun,graphRun.get(O), graphRun.get(D), ArcInspectorFactory.getAllFilters().get(1) );
			BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
			ShortestPathSolution solBellman = Bellman.run();
			ShortestPathSolution solDjikstra = calc(data);

			if (solDjikstra.isFeasible() && solBellman.isFeasible()) {
				assertEquals(solDjikstra.getPath().getLength(), solBellman.getPath().getLength(),1e-5);
			} else {
				assertFalse(solDjikstra.isFeasible());
				assertFalse(solBellman.isFeasible());
			}
		}
	}
	
}
	