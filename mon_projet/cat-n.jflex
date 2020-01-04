%%
%include Jflex.include
%{
	int num=0;
%}

NL = \R


%%

.* {NL}		{num++;System.out.println(num + " " + yytext());}
[^]		{ECHO();}
