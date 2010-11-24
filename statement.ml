open Ast

module type STATEMENT = sig

val atomstmt_to_java : expr -> string list
val cmpdstmt_to_java : stmt list -> string list
val nostmt_to_java : int -> string list
val print_to_java : expr -> string list

end

module Statement : STATEMENT = struct

let atomstmt_to_java expr = ["//atomstmt"]
let cmpdstmt_to_java block = ["//cmpdstmt"]
let nostmt_to_java i = ["//nostmt"]
let print_to_java str = ["//print"]

end