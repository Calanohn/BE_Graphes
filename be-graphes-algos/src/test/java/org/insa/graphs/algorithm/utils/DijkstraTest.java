package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.*;

import java.util.*;

import java.io.*;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Path;


import org.insa.graphs.model.*;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest {

    /* List of Graphs to be used in the tests */
	/* List of Graphs to be used in the tests */

	private static Graph graphRun = null ;

	protected ShortestPathSolution calc(ShortestPathData data) {
		DijkstraAlgorithm DijkstraData = new DijkstraAlgorithm(data);
		return DijkstraData.run();
	}

	
	public static void initAll() throws IOException  {//final String mapRun = "C:\\Users\\User\\Desktop\\INSA\\3A\\BE_Graphes\\Maps\\Midi-Py\\midi-pyrenees.mapgr";
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

	//chemin null
	@Test
	public void testNullPath() {
		final ShortestPathData nullPathData = new ShortestPathData(
				graphRun, graphRun.get(1), graphRun.get(1), ArcInspectorFactory.getAllFilters().get(0)) ;
		ShortestPathSolution solutionRun = calc(nullPathData);
		assertTrue(solutionRun.isFeasible());
	}


	@Test
	public void LengthComparaison() {
		ShortestPathData data;
		Random random=new Random();
		int origin=0;
		int dest=0;
		int max=graphRun.size();
		
		for (int i = 0 ; i<10 ; i++) {
			origin = random.nextInt(max);
			dest = random.nextInt(max);
			data=new ShortestPathData(graphRun, graphRun.get(origin), graphRun.get(dest), ArcInspectorFactory.getAllFilters().get(0));
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
		int origin=0;
		int dest=0;
		int max=graphRun.size();

		for (int i = 0 ; i<10 ; i++) {
			origin = random.nextInt(max);
			dest = random.nextInt(max);
			data=new ShortestPathData(graphRun,graphRun.get(origin), graphRun.get(dest), ArcInspectorFactory.getAllFilters().get(2) );
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

	
	/**
	 * Comparison of the algorithm and Bellman-Ford algorithm for 10 seperate Paths in "Shortest path, only roads open for cars" mode
	 * Tolerance of Length 1e-5 between the two
	 */
	@Test
	public void VoitureSeulement() {
		ShortestPathData data ;
		Random random=new Random();
		int origin=0;
		int dest=0;
		int max=graphRun.size();

		for (int i = 0 ; i<10 ; i++) {
			origin = random.nextInt(max);
			dest = random.nextInt(max);
			data=new ShortestPathData(graphRun,graphRun.get(origin), graphRun.get(dest), ArcInspectorFactory.getAllFilters().get(1) );
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
	public void EqualLength() {
		final ShortestPathData dataRun = new ShortestPathData(graphRun,graphRun.get(1), graphRun.get(1000), ArcInspectorFactory.getAllFilters().get(0) );
		ShortestPathSolution solutionRun = calc(dataRun);

		List <Node> nodes= new ArrayList <Node>();
		nodes.add(dataRun.getOrigin());

		List<Arc> arcs = solutionRun.getPath().getArcs();
		for (Arc arc : arcs) {
			nodes.add(arc.getDestination());
		}

		assertEquals(solutionRun.getPath().getLength(), Path.createShortestPathFromNodes(graphRun, nodes).getLength(),1e-15);
	}
	
	@Test
	public void EqualTime() {
		final ShortestPathData dataRun = new ShortestPathData(graphRun,graphRun.get(1), graphRun.get(1000), ArcInspectorFactory.getAllFilters().get(0) );
		ShortestPathSolution solutionRun = calc(dataRun);

		List <Node> nodes= new ArrayList <Node>();
		nodes.add(dataRun.getOrigin());

		List<Arc> arcs = solutionRun.getPath().getArcs();
		for (Arc arc : arcs) {
			nodes.add(arc.getDestination());
		}

		assertEquals(solutionRun.getPath().getMinimumTravelTime(), Path.createFastestPathFromNodes(graphRun, nodes).getMinimumTravelTime(),1e-15);
	}
}
	