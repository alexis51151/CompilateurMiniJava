%%
%include Jflex.include
%include JflexCup.include

DIGIT 	= [0-9]
ENTIER 	= 0 | [1-9] {DIGIT}*
OPBIN = [-+/*%]
BLANC = [ \t\f]
USELESS = ({BLANC}+ | #.*)       
%%
{ENTIER}	{ return TOKEN(ENTIER, Integer.parseInt(yytext())); }
{OPBIN}	{ return TOKEN(OPBIN, yytext().charAt(0)); }
"min"	{ return TOKEN(MIN); }
"max"	{ return TOKEN(MAX); }
"("	{ return TOKEN(PARENTH_O);}
")"	{ return TOKEN(PARENTH_F);}
","	{ return TOKEN(COMMA);}
\R	{ return TOKEN(NL);}
{USELESS} {}
[^]          {WARN("Unknown caractere : " + yytext()); return TOKEN(ERROR);}
