%%
%include Jflex.include
%{
  int nbOK=0, nbNOT=0, nbUNK=0;
  void OUT(String s) { System.out.print( s + ":" + yytext()); }
%}
%eof{
  System.out.println("OK=" + nbOK + ", NOT=" + nbNOT + ", UNK=" + nbUNK);
%eof}

OK  =  a.*|.*a 
NOT =  ([^a].*[^a])|b?

%%
^ {NOT} \R  { OUT("NOT"); nbNOT++;  }
^ {OK}  \R  { OUT("OK "); nbOK++;   }
^ [ab]* \R  { OUT("UNK"); nbUNK++;  } /* balai sur Sigma* */
[^]         { WARN("Invalid Char"); } /* autre que a,b ou NL */
