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
                        else if op == Or then (expr_to_java exp1) ^ " || " ^ (expr_to_java exp2)
                        else if op == And then (expr_to_java exp1) ^ " && " ^ (expr_to_java exp2)
                        else if op == Eq then (expr_to_java exp1) ^ " == " ^ (expr_to_java exp2) (* what about string equality? *)
                        else if op == Lt then (expr_to_java exp1) ^ " < " ^ (expr_to_java exp2)
                        else if op == Gt then (expr_to_java exp1) ^ " > " ^ (expr_to_java exp2)
                        else if op == Neq then (expr_to_java exp1) ^ " != " ^ (expr_to_java exp2)
                        else "WHAT"
   | Asn (type, str, exp) -> str ^ " = " ^ (expr_to_java exp)
   | Lit (i) -> string_of_int i
   | LitS (str) -> "\"" ^ str ^ "\""
   | Exists (type, str1, str2) -> "//TODO: match type and perform correct lookup characters.containsKey(\"" ^ str1 ^ "\") || locations.containsKey(\"" ^ str2 ^ "\")"
   | Has (tup1, tup2) -> "//look up character or location and then look at attribute map of that object  //TODO: match type and look up in corresponding map"
   | Neg (exp) -> "-" ^ (expr_to_java exp)
   | Not (exp) -> "!" ^ (expr_to_java exp)
   | Var (str) -> str

end