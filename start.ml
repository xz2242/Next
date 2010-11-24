open Ast

module type START = sig
val startend_to_java : string -> expr -> stmt -> string list * string list
end

module Start : START = struct
let startend_to_java str expr stmt = ["//startend"], ["//startend"]
end