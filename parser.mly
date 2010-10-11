%{ open Ast %}


%token PLUS MINUS TIMES DIVIDE LESSTHAN GREATERTHAN EQUAL NEQUAL LOGICAND LOGICOR ASSIGN
%token COMMA SEMICOLON QUOTATION DOT
%token LBRACKET RBRACKET LPAREN RPAREN 
%token IF THEN ELSE START END PROB WHEN NEXT
%token CHARACTER LOCATION ACTION OUTPUT ITEM INT STRING
%token EOF
%token <int> LITERAL
%token <string> VARIABLE
%token <string> VARIABLESTR
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
%nonassoc LPAREN RPAREN


%start block
%type < Ast.block> block

%%
stmt:
  IF expr THEN stmt ELSE stmt {Ifelse($2,$4,$6)}
| expr SEMICOLON {Atomstmt ($1) }
| LBRACKET block RBRACKET {Cmpdstmt ($2) }
| LBRACKET RBRACKET { Nostmt (0) }
| SEMICOLON { Nostmt (0) }
;

block:
  stmt { Onestmt ($1) }
| stmt block { Onestmtoneblk ($1, $2) }
| block block { Twoblks ($1, $2) }
;

expr:
  expr PLUS   expr 			{ Binop($1, Add, $3) }
| expr MINUS  expr 			{ Binop($1, Sub, $3) }
| expr TIMES  expr 			{ Binop($1, Mul, $3) }
| expr DIVIDE expr 			{ Binop($1, Div, $3) }
| expr LESSTHAN expr 		{ Binop($1, Lt, $3) }
| expr GREATERTHAN expr 	{ Binop($1, Gt ,$3) }
| expr EQUAL expr 			{ Binop($1, Eq ,$3) }
| expr NEQUAL expr 			{ Binop($1, Neq ,$3) }
| expr LOGICAND expr 		{ Binop($1, And ,$3) }
| expr LOGICOR expr 		{ Binop($1, Or ,$3) }
| expr COMMA expr 			{ Seq ($1,$3) }
| LPAREN expr RPAREN        { $2 }
| VARIABLE ASSIGN expr 		{ Asn($1, $3) }
| LITERAL          			{ Lit($1) }
| VARIABLE 			   		{ Var($1) }
| STRINGLIT					{ LitS($1) }
| OUTPUT expr				{ Print($2)}
;




