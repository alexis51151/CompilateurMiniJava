%%
%include Jflex.include
%%

(([^zq]*z[^zq]*q[^zq]*)|([^zq]*q[^zq]*z[^zq]*))$	{System.out.println(yytext());}
[^]		{ }
