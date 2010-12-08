open Ast
open Checktype
open Check


module type EXPRESSION = 
   sig 
    val expr_to_java : expr -> ('a VarMap.t VarMap.t) -> string
    val expr_to_java_boolean : expr -> ('a VarMap.t VarMap.t) -> string list * string (* string list contains statements that need to happen before the string condition is checked *)
    val next_type_to_string : Checktype.t -> string
    val check_type_to_string : string -> ('a VarMap.t VarMap.t) -> string
   end

module Expression : EXPRESSION = struct

let next_type_to_string = function
  	Integer -> "int"
    | String -> "string"
    | Character -> "character"
    | Item -> "item"
    | Location -> "location"
    | Action -> "action"
    | Key -> "key"

let check_type_to_string name  = function
    symt -> if (not (VarMap.mem (name,String) symt)) && (not (VarMap.mem (name,Integer) symt)) && (not (VarMap.mem (name,Location) symt))
  						&& (not (VarMap.mem (name,Character) symt)) && (not (VarMap.mem (name,Item) symt)) 
  			then raise ( NotFound("undefined variable " ^ name))
            else if (VarMap.mem (name,String) symt) then "String" 
	        else if (VarMap.mem (name,Integer) symt) then "Int"
	        else if (VarMap.mem (name,Location) symt) then "Location"
	        else if(VarMap.mem (name,Character) symt) then "Character"
            else "Item"

let rec expr_to_java exp tmap = match exp with
   Binop (exp1, op, exp2) -> if op == Add then "(" ^ (expr_to_java exp1 tmap) ^ ") + (" ^ (expr_to_java exp2 tmap) ^ ")"
                        else if op == Sub then "(" ^ (expr_to_java exp1 tmap) ^ ") - (" ^ (expr_to_java exp2 tmap) ^ ")"
                        else if op == Mul then "(" ^ (expr_to_java exp1 tmap) ^ ") * (" ^ (expr_to_java exp2 tmap) ^ ")"
                        else if op == Div then "(" ^ (expr_to_java exp1 tmap) ^ ") / (" ^ (expr_to_java exp2 tmap) ^ ")"
                        (*else if op == Or then (expr_to_java exp1) ^ " || " ^ (expr_to_java exp2)
                        else if op == And then (expr_to_java exp1) ^ " && " ^ (expr_to_java exp2)*)
                        else if op == Eq then "(" ^ (expr_to_java exp1 tmap) ^ ") == (" ^ (expr_to_java exp2 tmap) ^ ")" (* what about string equality? *)
                        else if op == Lt then "(" ^ (expr_to_java exp1 tmap) ^ ") < (" ^ (expr_to_java exp2 tmap) ^ ")"
                        else if op == Gt then "(" ^ (expr_to_java exp1 tmap) ^ ") > (" ^ (expr_to_java exp2 tmap) ^ ")"
                        else if op == Neq then "(" ^ (expr_to_java exp1 tmap) ^ ") != (" ^ (expr_to_java exp2 tmap) ^ ")"
                        else "WHAT"
   | Asn (id, exp) -> let t = check_id tmap id in 
                        (match id with
                        Var(str) -> str ^ " = " ^ (expr_to_java exp tmap)
                      | Has(name, subname) -> "(entitySet" ^ (String.capitalize (next_type_to_string t)) ^ "(" ^ name ^ ", Type." ^ (String.uppercase (check_type_to_string name tmap)) ^ ", " ^ subname ^ ", " ^ (expr_to_java exp tmap) ^ "))")
   | Lit (i) -> "(" ^ (string_of_int i) ^ ")"
   | LitS (str) -> "(\"" ^ str ^ "\")"
   | Exists (str1, str2) -> "isTrue(" ^ (expr_to_java (Ident(Has(str1, str2))) tmap) ^ ")"
   | Ident (id) ->  let t = check_id tmap id in 
                    (match id with
                      Var(name) -> name
                    | Has(name, subname) -> "(entityHas" ^ (String.capitalize (next_type_to_string t)) ^ "(\"" ^ name ^ "\", Type." ^ (String.uppercase (check_type_to_string name tmap))  ^ ", \"" ^ subname ^ "\"))")    
   | Neg (exp) -> "(-" ^ (expr_to_java exp tmap) ^ ")"
   | Not (exp) -> "(!" ^ (expr_to_java exp tmap) ^ ")"


let rec expr_to_java_boolean exp tmap = match exp with
	Binop (exp1, op, exp2) -> if op == Add then ([], (expr_to_java exp1 tmap) ^ " + " ^ (expr_to_java exp2 tmap) ^ " != 0" )
	                        else if op == Sub then ([], (expr_to_java exp1 tmap) ^ " - " ^ (expr_to_java exp2 tmap) ^ " != 0")
	                        else if op == Mul then ([], (expr_to_java exp1 tmap) ^ " * " ^ (expr_to_java exp2 tmap) ^ " != 0")
	                        else if op == Div then ([], (expr_to_java exp1 tmap) ^ " / " ^ (expr_to_java exp2 tmap) ^ " != 0")
	                        else if op == Eq then ([], (expr_to_java exp1 tmap) ^ " == " ^ (expr_to_java exp2 tmap)) (* what about string equality? *)
	                        else if op == Lt then ([], (expr_to_java exp1 tmap) ^ " < " ^ (expr_to_java exp2 tmap))
		                    else if op == Gt then ([], (expr_to_java exp1 tmap) ^ " > " ^ (expr_to_java exp2 tmap))
		                    else if op == Neq then ([], (expr_to_java exp1 tmap) ^ " != " ^ (expr_to_java exp2 tmap))
							else if op == Or then  (fst (expr_to_java_boolean exp1 tmap)@fst (expr_to_java_boolean exp2 tmap), (snd (expr_to_java_boolean exp1 tmap)) ^ " || " ^ (snd (expr_to_java_boolean exp2 tmap)))
                        	else if op == And then (fst (expr_to_java_boolean exp1 tmap)@fst (expr_to_java_boolean exp2 tmap), (snd (expr_to_java_boolean exp1 tmap)) ^ " && " ^ (snd (expr_to_java_boolean exp2 tmap)))
	                        else ([], "false")
	   | Asn (id, exp) -> ([], "(" ^ (expr_to_java exp tmap) ^ ") != 0 ")
	   | Lit (i) -> ([], "isTrue(" ^ (string_of_int i) ^ ")")
	   | LitS (str) -> ([], "isTrue(" ^ str ^ ")")
	   | Exists (str1, str2) -> ([], (expr_to_java exp tmap))
	   | Ident (id) -> ([], "isTrue(" ^ (expr_to_java exp tmap) ^ ")") 
	   | Neg (exp) -> ([], "(" ^ (expr_to_java exp tmap) ^ ") != 0 ")
	   | Not (exp) -> ([], (expr_to_java exp tmap))

end


