%%
%include Jflex.include
%%

(([^q]*q[^u][^q]*)+)$		{System.out.println(yytext());}
(([^qu]*(q[^u]|u)?)*q)$		{System.out.println(yytext());}
[^]		{ }
