%%
%include Jflex.include
%include JflexCup.include

DIGIT = [0-9]
TYPE = "int"|"long"|"char"           
IDENT = [a-zA-Z] [a-zA-Z0-9]*
OPBIN = "&"|"|"|"!"|"-"|"+"|"/"|"*"|"<"|">"
IGNORE = \R | [ \t\f] | "//".*
LITTERAL =  {DIGIT}+ \.? | {DIGIT}* \. {DIGIT}+ |  \' [^\'] \' | \" [^\"]* \"            
%%
{TYPE}       { return TOKEN(TYPE); }
";"          { return TOKEN(SEMI); }
","	     { return TOKEN(COMA); }
"("	     { return TOKEN(PARENTHESE_O); }
")"	     { return TOKEN(PARENTHESE_F); }
"{"	     { return TOKEN(ACC_O); }
"}"	     { return TOKEN(ACC_F); }
"while"	     { return TOKEN(WHILE); }
"for" 	     { return TOKEN(FOR);   }
"if"         { return TOKEN(IF);    }
"else"       { return TOKEN(ELSE);  }
"=" 	     { return TOKEN(EQ);    }
{OPBIN}	     { return TOKEN(OPBIN); }
{IDENT}      { return TOKEN(IDENT); }
{LITTERAL}   { return TOKEN(LITTERAL);}
{IGNORE}     { }
[^]          {WARN("Unknown caractere : " + yytext()); return TOKEN(UNK);}
