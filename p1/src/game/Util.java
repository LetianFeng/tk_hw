package game;

import java.util.Random;
import java.util.UUID;
import java.util.regex.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Util {
	
	public static String texture = "/resource/minion-front.jpg";
	public static String defaultRMI = "//localhost/GameServer";
	public static int numOfMinions = 3;
	public static boolean minionIsBeingAccessed = true;
	public static boolean minionIsAvailableForAccess = false;
	
	public static Minion createNewMinion() {
		float coord_x = randomCoord();
		float coord_y = randomCoord();
		return new Minion(coord_x, coord_y);
	}
	
	public static void updateMinionPosition(Minion minion) {
		float coord_x = randomCoord();
		float coord_y = randomCoord();
		minion.setX(coord_x);
		minion.setY(coord_y);
		System.out.println("New coordinates generated at " + coord_x + " " + coord_y);
	}
	
	public static float randomCoord() {
		 Random rand = new Random();
		 return rand.nextFloat();
	}
	
	public static Minion getMinionInRoom(UUID minionID, UUID roomID) {
		return null;
	}
	
	public static Player getPlayerInRoom(String usename, UUID roomID) {
		return null;
	}
	
	public static String resolveDuplicateName(String username, Map<String, Player> players) {
		 if (!players.containsKey(username)) return username;
		
		 Pattern p = Pattern.compile("(^.*_)([0-9]+)");
		 Matcher m = p.matcher(username);
		 if (m.find()) {
			 System.out.println("Resolved from " + username + " to " + username + (m.group(1) + (Integer.parseInt(m.group(2)) + 1)));
			 return resolveDuplicateName(m.group(1) + (Integer.parseInt(m.group(2)) + 1), players);
		 } else {
			 System.out.println("Resolved from " + username + " to " + username + "_1");
			 return resolveDuplicateName(username + "_1", players);
		 }
	}
	
	public static void incrementPlayerScore(String username, UUID roomID, Map<UUID, Room> room_list) {
		Room room = room_list.get(roomID);
		Map<String, Player> players_t = room.getPlayers();
		Player player_t = players_t.get(username);
		player_t.setScore(player_t.getScore() + 1);
	}
	
	public static Map<String, Player> sortPlayerByScoreDesc(Map<String, Player> unsortMap)
    {

        List<Entry<String, Player>> list = new LinkedList<Entry<String, Player>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Player>>()
        {
            public int compare(Entry<String, Player> p1,
                    Entry<String, Player> p2)
            {
            	if (p1.getValue().getScore() == p2.getValue().getScore()) {
            		return 0;
            	} else if (p1.getValue().getScore() > p2.getValue().getScore()) {
            		return -1;
            	} else {
            		return 1;
            	}
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Player> sortedMap = new LinkedHashMap<String, Player>();
        for (Entry<String, Player> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
