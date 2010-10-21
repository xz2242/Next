(*

open Ast

exception Varnotfound
exception SyntaxErr


module StringMap = Map.Make(String)
let varmap = ref StringMap.empty

let print_map = function smap ->
	StringMap.iter (fun s i -> (print_string s; print_string "\t"; print_endline (string_of_int i))) smap

let rec evalexpr = function 
    Lit(x) -> x
  | LitS(s) -> 0
 (* | Var(x) -> (*print_endline x ; print_endline (string_of_bool (StringMap.is_empty !varmap));*)
  		 if (StringMap.mem x !varmap) then ( StringMap.find x !varmap) else raise Varnotfound *)
  | Asn (str, e1) -> varmap := StringMap.add str (evalexpr e1) !varmap; evalexpr e1
(*  | Seq (e1, e2) -> ignore (evalexpr e1); evalexpr e2 *)
  | Print(e1) -> print_endline (strevalexpr e1); 0
  | Binop(e1, op, e2) ->
      let v1 = evalexpr e1 and v2 = evalexpr e2 in
      	match op with
		Add -> v1 + v2
      | Sub -> v1 - v2
      | Mul -> v1 * v2
      | Div -> v1 / v2
      | Or -> if (v1 !=0 || v2 != 0) then 1 else 0
      | And -> if (v1 != 0 && v2 != 0) then 1 else 0
      | Gt -> if (v1 > v2) then 1 else 0
      | Lt -> if (v1 < v2) then 1 else 0
      | Eq -> if (v1 == v2) then 1 else 0
      | Neq -> if (v1 != v2) then 1 else 0

and strevalexpr = function 
	Lit(x) -> string_of_int x
  | LitS(s) -> s
(*  | Var(x) -> (*print_endline x ; print_endline (string_of_bool (StringMap.is_empty !varmap));*)
  		 if (StringMap.mem x !varmap) then string_of_int ( StringMap.find x !varmap) else raise Varnotfound *)
  | Asn (str, e1) -> varmap := StringMap.add str (evalexpr e1) !varmap; string_of_int (evalexpr e1)
(*  | Seq (e1, e2) -> (strevalexpr e1) ^(strevalexpr e2)*)
  | Print(e1) -> raise SyntaxErr
  | Binop(e1, op, e2) ->
      let v1 = evalexpr e1 and v2 = evalexpr e2 in
      	match op with
		Add -> string_of_int (v1 + v2)
      | Sub -> string_of_int (v1 - v2)
      | Mul -> string_of_int (v1 * v2)
      | Div -> string_of_int (v1 / v2)
      | Or -> if (v1 !=0 || v2 != 0) then "true" else "false"
      | And -> if (v1 != 0 && v2 != 0) then "true" else "false"
      | Gt -> if (v1 > v2) then "true" else "false"
      | Lt -> if (v1 < v2) then "true" else "false"
      | Eq -> if (v1 == v2) then "true" else "false"
      | Neq -> if (v1 != v2) then "true" else "false"

let rec evalstmt = function 
	Ifelse (e1, s1 ,s2) -> if (evalexpr e1 != 0) then evalstmt s1 else evalstmt s2
  | Atomstmt (e1) -> evalexpr e1
  | Cmpdstmt (b1) -> evalblock b1
  | Nostmt(n) -> n
	and evalblock = function
	Onestmt (s1) -> evalstmt s1
  | Onestmtoneblk (s1, b1) -> ignore (evalstmt s1); evalblock b1
  | Twoblks (b1, b2) -> ignore (evalblock b1); evalblock b2

let _ =
  let lexbuf = Lexing.from_channel stdin in
  let block = Parser.block Scanner.token lexbuf in
  let result = evalblock block in
  print_endline (string_of_int result);
  
*)





