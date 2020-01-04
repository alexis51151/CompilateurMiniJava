%%
%include Jflex.include
%%
[^\(\: ]+(" "|")"|"."|","|\R)	{System.out.print("["+yytext()+"]");
[^]				{ }
