
public class Div extends Expr{
	public Div(Expr e1, Expr e2) {
		this.value = e1.eval() / e2.eval();
	}

}
