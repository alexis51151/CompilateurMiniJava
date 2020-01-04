%%
%include Jflex.include

FRAC= \.{DIGIT}+
EXP= e [+-]  {DIGIT}+
DIGIT= [0-9]
INT= 0 | [1-9] [0-9]*
NUMBER= -? {INT} {FRAC}? {EXP}?  
QUOTATIONMARK = \x22
UNESCAPED = [\x20-\x21] | [\x23-\x5B] | [\x5D-\U10FFFF]
CHAR = {UNESCAPED} | \x5C ( \x22 | \x5C | \x2F | \x62 | \x66 | \x6E | \x72 | \x74 | \x75 {HEXDIG}{4} )
HEXDIG = {DIGIT} | [\x41-\x46] | [\x61-\x66] | [0-9a-fA-F]
STRING = \x22 {CHAR}* \x22
%%
{NUMBER}	{ECHO("NUMBER");}
{QUOTATIONMARK}	{ECHO("QUOTATIONMARK");}
{CHAR}		{ECHO("CHAR");}
{STRING}	{ECHO("STRING");}
[^]          {ECHO("UNK");}
