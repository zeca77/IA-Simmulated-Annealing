import java.util.ArrayList;

import distanceMatrix.*;
import simulation.*;

public class Main {
   public static void main(String[] args) {
      String cities1 = "AHIKNRYZ";

      DistanceMatrix matrix = new DistanceMatrix("distances.txt");
      SimmulatedA simulation = new SimmulatedA(cities1, cities1.length(), matrix);
      ArrayList<String> sol = simulation.getSolution();
     
         System.out.println(sol);
         System.out.println(simulation.getSolutionDistance(sol));
         

      

   }
}