%%
%include Jflex.include
%%
"//".*$ 		 { }
(for|while|if|else)	 { ECHO("KW"); }
[a-zA-Z] [a-zA-Z0-9]*	 { ECHO("ID"); }
[+*/-]			 { ECHO("OP"); }
[<>]+ =?		 { ECHO("CMP"); }
([\+-]=)|=               { ECHO("AFF"); }
\+{2}			 { ECHO("INC"); }
[();,}{]|"["|"]"	 	 { ECHO("SEP"); }
([0-9]+)|([0-9]+\.[0.9]*)|([0-9]*\.[0-9]+) 	 { ECHO("NUM"); }
^\n			 {ECHO("");}
\R			 {ECHO();}
[ \t]+$			 { ECHO(); } 
[^]                      { }
