%{ open Ast %}


%token PLUS MINUS TIMES DIVIDE LESSTHAN GREATERTHAN EQUAL NEQUAL LOGICAND LOGICOR ASSIGN LOGICNOT
%token COMMA SEMICOLON DOT
%token LBRACKET RBRACKET LPAREN RPAREN RPROBBLOCK LPROBBLOCK
%token IF THEN ELSE START END PROB WHEN NEXT CHOOSE KILL GRAB HIDE EXISTS DROP SHOW
%token CHARACTER LOCATION ACTION OUTPUT ITEM INT STRING
%token EOF 
%token <int> LITERAL
%token <string> VARIABLE
%token <string> STRINGLIT


%nonassoc IF THEN ELSE START END PROB
%left SEMICOLON
%nonassoc OUTPUT
%left COMMA
%right ASSIGN
%left LOGICOR
%left LOGICAND
%left LOGICNOT
%left EQUAL NEQUAL
%left LESSTHAN GREATERTHAN
%left PLUS MINUS
%left TIMES DIVIDE
%left NEG
%nonassoc EXISTS
%left DOT
%nonassoc LPAREN RPAREN


%start program
%type < Ast.block> program
%type < Ast.block> file
%type < Ast.block> block
%type < Ast.expr> expr
%type < Ast.stmt> stmt
%type < Ast.pridec> pridec
%type < Ast.membervar> membervar
%type < Ast.probexpr> probexpr

%%
program:
  file EOF {$1}
;

file:
 		{[]}
| block {List.rev $1}
;

stmt:
  IF expr THEN stmt ELSE stmt {Ifelse($2,$4,$6)}
| START VARIABLE END LPAREN expr RPAREN stmt {Startend ($2, $5, $7)}
| KILL VARIABLE SEMICOLON				{Kill($2)}
| GRAB VARIABLE DOT VARIABLE SEMICOLON				{Grab($2,$4)}
| DROP VARIABLE DOT VARIABLE SEMICOLON				{Drop($2,$4)}
| HIDE VARIABLE DOT VARIABLE SEMICOLON				{Hide($2,$4)}
| SHOW VARIABLE DOT VARIABLE SEMICOLON				{Show($2,$4)}
| CHARACTER VARIABLE LBRACKET LPAREN membervarlist RPAREN COMMA LPAREN membervarlist RPAREN RBRACKET {Charadec($2, List.rev $5, List.rev $9)}
| LOCATION VARIABLE LBRACKET LPAREN membervarlist RPAREN COMMA LPAREN membervarlist RPAREN COMMA LPAREN membervarlist RPAREN RBRACKET {Locdec($2,List.rev $5,List.rev $9, List.rev $13)}
| ITEM VARIABLE LBRACKET LPAREN membervarlist RPAREN RBRACKET {Itemdec($2, List.rev $5)}
| RPROBBLOCK probexprlist LPROBBLOCK {Prob(List.rev $2)}
| expr SEMICOLON {Atomstmt ($1) }
| LBRACKET block RBRACKET {Cmpdstmt ($2) }
| LBRACKET RBRACKET { Nostmt (0) }
| SEMICOLON { Nostmt (0) }
| CHOOSE actiondeclist LBRACKET whenexprlist RBRACKET {Chwhen (List.rev $2, List.rev $4)}
| pridec SEMICOLON {IntStrdec($1)}
| OUTPUT expr { Print($2)} 
;

block:
  stmt { [$1] }
| block stmt { $2::$1 }
;

expr:
  expr PLUS expr 			{ Binop($1, Add, $3) }
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
| LPAREN expr RPAREN        { $2 }
| VARIABLE ASSIGN expr 		{ Asn($1, $3) }
| LITERAL          			{ Lit($1) }
| STRINGLIT					{ LitS($1) }
| EXISTS VARIABLE DOT VARIABLE	{ Exists($2,$4)}
| VARIABLE DOT VARIABLE		{ Has($1,$3)}
| VARIABLE					{ Var($1)}
| MINUS expr %prec NEG  	{ Neg($2)} 
;

membervarlist:
  {[]}
|  membervar {[$1]}
| membervarlist COMMA membervar {$3::$1}
;

membervar:
  pridec {Primember($1)}
| VARIABLE {Varref($1)}
;

pridec:
  STRING VARIABLE ASSIGN expr {Strdecinit($2,$4)}
| INT VARIABLE ASSIGN expr {Intdecinit($2,$4)}
| STRING VARIABLE {Strdec($2)}
| INT VARIABLE {Intdec($2)}
;


probexprlist:
  probexpr {[$1]}
| probexprlist probexpr {$2::$1}
;

probexpr:
  PROB LITERAL stmt { Unitprob ($2,$3)}
;

actiondeclist:
  actiondec {[$1]}
| actiondeclist actiondec {$2::$1}
;

actiondec:
  LPAREN VARIABLE COMMA STRINGLIT COMMA STRINGLIT RPAREN { Unitaction ($2,$4,$6)}
;

whenexprlist:
  whenexpr	{[$1]}
| whenexprlist whenexpr {$2::$1}
;

whenexpr:
  WHEN VARIABLE stmt NEXT VARIABLE { Unitwhen($2,$3,$5)}
;




