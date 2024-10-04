// NOTE: Compile with javac FlightProject.java before running

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Path {
    
    String name;
    int cost = 0;
    int time = 0;
    
    
}

class City {
    
    String name;
    
    LinkedList<Path> paths;
    
    City(String n) {
        name = n;
        paths = new LinkedList<Path>();
    }
    
    void updatePath (String destination, int cost, int time) {
        Path p;
        for(int i = 0; i < paths.size(); i++) {
            p = paths.get(i);
            if(p == null) {
                break;
            }
            if(p.name.equals(destination)) {
                p.cost = cost;
                p.time = time;
                return;
            }
            
        }
        p = new Path();
        
        p.name = destination;
        
        p.cost = cost;
        p.time = time;
        
        paths.add(p);
    }
    
    void printPath() {
        Path p;
        for(int i = 0; i < paths.size(); i++) {
            p = paths.get(i);
        }
    }
}


class Plan {
	
	String source, destination, sortBy;
	LinkedList<Plan> plans;
	
	Plan(String s, String d, String sort) {
		
		source = s;
		destination = d;
		sortBy = sort;
		
	}
	
	void printPlan() {
		Plan p;
		for(int i = 0; i < plans.size(); i++) {
            p = plans.get(i);
            System.out.println("   Source: " + p.source + "   Destination: " + p.destination + "    Sort By: " + p.sortBy);
        }
		
	}
	
}


class Route
{
    LinkedList<Path> path; // route path
    LinkedList<City> list; // list of cities visited so far
    int level;

    Route() {
         path = new LinkedList<Path>();
         list = new LinkedList<City>();
	 level = 1;
    }

    Route(Route o) {
        path = new LinkedList();
	path = (LinkedList) o.path.clone();

        list = new LinkedList();
	list = (LinkedList) o.list.clone();

	level = o.level + 1;
    }

    int getTotalCost() {
        int retVal = 0;

	Path p;
        for(int i = 0; i < path.size(); i++) {
           p = path.get(i);
	   retVal += p.cost;
	}
	return retVal;
    }

    int getTotalTime() {
        int retVal = 0;

	Path p;
        for(int i = 0; i < path.size(); i++) {
           p = path.get(i);
	   retVal += p.time;
	}
	return retVal;
    }

    String pathString() {
        StringBuilder buffer = new StringBuilder();
	Path p;
        for(int i = 0; i < path.size(); i++) {
           p = path.get(i);
	   buffer.append("-");
	   buffer.append(p.name); 
        }
	return buffer.toString();
    }

    void dumpPath() {
    	
        System.out.println();
        //System.out.println("Orig:" + list.getFirst().name);
        //System.out.println("Dest:" + path.getLast().name);
        //System.out.println("Path:" + pathString());
        System.out.println("total cost: " + getTotalCost());
        System.out.println("total time: " + getTotalTime());

	Path p;
        for(int i = 0; i < path.size(); i++) {
           p = path.get(i);
           int k = i+1;
           System.out.println("Stop[" + k + "] " + p.name + " cost:" + p.cost + " time:" + p.time);
	}
        System.out.println();
        System.out.println();
        
    }

    void dumpData() {
    	// (removed)
    }
}


public class FlightProject
{
    LinkedList<City> cities;
    LinkedList<Plan> plans;
    
    public FlightProject() {
        cities = new LinkedList<City>();
        plans = new LinkedList<Plan>();
        
    }
    
    City getCity(String name) {
        City c;
		for (int i = 0; i < cities.size(); i++) {
		    c = cities.get(i);
			
			if(c.name.equals(name)) {
			    return c;
			}

        }
        
        return null;
        
    }
    
    City addCity(String name) {
        City c = new City(name);
        
        cities.add(c);
        
        return c;
    }
    
    void dumpCity() {
        City c;
        for(int i = 0; i < cities.size(); i++) {
            c = cities.get(i);
            c.printPath();
        }
    }
    
    void addPlan(String s, String d, String sortBy) {
    	Plan p =  new Plan(s,d,sortBy);
    	
    	plans.add(p);
    
    }
    
    
    void dumpPlan() {
    	Plan p;
    	for(int i = 0; i < plans.size(); i++) {
    		p = plans.get(i);
    		
    	}
    	
    }
    
    boolean searchCityList(String name, LinkedList<City> visited) {
        for(int i = 0; i < visited.size(); i++) {
	   if (name.equals(visited.get(i).name))
	      return true;
	}
        
	return false;
    }

    boolean searchPathList(String name, LinkedList<Path> visited) {
        for(int i = 0; i < visited.size(); i++) {
	   if (name.equals(visited.get(i).name))
	      return true;
	}

	return false;
    }


    // Generate: generate the list of paths from orig to dest
    LinkedList<Route> Generate(String orig, String dest) {
	Stack<Route> theStack;
        LinkedList<Route> rList;

	theStack = new Stack<Route>();
	rList = new LinkedList<Route>();

		System.out.println("====================================");
        System.out.println("Orig:" + orig + " Dest:" + dest);
        City c, d, newC;

        c = getCity(orig); // Check if the input cities exist in our list of cities
	if (c == null) {
           System.out.println("Orig:" + orig + " not found");
	   return rList;
	}	

        d = getCity(dest);
	if (c == null) {
           System.out.println("Dest:" + dest + " not found");
	   return rList;
	}	

	// first level path
	Path p;
	Route r, newR;
	for(int i = 0; i < c.paths.size(); i++) {
	   p = c.paths.get(i);
           r = new Route();
           r.list.add(c);
	   r.path.add(p);
	   theStack.push(r);

	   r.dumpData();
	   if (dest.equals(r.path.getLast().name)) {
		   rList.add(r);
	   }
	}

	boolean done = false;
	while (!done) {
	  if (theStack.empty()) {
	     break;
	  }

          r = theStack.pop(); 
	  r.dumpData();

	  p = r.path.getLast();
	  c = getCity(p.name);
	  if (c == null) {
	      continue;
          }

	  for(int i = 0; i < c.paths.size(); i++) {
	     p = c.paths.get(i);
	     newC = getCity(p.name);
	     if (c == null) {
		 continue;
	     }

	     if (!searchCityList(p.name, r.list)) {

	           if (!searchPathList(p.name, r.path)) {
	              newR = new Route(r);
	              newR.list.add(c);
	              newR.path.add(p);
	              theStack.push(newR);
	              
	              newR.dumpData();

	              if (dest.equals(newR.path.getLast().name)) {
	            	  
		          rList.add(newR);
	              }
		   }

             }
	     else {

	     }
	  } 
	}

	return rList;
    }
    
	public static void main(String[] args) {
	    
	    int count = 0;
	    
	    // Used for command line
	    if (args.length != 3) {
            System.out.println("Usage: ./flightPlan <FlightDataFile> <PathsToCalculateFile> <OutputFile>");
            return;
	    }
	    
	    String cityName;
	    String destName;
	    int cost = 0;
	    int time = 0;
	    
	    BufferedReader reader;
	    
	    FlightProject project = new FlightProject();
	    
	    try {
			reader = new BufferedReader(new FileReader(args[0]));
			String line = reader.readLine();
			
			count = Integer.parseInt(line);
			
			if(count <= 0) {
			    return;
			}
			
			line = reader.readLine();
			
			while (line != null) {
				String[] splitLine = line.split("\\|"); // "|" is a special character - use \\
				
				// Search city linked list 
				City c = project.getCity(splitLine[0]);
				
				if (c == null) {
				    c = project.addCity(splitLine[0]);
				}
				
				c.updatePath(splitLine[1],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[3])); 
				
				// Search city2 linked list
				City c2 = project.getCity(splitLine[1]);
				
				if (c2 == null) {
				    c2 = project.addCity(splitLine[1]);
				}
				
				c2.updatePath(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[3])); 
				
				
				
				// read next line
				line = reader.readLine();
				
				
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    project.dumpCity();
	    
	    // Read in file name for request flights
	    try {
			reader = new BufferedReader(new FileReader(args[1])); // Use better file name
			String line = reader.readLine();
			

			count = Integer.parseInt(line);
	    
			if(count <= 0) {
			    return;
			}
			
			line = reader.readLine();
			
			while (line != null) {
				
				String[] splitLine = line.split("\\|"); // "|" is a special character - use \\
				
				LinkedList<Route> candidateList;
				
				project.addPlan(splitLine[0], splitLine[1], splitLine[2]);
				
				candidateList = project.Generate(splitLine[0], splitLine[1]);
				
				String sortCon = splitLine[2];
				
			    Route r;
		        
		        if(sortCon.equals("C")) {
		        
		        System.out.println("Sorting by cost.");
		        System.out.println();
		        candidateList.sort( new Comparator<Route>() {
		        @Override
		        public int compare(Route r1, Route r2) {
		           return r1.getTotalCost() - r2.getTotalCost();
		        }
				    
				    
			    });
		        
		        }
		        else if(sortCon.equals("T")) {
		        	
		        System.out.println("Sorting by time.");
		        System.out.println();
		        candidateList.sort( new Comparator<Route>() {
		        @Override
		        public int compare(Route r1, Route r2) {
		           return r1.getTotalTime() - r2.getTotalTime();
		        }

		        });
		            
		        }
		        else { // Case anything other than C or T is there
		        	
		        	System.out.println("Invalid argument for sort condition. Defaulting to sorting by cost.");
		        	System.out.println();
		        	
		        	candidateList.sort( new Comparator<Route>() {
		            @Override
		            public int compare(Route r1, Route r2) {
		               return r1.getTotalCost() - r2.getTotalCost();
		            }
		        		
		        		
		        	});
		        }
		        
		        int pathNum;
		        for(int i = 0; i < 3; i++) {
			        r = candidateList.get(i);
			        
			        pathNum = i + 1;
			        
			        System.out.println("Path " + pathNum);
		            r.dumpPath();
		            
		            
			    }
				
				line = reader.readLine();
			}
	    } catch (IOException e) {
			e.printStackTrace();
		}
	    
	    project.dumpPlan();
	    
	}
	
}

