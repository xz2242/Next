open Ast

module type EXPRESSION = 
   sig 
    val expr_to_java : expr -> string
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
   | Asn (str, exp) -> str ^ " = " ^ (expr_to_java exp)
   | Lit (i) -> string_of_int i
   | LitS (str) -> "\"" ^ str ^ "\""
   | Exists (str1, str2) -> "characters.containsKey(\"" ^ str1 ^ "\") || locations.containsKey(\"" ^ str2 ^ "\")"
   | Has (str1, str2) -> "//has WHAT IS THIS"
   | Neg (exp) -> "-" ^ (expr_to_java exp)
   | Not (exp) -> "!" ^ (expr_to_java exp)
   | Var (str) -> str

end