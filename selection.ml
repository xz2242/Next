open Ast

module type SELECTION = sig

val ifelse_to_java : expr -> stmt -> stmt -> string list
val chwhen_to_java : actiondec list -> whenexpr list -> string list
val prob_to_java : probexpr list -> string list

end

module Selection : SELECTION = struct

let ifelse_to_java expr stmt1 stmt2 = ["//ifelse"]
let chwhen_to_java actiondeclist whenexprlist = ["//chwhen"]
let prob_to_java probexprlist = ["//prob"]

end