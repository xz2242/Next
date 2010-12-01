open Ast
open Compile
open Check

let java_of_prog program symt = 
let (playcode, startfns) = Compile.javacode program symt in 
"
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

	public int entityIdentInt(String key1, Type type1, String key2) {
		int returnValue;
		if(type1 == Type.LOCATION) {
			Locatation loc = locations.get(key1);
			if(loc != null) {
				returnValue = loc.intAttr.get(key2);
			}
		}
		else if(type1 == Type.CHARACTER) {
			Character character = characters.get(key1);
			if(character != null) {
				returnValue = character.intAttr.get(key2);
			}
		}
		else if(type1 == Type.ITEM) {
			Item item = items.get(key1);
			if(item != null) {
				returnValue = item.intAttr.get(key2);
			}
		}
	
		if(returnValue == null) {
			throw new RuntimeException;
		}
	
		return returnValue;
	}
	
	public int entityIdentString(String key1, Type type1, String key2) {
		String returnValue;
		if(type1 == Type.LOCATION) {
			Locatation loc = locations.get(key1);
			if(loc != null) {
				returnValue = loc.strAttr.get(key2);
			}
		}
		else if(type1 == Type.CHARACTER) {
			Character character = characters.get(key1);
			if(character != null) {
				returnValue = character.strAttr.get(key2);
			}
		}
		else if(type1 == Type.ITEM) {
			Item item = items.get(key1);
			if(item != null) {
				returnValue = item.strAttr.get(key2);
			}
		}
	
		if(returnValue == null) {
			throw new RuntimeException;
		}
	
		return returnValue;
	}

	public Item entityIdentItem(String key1, Type type1, String key2) {
		Item returnValue;
		if(type1 == Type.LOCATION) {
			Locatation loc = locations.get(key1);
			if(loc != null) {
				String itemKey = loc.items.get(key2);
				if(!itemKey.isEmpty()) {
					returnValue = items.get(itemKey);
				}
			}
		}
		else if(type1 == Type.CHARACTER) {
			Character character = characters.get(key1);
			if(character != null) {
				returnValue = character.intAttr.get(key2);
			}
		}
		
		if(returnValue == null) {
			throw new RuntimeException;
		}
		
		return returnValue;
	}
	
	public Item entityIdentCharacter(String key1, Type type1, String key2) {
		Character returnValue;
		if(type1 == Type.LOCATION) {
			Locatation loc = locations.get(key1);
			if(loc != null) {
				String itemKey = loc.characters.get(key2);
				if(!itemKey.isEmpty()) {
					returnValue = characters.get(itemKey);
				}
			}
		}
		
		if(returnValue == null) {
			throw new RuntimeException;
		}
		
		return returnValue;
	}
	
   public void endGame() {
      System.out.println(\"GAME OVER!!!!!\");
      System.exit(0);
   }
   
   public void play() {
      System.out.println(\"this is a java program!\");
"
 ^ (String.concat "\n" playcode) ^ "
   endGame();
   } \n"

 ^ (String.concat "\n" startfns) ^ "
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
}"

let _ =
  let lexbuf = Lexing.from_channel stdin in 
  let program = Parser.program Scanner.token lexbuf in
  let symt = check_program VarMap.empty program in
  let java = java_of_prog program symt in
  print_endline java
  






