
public class Args extends ASTNode {

	public int value;
	public Args(ASTNode... fils) {
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