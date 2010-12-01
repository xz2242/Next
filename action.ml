

module type ACTION = sig

   val kill_to_java : string -> string list
   val grab_to_java : string -> string -> string list
   val drop_to_java : string -> string -> string list
   val show_to_java : string -> string -> string list
   val hide_to_java : string -> string -> string list

end

module Action : ACTION = struct

let kill_to_java str = ["killFunction(" ^ str ^ ");"]
let grab_to_java str1 str2 = [str1 ^ ".addItem (" ^ str2 ");"]
let drop_to_java str1 str2 = [str1 ^ ".removeItem (" ^ str2 ");"]
let show_to_java str1 str2 = [str1 ^ ".showCharacter (" ^ str2 ");"]
let hide_to_java str1 str2 = [str1 ^ ".hideCharacter (" ^ str2 ");"]

end