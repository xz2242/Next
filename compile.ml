open Ast
open Expression
open Declaration
open Action
open Selection
open Start
open Statement
open Check

module type COMPILE = 
  sig
    exception CompileError of string
    val javacode : 
      globaldec list -> ('a VarMap.t VarMap.t) -> string list * string list
    val stmt_to_java : 
      ('a VarMap.t VarMap.t) -> string list * string list -> stmt  
      -> string list * string list
    val global_dec_to_java : 
      string list * string list -> globaldec 
      -> ('a VarMap.t VarMap.t) -> string list * string list
  end

module Compile : COMPILE = struct

exception CompileError of string

let rec startend_stmt_check (expression:string) (statement:string list) = 
  match statement with
    []->[]
  |hd::tl -> 
    if (String.contains hd ';') then 
      [hd] @ ["if (" ^expression ^")"; "endGame();"] @ 
	  (startend_stmt_check expression tl) 
	else [hd] @ (startend_stmt_check expression tl)

let rec actiondeclist_to_java list num = match list with
    [] -> []
  | hd::tail -> 
      match hd with
        Unitaction(action, actionname, key) -> 
          ["keysToActionName" ^ string_of_int num ^ 
           ".put(\"" ^ key ^ "\", \"" ^ action ^ "\"" ^ ");";
           "actionNameToOutput" ^ string_of_int num ^ 
           ".put(\"" ^ action ^ "\", \"" ^ actionname ^ "\");";
           "System.out.println(\"Type " ^ key ^ 
           " for " ^ actionname ^ "\");"] @ actiondeclist_to_java tail num

let rec prob_sum list = match list with
    [] -> 0
  | hd::tail -> 
      match hd with Unitprob(i, stmt) -> i + (prob_sum tail)

let rec stmt_to_java tmap (playcode, startfns) stmt = 
  match stmt with
    Ifelse (expr, stmt1, stmt2) -> 
      let (expr_precode, expr_exp) = 
          Expression.expr_to_java_boolean expr tmap in 
      let (stmt1_playcode, stmt1_startfns) = 
          stmt_to_java tmap ([], []) stmt1  in 
      let (stmt2_playcode, stmt2_startfns) = 
          stmt_to_java tmap ([], []) stmt2  in
      (playcode @ expr_precode @ ["if(" ^ expr_exp ^ ") {"] @ 
      stmt1_playcode @ ["}"; "else {"] @ stmt2_playcode @ ["}"], 
      startfns @ stmt1_startfns @ stmt2_startfns)
  | Chwhen (actiondeclist, whenexprlist) -> 
      let num = List.length(playcode) in 
      let mapDecl = ["Map<String,String> keysToActionName" ^ 
                    string_of_int num ^ " = new HashMap<String, String>();"; 
                    "Map<String, String> actionNameToOutput" ^ 
                    string_of_int num ^ 
                    " = new HashMap<String, String>();"] in
      let actiondecs = ["System.out.println(\"CHOOSE AN ACTION:\");"] 
                       @ actiondeclist_to_java actiondeclist num in
      let getinput = ["Scanner in" ^ string_of_int num ^ 
                      " = new Scanner(System.in);";
                     "String input" ^ string_of_int num ^ 
                     " = in" ^ string_of_int num ^ ".nextLine();";
                     "while(!keysToActionName" ^ string_of_int num ^ 
                     ".containsKey(input" ^ string_of_int num ^ ")) {";
                     "System.out.println(\"Invalid input, try again\");";
                     "input" ^ string_of_int num ^ " = in" ^ 
                     string_of_int num ^ ".nextLine();";
                     "}";
                     "System.out.println(\"You typed \" + input" ^ 
                     string_of_int num ^ ");";
                     "String action" ^ string_of_int num ^ 
                     " = keysToActionName" ^ string_of_int num ^ 
                     ".get(input" ^ string_of_int num ^ ");" ] in
      let whenexprs_playcode, whenexprs_startfns = 
        whenexprs_to_java whenexprlist num tmap in
      (playcode @ mapDecl @ actiondecs @ getinput @ 
      whenexprs_playcode, startfns @ whenexprs_startfns)

  | Prob (probexprlist) -> 
      let sum = prob_sum probexprlist in
      if sum != 100 then 
        raise (CompileError "Probabilities did not sum to 100")
      else 
        let randomcode = ["int num = r.nextInt(100);"] in
        let (probexprs_code, probexprs_startfns) = 
          probexprs_to_java 0 probexprlist tmap in
      (playcode @ randomcode @ probexprs_code, startfns @ probexprs_startfns)

  | Kill (str) -> (playcode @ (Action.kill_to_java str), startfns)
  | Grab (str1, str2) -> 
      (playcode @ (Action.grab_to_java str1 str2), startfns)
  | Drop (str1, str2) -> 
      (playcode @ (Action.drop_to_java str1 str2), startfns)
  | Show (str1, str2) -> 
      (playcode @ (Action.show_to_java str1 str2 tmap), startfns)
  | Hide (str1, str2) -> 
      (playcode @ (Action.hide_to_java str1 str2 tmap), startfns)
  | Atomstmt (expr) -> 
      (playcode @ ["dummy = (" ^(Expression.expr_to_java expr tmap)^");"], 
      startfns)
  | Cmpdstmt (codeblock) -> 
      let (blockcode, startfns) = 
        List.fold_left (stmt_to_java tmap) ([], []) codeblock
      in 
      (playcode @ ["{"] @ (blockcode) @ ["}"], startfns)   							
  | Nostmt (i) -> (playcode @ ["//Empty stmt"], startfns)
  | Print (str) -> 
    (playcode @ 
    ["System.out.println(\"\"+ (" ^Expression.expr_to_java str tmap^"));"], 
    startfns)


and whenexprs_to_java list num tmap = match list with
    [] -> ([], [])
  | hd :: tail -> 
      let (hd_playcode, hd_startfns) = whenexpr_to_java hd num tmap in
      let (tail_playcode, tail_startfns) = whenexprs_to_java tail num tmap in
      (hd_playcode @ tail_playcode, hd_startfns @ tail_startfns)

and whenexpr_to_java whenexpr num tmap = 
  match whenexpr with 
    Unitwhen(action, stmt, loc) -> 
      let (when_playcode, when_startfns) = stmt_to_java tmap ([], []) stmt in 
      let nextcode = [loc ^ "();"] in
      (["if(action" ^ string_of_int num ^ ".equals(\"" ^ action ^   "\")) {"]
      @ when_playcode @ nextcode @ ["}"], when_startfns)

and probexprs_to_java start_num list tmap = match list with
    [] -> ([], [])
  | hd::tail -> 
    let (hd_playcode, hd_startfns, curr_num) = 
      probexpr_to_java hd start_num tmap in
    let (tail_playcode, tail_startfns) = 
      probexprs_to_java curr_num tail tmap in
    (hd_playcode @ tail_playcode, hd_startfns @ tail_startfns)

and probexpr_to_java probexpr start_num tmap = 
  match probexpr with 
    Unitprob(i, stmt) -> 
      let (prob_playcode, prob_startfns) = stmt_to_java tmap ([], []) stmt in
      (["if(num >= " ^ string_of_int start_num ^ " && num < " 
      ^ string_of_int (start_num + i) ^ ") {"] @ prob_playcode @ ["}"], 
      prob_startfns, start_num + i)

exception InvalidCode of string

(* TODO: FIX THIS *)
let global_dec_to_java (playcode, startfns) global_dec tmap = 
  match global_dec with
    IntStrdec (pridec) ->
	  let l = Declaration.intstrdec_to_java pridec tmap in
	  (match l with
        []-> (playcode, startfns)
      | hd::hd2::tl -> (playcode @ tl, startfns @ [hd; hd2])
      | _::tl -> raise (InvalidCode("Invalid Code")))
  | Charadec (name, membervar1, membervar2) -> 
      let l = Declaration.charadec_to_java name membervar1 membervar2 tmap in
	  (match l with
        []-> (playcode, startfns)
      | hd::hd2::tl -> (playcode @ tl, startfns @ [hd; hd2])
      | _::tl -> raise (InvalidCode("Invalid Code")))
  | Itemdec (name, membervar) -> 
      let l = Declaration.itemdec_to_java name membervar tmap in
      (match l with
        []-> (playcode, startfns)
      | hd::hd2::tl -> (playcode @ tl, startfns @ [hd; hd2])
      | _::tl -> raise (InvalidCode("Invalid Code")))
  | Locdec (name, membervar1, membervar2, membervar3) -> 
      let l = 
        Declaration.locdec_to_java name membervar1 membervar2 membervar3 tmap in
        (match l with
          []-> (playcode, startfns)
        | hd::hd2::tl -> (playcode @ tl, startfns @ [hd; hd2])
        | _::tl -> raise (InvalidCode("Invalid Code")))
  | Startend (name, expr, stmt) -> 
      playcode @ ["//Location function call"; name ^ "();"], 
      startfns @ ["//start funtion"; "public void " ^ name ^ "() {"] @ 
	  (fst (Expression.expr_to_java_boolean expr tmap)) @ 
	  ["currentLocation = \"" ^ name ^ "\";"]@ 
	  ["if (" ^(snd (Expression.expr_to_java_boolean expr tmap)) ^")"; 
	  "endGame();"] @ 
	  ["while (!(" ^ (snd (Expression.expr_to_java_boolean expr tmap)) ^")){"] @ 
	  (startend_stmt_check (snd (Expression.expr_to_java_boolean expr tmap)) 
	  (fst (stmt_to_java tmap ([], []) stmt)) )   
	  @ (fst (Expression.expr_to_java_boolean expr tmap))@ ["}"  ; "}"]

let print_globaldec global_dec = match global_dec with
    IntStrdec (pridec) ->
      print_endline "pridec"
  | Charadec (name, membervar1, membervar2) -> 
      print_endline ("char " ^ name)
  | Itemdec (name, membervar) -> 
      print_endline ("item " ^ name)
  | Locdec (name, membervar1, membervar2, membervar3) -> 
      print_endline ("loc " ^ name)
  | Startend (name, expr, stmt) -> print_endline ("Start "^name)

let rec javacode program symt = match program with
    [] -> ([], [])
  | hd::tl -> 
    let tuple = javacode tl symt in
    (fst (global_dec_to_java ([], []) hd symt))@ (fst tuple), 
    (snd (global_dec_to_java ([], []) hd symt))@ (snd tuple)
end
