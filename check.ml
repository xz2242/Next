open Ast
open Checktype

module VarMap = Map.Make( struct
type t = string * Checktype.t
let compare x y = Pervasives.compare x y
end)


exception DupVar of string
exception NotFound of string
exception WrongType of string
exception ProbError of string
exception InvalidKey of string


let print_next_type = function
	Integer -> print_string "int"
  | String -> print_string "string"
  | Character -> print_string "character"
  | Item -> print_string "item"
  | Location -> print_string "location"
  | Action -> print_string "action"
  | Key -> print_string "key"

let print_symboltable symt = 
	let print_entry (name,t) tt  = print_string name; print_next_type t; print_endline "" in
		VarMap.iter print_entry symt


let check_id symt = function
	  Var (name) -> if (not (VarMap.mem (name,String) symt)) && (not (VarMap.mem (name,Integer) symt)) && (not (VarMap.mem (name,Location) symt))
  						&& (not (VarMap.mem (name,Character) symt)) && (not (VarMap.mem (name,Item) symt)) 
  					then raise ( NotFound("undefined variable " ^ name))
  					else if (VarMap.mem (name,String) symt) then String 
  					else if (VarMap.mem (name,Integer) symt) then Integer
  					else if (VarMap.mem (name,Location) symt) then Location
  					else if (VarMap.mem (name,Character) symt) then Character
  					else Item
  	| Has (name, subname) -> if (not (VarMap.mem (name,Character) symt) ) && (not (VarMap.mem (name,Item) symt) ) && (not (VarMap.mem (name,Location) symt) ) then
  								raise ( NotFound("undefined variable " ^ name))
  						   else
  						   		if (VarMap.mem (name,Character) symt) then
  						   			let subsymt = VarMap.find (name,Character) symt in
  						   				if (not (VarMap.mem (subname, Integer) subsymt)) && (not (VarMap.mem (subname, String) subsymt)) then
  						   					raise ( NotFound("member not found " ^ name ^"." ^ subname))
  						   				else
  						   					if (VarMap.mem (subname, Integer) subsymt) then Integer 
  						   					else String
  						   		else if (VarMap.mem (name,Location) symt) then
  						   			let subsymt = VarMap.find (name,Location) symt in
  						   				if (not (VarMap.mem (subname, Integer) subsymt)) && (not (VarMap.mem (subname, String) subsymt)) then
  						   					raise ( NotFound("member not found " ^ name ^"." ^ subname))
  						   				else
  						   					if (VarMap.mem (subname, Integer) subsymt) then Integer 
  						   					else String
  						   		else
  						   			let subsymt = VarMap.find (name,Item) symt in
  						   				if (not (VarMap.mem (subname, Integer) subsymt)) && (not (VarMap.mem (subname, String) subsymt)) then
  						   					raise ( NotFound("member not found " ^ name ^"." ^ subname))
  						   				else
  						   					if (VarMap.mem (subname, Integer) subsymt) then Integer 
  						   					else String
  

let rec check_expr symt = function
    Binop (expr1 , op, expr2) -> if (check_expr symt expr1)=Integer && (check_expr symt expr2) = Integer then Integer else raise (WrongType("Type does not match"))
  | Asn (id, expr) -> if ((check_id symt id) = Integer && (check_expr symt expr) = Integer) then Integer
  					  else if ((check_id symt id) = String && (check_expr symt expr) = String) then String
  					  else raise (WrongType("Type does not match"))  						
  | Lit (intvalue) -> Integer
  | LitS (strvalue) -> String
  | Exists (name, subname) -> Integer					   				
  | Neg (expr) -> if (check_expr symt expr) = Integer then Integer else raise (WrongType("Type does not match"))
  | Not (expr) -> if (check_expr symt expr) = Integer then Integer else raise (WrongType("Type does not match"))
  | Ident (id) -> check_id symt id
  

let check_action = fun actionmap actiondec -> match actiondec with
												Unitaction (vname, outstr, key) -> 
												  
		 										  if (VarMap.mem (vname,Action) actionmap) then raise ( DupVar("duplicated action name " ^ vname))
												  else if (VarMap.mem (key,Key) actionmap) then raise ( DupVar("Multiple binding for key " ^ key))
												  else if not (String.length key = 1) then raise (InvalidKey ("Key should be one single character" ^ key))
												  else if not ( (Char.code key.[0]) >= (Char.code 'a') && (Char.code key.[0]) <= (Char.code 'z') ||
												  				(Char.code key.[0]) >= (Char.code 'A') && (Char.code key.[0]) <= (Char.code 'Z') ||
												  				(Char.code key.[0]) >= (Char.code '0') && (Char.code key.[0]) <= (Char.code '9') )
												  				 then
												   raise (InvalidKey ("Key should be either a digit or a letter" ^ key))
												  else
										 			let actionmap = VarMap.add (vname, Action) Action actionmap in
										 				VarMap.add (key, Key) Key actionmap

let rec check_probexpr symt total probexpr = match probexpr with
										Unitprob (pvalue, probstmt) ->
											check_stmt symt probstmt;
											total+pvalue

and check_whenexpr symt actionmap whenexpr = match whenexpr with
										Unitwhen (actionname, whenstmt, locname) ->
											(if not (VarMap.mem (actionname, Action) actionmap) then raise ( NotFound("action not defined " ^ actionname))
											else check_stmt symt whenstmt);
											if not (VarMap.mem (locname, Location) symt) then raise ( NotFound("Location not found in next " ^ locname))
											else ()
												
and check_stmt symt =  function
	Ifelse (cond, truestmt, falsestmt) -> if not ((check_expr symt cond) = Integer) then raise (WrongType("Type does not match"))
										  else check_stmt symt truestmt; check_stmt symt falsestmt
  | Chwhen (actiondeclist, whenexprlist) -> let actionmap = List.fold_left check_action VarMap.empty actiondeclist in
  												List.iter (check_whenexpr symt actionmap) whenexprlist
  												  												
  | Prob (probexprlist) -> let total = List.fold_left (check_probexpr symt) 0 probexprlist in
  								if not (total = 100) then raise ( ProbError ("Total Probability is not 100 "))
  								else ()
  | Kill (name) -> if (not (VarMap.mem (name,Character) symt) ) && (not (VarMap.mem (name,Item) symt) ) 
  					then raise ( NotFound("Var not found, kill fail " ^ name))
  				   else ()
  | Grab (name, subname) -> if (not (VarMap.mem (name,Character) symt) )
  								then raise ( NotFound("Charactor not found, invalid grab " ^ name))
  				   			else if (not (VarMap.mem (subname,Item) symt) )
  				   				then raise ( NotFound("Item not found, invalid grab " ^ subname))
  				   			else ()
  | Drop (name, subname) -> if (not (VarMap.mem (name,Character) symt) )
  								then raise ( NotFound("Charactor not found, invalid drop " ^ name))
  				   			else if (not (VarMap.mem (subname,Item) symt) )
  				   				then raise ( NotFound("Item not found, invalid drop " ^ subname))
  				   			else ()
  | Show (name, subname) -> if (not (VarMap.mem (name,Location) symt) )
  								then raise ( NotFound("Location not found, invalid show " ^ name))
  				   			else if (not (VarMap.mem (subname,Item) symt) )
  				   				then raise ( NotFound("Item not found, invalid show " ^ subname))
  				   			else ()
  | Hide (name, subname) -> if (not (VarMap.mem (name,Location) symt) )
  								then raise ( NotFound("Location not found, invalid hide " ^ name))
  				   			else if (not (VarMap.mem (subname,Item) symt) )
  				   				then raise ( NotFound("Item not found, invalid hide " ^ subname))
  				   			else ()
  | Atomstmt (exp) -> ignore (check_expr symt exp)
  | Cmpdstmt (blk) -> List.iter (check_stmt symt) blk
  | Nostmt (nonsense) -> ()
  | Print  (exp) -> if (not ((check_expr symt exp) = Integer)) && (not ((check_expr symt exp) = String)) then raise (WrongType("Type does not match"))
  					else ()

let pridec_tostr = function
   Strdec (name) -> name
 | Intdec (name) -> name
 | Strdecinit (name,initexpr) -> name
 | Intdecinit (name,initexpr) -> name

let check_pridec symt = function
	Strdec (name) -> (*print_endline("symt----------");
						print_symboltable symt;*)
					 if (VarMap.mem (name, Location) symt) || 
  						(VarMap.mem (name, Character) symt) || 
  						(VarMap.mem (name, Item) symt) || 
  						(VarMap.mem (name, String) symt) || 
  						(VarMap.mem (name, Integer) symt) then
						(* *)
						(print_endline("error");
					 	raise ( DupVar("duplicated identifier " ^ name)))
					 else
					 	VarMap.add (name, String) VarMap.empty symt
					 						 	
  | Intdec (name) -> (*print_endline("symt----------");
  						print_symboltable symt;*)
  					 if (VarMap.mem (name, Location) symt) || 
  						(VarMap.mem (name, Character) symt) || 
  						(VarMap.mem (name, Item) symt) || 
  						(VarMap.mem (name, String) symt) || 
  						(VarMap.mem (name, Integer) symt) then
  						(print_endline("error");
					 	(* *)
					 	raise ( DupVar("duplicated identifier " ^ name)))					
					 else
						VarMap.add (name, Integer) VarMap.empty symt
							 	
  | Strdecinit (name,initexpr) -> ignore (check_expr symt initexpr);
  									(*print_endline("symt----------");
									print_symboltable symt;*)
								 if (VarMap.mem (name, Location) symt) || 
  									(VarMap.mem (name, Character) symt) || 
  									(VarMap.mem (name, Item) symt) || 
  									(VarMap.mem (name, String) symt) || 
  									(VarMap.mem (name, Integer) symt) then
									(* *)
									(print_endline("error");
								 	raise ( DupVar("duplicated identifier " ^ name)))
								 else
								 	VarMap.add (name, String) VarMap.empty symt
  | Intdecinit (name,initexpr) -> ignore (check_expr symt initexpr);
  									(*print_symboltable symt;*)
								 if (VarMap.mem (name, Location) symt) || 
  									(VarMap.mem (name, Character) symt) || 
  									(VarMap.mem (name, Item) symt) || 
  									(VarMap.mem (name, String) symt) || 
  									(VarMap.mem (name, Integer) symt) then
									(* *)
									(print_endline("error");
								 	raise ( DupVar("duplicated identifier " ^ name)))
								 else
								 	VarMap.add (name, Integer) VarMap.empty symt


let rec check_membervarlist_intstr subsymt = function
    [] -> subsymt
  | member::tl -> match member with
  						  Primember (pridec) -> check_membervarlist_intstr (check_pridec subsymt pridec) tl
  						| Varref (varname) -> raise (WrongType("Type does not match "^varname))
 
let rec check_membervarlist_item symt subsymt = function
    [] -> subsymt
  | member::tl -> match member with
  						  Primember (pridec) -> raise (WrongType("Type does not match " ^(pridec_tostr pridec)))
  						| Varref (varname) -> if not (VarMap.mem (varname, Item) symt) then 
  													raise ( NotFound("undefined variable " ^ varname))
  											  else 
  												 if (VarMap.mem (varname,Item) subsymt) then
  												 	raise ( DupVar("duplicated identifier " ^ varname))
  												 else
  												 	check_membervarlist_item symt (VarMap.add (varname,Item) VarMap.empty subsymt) tl

let rec check_membervarlist_chara symt subsymt = function
    [] -> subsymt
  | member::tl -> match member with
  						  Primember (pridec) -> raise (WrongType("Type does not match " ^(pridec_tostr pridec)))
  						| Varref (varname) -> if not (VarMap.mem (varname, Character) symt) then 
  													raise ( NotFound("undefined variable " ^ varname))
  											  else 
  												 if (VarMap.mem (varname,Character) subsymt) then
  												 	raise ( DupVar("duplicated identifier " ^ varname))
  												 else
  												 	check_membervarlist_item symt (VarMap.add (varname,Character) VarMap.empty subsymt) tl
  

let check_globaldec symt= function
    IntStrdec (pridec) -> check_pridec symt pridec
  | Charadec (name, memberlist1, memberlist2) -> (*print_symboltable symt;*)
  												if (VarMap.mem (name, Location) symt) || 
  													(VarMap.mem (name, Character) symt) || 
  													(VarMap.mem (name, Item) symt) || 
  													(VarMap.mem (name, String) symt) || 
  													(VarMap.mem (name, Integer) symt) then
  													raise ( DupVar("duplicated identifier " ^ name))
  												else
  													let subsymt = VarMap.empty in
  														let subsymt = check_membervarlist_intstr subsymt memberlist1 in
  															let subsymt = check_membervarlist_item symt subsymt memberlist2 in
  																VarMap.add (name, Character) subsymt symt
  | Itemdec (name, memberlist1) -> (*print_symboltable symt;*)
  									if (VarMap.mem (name, Location) symt) || 
  										(VarMap.mem (name, Character) symt) || 
  										(VarMap.mem (name, Item) symt) || 
  										(VarMap.mem (name, String) symt) || 
  										(VarMap.mem (name, Integer) symt) then
  										raise ( DupVar("duplicated identifier " ^ name))
  									else
  										let subsymt = VarMap.empty in
  											let subsymt = check_membervarlist_intstr subsymt memberlist1 in
  												VarMap.add (name, Item) subsymt symt
  | Locdec (name, memberlist1,memberlist2, memberlist3)-> (*print_symboltable symt;*)
  															if (VarMap.mem (name, Location) symt) || 
  															(VarMap.mem (name, Character) symt) || 
  															(VarMap.mem (name, Item) symt) || 
  															(VarMap.mem (name, String) symt) || 
  															(VarMap.mem (name, Integer) symt) then
			  													raise ( DupVar("duplicated identifier " ^ name))
			  												else
			  													let subsymt = VarMap.empty in
			  														let subsymt = check_membervarlist_intstr subsymt memberlist1 in
			  															let subsymt = check_membervarlist_item symt subsymt memberlist2 in
			  																let subsymt = check_membervarlist_chara symt subsymt memberlist3 in
			  																	VarMap.add (name, Location) subsymt symt
  | Startend (name, cond, logicstmt) -> (*print_symboltable symt;*)
  										if not (VarMap.mem (name, Location) symt)  then
  											raise ( NotFound("undefined variable " ^ name))
  										else
  											ignore (check_expr symt cond);
  											check_stmt symt logicstmt;
  											symt

	

let rec check_program symt = function
    [] -> ()
  | dec::tl -> check_program (check_globaldec symt dec) tl



