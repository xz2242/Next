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


type pridec =
	Strdec of string
  | Intdec of string
  | Strdecinit of string * expr
  | Intdecinit of string * expr

and membervar =
	Primember of pridec
  | Var of string
  | Seq of membervar * membervar

and expr =
    Binop of expr * operator * expr
  | Asn of string * expr
  | Lit of int
  | LitS of string
  | Print of expr
  | Exists of string
  | Has of string * string
  | Neg of expr
  | Not of expr


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
  | Charadec of string * membervar
  | Itemdec of string * membervar
  | Locdec of string * membervar
  | Startend of string * expr * stmt
  | Atomstmt of expr
  | Cmpdstmt of block
  | Nostmt of int
and block = stmt list
and actiondeclist = actiondec list
and whenexprlist = whenexpr list

