%%
%include Jflex.include
%include JflexCup.include

FRAC= \.{DIGIT}+
EXP= (e|E) [+-]?  {DIGIT}+
DIGIT= [0-9]
INT= 0 | [1-9] [0-9]*
NUMBER= -? {INT} {FRAC}? {EXP}?  
UNESCAPED = [\x20-\x21] | [\x23-\x5B] | [\x5D-\U10FFFF]
CHAR = {UNESCAPED} | \x5C ( \x22 | \x5C | \x2F | \x62 | \x66 | \x6E | \x72 | \x74 | \x75 {HEXDIG}{4} )
HEXDIG = {DIGIT} | [\x41-\x46] | [\x61-\x66] | [0-9a-fA-F]   
STRING = \x22 {CHAR}* \x22
DECIMALPOINT = \x2E 
WS = \x20 | \x09 | \x0A | \x0D
CROCHET_O = \x5B
CROCHET_F = \x5D
ACC_O = \x7B
ACC_F = \x7D
COLON = \x3A
COMMA = \x2C
%%

{NUMBER}	{return TOKEN(NUMBER, yytext());}
{STRING}        {return TOKEN(STRING, yytext());}
"false"         {return TOKEN(FALSE, yytext());}
"true"          {return TOKEN(TRUE, yytext());}
"null"          {return TOKEN(NULL, yytext());}
{DECIMALPOINT}  {return TOKEN(DECIMALPOINT);}
{WS}+ 		{ }
{ACC_O} 	{return TOKEN(ACC_O);}
{ACC_F}        	{return TOKEN(ACC_F);}
{CROCHET_O}   	{return TOKEN(CROCHET_O);}
{CROCHET_F}   	{return TOKEN(CROCHET_F);}
{COLON} 	{return TOKEN(COLON);}
{COMMA} 	{return TOKEN(COMMA);}
[^]         	{WARN("Unknown caractere : " + yytext()); return TOKEN(UNK);}
