%%
%include Jflex.include

LIGNEVIDE  	= ^ "\R"
LIGNE_BLANCHE   = ^ [ \t]+"\R"
BLANCSINUTILES = [ \t]+ "\R"

%%
{LIGNEVIDE}		{}
{LIGNEBLANCHE}		{}
{BLANCSINUTILES}	{}
[^]			{ECHO();}

