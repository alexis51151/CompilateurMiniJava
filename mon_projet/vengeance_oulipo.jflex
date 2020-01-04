%%
%include Jflex.include

%{
	int nbLignes;
	int nbMots;
	int nbMotsV;
	int nbPonct;
%}
%init{
	nbLignes = 0; nbMots=0; nbMotsV = 0; nbPonct =0;
%init}
%eof{
	System.out.println("Lignes : " + nbLignes + ", Mots : " + nbMots +", MotsV : " + nbMotsV +", Ponctuations : " + nbPonct);
%eof}

PONCTUATION = [\?\:\!\,\;\.\(\)\[\]\'\"]
CHAR  = [^ \t\n\r\?\:\!\,\;\.\(\)\[\]\'\"]
LIGNE = \R

%%

{PONCTUATION}	{ECHO("Ponct:");nbPonct++;}
(v|V){CHAR}*	{ECHO("MotsV:");nbMots++;nbMotsV++;}
{CHAR}+		{ECHO("Char:");nbMots++;}
{LIGNE}		{ECHO("Ligne:");nbLignes++;}
[^]		{ECHO();}

