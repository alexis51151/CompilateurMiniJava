%%
%include Jflex.include

%%

^ ([ \t]+ \R)* (\R)+	{System.out.println();}
[^]			{ECHO();}
