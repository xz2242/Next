

module type ACTION = sig

   val kill_to_java : string -> string list
   val grab_to_java : string -> string -> string list
   val drop_to_java : string -> string -> string list
   val show_to_java : string -> string -> string list
   val hide_to_java : string -> string -> string list

end

module Action : ACTION = struct

let kill_to_java str = ["//kill"]
let grab_to_java str1 str2 = ["//grab"]
let drop_to_java str1 str2 = ["//drop"]
let show_to_java str1 str2 = ["//show"]
let hide_to_java str1 str2 = ["//hide"]

end