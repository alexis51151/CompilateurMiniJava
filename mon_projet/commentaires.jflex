%%
%include Jflex.include
%%

("/*" ([^*] | "*"+ [^*/])* "*"* "*/")	{ECHO("commentaire");}
"//".*					{ECHO("commentaire");}
[^] 	{ECHO();}
