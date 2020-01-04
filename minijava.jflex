package syntax;
%% 
%include Jflex.include
%include JflexCup.include

// Macros
WS         = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore     = {WS}+ | {EOLComment} | {C89Comment}
Integer    = 0 | [1-9] [0-9]*
Identifier = [:jletter:] [:jletterdigit:]*
Println    = "System" {WS}* "." {WS}* "out" {WS}* "." {WS}* "println"
Boolean = "true" | "false"

%%
// Mots Clés
"class"      { return TOKEN(CLASS);   }
"main"       { return TOKEN(MAIN);    }
{Println}    { return TOKEN(PRINTLN); }
"public"     { return TOKEN(PUBLIC);  }
"static"     { return TOKEN(STATIC);  }
"String"     { return TOKEN(STRING);  }
"void"       { return TOKEN(VOID);    }
"return"	 { return TOKEN(RETURN);  }
"if"		 { return TOKEN(IF);      }
"else"       { return TOKEN(ELSE);    }
"while"      { return TOKEN(WHILE);   }
"new" 		 { return TOKEN(NEW);     }
"this"       { return TOKEN(THIS);    }
"extends"    { return TOKEN(EXTENDS); }
"boolean"    { return TOKEN(BOOLEAN); }
"int"		 { return TOKEN(INT);     }
"length"	 { return TOKEN(LENGTH);  }
// Operateurs
"&&"		 { return TOKEN(AND);	  }
"<"			 { return TOKEN(LESS);    }
"+"			 { return TOKEN(PLUS);    }
"-"          { return TOKEN(MINUS);   }
"*"          { return TOKEN(TIMES);   }
"!"			 { return TOKEN(NOT);     }
"="			 { return TOKEN(EQ);	  }
// Ponctuations 
";"          { return TOKEN(SEP);     }
"{"          { return TOKEN(LC);      }
"}"          { return TOKEN(RC);      }
"("          { return TOKEN(LP);      }
")"          { return TOKEN(RP);      }
"["          { return TOKEN(LB);      }
"]"          { return TOKEN(RB);      }
","			 { return TOKEN(COMMA);   }
"."			 { return TOKEN(POINT);   }
// literals
{Integer}    { return TOKEN(LIT_INT,  Integer.parseInt(yytext()));     }  
{Boolean}	 { return TOKEN(LIT_BOOL, new String(yytext()));		   }
{Identifier} { return TOKEN(IDENT,    new String(yytext())) ;          }
// Ignore 
{Ignore}     {}
// Ramasse Miette
[^]          { WARN("Unknown char '"+yytext()+"' "); return TOKEN(error); }
