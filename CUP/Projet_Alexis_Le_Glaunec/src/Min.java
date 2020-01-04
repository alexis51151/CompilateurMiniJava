import java.lang.Math;

public class Min extends Expr{
	public Min(Expr e1, Expr e2) {
		this.value = Math.min(e1.eval(),e2.eval());
	}

}
