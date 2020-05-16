import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Player {

	public static void main(String[] args) {

		final Scanner in = new Scanner(System.in);
		GameManager gm=new GameManager(in);
		gm.initialize();
		
		while(true) {
			gm.update();
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
             if(podsP0>0) myPos.add(zId);
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