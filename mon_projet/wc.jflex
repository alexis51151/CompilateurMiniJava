%%
%include Jflex.include

%{
	int nbLignes;
	int nbMots;
	int nbChars;
%}
%init{
	nbLignes = 0; nbMots=0; nbChars = 0;
%init}
%eof{
	System.out.println("Mots :" +nbMots + "Chars :" + nbChars + "Lignes :" + nbLignes);
%eof}

CHAR  = [^ \t\n\r]
MOT   = " "
LIGNE = \R

%%

{CHAR} 		{ECHO("Char:");nbChars++;}
{LIGNE}		{ECHO("Ligne:");nbLignes++;}
{MOT}		{ECHO("Mot:");nbMots++;}


