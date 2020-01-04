%%
%include Jflex.include
%%

[a-zA-z]+	{yytext().CharAt(0);}
\R		{ECHO( );}
[^]		{ }

