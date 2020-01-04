
public class Expr extends ASTNode {

	public int value;
	public Expr(ASTNode... fils) {
		super(fils);
	}
	
	@Override
	public void accept(ASTVisitor v) {
		// TODO Auto-generated method stub
		
	}
	
	public int eval() {
		return (this.value);
	}
	
	
}
