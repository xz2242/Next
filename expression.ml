open Ast

module type EXPRESSION = 
   sig 
    val expr_to_java : expr -> string
    val expr_to_java_boolean : expr -> string list * string (* string list contains statements that need to happen before the string condition is checked *)
   end

module Expression : EXPRESSION = struct


let rec expr_to_java exp = match exp with
   Binop (exp1, op, exp2) -> if op == Add then (expr_to_java exp1) ^ " + " ^ (expr_to_java exp2)
                        else if op == Sub then (expr_to_java exp1) ^ " - " ^ (expr_to_java exp2)
                        else if op == Mul then (expr_to_java exp1) ^ " * " ^ (expr_to_java exp2)
                        else if op == Div then (expr_to_java exp1) ^ " / " ^ (expr_to_java exp2)
                        (*else if op == Or then (expr_to_java exp1) ^ " || " ^ (expr_to_java exp2)
                        else if op == And then (expr_to_java exp1) ^ " && " ^ (expr_to_java exp2)*)
                        else if op == Eq then (expr_to_java exp1) ^ " == " ^ (expr_to_java exp2) (* what about string equality? *)
                        else if op == Lt then (expr_to_java exp1) ^ " < " ^ (expr_to_java exp2)
                        else if op == Gt then (expr_to_java exp1) ^ " > " ^ (expr_to_java exp2)
                        else if op == Neq then (expr_to_java exp1) ^ " != " ^ (expr_to_java exp2)
                        else "WHAT"
   | Asn (id, exp) -> "//assignment"
   | Lit (i) -> string_of_int i
   | LitS (str) -> "\"" ^ str ^ "\""
   | Exists (str1, str2) -> str1 ^ ".containsKey(\"" ^ str2 ^ "\")"
   | Ident (id) -> "//look up character or location and then look at attribute map of that object  //TODO: match type and look up in corresponding map"
   | Neg (exp) -> "-" ^ (expr_to_java exp)
   | Not (exp) -> "!" ^ (expr_to_java exp)
   (*| Var (str) -> str*)


let rec expr_to_java_boolean exp = match exp with
	Binop (exp1, op, exp2) -> if op == Add then ([], (expr_to_java exp1) ^ " + " ^ (expr_to_java exp2) ^ " != 0" )
	                        else if op == Sub then ([], (expr_to_java exp1) ^ " - " ^ (expr_to_java exp2) ^ " != 0")
	                        else if op == Mul then ([], (expr_to_java exp1) ^ " * " ^ (expr_to_java exp2) ^ " != 0")
	                        else if op == Div then ([], (expr_to_java exp1) ^ " / " ^ (expr_to_java exp2) ^ " != 0")
	                        else if op == Eq then ([], (expr_to_java exp1) ^ " == " ^ (expr_to_java exp2)) (* what about string equality? *)
	                        else if op == Lt then ([], (expr_to_java exp1) ^ " < " ^ (expr_to_java exp2))
		                    else if op == Gt then ([], (expr_to_java exp1) ^ " > " ^ (expr_to_java exp2))
		                    else if op == Neq then ([], (expr_to_java exp1) ^ " != " ^ (expr_to_java exp2))
							else if op == Or then  (fst (expr_to_java_boolean exp1)@fst (expr_to_java_boolean exp2), (snd (expr_to_java_boolean exp1)) ^ " || " ^ (snd (expr_to_java_boolean exp2)))
                        	else if op == And then (fst (expr_to_java_boolean exp1)@fst (expr_to_java_boolean exp2), (snd (expr_to_java_boolean exp1)) ^ " && " ^ (snd (expr_to_java_boolean exp2)))
	                        else ([], "false")
	   | Asn (id, exp) -> ([], "true")
	   | Lit (i) -> ([], string_of_int i ^ " != 0")
	   | LitS (str) -> ([], "true")
	   | Exists (str1, str2) -> ([], (expr_to_java exp))
	   | Ident (id) -> ([], "true")
	   | Neg (exp) -> ([], "-" ^ (expr_to_java exp) ^ " != 0 ")
	   | Not (exp) -> ([], "true")
	   (*| Var (str) -> ([], str ^ " != 0 ")*)


end


