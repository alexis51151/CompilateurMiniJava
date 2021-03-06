package semantic;

import syntax.ast.ASTNode;
import syntax.ast.ASTVisitorDefault;
import syntax.ast.Klass;
import syntax.ast.Method;

public class TestScope extends ASTVisitorDefault {
	
	public TestScope(ASTNode axiom) {
		axiom.accept(this);
	}
	
	public void visit(Klass n) {
		System.out.print(n.klassId.name);
		System.out.print("{");
		defaultVisit(n);
		System.out.print("}");
	}
	public void visit(Method n) {
		System.out.print(n.methodId.name);
		System.out.print("{");
		defaultVisit(n);
		System.out.print("}");
	}
	
}
