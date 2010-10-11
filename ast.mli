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

type expr =
    Binop of expr * operator * expr
  | Asn of string * expr
  | Seq of expr * expr
  | Lit of int
  | Var of string
  | LitS of string
  | Print of expr
  
type stmt = 
	Ifelse of expr * stmt * stmt
  | Atomstmt of expr
  | Cmpdstmt of block
  | Nostmt of int
and block = 
	Onestmt of stmt
  | Onestmtoneblk of stmt * block
  | Twoblks of block * block
