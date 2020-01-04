%%
%include Jflex.include
%%

(.*z.*q.*)|(.*q.*z.*)	{System.out.println(yytext());}
[^]		{ }
