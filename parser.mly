%{ open Ast %}


%token PLUS MINUS TIMES DIVIDE LESSTHAN GREATERTHAN EQUAL NEQUAL LOGICAND LOGICOR ASSIGN LOGICNOT
<<<<<<< HEAD:parser.mly
%token COMMA SEMICOLON DOT
=======
%token COMMA SEMICOLON QUOTATION DOT
>>>>>>> dpark27-master:parser.mly
%token LBRACKET RBRACKET LPAREN RPAREN RPROBBLOCK LPROBBLOCK
%token IF THEN ELSE START END PROB WHEN NEXT CHOOSE KILL GRAB HIDE EXISTS DROP SHOW
%token CHARACTER LOCATION ACTION OUTPUT ITEM INT STRING
%token EOF 
%token <int> LITERAL
%token <string> VARIABLE
%token <string> STRINGLIT


<<<<<<< HEAD:parser.mly
%nonassoc IF THEN ELSE START END PROB
=======
%nonassoc IF THEN ELSE
>>>>>>> dpark27-master:parser.mly
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
<<<<<<< HEAD:parser.mly
=======
%type < Ast.varref> varref
>>>>>>> dpark27-master:parser.mly

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
| START VARIABLE END expr stmt {Startend ($2, $4, $5)}
<<<<<<< HEAD:parser.mly
| KILL VARIABLE SEMICOLON				{Kill($2)}
| GRAB VARIABLE SEMICOLON				{Grab($2)}
| DROP VARIABLE SEMICOLON				{Drop($2)}
| HIDE VARIABLE SEMICOLON				{Hide($2)}
| SHOW VARIABLE SEMICOLON				{Show($2)}
| CHARACTER VARIABLE LBRACKET membervarlist RBRACKET {Charadec($2, List.rev $4)}
| LOCATION VARIABLE LBRACKET membervarlist RBRACKET {Itemdec($2,List.rev $4)}
| ITEM VARIABLE LBRACKET membervarlist RBRACKET {Locdec($2, List.rev $4)}
| RPROBBLOCK probexprlist LPROBBLOCK {Prob(List.rev $2)}
=======
| KILL varref SEMICOLON				{Kill($2)}
| GRAB varref SEMICOLON				{Grab($2)}
| DROP varref SEMICOLON				{Drop($2)}
| HIDE varref SEMICOLON				{Hide($2)}
| SHOW varref SEMICOLON				{Show($2)}
| CHARACTER VARIABLE LBRACKET membervar RBRACKET {Charadec($2,$4)}
| LOCATION VARIABLE LBRACKET membervar RBRACKET {Itemdec($2,$4)}
| ITEM VARIABLE LBRACKET membervar RBRACKET {Locdec($2,$4)}
| RPROBBLOCK probexpr LPROBBLOCK {Prob($2)}
>>>>>>> dpark27-master:parser.mly
| expr SEMICOLON {Atomstmt ($1) }
| LBRACKET block RBRACKET {Cmpdstmt ($2) }
| LBRACKET RBRACKET { Nostmt (0) }
| SEMICOLON { Nostmt (0) }
<<<<<<< HEAD:parser.mly
| CHOOSE actiondeclist LBRACKET whenexprlist RBRACKET {Chwhen (List.rev $2, List.rev $4)}
| pridec SEMICOLON {IntStrdec($1)}
=======
| CHOOSE actiondec LBRACKET whenexpr RBRACKET {Chwhen ($2,$4)}
>>>>>>> dpark27-master:parser.mly
;

block:
  stmt { [$1] }
| block stmt { $2::$1 }
;

expr:
  expr PLUS   expr 			{ Binop($1, Add, $3) }
<<<<<<< HEAD:parser.mly
/*| MINUS expr %prec NEG		{ Neg ($2)}*/
=======
| MINUS expr %prec NEG		{ Neg ($2)}
>>>>>>> dpark27-master:parser.mly
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
<<<<<<< HEAD:parser.mly
| LPAREN expr RPAREN        { $2 }
| VARIABLE ASSIGN expr 		{ Asn($1, $3) }
| LITERAL          			{ Lit($1) }
| STRINGLIT					{ LitS($1) }
| OUTPUT expr				{ Print($2)}
| EXISTS VARIABLE			{ Exists($2)}
| VARIABLE DOT VARIABLE		{ Has($1,$3)}
| VARIABLE					{ Var($1)}
;

membervarlist:
  membervar {[$1]}
| membervarlist COMMA membervar {$3::$1}
=======
/*| expr COMMA expr 			{ Seq ($1,$3) }*/
| LPAREN expr RPAREN        { $2 }
| VARIABLE ASSIGN expr 		{ Asn($1, $3) }
| LITERAL          			{ Lit($1) }
/*| VARIABLE 			   	{ Var($1) }*/
| STRINGLIT					{ LitS($1) }
| OUTPUT expr				{ Print($2)}
| EXISTS varref				{Exists($2)}
| VARIABLE DOT VARIABLE		{ Has($1,$3)}
>>>>>>> dpark27-master:parser.mly
;

membervar:
  pridec {Primember($1)}
<<<<<<< HEAD:parser.mly
| VARIABLE {Varref($1)}
=======
| varref {Varref($1)}
| membervar COMMA membervar {Seq($1,$3)}
>>>>>>> dpark27-master:parser.mly
;

pridec:
  STRING VARIABLE ASSIGN expr {Strdecinit($2,$4)}
| INT VARIABLE ASSIGN expr {Intdecinit($2,$4)}
| STRING VARIABLE {Strdec($2)}
| INT VARIABLE {Intdec($2)}
<<<<<<< HEAD:parser.mly
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
=======
>>>>>>> dpark27-master:parser.mly
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




