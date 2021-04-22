package simulation;

import distanceMatrix.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimmulatedA {
    // Best solution
    private ArrayList<String> bestSolution;
    // Current Solution
    private ArrayList<String> currentSolution;

    // Matrix containing all the cities
    private ArrayList<String> nextSolution;
    // Array containing the cities of the problem
    private DistanceMatrix allCities;

    private float temperature;
    private int nIter;
    private int initIter;
    Random rand;

    public SimmulatedA(String cities1, int n_iter, DistanceMatrix matrix) {
        allCities = new DistanceMatrix(matrix, cities1);
        

        currentSolution = (ArrayList<String>) allCities.getCities().clone();
        rand = new Random();
        Collections.shuffle(currentSolution);
        bestSolution = (ArrayList<String>) currentSolution.clone();
        nIter = n_iter;
        initIter = n_iter;
        temperature = (float) (-dMax(allCities) / Math.log(0.9));

    }

    // maxDistance
    private static int dMax(DistanceMatrix dMatrix) {
        int maxD = 0, minD = 0;
        ArrayList<ArrayList<Integer>> distances = dMatrix.getDistances();
        for (ArrayList<Integer> x : distances) {
            for (Integer a : x) {
                if (minD > a)
                    minD = a;
                if (maxD < a)
                    maxD = a;
            }
        }
        return 2 * (maxD - minD);
    }

    public ArrayList<String> getSolution() {
        while (!stopCriteria() && nIter > 0) {
            getNeighbour();
            int d = getSolutionDistance(nextSolution) - getSolutionDistance(currentSolution);
            if (getSolutionDistance(nextSolution) < getSolutionDistance(bestSolution)) {
                currentSolution = (ArrayList<String>) nextSolution.clone();
                bestSolution = (ArrayList<String>) currentSolution.clone();
            } else if (Math.random() <= Math.exp(-d / temperature))
                currentSolution = (ArrayList<String>) nextSolution.clone();

            nIter--;
            if (nIter == 0) {
            	var_n_iter();
                temperature = decay(temperature);

            }

        }
        return bestSolution;

    }

    private float decay(float t) {
        return t * 0.5f;
    }

    private void var_n_iter() {
        nIter = (int) (initIter* 1.1);
        initIter = nIter;
    }

    public void getNeighbour() {
        int i = rand.nextInt(currentSolution.size() - 1);
        int j = rand.nextInt(currentSolution.size() - i - 1) + i + 1;
        int diff = j - i;
        nextSolution = (ArrayList<String>) currentSolution.clone();
        for (int k = 0; k < diff / 2; k++) {
            Collections.swap(nextSolution, i + k, j - k);
        }

    }

    public boolean stopCriteria() {
        return temperature < 1;

    }

    public int getSolutionDistance(ArrayList<String> solution) {
    	int distance = allCities.distance(solution.get(0), solution.get(solution.size()-1));
        // System.out.println(solution);
        for (int i = 0; i < solution.size() - 1; i++) {
            distance += (allCities.distance(solution.get(i), solution.get(i + 1)));
        }
        // System.out.println(distance);

        return distance;
    }
}
