open Ast
open Expression
open Check

module type DECLARATION = sig
   val intstrdec_to_java : pridec -> ('a VarMap.t VarMap.t) -> string list
   val itemdec_to_java : string -> membervarlist -> ('a VarMap.t VarMap.t) -> string list
   val charadec_to_java : string -> membervarlist -> membervarlist -> ('a VarMap.t VarMap.t) -> string list
   val locdec_to_java : string -> membervarlist -> membervarlist -> membervarlist -> ('a VarMap.t VarMap.t)  -> string list
end


module Declaration : DECLARATION = struct

let attr_to_java tmap (var:string) (attr:membervar) : string list = match attr with
   Primember(pridec) -> (match pridec with 
       Strdec (str) -> [var ^ ".addStrAttr(\"" ^ str ^ "\", \"\");"; "types.put(\"" ^ str ^ "\", Type.STRING);"]
       | Intdec (str) -> [var ^ ".addIntAttr(\"" ^ str ^ "\", 0);"; "types.put(\"" ^ str ^ "\", Type.INT);"]
       | Strdecinit (str, expr) -> [var ^ ".addStrAttr(\"" ^ str ^ "\"," ^ (Expression.expr_to_java expr tmap) ^ ");"; "types.put(\"" ^ str ^ "\", Type.STRING);"]
       | Intdecinit (str, expr) -> [ var ^ ".addIntAttr(\"" ^ str ^ "\", " ^ (Expression.expr_to_java expr tmap) ^ ");"; "types.put(\"" ^ str ^ "\", Type.INT);"])
   | Varref(str) -> ["//OOPS THIS WAS BAD!"]

let rec attrlist_to_java var attrlist tmap = match attrlist with 
    [] -> []
    | hd::tl -> (attr_to_java tmap var hd) @ (attrlist_to_java var tl tmap)

let itemdec_to_java str attrlist tmap = ["//itemdec"; "Item " ^ str ^ " = new Item();"; "types.put(\"" ^ str ^ "\", Type.ITEM);"] @ (attrlist_to_java str attrlist tmap)

let item_to_java (var:string) (item:membervar) : string list = match item with
    Varref(str) -> [var ^".addItem(\"" ^str^ "\");"]
	|Primember(pridec) ->["//This was bad. Not a reference to a complex variable"]

let rec itemlist_to_java var itemlist = match itemlist with 
    [] -> []
    | hd::tl -> (item_to_java var hd) @ (itemlist_to_java var tl)

let charadec_to_java str attrlist itemlist tmap = ["//charadec"; "Character " ^ str ^ " = new Character();"; "types.put(\"" ^ str ^ "\", Type.CHARACTER);"]@ (attrlist_to_java str attrlist tmap) @ (itemlist_to_java str itemlist)

let character_to_java (var:string) (character:membervar) : string list = match character with
    Varref(str) -> [var ^".showCharacter(\"" ^str^ "\");"]
	|Primember(pridec) ->["//This was bad. Not a reference to a complex variable"]

let rec characterlist_to_java var characterlist = match characterlist with 
    [] -> []
    | hd::tl -> (character_to_java var hd) @ (characterlist_to_java var tl)

let locdec_to_java str attrlist itemlist charlist tmap = ["//locdec"; "Location " ^ str ^ " = new Location();"; "types.put(\"" ^ str ^ "\", Type.LOCATION);"]@ (attrlist_to_java str attrlist tmap) @ (itemlist_to_java str itemlist) @ (characterlist_to_java str charlist) 

let intstrdec_to_java pridec tmap = match pridec with
   Strdec(str) -> ["//strdec"; "String " ^ str ^ ";"]
   | Intdec(str) -> ["//intdec"; "int " ^ str ^ ";"]
   | Strdecinit(str, expr) -> ["//strdecinit"; "String " ^ str ^ " = " ^ (Expression.expr_to_java expr tmap) ^ ";"]
   | Intdecinit(str, expr) -> ["//intdecinit"; "int " ^ str ^ " = " ^ (Expression.expr_to_java expr tmap) ^ ";"]

end