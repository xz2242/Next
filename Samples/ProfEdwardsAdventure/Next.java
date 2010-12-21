
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
items.put("_laser",_laser);
types.put("_laser", Type.ITEM);
_laser.addStrAttr("_color","Green");
types.put("_color", Type.STRING);
_laser.addIntAttr("_damage", (10));
types.put("_damage", Type.INT);
items.put("_PDP_8",_PDP_8);
types.put("_PDP_8", Type.ITEM);
_PDP_8.addIntAttr("_memory", (10));
types.put("_memory", Type.INT);
items.put("_not_windows_computer",_not_windows_computer);
types.put("_not_windows_computer", Type.ITEM);
_not_windows_computer.addStrAttr("_OS","Not-Windows");
types.put("_OS", Type.STRING);
items.put("_fish_sandwich",_fish_sandwich);
types.put("_fish_sandwich", Type.ITEM);
_fish_sandwich.addIntAttr("_portion_left", (100));
types.put("_portion_left", Type.INT);
_fish_sandwich.addStrAttr("_taste","yummy");
types.put("_taste", Type.STRING);
characters.put("_prof_edwards",_prof_edwards);
types.put("_prof_edwards", Type.CHARACTER);
_prof_edwards.addIntAttr("_life", (10000));
types.put("_life", Type.INT);
_prof_edwards.addIntAttr("_awesomeness", (300));
types.put("_awesomeness", Type.INT);
_prof_edwards.addStrAttr("_slogan","Add another layer of indirection!");
types.put("_slogan", Type.STRING);
items.put("_dragon_book",_dragon_book);
types.put("_dragon_book", Type.ITEM);
_dragon_book.addIntAttr("_damage", (5));
types.put("_damage", Type.INT);
characters.put("_prof_aho",_prof_aho);
types.put("_prof_aho", Type.CHARACTER);
_prof_aho.addIntAttr("_life", (20));
types.put("_life", Type.INT);
_prof_aho.addIntAttr("_awesomeness", (20));
types.put("_awesomeness", Type.INT);
_prof_aho.addStrAttr("_slogan","I believe thats on page 42 of the Dragon Book!");
types.put("_slogan", Type.STRING);
_prof_aho.addItem("_dragon_book");
characters.put("_godzilla",_godzilla);
types.put("_godzilla", Type.CHARACTER);
_godzilla.addIntAttr("_life", (100));
types.put("_life", Type.INT);
characters.put("_ronald",_ronald);
types.put("_ronald", Type.CHARACTER);
characters.put("_plt_mob",_plt_mob);
types.put("_plt_mob", Type.CHARACTER);
_plt_mob.addIntAttr("_life", (3));
types.put("_life", Type.INT);
_plt_mob.addIntAttr("_damage", (6));
types.put("_damage", Type.INT);
characters.put("_bill_gates",_bill_gates);
types.put("_bill_gates", Type.CHARACTER);
_bill_gates.addIntAttr("_life", (2));
types.put("_life", Type.INT);
_bill_gates.addStrAttr("_money","$$$54 billion$$$");
types.put("_money", Type.STRING);
_bill_gates.addStrAttr("_slogan","It just works");
types.put("_slogan", Type.STRING);
locations.put("_dragon_cave",_dragon_cave);
types.put("_dragon_cave", Type.LOCATION);
_dragon_cave.addIntAttr("_depth", (500));
types.put("_depth", Type.INT);
_dragon_cave.showCharacter("_prof_edwards");
_dragon_cave.showCharacter("_prof_aho");
locations.put("_ucb",_ucb);
types.put("_ucb", Type.LOCATION);
_ucb.addItem("_not_windows_computer");
_ucb.showCharacter("_bill_gates");
_ucb.showCharacter("_plt_mob");
locations.put("_mcdonalds",_mcdonalds);
types.put("_mcdonalds", Type.LOCATION);
_mcdonalds.addItem("_fish_sandwich");
_mcdonalds.showCharacter("_ronald");
locations.put("_taiwan",_taiwan);
types.put("_taiwan", Type.LOCATION);
_taiwan.addItem("_PDP_8");
_itemNum = (0);
_intro = (0);
locations.put("_columbia",_columbia);
types.put("_columbia", Type.LOCATION);
_columbia.showCharacter("_prof_edwards");
//Location function call
_columbia();
_moment = (0);
//Location function call
_dragon_cave();
//Location function call
_ucb();
//Location function call
_mcdonalds();
_narrated = (0);
//Location function call
_taiwan();
   endGame();
   } 
//itemdec
Item _laser = new Item();
//itemdec
Item _PDP_8 = new Item();
//itemdec
Item _not_windows_computer = new Item();
//itemdec
Item _fish_sandwich = new Item();
//charadec
Character _prof_edwards = new Character();
//itemdec
Item _dragon_book = new Item();
//charadec
Character _prof_aho = new Character();
//charadec
Character _godzilla = new Character();
//charadec
Character _ronald = new Character();
//charadec
Character _plt_mob = new Character();
//charadec
Character _bill_gates = new Character();
//locdec
Location _dragon_cave = new Location();
//locdec
Location _ucb = new Location();
//locdec
Location _mcdonalds = new Location();
//locdec
Location _taiwan = new Location();
//intdecinit
int _itemNum;
//intdec
int _enemy;
//intdecinit
int _intro;
//locdec
Location _columbia = new Location();
//start funtion
public void _columbia() {
currentLocation = "_columbia";
while (!((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))){
{
if(_intro == (0)) {
{
System.out.println(""+ ("Professor Edwards has woken up this morning from a dream!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("In the dream a GCD function tells him to go forth and create the greatest compiler ever created!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("It is a sign and he knows he must do it. He will go forth and create the most premiums compiler eeeeeeever"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("There is a problem, however..."));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("His items of compiler power have been stolen. He must go forth and find them"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
dummy = (_intro = (1));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
dummy = (_enemy = (0));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You are in columbia"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Where to next?"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName16 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput16 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
keysToActionName16.put("d", "_dcave");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
actionNameToOutput16.put("_dcave", "The dragon cave");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println("Type d for The dragon cave");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
keysToActionName16.put("t", "_tai");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
actionNameToOutput16.put("_tai", "Taiwan");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println("Type t for Taiwan");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
keysToActionName16.put("u", "_u");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
actionNameToOutput16.put("_u", " UC Berkeley");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println("Type u for  UC Berkeley");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
keysToActionName16.put("m", "_mcd");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
actionNameToOutput16.put("_mcd", " McDonalds");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
System.out.println("Type m for  McDonalds");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
Scanner in16 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
String input16 = in16.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
while(!keysToActionName16.containsKey(input16)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
input16 = in16.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input16);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
String action16 = keysToActionName16.get(input16);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
if(action16.equals("_dcave")) {
{
System.out.println(""+ ("Off to the Dragon Cave to battle Professor Aho"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
_dragon_cave();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
if(action16.equals("_tai")) {
{
System.out.println(""+ ("Off to Taiwan in search of the powerful PDP-8"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
_taiwan();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
if(action16.equals("_u")) {
{
System.out.println(""+ ("Off to UC Berkeley! ... for some reason."));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
if(action16.equals("_mcd")) {
{
System.out.println(""+ ("Off to McDonalds for a fish burger"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
_mcdonalds();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") < (0)) || _itemNum == (4))
endGame();
}
}
}
endGame();
}
//intdecinit
int _moment;
//start funtion
public void _dragon_cave() {
currentLocation = "_dragon_cave";
while (!((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))){
{
if(_moment == (0)) {
{
System.out.println(""+ ("You are in Prof. Aho's dragon cave!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Aho is ready to fight you.. You must fight him to get his dragon book!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("... or maybe not?..."));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (_moment = (1));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
Map<String,String> keysToActionName11 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput11 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName11.put("f", "_fight");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput11.put("_fight", "fight");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type f for fight");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName11.put("r", "_runaway");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput11.put("_runaway", "run away");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type r for run away");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName11.put("a", "_ask");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput11.put("_ask", " ask nicely");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type a for  ask nicely");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in11 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input11 = in11.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName11.containsKey(input11)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input11 = in11.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input11);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action11 = keysToActionName11.get(input11);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action11.equals("_fight")) {
{
int num = r.nextInt(100);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(num >= 0 && num < 70) {
{
System.out.println(""+ ("You say: "));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("behold Aho, my shiny laser pointer"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_aho", Type.CHARACTER, "_life", (entityHasInt("_prof_aho", Type.CHARACTER, "_life") - entityHasInt("_laser", Type.ITEM, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You caused Prof. Aho a damage of:"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ (entityHasInt("_laser", Type.ITEM, "_damage")));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if((entityHasInt("_prof_aho", Type.CHARACTER, "_life") <= (1))) {
{
System.out.println(""+ ("You have defeated Aho... well done!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_aho.removeItem ("_dragon_book", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
}
}
if(num >= 70 && num < 90) {
{
System.out.println(""+ ("As he hits you Prof. Aho lets you know:"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ (entityHasString("_prof_aho", Type.CHARACTER, "_slogan")));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_dragon_book", Type.ITEM, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Prof Aho hurt you this much:"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ (entityHasInt("_dragon_book", Type.ITEM, "_damage")));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
if(num >= 90 && num < 100) {
{
System.out.println(""+ ("wuah wuah wuah"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Aho surrenders."));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("YOu have won the dragon book"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_aho.removeItem ("_dragon_book", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
if(isTrue(entityExistsItem("_dragon_cave", Type.LOCATION, "_dragon_book"))) {
{
System.out.println(""+ ("In victory you say your famous slogan:"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ (entityHasString("_prof_edwards", Type.CHARACTER, "_slogan")));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Would you like to pick up the dragon book?"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName3 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput3 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName3.put("g", "_grabbook");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput3.put("_grabbook", "grab");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type g for grab");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName3.put("n", "_notgrab");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput3.put("_notgrab", "not grab");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type n for not grab");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in3 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input3 = in3.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName3.containsKey(input3)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input3 = in3.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input3);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action3 = keysToActionName3.get(input3);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action3.equals("_grabbook")) {
{
System.out.println(""+ ("You have the dragon book!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_edwards.addItem ("_dragon_book", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(_itemNum == (3)) {
{
System.out.println(""+ ("You did it!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have collected al four items"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("With them you build the most premiums compiler eveeeeer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
dummy = (_itemNum = (_itemNum + (1)));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Lets go back to columbia"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action3.equals("_notgrab")) {
{
System.out.println(""+ ("You dont have the dragon book!!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_aho.addItem ("_dragon_book", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_dragon_cave();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
else {
//Empty stmt
}
}
_dragon_cave();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action11.equals("_runaway")) {
{
System.out.println(""+ ("You run away to columbia.. aaaawww"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (_moment = (0));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action11.equals("_ask")) {
{
if(isTrue(entityExistsItem("_prof_aho", Type.CHARACTER, "_dragon_book"))) {
{
_prof_aho.removeItem ("_dragon_book", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_edwards.addItem ("_dragon_book", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("He actually just gave you the dragon book!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("... after 3000 hours of telling you about Awk..."));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(_itemNum == (3)) {
{
System.out.println(""+ ("You did it!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have collected all four items"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("With them you build the most premiums compiler eveeeeer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
dummy = (_itemNum = (_itemNum + (1)));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Anyway, time to go back to columbia and choose the next location"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
System.out.println(""+ ("The professor does not have the dragon book!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
endGame();
}
//intdec
int _attack;
//start funtion
public void _ucb() {
currentLocation = "_ucb";
while (!((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))){
{
if((entityHasInt("_bill_gates", Type.CHARACTER, "_life") <= (0)) || (entityHasInt("_plt_mob", Type.CHARACTER, "_life") <= (0))) {
{
if(isTrue(entityExistsItem("_ucb", Type.LOCATION, "_not_windows_computer"))) {
{
System.out.println(""+ ("You have won!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have received the not-windows computer!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_edwards.addItem ("_not_windows_computer", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(_itemNum == (3)) {
{
System.out.println(""+ ("You did it!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have collected all four items"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("With them you build the most premiums compiler eveeeeer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
dummy = (_itemNum = (_itemNum + (1)));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Time to return to Columbia"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName15 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput15 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName15.put("c", "_col");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput15.put("_col", "Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in15 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input15 = in15.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName15.containsKey(input15)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input15 = in15.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input15);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action15 = keysToActionName15.get(input15);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action15.equals("_col")) {
//Empty stmt
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
else {
{
System.out.println(""+ ("You have what you needed from here"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Time to return to Columbia"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName2 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput2 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName2.put("c", "_col");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput2.put("_col", "Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in2 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input2 = in2.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName2.containsKey(input2)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input2 = in2.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input2);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action2 = keysToActionName2.get(input2);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action2.equals("_col")) {
//Empty stmt
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
}
else {
//Empty stmt
}
if(_enemy == (0)) {
{
int num = r.nextInt(100);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(num >= 0 && num < 50) {
dummy = (_enemy = (1));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(num >= 50 && num < 100) {
dummy = (_enemy = (2));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
else {
if(_enemy == (1)) {
{
System.out.println(""+ ("Get ready to fight Bill Gates!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
int num = r.nextInt(100);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(num >= 0 && num < 40) {
{
dummy = (_attack = (1));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Bill throws Microsoft Office at your head!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
if(num >= 40 && num < 70) {
{
dummy = (_attack = (2));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Bill throws Windows 7 at your feet!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
if(num >= 70 && num < 100) {
{
dummy = (_attack = (3));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Bill is trying to tell you the new features of c#!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
Map<String,String> keysToActionName20 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput20 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName20.put("d", "_duck");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput20.put("_duck", "duck");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type d for duck");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName20.put("j", "_jump");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput20.put("_jump", "jump");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type j for jump");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName20.put("c", "_cover");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput20.put("_cover", "cover your ears");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for cover your ears");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in20 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input20 = in20.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName20.containsKey(input20)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input20 = in20.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input20);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action20 = keysToActionName20.get(input20);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action20.equals("_duck")) {
{
if(_attack == (1)) {
{
System.out.println(""+ ("You escaped the horror!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Gates is hyperventilating!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_bill_gates", Type.CHARACTER, "_life", (entityHasInt("_bill_gates", Type.CHARACTER, "_life") - (1))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
if(_attack == (2)) {
{
System.out.println(""+ ("You got hit by windows.. you will never function correctly again!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - (5))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("You heard it all... You are utterly confused!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - (5))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action20.equals("_jump")) {
{
if(_attack == (2)) {
{
System.out.println(""+ ("You escaped that which makes many stumble and crack their heards!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Gates is sweating so much!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_bill_gates", Type.CHARACTER, "_life", (entityHasInt("_bill_gates", Type.CHARACTER, "_life") - (1))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
if(_attack == (1)) {
{
System.out.println(""+ ("You got hit by office square in the face... just like every user!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - (5))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("You heard it all... You are utterly confused!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - (5))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action20.equals("_cover")) {
{
if(_attack == (3)) {
{
System.out.println(""+ ("Good! You would have never been able to make a good compiler again!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Gates looks dizzy!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_bill_gates", Type.CHARACTER, "_life", (entityHasInt("_bill_gates", Type.CHARACTER, "_life") - (1))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
if(_attack == (1)) {
{
System.out.println(""+ ("You got hit by office square in the face... just like every user!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - (5))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("You got hit by windows.. you will never function correctly again!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - (5))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
else {
{
System.out.println(""+ ("Get ready to fight a huge PLT mob!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
int num = r.nextInt(100);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(num >= 0 && num < 40) {
{
dummy = (_attack = (1));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Someone throws a C Reference Manual at you!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
if(num >= 40 && num < 70) {
{
dummy = (_attack = (2));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("someone is trying to throw the Ocaml camel at your feet!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
if(num >= 70 && num < 100) {
{
dummy = (_attack = (3));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("They are all screaming about a new language that is scanned backwards!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
Map<String,String> keysToActionName20 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput20 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName20.put("d", "_duck");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput20.put("_duck", "duck");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type d for duck");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName20.put("j", "_jump");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput20.put("_jump", "jump");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type j for jump");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName20.put("c", "_cover");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput20.put("_cover", "cover your ears");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for cover your ears");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in20 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input20 = in20.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName20.containsKey(input20)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input20 = in20.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input20);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action20 = keysToActionName20.get(input20);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action20.equals("_duck")) {
{
if(_attack == (1)) {
{
System.out.println(""+ ("You escaped the horror!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("They are getting tired!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_plt_mob", Type.CHARACTER, "_life", (entityHasInt("_plt_mob", Type.CHARACTER, "_life") - (1))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
if(_attack == (2)) {
{
System.out.println(""+ ("You got hit by the camel.. you appreciate O'Caml a little less!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_plt_mob", Type.CHARACTER, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("You heard it all... You are utterly confused!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_plt_mob", Type.CHARACTER, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action20.equals("_jump")) {
{
if(_attack == (2)) {
{
System.out.println(""+ ("You escaped that which makes many stumble and crack their heards!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("The mob is getting bored!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_plt_mob", Type.CHARACTER, "_life", (entityHasInt("_plt_mob", Type.CHARACTER, "_life") - (1))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
if(_attack == (1)) {
{
System.out.println(""+ ("You got hit by the manual square in the face... your brain is now a big null pointer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_plt_mob", Type.CHARACTER, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("You heard it all... You are utterly confused!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_plt_mob", Type.CHARACTER, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action20.equals("_cover")) {
{
if(_attack == (3)) {
{
System.out.println(""+ ("Good! You would have never been able to make a good compiler again!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Every head in that mob hurts!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_bill_gates", Type.CHARACTER, "_life", (entityHasInt("_bill_gates", Type.CHARACTER, "_life") - (1))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
if(_attack == (1)) {
{
System.out.println(""+ ("You got hit by the manual square in the face... your brain is now a big null pointer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_plt_mob", Type.CHARACTER, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
{
System.out.println(""+ ("You got hit by the camel.. you appreciate O'Caml a little less!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You loose 5 life points!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (entityHasInt("_prof_edwards", Type.CHARACTER, "_life") - entityHasInt("_plt_mob", Type.CHARACTER, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
_ucb();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
}
}
endGame();
}
//start funtion
public void _mcdonalds() {
currentLocation = "_mcdonalds";
while (!((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))){
{
if(isTrue(entityExistsItem("_mcdonalds", Type.LOCATION, "_fish_sandwich"))) {
{
System.out.println(""+ ("Ronald gives you a riddle!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("What Burger was featured in the greatest advertising campaign ever?"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName2 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput2 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName2.put("f", "_fish");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput2.put("_fish", " fish sandwitch");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type f for  fish sandwitch");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName2.put("b", "_burger");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput2.put("_burger", " Big Mac");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type b for  Big Mac");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName2.put("c", "_caesar");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput2.put("_caesar", " Caesar Salad");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for  Caesar Salad");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in2 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input2 = in2.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName2.containsKey(input2)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input2 = in2.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input2);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action2 = keysToActionName2.get(input2);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action2.equals("_fish")) {
{
System.out.println(""+ ("Yes, you do believe in magic!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("YOu have earned a  fish Burger"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Lets go back to Columbia to keep on with the adventure!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_prof_edwards.addItem ("_fish_sandwich", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(_itemNum == (3)) {
{
System.out.println(""+ ("You did it!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have collected all four items"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("With them you build the most premiums compiler eveeeeer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
dummy = (_itemNum = (_itemNum + (1)));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action2.equals("_burger")) {
System.out.println(""+ ("no... come on... you were there!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_mcdonalds();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action2.equals("_caesar")) {
System.out.println(""+ ("Thats not even a burger!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
_mcdonalds();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
else {
{
System.out.println(""+ ("you have collected what you needed here"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName1 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput1 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName1.put("c", "_col");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput1.put("_col", "Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in1 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input1 = in1.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName1.containsKey(input1)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input1 = in1.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input1);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action1 = keysToActionName1.get(input1);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action1.equals("_col")) {
//Empty stmt
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
}
}
endGame();
}
//intdecinit
int _narrated;
//start funtion
public void _taiwan() {
currentLocation = "_taiwan";
while (!((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))){
{
if((entityHasInt("_godzilla", Type.CHARACTER, "_life") <= (0))) {
{
System.out.println(""+ ("...oh no wait, you beat Godzilla!! holy shhh... wow!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have received the PDP-8!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(!isTrue(entityExistsItem("_prof_edwards", Type.CHARACTER, "_PDP_8"))) {
{
_prof_edwards.addItem ("_PDP_8", currentLocation, locations);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(_itemNum == (3)) {
{
System.out.println(""+ ("You did it!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have collected all four items"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("With them you build the most premiums compiler eveeeeer!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
dummy = (_itemNum = (_itemNum + (1)));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
}
}
else {
//Empty stmt
}
if(isTrue(entityExistsItem("_taiwan", Type.LOCATION, "_PDP_8"))) {
{
if(_narrated == (0)) {
{
System.out.println(""+ ("You have traveled to Taiwan to get the biggest PDP-8 in the world"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You need it for its humongous stack!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("The problem is, it belongs to Godzilla!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (_narrated = (1));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
else {
//Empty stmt
}
System.out.println(""+ ("Godzilla is about to step on you!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName12 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput12 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName12.put("s", "_shoot");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput12.put("_shoot", "shooting your laser pointer at his foot");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type s for shooting your laser pointer at his foot");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName12.put("d", "_die");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput12.put("_die", "dying squished between its toes");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type d for dying squished between its toes");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName12.put("t", "_tickle");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput12.put("_tickle", "tickling the monster's foot");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type t for tickling the monster's foot");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in12 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input12 = in12.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName12.containsKey(input12)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input12 = in12.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input12);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action12 = keysToActionName12.get(input12);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action12.equals("_shoot")) {
{
System.out.println(""+ ("You hit the monster in the foot"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_godzilla", Type.CHARACTER, "_life", (entityHasInt("_godzilla", Type.CHARACTER, "_life") - entityHasInt("_laser", Type.ITEM, "_damage"))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ (" You did 10 points damage!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("Keep fighting!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_taiwan();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action12.equals("_die")) {
{
System.out.println(""+ ("You have died between its green toes"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_prof_edwards", Type.CHARACTER, "_life", (0)));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
if(action12.equals("_tickle")) {
{
System.out.println(""+ ("the monster fell back laughing"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println(""+ ("You have done 50 damage!!"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
dummy = (entitySetInt("_godzilla", Type.CHARACTER, "_life", (entityHasInt("_godzilla", Type.CHARACTER, "_life") - (50))));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
_taiwan();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
}
else {
{
System.out.println(""+ ("You have the calculator. Time to return to columbia"));
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String,String> keysToActionName1 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Map<String, String> actionNameToOutput1 = new HashMap<String, String>();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("CHOOSE AN ACTION:");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
keysToActionName1.put("c", "_col");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
actionNameToOutput1.put("_col", "Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
System.out.println("Type c for Columbia");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
Scanner in1 = new Scanner(System.in);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String input1 = in1.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
while(!keysToActionName1.containsKey(input1)) {
System.out.println("Invalid input, try again");
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
input1 = in1.nextLine();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
System.out.println("You typed " + input1);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
String action1 = keysToActionName1.get(input1);
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
if(action1.equals("_col")) {
//Empty stmt
_columbia();
if ((entityHasInt("_prof_edwards", Type.CHARACTER, "_life") <= (0)) || _itemNum == (4))
endGame();
}
}
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
