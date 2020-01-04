%%
%include Jflex.include
%%

[^q]*q[^u].*\n		{System.out.println(yytext());}
.*q\n			{System.out.println(yytext());}
[^]			{ }
