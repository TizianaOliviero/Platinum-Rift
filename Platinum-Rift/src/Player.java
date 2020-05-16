import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

 class Player {

	public static void main(String[] args) {

		final Scanner in = new Scanner(System.in);
		GameManager gm=new GameManager(in);
		gm.initialize();
		
		while(true) {
            gm.update();
			String output="";
            HashSet<Integer> whereToGo = new HashSet<Integer>(); //per evitare 2 nella stessa pos ce le salviamo
			for(int i: gm.myPos) { //per ogni posizione in cui abbiamo pod
				System.err.println("robot in "+i);
				int value=gm.myPodZone.get(i); //ci salviamo quanti pod abbiamo
				
				ArrayList<Integer> possibleReach=gm.reachable.get(i); //vediamo dove possiamo andare
				
				
			    for(int j:possibleReach) {
			    	if(gm.ownZone.get(j)== -1 && !whereToGo.contains(j) && value>0) //se è neutra
			    	{
                        System.err.println("possiamo andare in "+j);
			    		value--; //perchè un nostro pod si muoverà 
			    		
                        output+="1"; output+=" "; output+=i; output+=" "; output+=j; output+=" "; 
                       
			    		whereToGo.add(j);
			    		
			    	}
			    }
				
			}
			
			/*
			for(Map.Entry<Integer, Integer> entry : gm.myPodZone.entrySet()) {
			    Integer position = entry.getKey();
			    Integer value = entry.getValue();

			   }*/
			System.out.println(output);
			System.out.println("WAIT");
        }


	}



}


class GameManager {
	
	Scanner sc;
	HashMap<Integer,ArrayList<Integer>> reachable=new HashMap<Integer,ArrayList<Integer>>();
	HashMap<Integer,Integer> platinumPresence=new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> ownZone=new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> myPodZone=new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> enemyPodZone=new HashMap<Integer,Integer>();
	ArrayList<Integer> visibleZone=new ArrayList<Integer>();
	ArrayList<Integer> myPos=new ArrayList<Integer>();
	ArrayList<Integer> enemyPos=new ArrayList<Integer>();
	int numPlayer, myId, zoneCount, linkCount, myPlatinum;
	
	public GameManager(Scanner sc) {
		this.sc=sc;
	}
	
	public void initialize() {
		
		numPlayer = sc.nextInt();
        myId = sc.nextInt(); 
        zoneCount = sc.nextInt(); 
        linkCount = sc.nextInt(); 
        
        for (int i = 0; i < zoneCount; i++) {
            int zoneId = sc.nextInt(); 
            reachable.put(zoneId, new ArrayList<Integer>());
            int platinumSource = sc.nextInt(); 
            platinumPresence.put(zoneId, platinumSource);
        }
        for (int i = 0; i < linkCount; i++) {
            int zone1 = sc.nextInt();
            int zone2 = sc.nextInt();
            reachable.get(zone1).add(zone2);
            reachable.get(zone2).add(zone1);
        }
	}
	
	public void update() {
		myPlatinum = sc.nextInt();
		 for (int i = 0; i < zoneCount; i++) {
             int zId = sc.nextInt(); // this zone's ID
             int ownerId = sc.nextInt(); // the player who owns this zone (-1 otherwise)
             int podsP0 = sc.nextInt(); // player 0's PODs on this zone
             int podsP1 = sc.nextInt(); // player 1's PODs on this zone
             int visible = sc.nextInt(); // 1 if one of your units can see this tile, else 0
             int platinum = sc.nextInt(); // the amount of Platinum this zone can provide (0 if hidden by fog)
             
             if(visible==1) visibleZone.add(zId);
             if(podsP0>0 && !myPos.contains(zId)) myPos.add(zId);
             if(podsP1>0) enemyPos.add(zId);
             platinumPresence.put(zId, platinum);
             ownZone.put(zId, ownerId);
             myPodZone.put(zId, podsP0);
             enemyPodZone.put(zId, podsP1);
         }
	}
	
	public ArrayList<Integer> myPodPos(){
		return  myPos;
	}
	
	public ArrayList<Integer> enemyPodPos(){
		return enemyPos;
	}
	
	public ArrayList<Integer> VisibleZone(){
		return visibleZone;
	}
	
	public Integer getPlatinumPresence(int zone) {
		return platinumPresence.get(zone);
	}
	
	public Integer ownZone(int zone) {
		return ownZone.get(zone);
	}
	
	public Integer myNumberPods(int zone) {
		return myPodZone.get(zone);
	}
	
	public Integer enemyNumberPods(int zone) {
		return enemyPodZone.get(zone);
	}
	
	public ArrayList<Integer> adiacent(Integer zone) {
		return reachable.get(zone);
	}
	
	
		
	
}