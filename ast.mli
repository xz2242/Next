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
  | Varref of varref
  | Seq of membervar * membervar

and varref = Var of string

and expr =
    Binop of expr * operator * expr
  | Asn of string * expr
  | Lit of int
  | LitS of string
  | Print of expr
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
  | Startend of string * expr * stmt
  | Atomstmt of expr
  | Cmpdstmt of block
  | Nostmt of int
and block = 
	Onestmt of stmt
  | Onestmtoneblk of stmt * block
  | Twoblks of block * block
