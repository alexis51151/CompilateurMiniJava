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
Boolean    = "true" | "false"
Identifier = [:jletter:] [:jletterdigit:]*
Println    = "System" {WS}* "." {WS}* "out" {WS}* "." {WS}* "println"

%%
// Mots Clés
"boolean"    { return TOKEN(BOOLEAN); }
"class"      { return TOKEN(CLASS);   }
"else"       { return TOKEN(ELSE);    } 
"extends"    { return TOKEN(EXTENDS); }
"if"         { return TOKEN(IF);      }
"int"        { return TOKEN(INT);     }
"main"       { return TOKEN(MAIN);    }
"new"        { return TOKEN(NEW);     }
{Println}    { return TOKEN(PRINTLN); }
"public"     { return TOKEN(PUBLIC);  }
"return"     { return TOKEN(RETURN);  }
"static"     { return TOKEN(STATIC);  }
"String"     { return TOKEN(STRING);  }
"this"       { return TOKEN(THIS);    }
"void"       { return TOKEN(VOID);    }
"while"      { return TOKEN(WHILE);   }
// Operateurs
"="          { return TOKEN(ASSIGN);  }
"+"          { return TOKEN(PLUS);    }
"-"          { return TOKEN(MINUS);   }
"*"          { return TOKEN(TIMES);   }
"!"          { return TOKEN(NOT);     }
"<"          { return TOKEN(LESS);    }
"&&"         { return TOKEN(AND);     }
// Ponctuations 
"."          { return TOKEN(DOT);     }
","          { return TOKEN(COMMA);   }
";"          { return TOKEN(SEP);     }
"{"          { return TOKEN(LC);      }
"}"          { return TOKEN(RC);      }
"("          { return TOKEN(LP);      }
")"          { return TOKEN(RP);      }
"["          { return TOKEN(LB);      }
"]"          { return TOKEN(RB);      }
// literals
{Boolean}    { return TOKEN(LIT_BOOL, Boolean.parseBoolean(yytext())); }
{Integer}    { return TOKEN(LIT_INT,  Integer.parseInt(yytext()));     }  
{Identifier} { return TOKEN(IDENT,    new String(yytext())) ;          }
// Ignore 
{Ignore}     {}
// Ramasse Miette
[^]          { WARN("Unknown char '"+yytext()+"' "); return TOKEN(error); }
{Ignore}     {}
// Ramasse Miette
[^]          { WARN("Unknown char '"+yytext()+"' "); return TOKEN(error); }
