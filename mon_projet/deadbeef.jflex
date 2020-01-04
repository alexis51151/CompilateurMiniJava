%%
%include Jflex.include
%%

[abcdef0IZSG]+			{System.out.println(yytext());}
[abcdef0IZSG]{4}		{System.out.println(yytext());}
[^]				{ }
