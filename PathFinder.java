import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class PathFinder{
  private MysteryUnweightedGraphImplementation wikiPages;
  //holds nodes for reference
  private HashMap<String, Integer> nameToID;
  private ArrayList<String> iDToName;
  /**
  * Constructs a PathFinder that represents the graph with nodes (vertices) specified as in
  * nodeFile and edges specified as in edgeFile.
  * @param nodeFile name of the file with the node names
  * @param edgeFile name of the file with the edge names
  */
  public PathFinder(String nodeFile, String edgeFile){
    wikiPages = new MysteryUnweightedGraphImplementation();
    nameToID = new HashMap<String, Integer>();
    iDToName = new ArrayList<String>();
    
    try{
      Scanner scan0 = new Scanner(new File(nodeFile));
      
      while(scan0.hasNext()){
        String next = scan0.nextLine();
        if(!next.contains("#")){
          String[] nextL = next.split("\t");
          nameToID.put(nextL[0], wikiPages.addVertex());
          iDToName.add(nextL[0]);
        }
      }

      Scanner scan1 = new Scanner(new File(edgeFile));

      while(scan1.hasNext()){
        String next = scan1.nextLine();
        if(!next.contains("#")){
          String[] nextL = next.split("\t");
          for(int i = 1; i < nextL.length; i ++){
            int headIdx = nameToID.get(nextL[0]);
            int endIdx = nameToID.get(nextL[i]);
            if(!wikiPages.hasEdge(headIdx, endIdx)){
              wikiPages.addEdge(headIdx, endIdx);
            }
          }
        }
      }
    }catch(FileNotFoundException e){
      System.err.println(e);
    }
  }
  
  /**
  * Returns the length of the shortest path from node1 to node2. If no path exists,
  * returns -1. If the two nodes are the same, the path length is 0.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return length of shortest path
  */
  public int getShortestPathLength(String node1, String node2){
    int end = nameToID.get(node2);
    int start = nameToID.get(node1);
    List<Integer> path = new ArrayList<Integer>();

    int prev[] = new int[iDToName.size()];

    List<Integer> visitedVerts = new ArrayList<Integer>();
    List<Integer> vertexQueue = new ArrayList<Integer>();//treat as queue
    visitedVerts.add(start);
    vertexQueue.add(start);

    while(vertexQueue.size() != 0){
      int frontVert = vertexQueue.remove(0);
      for(int neighbor : wikiPages.getNeighbors(frontVert)){
        if(!visitedVerts.contains(neighbor)){
          visitedVerts.add(neighbor);
          vertexQueue.add(neighbor);
          prev[neighbor] = frontVert;

          if(neighbor == end){
            //getPath
            int cur = end;
            while(cur != start){
              path.add(0, cur);
              int parent = prev[cur];
              cur = parent;
            }
            
            return path.size();
          }
        }
      }
    }
    return -1;
  }
  
  /**
  * Returns a shortest path from node1 to node2, represented as list that has node1 at
  * position 0, node2 in the final position, and the names of each node on the path
  * (in order) in between. If the two nodes are the same, then the "path" is just a
  * single node. If no path exists, returns an empty list.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return list of the names of nodes on the shortest path
  */
  public List<String> getShortestPath(String node1, String node2){
    int end = nameToID.get(node2);
    int start = nameToID.get(node1);
    List<String> path = new ArrayList<String>();

    int prev[] = new int[iDToName.size()];

    List<Integer> visitedVerts = new ArrayList<Integer>();
    List<Integer> vertexQueue = new ArrayList<Integer>();//treat as queue
    visitedVerts.add(start);
    vertexQueue.add(start);

    while(vertexQueue.size() != 0){
      //System.out.println("while");
      int frontVert = vertexQueue.remove(0);
      for(int neighbor : wikiPages.getNeighbors(frontVert)){
        if(!visitedVerts.contains(neighbor)){
          //System.out.println(".contains()");
          visitedVerts.add(neighbor);
          vertexQueue.add(neighbor);
          prev[neighbor] = frontVert;

          if(neighbor == end){
            //System.out.println("front = end");
            //getPath
            int cur = end;
            while(cur != start){
              String strCur = iDToName.get(cur);
              path.add(0, strCur);
              int parent = prev[cur];
              cur = parent;
            }
            path.add(0, node1);
            return path;
          }
        }
      }
    }
    return path;
  }
  
  /**
  * Returns a shortest path from node1 to node2 that includes the node intermediateNode.
  * This may not be the absolute shortest path between node1 and node2, but should be 
  * a shortest path given the constraint that intermediateNodeAppears in the path. If all
  * three nodes are the same, the "path" is just a single node.  If no path exists, returns
  * an empty list.
  * @param node1 name of the starting article node
  * @param node2 name of the ending article node
  * @return list that has node1 at position 0, node2 in the final position, and the names of each node 
  *      on the path (in order) in between. 
  */
  public List<String> getShortestPath(String node1, String intermediateNode, String node2){
    List<String> path0 = getShortestPath(node1, intermediateNode);
    List<String> path1 = getShortestPath(intermediateNode, node2);
    //printPath(path1);
    //System.out.println(path1.size());
    for(int i = 1; i < path1.size(); i ++){
      path0.add(path1.get(i));
    }
    if(getShortestPathLength(node1, intermediateNode) != -1 && getShortestPathLength(intermediateNode, node2) != -1){
      return path0;
    }

    List<String> emptyPath = new ArrayList<String>();
    return emptyPath;
  }
                
  public void printWikiPages(){
    //each i stands for the num of a vert
    for(int i = 0; i < wikiPages.numVerts(); i ++){
      System.out.print(iDToName.get(i) + ": ");
      for(int neighbor : wikiPages.getNeighbors(i)){
        System.out.print("--" + iDToName.get(neighbor));
      }
      System.out.println("");
    }
  }

  public void printPath(List<String> path){
    for(int i = 0; i < path.size(); i ++){
      if(i != path.size() - 1){
        System.out.print(path.get(i) + " --> ");
      }else{
        System.out.println(path.get(i));
      }
    }
  }

  public ArrayList<String> getVertsList(){
    return iDToName;
  }

  public static void main(String[] args){
    PathFinder game = new PathFinder(args[0], args[1]);
    //game.printWikiPages();

    if(args.length == 5){
      String start = args[2];
      String mid = args[3];
      String end = args[4];
      if(!game.getVertsList().contains(start) || !game.getVertsList().contains(mid) || !game.getVertsList().contains(end)){
        System.err.println("Your vertices were not in the Files");
        System.exit(0);
      }
      List<String> path = game.getShortestPath(start, mid, end);
      System.out.print("Path from " + start + " to " + end + " through " + mid + ", length ");
      System.out.println(path.size() - 1);
      game.printPath(path);

    }else{
      String start = args[2];
      String end = args[3];
      if(!game.getVertsList().contains(start) || !game.getVertsList().contains(end)){
        System.err.println("Your vertices were not in the Files");
        System.exit(0);
      }
      System.out.print("Path from " + start + " to " + end + ", length ");
      System.out.println(game.getShortestPathLength(start, end));
      game.printPath(game.getShortestPath(start, end));
    }
  }
}