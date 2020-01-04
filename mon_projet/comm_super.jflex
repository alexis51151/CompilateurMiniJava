%%
%include Jflex.include
%state IN_COMMENTS, C_COMMENTS
%%
"/*"			{ yybegin(IN_COMMENTS);}
<IN_COMMENTS> "*/"	{ yybegin(YYINITIAL);}
<IN_COMMENTS> [^]	{ }
"//"			{ yybegin(C_COMMENTS);}
<C_COMMENTS> .		{ }
<C_COMMENTS> "\n"	{ yybegin(YYINITIAL);}
[^] 			{ECHO();}
