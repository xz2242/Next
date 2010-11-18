open Ast
open Expression
open Declaration
open Action
open Selection
open Start
open Statement

module type COMPILE = 
   sig
     val javacode : stmt list -> string list * string list
   end

module Compile : COMPILE = struct

let stmt_to_java (playcode, startfns) stmt = match stmt with
      Ifelse (expr, stmt1, stmt2) -> (playcode @ (Selection.ifelse_to_java expr stmt1 stmt2), startfns)
    | Chwhen (actiondeclist, whenexprlist) -> (playcode @ (Selection.chwhen_to_java actiondeclist whenexprlist), startfns)
    | Prob (probexprlist) -> (playcode @ (Selection.prob_to_java probexprlist), startfns)
    | Kill (str) -> (playcode @ (Action.kill_to_java str), startfns)
    | Grab (str1, str2) -> (playcode @ (Action.grab_to_java str1 str2), startfns)
    | Drop (str1, str2) -> (playcode @ (Action.drop_to_java str1 str2), startfns)
    | Show (str1, str2) -> (playcode @ (Action.show_to_java str1 str2), startfns)
    | Hide (str1, str2) -> (playcode @ (Action.hide_to_java str1 str2), startfns)
    | Charadec (str, attrlist, itemlist) -> (playcode @ (Declaration.charadec_to_java str attrlist itemlist), startfns)
    | Itemdec (str, list) -> (playcode @ (Declaration.itemdec_to_java str list), startfns)
    | Locdec (str, attrlist, itemlist, charlist) -> (playcode @ (Declaration.locdec_to_java str attrlist itemlist charlist), startfns)
    | Startend (str, expr, stmt) -> let (playcode2, startfns2) = (Start.startend_to_java str expr stmt) in (playcode @ playcode2, startfns @ startfns2)
    | Atomstmt (expr) -> (playcode @ (Statement.atomstmt_to_java expr), startfns)
    | Cmpdstmt (block) -> (playcode @ (Statement.cmpdstmt_to_java block), startfns)
    | Nostmt (i) -> (playcode @ (Statement.nostmt_to_java i), startfns)
    | IntStrdec (pridec) -> (playcode @ (Declaration.intstrdec_to_java pridec), startfns)
    | Print (str) -> (playcode @ (Statement.print_to_java str), startfns)

let javacode program = List.fold_left stmt_to_java ([], []) program

end