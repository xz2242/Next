
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
characters.put("_person",_person);
types.put("_person", Type.CHARACTER);
locations.put("_here",_here);
types.put("_here", Type.LOCATION);
items.put("_object",_object);
types.put("_object", Type.ITEM);
//Location function call
_here();
   endGame();
   } 
//intdec
int _num;
//charadec
Character _person = new Character();
//locdec
Location _here = new Location();
//itemdec
Item _object = new Item();
//start funtion
public void _here() {
currentLocation = "_here";
while (!(_num == (1))){
{
int num = r.nextInt(100);
if (_num == (1))
endGame();
if(num >= 0 && num < 50) {
System.out.println(""+ ("This is line 1 of a possible 2"));
if (_num == (1))
endGame();
}
if(num >= 50 && num < 100) {
System.out.println(""+ ("This is line 2 of a possible 2"));
if (_num == (1))
endGame();
}
if(isTrue(entityExistsItem("_person", Type.CHARACTER, "_object"))) {
System.out.println(""+ ("The person has the object"));
if (_num == (1))
endGame();
}
else {
System.out.println(""+ ("The person does not have the object"));
if (_num == (1))
endGame();
}
if(isTrue(entityExistsItem("_here", Type.LOCATION, "_object"))) {
System.out.println(""+ ("The object is in the location"));
if (_num == (1))
endGame();
}
else {
System.out.println(""+ ("The object is not in the location"));
if (_num == (1))
endGame();
}
Map<String,String> keysToActionName19 = new HashMap<String, String>();
if (_num == (1))
endGame();
Map<String, String> actionNameToOutput19 = new HashMap<String, String>();
if (_num == (1))
endGame();
System.out.println("CHOOSE AN ACTION:");
if (_num == (1))
endGame();
keysToActionName19.put("g", "_grabItem");
if (_num == (1))
endGame();
actionNameToOutput19.put("_grabItem", "character grab item");
if (_num == (1))
endGame();
System.out.println("Type g for character grab item");
if (_num == (1))
endGame();
keysToActionName19.put("d", "_dropItem");
if (_num == (1))
endGame();
actionNameToOutput19.put("_dropItem", "character drop item");
if (_num == (1))
endGame();
System.out.println("Type d for character drop item");
if (_num == (1))
endGame();
keysToActionName19.put("s", "_showItem");
if (_num == (1))
endGame();
actionNameToOutput19.put("_showItem", "show item in location");
if (_num == (1))
endGame();
System.out.println("Type s for show item in location");
if (_num == (1))
endGame();
keysToActionName19.put("h", "_hideItem");
if (_num == (1))
endGame();
actionNameToOutput19.put("_hideItem", "hide item from location");
if (_num == (1))
endGame();
System.out.println("Type h for hide item from location");
if (_num == (1))
endGame();
keysToActionName19.put("e", "_exit");
if (_num == (1))
endGame();
actionNameToOutput19.put("_exit", "exit");
if (_num == (1))
endGame();
System.out.println("Type e for exit");
if (_num == (1))
endGame();
Scanner in19 = new Scanner(System.in);
if (_num == (1))
endGame();
String input19 = in19.nextLine();
if (_num == (1))
endGame();
while(!keysToActionName19.containsKey(input19)) {
System.out.println("Invalid input, try again");
if (_num == (1))
endGame();
input19 = in19.nextLine();
if (_num == (1))
endGame();
}
System.out.println("You typed " + input19);
if (_num == (1))
endGame();
String action19 = keysToActionName19.get(input19);
if (_num == (1))
endGame();
if(action19.equals("_grabItem")) {
_person.addItem ("_object", currentLocation, locations);
if (_num == (1))
endGame();
_here();
if (_num == (1))
endGame();
}
if(action19.equals("_dropItem")) {
_person.removeItem ("_object", currentLocation, locations);
if (_num == (1))
endGame();
_here();
if (_num == (1))
endGame();
}
if(action19.equals("_showItem")) {
_here.addItem ("_object",items);
if (_num == (1))
endGame();
_here();
if (_num == (1))
endGame();
}
if(action19.equals("_hideItem")) {
_here.removeItem ("_object");
if (_num == (1))
endGame();
_here();
if (_num == (1))
endGame();
}
if(action19.equals("_exit")) {
dummy = (_num = (1));
if (_num == (1))
endGame();
_here();
if (_num == (1))
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
