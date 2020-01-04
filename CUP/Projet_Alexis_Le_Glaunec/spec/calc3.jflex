%%
%include Jflex.include
%include JflexCup.include

DIGIT 	= [0-9]
ENTIER 	= 0 | [1-9] {DIGIT}*
BLANC = [ \t\f]
USELESS = ({BLANC}+ | #.*)
VAR = [a-z]*       
%%
{ENTIER}	{ return TOKEN(ENTIER, Integer.parseInt(yytext())); }
"-"	{ return TOKEN(MINUS, yytext().charAt(0)); }
"+"	{ return TOKEN(PLUS, yytext().charAt(0)); }
"*"	{ return TOKEN(TIMES, yytext().charAt(0)); }
"/"	{ return TOKEN(DIV, yytext().charAt(0)); }
"%"	{ return TOKEN(MOD, yytext().charAt(0)); }
"min"	{ return TOKEN(MIN); }
"max"	{ return TOKEN(MAX); }
"("	{ return TOKEN(PARENTH_O);}
")"	{ return TOKEN(PARENTH_F);}
","	{ return TOKEN(COMMA);}
\R	{ return TOKEN(NL);}
"="	{ return TOKEN(EQ); }
{VAR}	{ return TOKEN(VAR); }
{USELESS} {}
[^]          {WARN("Unknown caractere : " + yytext()); return TOKEN(ERROR);}
