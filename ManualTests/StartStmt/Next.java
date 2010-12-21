
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
items.put("_i1",_i1);
types.put("_i1", Type.ITEM);
items.put("_i2",_i2);
types.put("_i2", Type.ITEM);
_i2.addStrAttr("_squeak","squeak");
types.put("_squeak", Type.STRING);
items.put("_i3",_i3);
types.put("_i3", Type.ITEM);
_i3.addIntAttr("_size", (50));
types.put("_size", Type.INT);
characters.put("_c1",_c1);
types.put("_c1", Type.CHARACTER);
_c1.addIntAttr("_life", (10));
types.put("_life", Type.INT);
characters.put("_c2",_c2);
types.put("_c2", Type.CHARACTER);
_c2.addIntAttr("_life", (10));
types.put("_life", Type.INT);
_count = (10);
locations.put("_l1",_l1);
types.put("_l1", Type.LOCATION);
_l1.addIntAttr("_sizex", (10000));
types.put("_sizex", Type.INT);
_l1.addIntAttr("_sizey", (9283));
types.put("_sizey", Type.INT);
locations.put("_l2",_l2);
types.put("_l2", Type.LOCATION);
locations.put("_l3",_l3);
types.put("_l3", Type.LOCATION);
//Location function call
_l1();
//Location function call
_l2();
//Location function call
_l3();
   endGame();
   } 
//itemdec
Item _i1 = new Item();
//itemdec
Item _i2 = new Item();
//itemdec
Item _i3 = new Item();
//charadec
Character _c1 = new Character();
//charadec
Character _c2 = new Character();
//intdecinit
int _count;
//locdec
Location _l1 = new Location();
//locdec
Location _l2 = new Location();
//locdec
Location _l3 = new Location();
//start funtion
public void _l1() {
currentLocation = "_l1";
while (!(_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))){
{
System.out.println(""+ ("in l1"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
Map<String,String> keysToActionName1 = new HashMap<String, String>();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
Map<String, String> actionNameToOutput1 = new HashMap<String, String>();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("CHOOSE AN ACTION:");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
keysToActionName1.put("1", "_a");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
actionNameToOutput1.put("_a", "1");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("Type 1 for 1");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
keysToActionName1.put("2", "_b");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
actionNameToOutput1.put("_b", "2");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("Type 2 for 2");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
keysToActionName1.put("3", "_c");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
actionNameToOutput1.put("_c", "3");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("Type 3 for 3");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
Scanner in1 = new Scanner(System.in);
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
String input1 = in1.nextLine();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
while(!keysToActionName1.containsKey(input1)) {
System.out.println("Invalid input, try again");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
input1 = in1.nextLine();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
System.out.println("You typed " + input1);
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
String action1 = keysToActionName1.get(input1);
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
if(action1.equals("_a")) {
{
System.out.println(""+ ("go to l1"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
_l1();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
if(action1.equals("_b")) {
{
System.out.println(""+ ("go to l2"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
_l2();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
if(action1.equals("_c")) {
{
System.out.println(""+ ("go to l3"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
_l3();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
}
}
endGame();
}
//start funtion
public void _l2() {
_count = (0)
entitySetInt("_c1", Type.CHARACTER, "_life", (0))
currentLocation = "_l2";
while (!(((0)) != 0  || ((0)) != 0 )){
{
System.out.println(""+ ("in l2"));
if (((0)) != 0  || ((0)) != 0 )
endGame();
System.out.println(""+ (_count));
if (((0)) != 0  || ((0)) != 0 )
endGame();
Map<String,String> keysToActionName2 = new HashMap<String, String>();
if (((0)) != 0  || ((0)) != 0 )
endGame();
Map<String, String> actionNameToOutput2 = new HashMap<String, String>();
if (((0)) != 0  || ((0)) != 0 )
endGame();
System.out.println("CHOOSE AN ACTION:");
if (((0)) != 0  || ((0)) != 0 )
endGame();
keysToActionName2.put("1", "_a");
if (((0)) != 0  || ((0)) != 0 )
endGame();
actionNameToOutput2.put("_a", "1");
if (((0)) != 0  || ((0)) != 0 )
endGame();
System.out.println("Type 1 for 1");
if (((0)) != 0  || ((0)) != 0 )
endGame();
keysToActionName2.put("2", "_b");
if (((0)) != 0  || ((0)) != 0 )
endGame();
actionNameToOutput2.put("_b", "2");
if (((0)) != 0  || ((0)) != 0 )
endGame();
System.out.println("Type 2 for 2");
if (((0)) != 0  || ((0)) != 0 )
endGame();
keysToActionName2.put("3", "_c");
if (((0)) != 0  || ((0)) != 0 )
endGame();
actionNameToOutput2.put("_c", "3");
if (((0)) != 0  || ((0)) != 0 )
endGame();
System.out.println("Type 3 for 3");
if (((0)) != 0  || ((0)) != 0 )
endGame();
Scanner in2 = new Scanner(System.in);
if (((0)) != 0  || ((0)) != 0 )
endGame();
String input2 = in2.nextLine();
if (((0)) != 0  || ((0)) != 0 )
endGame();
while(!keysToActionName2.containsKey(input2)) {
System.out.println("Invalid input, try again");
if (((0)) != 0  || ((0)) != 0 )
endGame();
input2 = in2.nextLine();
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
System.out.println("You typed " + input2);
if (((0)) != 0  || ((0)) != 0 )
endGame();
String action2 = keysToActionName2.get(input2);
if (((0)) != 0  || ((0)) != 0 )
endGame();
if(action2.equals("_a")) {
{
System.out.println(""+ ("go to l1"));
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
_l1();
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
if(action2.equals("_b")) {
{
System.out.println(""+ ("go to l2"));
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
_l2();
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
if(action2.equals("_c")) {
{
System.out.println(""+ ("go to l3"));
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
_l3();
if (((0)) != 0  || ((0)) != 0 )
endGame();
}
}
_count = (0)
entitySetInt("_c1", Type.CHARACTER, "_life", (0))
}
endGame();
}
//start funtion
public void _l3() {
currentLocation = "_l3";
while (!(_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))){
{
System.out.println(""+ ("in l3"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
Map<String,String> keysToActionName1 = new HashMap<String, String>();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
Map<String, String> actionNameToOutput1 = new HashMap<String, String>();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("CHOOSE AN ACTION:");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
keysToActionName1.put("1", "_a");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
actionNameToOutput1.put("_a", "1");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("Type 1 for 1");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
keysToActionName1.put("2", "_b");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
actionNameToOutput1.put("_b", "2");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("Type 2 for 2");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
keysToActionName1.put("3", "_c");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
actionNameToOutput1.put("_c", "3");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
System.out.println("Type 3 for 3");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
Scanner in1 = new Scanner(System.in);
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
String input1 = in1.nextLine();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
while(!keysToActionName1.containsKey(input1)) {
System.out.println("Invalid input, try again");
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
input1 = in1.nextLine();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
System.out.println("You typed " + input1);
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
String action1 = keysToActionName1.get(input1);
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
if(action1.equals("_a")) {
{
System.out.println(""+ ("go to l1"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
_l1();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
if(action1.equals("_b")) {
{
System.out.println(""+ ("go to l2"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
_l2();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
if(action1.equals("_c")) {
{
System.out.println(""+ ("go to l3"));
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
endGame();
}
_l3();
if (_count == (0) || entityHasInt("_c1", Type.CHARACTER, "_life") == (0))
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
