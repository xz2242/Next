%{ open Ast %}


%token PLUS MINUS TIMES DIVIDE LESSTHAN GREATERTHAN EQUAL NEQUAL LOGICAND LOGICOR ASSIGN LOGICNOT
%token COMMA SEMICOLON QUOTATION DOT
%token LBRACKET RBRACKET LPAREN RPAREN RPROBBLOCK LPROBBLOCK
%token IF THEN ELSE START END PROB WHEN NEXT CHOOSE KILL GRAB HIDE EXISTS DROP SHOW
%token CHARACTER LOCATION ACTION OUTPUT ITEM INT STRING
%token EOF 
%token <int> LITERAL
%token <string> VARIABLE
%token <string> STRINGLIT

%nonassoc IF THEN ELSE
%left SEMICOLON

%nonassoc OUTPUT
%left COMMA
%right ASSIGN
%left LOGICOR
%left LOGICAND
%left EQUAL NEQUAL
%left LESSTHAN GREATERTHAN
%left PLUS MINUS
%left TIMES DIVIDE
%left NEG
%nonassoc LPAREN RPAREN


%start block
%type < Ast.block> block
%type < Ast.expr> expr
%type < Ast.stmt> stmt
%type < Ast.pridec> pridec
%type < Ast.membervar> membervar
%type < Ast.probexpr> probexpr
%type < Ast.varref> varref

%%
stmt:
  IF expr THEN stmt ELSE stmt {Ifelse($2,$4,$6)}
| START VARIABLE END expr stmt {Startend ($2, $4, $5)}
| KILL varref				{Kill($2)}
| GRAB varref				{Grab($2)}
| DROP varref				{Drop($2)}
| HIDE varref				{Hide($2)}
| SHOW varref				{Show($2)}
| CHARACTER VARIABLE LBRACKET membervar RBRACKET {Charadec($2,$4)}
| LOCATION VARIABLE LBRACKET membervar RBRACKET {Itemdec($2,$4)}
| ITEM VARIABLE LBRACKET membervar RBRACKET {Locdec($2,$4)}
| RPROBBLOCK probexpr LPROBBLOCK {Prob($2)}
| expr SEMICOLON {Atomstmt ($1) }
| LBRACKET block RBRACKET {Cmpdstmt ($2) }
| LBRACKET RBRACKET { Nostmt (0) }
| SEMICOLON { Nostmt (0) }
| CHOOSE actiondec LBRACKET whenexpr RBRACKET {Chwhen ($2,$4)}
;

block:
  stmt { Onestmt ($1) }
| stmt block { Onestmtoneblk ($1, $2) }
| block block { Twoblks ($1, $2) }
;

expr:
  expr PLUS   expr 			{ Binop($1, Add, $3) }
| MINUS expr %prec NEG		{ Neg ($2)}
| LOGICNOT expr				{ Not ($2)}
| expr MINUS  expr 			{ Binop($1, Sub, $3) }
| expr TIMES  expr 			{ Binop($1, Mul, $3) }
| expr DIVIDE expr 			{ Binop($1, Div, $3) }
| expr LESSTHAN expr 		{ Binop($1, Lt, $3) }
| expr GREATERTHAN expr 	{ Binop($1, Gt ,$3) }
| expr EQUAL expr 			{ Binop($1, Eq ,$3) }
| expr NEQUAL expr 			{ Binop($1, Neq ,$3) }
| expr LOGICAND expr 		{ Binop($1, And ,$3) }
| expr LOGICOR expr 		{ Binop($1, Or ,$3) }
/*| expr COMMA expr 			{ Seq ($1,$3) }*/
| LPAREN expr RPAREN        { $2 }
| VARIABLE ASSIGN expr 		{ Asn($1, $3) }
| LITERAL          			{ Lit($1) }
/*| VARIABLE 			   	{ Var($1) }*/
| STRINGLIT					{ LitS($1) }
| OUTPUT expr				{ Print($2)}
| EXISTS varref				{Exists($2)}
| VARIABLE DOT VARIABLE		{ Has($1,$3)}
;

membervar:
  pridec {Primember($1)}
| varref {Varref($1)}
| membervar COMMA membervar {Seq($1,$3)}
;

pridec:
  STRING VARIABLE ASSIGN expr {Strdecinit($2,$4)}
| INT VARIABLE ASSIGN expr {Intdecinit($2,$4)}
| STRING VARIABLE {Strdec($2)}
| INT VARIABLE {Intdec($2)}
;

probexpr:
  PROB LITERAL stmt { Unitprob ($2,$3)}
| probexpr probexpr { Probblk ($1,$2)}

varref:
  VARIABLE {Var($1)}

actiondec:
  LPAREN VARIABLE STRINGLIT STRINGLIT RPAREN { Unitaction ($2,$3,$4)}
| actiondec actiondec { Actionblk ($1,$2)}

whenexpr:
  WHEN VARIABLE stmt NEXT VARIABLE { Unitwhen($2,$3,$5)}
| whenexpr whenexpr {Whenblk ($1,$2)}




