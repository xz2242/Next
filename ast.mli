type operator = 
	Add 
  | Sub 
  | Mul 
  | Div 
  | Or 
  | And 
  | Eq
  | Lt 
  | Gt 
  | Neq

<<<<<<< HEAD:ast.mli

=======
>>>>>>> dpark27-master:ast.mli
type pridec =
	Strdec of string
  | Intdec of string
  | Strdecinit of string * expr
  | Intdecinit of string * expr
<<<<<<< HEAD:ast.mli
and membervarlist = membervar list
and membervar =
	Primember of pridec
  | Varref of string
=======

and membervar =
	Primember of pridec
  | Varref of varref
  | Seq of membervar * membervar

and varref = Var of string
>>>>>>> dpark27-master:ast.mli

and expr =
    Binop of expr * operator * expr
  | Asn of string * expr
  | Lit of int
  | LitS of string
  | Print of expr
<<<<<<< HEAD:ast.mli
  | Exists of string
  | Has of string * string
  | Neg of expr
  | Not of expr
  | Var of string

and probexpr =
	Unitprob of int * stmt

and probexprlist = probexpr list

and actiondec = 
	Unitaction of string * string * string
  
and whenexpr =
	Unitwhen of string * stmt * string

and stmt = 
	Ifelse of expr * stmt * stmt
  | Chwhen of actiondeclist * whenexprlist
  | Prob of probexprlist
  | Kill of string
  | Grab of string
  | Drop of string
  | Show of string
  | Hide of string
  | Charadec of string * membervar list
  | Itemdec of string * membervar list
  | Locdec of string * membervar list
=======
  | Exists of varref
  | Has of string * string
  | Neg of expr
  | Not of expr

and probexpr =
	Unitprob of int * stmt
  | Probblk of probexpr * probexpr

and actiondec = 
	Unitaction of string * string * string
  | Actionblk of actiondec * actiondec
  
and whenexpr =
	Unitwhen of string * stmt * string
  | Whenblk of whenexpr * whenexpr

and stmt = 
	Ifelse of expr * stmt * stmt
  | Chwhen of actiondec * whenexpr
  | Prob of probexpr
  | Kill of varref
  | Grab of varref
  | Drop of varref
  | Show of varref
  | Hide of varref
  | Charadec of string * membervar
  | Itemdec of string * membervar
  | Locdec of string * membervar
>>>>>>> dpark27-master:ast.mli
  | Startend of string * expr * stmt
  | Atomstmt of expr
  | Cmpdstmt of block
  | Nostmt of int
  | IntStrdec of pridec
and block = stmt list
and actiondeclist = actiondec list
and whenexprlist = whenexpr list

