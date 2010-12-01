open Ast

module type START = sig
val startend_to_java : string -> expr -> stmt -> string list * string list
end

module Start : START = struct
let rec startend_stmt_check (expression:string) (statement:string list) = match statement with
	[]->[]
	
	|hd::tl -> if (String.contains hd '}') then [hd] @ ["if (" ^expression ^")"; "endGame();"] @ 
		(startend_stmt_check expression tl) else [hd] @ (startend_stmt_check expression tl) 


let startend_to_java str expr stmt = [str ^ "();"], ["//startend"; "public void " ^ str ^ "() {"] @ 
(fst (Expression.expr_to_java_boolean expr)) @ ["while (" ^ (snd (Expression.expr_to_java_boolean expr)) ^ "){"  ] 
@ (startend_stmt_check (snd (Expression.expr_to_java_boolean expr)) (fst (stmt_to_java ([], []) stmt))) 
@ (fst (Expression.expr_to_java_boolean expr)) @ ["if (" ^expr ^")"; "endGame();"]
@ ["} }"]
end