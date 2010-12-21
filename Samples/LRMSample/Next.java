
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
items.put("_the_greatest_sword_ever",_the_greatest_sword_ever);
types.put("_the_greatest_sword_ever", Type.ITEM);
_the_greatest_sword_ever.addIntAttr("_damage", (100000000));
types.put("_damage", Type.INT);
characters.put("_xiaowei_the_greatest_man_ever",_xiaowei_the_greatest_man_ever);
types.put("_xiaowei_the_greatest_man_ever", Type.CHARACTER);
_xiaowei_the_greatest_man_ever.addIntAttr("_life", (100000000));
types.put("_life", Type.INT);
_xiaowei_the_greatest_man_ever.addIntAttr("_level", (99999));
types.put("_level", Type.INT);
_xiaowei_the_greatest_man_ever.addStrAttr("_haha","hahahahaha");
types.put("_haha", Type.STRING);
_xiaowei_the_greatest_man_ever.addItem("_the_greatest_sword_ever");
locations.put("_where_is_this_place",_where_is_this_place);
types.put("_where_is_this_place", Type.LOCATION);
_where_is_this_place.addIntAttr("_sizex", (10000));
types.put("_sizex", Type.INT);
_where_is_this_place.addIntAttr("_sizey", (9283));
types.put("_sizey", Type.INT);
_where_is_this_place.showCharacter("_xiaowei_the_greatest_man_ever");
//Location function call
_where_is_this_place();
   endGame();
   } 
//intdec
int _count;
//itemdec
Item _the_greatest_sword_ever = new Item();
//charadec
Character _xiaowei_the_greatest_man_ever = new Character();
//locdec
Location _where_is_this_place = new Location();
//start funtion
public void _where_is_this_place() {
currentLocation = "_where_is_this_place";
while (!((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))){
{
Map<String,String> keysToActionName0 = new HashMap<String, String>();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
Map<String, String> actionNameToOutput0 = new HashMap<String, String>();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
keysToActionName0.put("a", "_attack");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
actionNameToOutput0.put("_attack", "hia!");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println("Type a for hia!");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
keysToActionName0.put("u", "_up");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
actionNameToOutput0.put("_up", "up");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println("Type u for up");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
keysToActionName0.put("f", "_fin");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
actionNameToOutput0.put("_fin", "end");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println("Type f for end");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
Scanner in0 = new Scanner(System.in);
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
String input0 = in0.nextLine();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
while(!keysToActionName0.containsKey(input0)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
input0 = in0.nextLine();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
System.out.println("You typed " + input0);
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
String action0 = keysToActionName0.get(input0);
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
if(action0.equals("_attack")) {
{
dummy = ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") + (1)));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
_where_is_this_place();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
if(action0.equals("_up")) {
{
int num = r.nextInt(100);
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
if(num >= 0 && num < 40) {
{
dummy = (_count = (_count - (1)));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println(""+ ("count:"));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println(""+ (_count));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
}
if(num >= 40 && num < 100) {
{
dummy = (_count = (_count + (1)));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println(""+ ("count:"));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
System.out.println(""+ (_count));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
}
}
_where_is_this_place();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
if(action0.equals("_fin")) {
{
dummy = (entitySetInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life", (-(1))));
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
endGame();
}
_where_is_this_place();
if ((entityHasInt("_xiaowei_the_greatest_man_ever", Type.CHARACTER, "_life") < (0)))
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
