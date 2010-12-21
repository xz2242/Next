
import java.util.*;

public class Next {
   enum Type {INT, STRING, CHARACTER, ITEM, LOCATION}

   static Random r = new Random();
   Object dummy;
   String currentLocation;
   Map<String, Location> locations = new HashMap<String, Location>();
   Map<String, Character> characters = new HashMap<String, Character>();
   Map<String, Item> items = new HashMap<String, Item>();
   Map<String, Type> types = new HashMap<String, Type>();
   
   public static void main(String[] args) {
    (new Next()).play();
   }
   
   public int boolToInt(boolean value) {
        if(value) {
            return 1;
        }
        else {
            return 0;
        }
   }
   
   public String entitySetString(String key1, Type type1, String key2, String value) {
    boolean valueSet = false;
   	if(type1 == Type.LOCATION) {
   		Location loc = locations.get(key1);
   		if(loc != null) {
   			loc.strAttrs.put(key2, value);
   			valueSet = true;
   		}
   	}
   	else if(type1 == Type.CHARACTER) {
   		Character character = characters.get(key1);
   		if(character != null) {
   			character.strAttrs.put(key2, value);
   			valueSet = true;
   		}
   	}
   	else if(type1 == Type.ITEM) {
   		Item item = items.get(key1);
   		if(item != null) {
   			item.strAttrs.put(key2, value);
   			valueSet = true;
   		}
   	}
	
   	if(!valueSet) {
   		throw new RuntimeException();
   	}
   	
   	return value;
   }
   
   public int entitySetInt(String key1, Type type1, String key2, int value) {
   	boolean foundReturnValue = false;
	
   	if(type1 == Type.LOCATION) {
   		Location loc = locations.get(key1);
   		if(loc != null) {
            loc.intAttrs.put(key2, value);
   			foundReturnValue = true;
   		}
   	}
   	else if(type1 == Type.CHARACTER) {
   		Character character = characters.get(key1);
   		if(character != null) {
   			character.intAttrs.put(key2, value);
   			foundReturnValue = true;
   		}
   	}
   	else if(type1 == Type.ITEM) {
   		Item item = items.get(key1);
   		if(item != null) {
   			item.intAttrs.put(key2, value);
   			foundReturnValue = true;
   		}
   	}
	
   	if(foundReturnValue == false) {
   		throw new RuntimeException();
   	}
   	
   	return value;
   }
   
	public boolean isTrue(Object object) {
	    if(object instanceof String) {
	       if(((String)object).isEmpty()) {
	           return false;
	       }
	    }
	    else if(object instanceof Integer) {
	       if((Integer)object == 0) {
	           return false;
	       } 
	    }
	    else {
	        if(object == null) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	
    public void killFunction(String varName){
    	if (characters.containsKey(varName)){
    		characters.remove(varName);
    		for (String key: locations.keySet()){
    				locations.get(key).hideCharacter(varName);
    		}
    	}
    	else if (items.containsKey(varName)){
    		items.remove(varName);
    		for (String key:locations.keySet()){
    			locations.get(key).removeItem(varName);
    			}
    		for (String key : characters.keySet()){
    			characters.get(key).removeItem(varName);
    		}
    	}
    }

    public int entityHasInt(String key1, Type type1, String key2) {
    	int returnValue = 0;
    	boolean foundReturnValue = false;
	
    	if(type1 == Type.LOCATION) {
    		Location loc = locations.get(key1);
    		if(loc != null) {
    			returnValue = loc.intAttrs.get(key2);
    			foundReturnValue = true;
    		}
    	}
    	else if(type1 == Type.CHARACTER) {
    		Character character = characters.get(key1);
    		if(character != null) {
    			returnValue = character.intAttrs.get(key2);
    			foundReturnValue = true;
    		}
    	}
    	else if(type1 == Type.ITEM) {
    		Item item = items.get(key1);
    		if(item != null) {
    			returnValue = item.intAttrs.get(key2);
    			foundReturnValue = true;
    		}
    	}
	
    	if(foundReturnValue == false) {
    		throw new RuntimeException();
    	}
	
    	return returnValue;
    }

    public String entityHasString(String key1, Type type1, String key2) {
    	String returnValue = null;
    	if(type1 == Type.LOCATION) {
    		Location loc = locations.get(key1);
    		if(loc != null) {
    			returnValue = loc.strAttrs.get(key2);
    		}
    	}
    	else if(type1 == Type.CHARACTER) {
    		Character character = characters.get(key1);
    		if(character != null) {
    			returnValue = character.strAttrs.get(key2);
    		}
    	}
    	else if(type1 == Type.ITEM) {
    		Item item = items.get(key1);
    		if(item != null) {
    			returnValue = item.strAttrs.get(key2);
    		}
    	}
	
    	if(returnValue == null) {
    		throw new RuntimeException();
    	}
	
    	return returnValue;
    }

    public Item entityHasItem(String key1, Type type1, String key2) {
    	Item returnValue = null;
    	if(type1 == Type.LOCATION) {
    		Location loc = locations.get(key1);
    		if(loc != null) {
    			if(loc.items.contains(key2)) {
    				returnValue = items.get(key2);
    			}
    		}
    	}
    	else if(type1 == Type.CHARACTER) {
    		Character character = characters.get(key1);
    		if(character != null) {
    			if(character.items.contains(key2)) {
    				returnValue = items.get(key2);
    			}
    		}
    	}
	
    	if(returnValue == null) {
    		throw new RuntimeException();
    	}
	
    	return returnValue;
    }

    public Character entityHasCharacter(String key1, Type type1, String key2) {
    	Character returnValue = null;
    	if(type1 == Type.LOCATION) {
    		Location loc = locations.get(key1);
    		if(loc != null) {
    			if(loc.characters.contains(key2)) {
    				returnValue = characters.get(key2);
    			}
    		}
    	}

    	if(returnValue == null) {
    		throw new RuntimeException();
    	}

    	return returnValue;
    }

    public int entityExistsItem(String key1, Type type1, String key2) {
    	Object returnValue = null;
    	if(type1 == Type.LOCATION) {
    		Location loc = locations.get(key1);
    		if(loc != null) {
    			if(loc.items.contains(key2)) {
    				returnValue = items.get(key2);
    			}
    		}
    	}
    	else if(type1 == Type.CHARACTER) {
    		Character character = characters.get(key1);
    		if(character != null) {
    			if(character.items.contains(key2)) {
    				returnValue = items.get(key2);
    			}
    		}
    	}

    	if(returnValue == null) {
    		return 0;
    	}

    	return 1;
    }

    public int entityExistsCharacter(String key1, Type type1, String key2) {
    	Object returnValue = null;
    	if(type1 == Type.LOCATION) {
    		Location loc = locations.get(key1);
    		if(loc != null) {
    			if(loc.characters.contains(key2)) {
    				returnValue = characters.get(key2);
    			}
    		}
    	}

    	if(returnValue == null) {
    		return 0;
    	}

    	return 1;
    }
    
   public void endGame() {
      System.exit(0);
   }
   
   public void play() {
locations.put("_game",_game);
types.put("_game", Type.LOCATION);
_game.addIntAttr("_outcome", 0);
types.put("_outcome", Type.INT);
//Location function call
_game();
   endGame();
   } 
//intdec
int _won;
//intdec
int _lost;
//locdec
Location _game = new Location();
//start funtion
public void _game() {
currentLocation = "_game";
while (!(entityHasInt("_game", Type.LOCATION, "_outcome") == (4))){
{
System.out.println(""+ ("Games won:"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ (_won));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ (""));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("Games lost:"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ (_lost));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ (""));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("rock paper scissors says shoot!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
int num = r.nextInt(100);
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
if(num >= 0 && num < 33) {
dummy = (entitySetInt("_game", Type.LOCATION, "_outcome", (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
if(num >= 33 && num < 66) {
dummy = (entitySetInt("_game", Type.LOCATION, "_outcome", (2)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
if(num >= 66 && num < 100) {
dummy = (entitySetInt("_game", Type.LOCATION, "_outcome", (3)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
Map<String,String> keysToActionName17 = new HashMap<String, String>();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
Map<String, String> actionNameToOutput17 = new HashMap<String, String>();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
keysToActionName17.put("s", "_scissors");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
actionNameToOutput17.put("_scissors", "scissors");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println("Type s for scissors");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
keysToActionName17.put("p", "_paper");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
actionNameToOutput17.put("_paper", "paper");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println("Type p for paper");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
keysToActionName17.put("r", "_rock");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
actionNameToOutput17.put("_rock", "rock");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println("Type r for rock");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
keysToActionName17.put("e", "_exit");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
actionNameToOutput17.put("_exit", "exit");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println("Type e for exit");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
Scanner in17 = new Scanner(System.in);
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
String input17 = in17.nextLine();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
while(!keysToActionName17.containsKey(input17)) {
System.out.println("Invalid input, try again");
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
input17 = in17.nextLine();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
System.out.println("You typed " + input17);
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
String action17 = keysToActionName17.get(input17);
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
if(action17.equals("_scissors")) {
{
if(entityHasInt("_game", Type.LOCATION, "_outcome") == (1)) {
{
System.out.println(""+ ("opponent picked scissors!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You tied"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
else {
if(entityHasInt("_game", Type.LOCATION, "_outcome") == (2)) {
{
System.out.println(""+ ("opponent picked paper!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You won!!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
dummy = (_won = (_won + (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("opponent picked rock!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You lost"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
dummy = (_lost = (_lost + (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
}
}
_game();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
if(action17.equals("_paper")) {
{
if(entityHasInt("_game", Type.LOCATION, "_outcome") == (2)) {
{
System.out.println(""+ ("opponent picked paper!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You tied"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
else {
if(entityHasInt("_game", Type.LOCATION, "_outcome") == (3)) {
{
System.out.println(""+ ("opponent picked rock!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You won!!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
dummy = (_won = (_won + (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("opponent picked scissors!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You lost"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
dummy = (_lost = (_lost + (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
}
}
_game();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
if(action17.equals("_rock")) {
{
if(entityHasInt("_game", Type.LOCATION, "_outcome") == (3)) {
{
System.out.println(""+ ("opponent picked rock!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You tied"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
else {
if(entityHasInt("_game", Type.LOCATION, "_outcome") == (1)) {
{
System.out.println(""+ ("opponent picked scissors!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You won!!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
dummy = (_won = (_won + (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("opponent picked paper!"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
System.out.println(""+ ("You lost"));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
dummy = (_lost = (_lost + (1)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
}
}
_game();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
if(action17.equals("_exit")) {
dummy = (entitySetInt("_game", Type.LOCATION, "_outcome", (4)));
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
_game();
if (entityHasInt("_game", Type.LOCATION, "_outcome") == (4))
endGame();
}
}
}
endGame();
}
} 

abstract class Entity {

   Map<String, Integer> intAttrs = new HashMap<String, Integer>();
   Map<String, String> strAttrs = new HashMap<String, String>(); 

   public void addIntAttr(String name, int value) {
      intAttrs.put(name, value);
   }

   public void addStrAttr(String name, String value) {
      strAttrs.put(name, value);
   }
}

class Location extends Entity {
   Set<String> characters = new HashSet<String>();
   Set<String> items = new HashSet<String>();

   public void addItem(String name, Map<String, Item> itemses) {
	if(itemses.containsKey(name))
      		items.add(name);
	else
		System.out.println("Error: The item you attempted to add no longer exists");
   }

   public void addItem(String name){
	items.add(name);
	}

   public void removeItem(String name) {
      items.remove(name);
   }

   public void showCharacter(String name, Map<String, Character> characterses) {
      if(characterses.containsKey(name))
	characters.add(name);
	else 
		System.out.println("Error: The character you attempted to use no longer exists");
   }
   public void showCharacter(String name){
	
	characters.add(name);
	}

   public void hideCharacter(String name) {
      characters.remove(name);
   }
}

class Character extends Entity {
   Set<String> items = new HashSet<String>();

   public void addItem(String name, String locationNow, Map<String, Location> locations){

	if(locations.get(locationNow).items.contains(name)){
		locations.get(locationNow).removeItem(name);
		items.add(name);		
	}
	else
		System.out.println("Error: The item you attempted to grab is not in this location");
}

public void removeItem(String name, String locationNow, Map<String, Location> locations) {
      	if (items.contains(name)){
		items.remove(name);
		locations.get(locationNow).addItem(name);
   }
	else
		System.out.println("Error: The character does not have the item you attempted to drop");
}

   public void addItem(String name) {
      items.add(name);
   }

   public void removeItem(String name) {
      items.remove(name);
   }
}

class Item extends Entity {
}
