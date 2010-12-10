
import java.util.*;

public class Next {
   enum Type {INT, STRING, CHARACTER, ITEM, LOCATION}

   static Random r = new Random();

   Map<String, Location> locations = new HashMap<String, Location>();
   Map<String, Character> characters = new HashMap<String, Character>();
   Map<String, Item> items = new HashMap<String, Item>();
   Map<String, Type> types = new HashMap<String, Type>();
   
   public static void main(String[] args) {
    (new Next()).play();
   }
   
   public void entitySetString(String key1, Type type1, String key2, String value) {
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
   }
   
   public void entitySetInt(String key1, Type type1, String key2, int value) {
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

    public boolean entityExistsItem(String key1, Type type1, String key2) {
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
    		return false;
    	}

    	return true;
    }

    public boolean entityExistsCharacter(String key1, Type type1, String key2) {
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
    		return false;
    	}

    	return true;
    }
    
   public void endGame() {
      System.out.println("GAME OVER!!!!!");
      System.exit(0);
   }
   
   public void play() {
items.put("the_greatest_sword_ever",the_greatest_sword_ever);
types.put("the_greatest_sword_ever", Type.ITEM);
the_greatest_sword_ever.addIntAttr("damage", 100000000);
types.put("damage", Type.INT);
items.put("rubberDuckie",rubberDuckie);
types.put("rubberDuckie", Type.ITEM);
rubberDuckie.addStrAttr("squeak","squeak");
types.put("squeak", Type.STRING);
items.put("columbiaBinder",columbiaBinder);
types.put("columbiaBinder", Type.ITEM);
columbiaBinder.addIntAttr("size", 50);
types.put("size", Type.INT);
characters.put("xiaowei_the_greatest_man_ever",xiaowei_the_greatest_man_ever);
types.put("xiaowei_the_greatest_man_ever", Type.CHARACTER);
xiaowei_the_greatest_man_ever.addIntAttr("life", 100000000);
types.put("life", Type.INT);
xiaowei_the_greatest_man_ever.addIntAttr("level", 99999);
types.put("level", Type.INT);
xiaowei_the_greatest_man_ever.addStrAttr("haha","hahahahaha");
types.put("haha", Type.STRING);
xiaowei_the_greatest_man_ever.addItem("the_greatest_sword_ever");
xiaowei_the_greatest_man_ever.addItem("rubberDuckie");
characters.put("ernesto_the_averagest_dude_forever",ernesto_the_averagest_dude_forever);
types.put("ernesto_the_averagest_dude_forever", Type.CHARACTER);
ernesto_the_averagest_dude_forever.addIntAttr("life", 100000000);
types.put("life", Type.INT);
ernesto_the_averagest_dude_forever.addIntAttr("level", 99999 + 1);
types.put("level", Type.INT);
ernesto_the_averagest_dude_forever.addStrAttr("hehe","hey beavis");
types.put("hehe", Type.STRING);
ernesto_the_averagest_dude_forever.addItem("the_greatest_sword_ever");
ernesto_the_averagest_dude_forever.addItem("rubberDuckie");
locations.put("where_is_this_place",where_is_this_place);
types.put("where_is_this_place", Type.LOCATION);
where_is_this_place.addIntAttr("sizex", 10000);
types.put("sizex", Type.INT);
where_is_this_place.addIntAttr("sizey", 9283);
types.put("sizey", Type.INT);
where_is_this_place.addItem("columbiaBinder");
where_is_this_place.showCharacter("xiaowei_the_greatest_man_ever");
where_is_this_place.showCharacter("ernesto_the_averagest_dude_forever");
//Location function call
where_is_this_place();
   endGame();
   } 
//intdec
int count;
//itemdec
Item the_greatest_sword_ever = new Item();
//itemdec
Item rubberDuckie = new Item();
//itemdec
Item columbiaBinder = new Item();
//charadec
Character xiaowei_the_greatest_man_ever = new Character();
//charadec
Character ernesto_the_averagest_dude_forever = new Character();
//locdec
Location where_is_this_place = new Location();
//start funtion
public void where_is_this_place() {
while (!(entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)){
{
Map<String,String> keysToActionName = new HashMap<String, String>();
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
Map<String, String> actionNameToOutput = new HashMap<String, String>();
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
System.out.println("CHOOSE AN ACTION:");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
keysToActionName.put("a", "attack");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
actionNameToOutput.put("attack", "hia!");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
System.out.println("Type a for hia!");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
keysToActionName.put("u", "up");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
actionNameToOutput.put("up", "up");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
System.out.println("Type u for up");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
Scanner in = new Scanner(System.in);
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
String input = in.nextLine();
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
while(!keysToActionName.containsKey(input)) {
System.out.println("Invalid input, try again");
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
input = in.nextLine();
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
}
System.out.println("You typed " + input);
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
String action = keysToActionName.get(input);
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
if(action.equals("attack")) {
{
entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") + 1;
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
}
//where_is_this_place();
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
}
if(action.equals("up")) {
{
int num = r.nextInt(100);
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
if(num >= 0 && num < 40) {
count = count - 1;
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
}
if(num >= 40 && num < 100) {
count = count + 1;
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
endGame();
}
}
//where_is_this_place();
if (entityHasInt("xiaowei_the_greatest_man_ever", Type.CHARACTER, "life") < 0)
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

   public void addItem(String name) {
      items.add(name);
   }

   public void removeItem(String name) {
      items.remove(name);
   }

   public void showCharacter(String name) {
      characters.add(name);
   }

   public void hideCharacter(String name) {
      characters.remove(name);
   }
}

class Character extends Entity {
   Set<String> items = new HashSet<String>();

   public void addItem(String name) {
      items.add(name);
   }

   public void removeItem(String name) {
      items.remove(name);
   }
}

class Item extends Entity {
}
