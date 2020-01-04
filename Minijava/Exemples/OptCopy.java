class testArgs{
    public static void main(String[] a){
	int a;
	int b;
	a  = 5;
	b = 1;
    	System.out.println(new Operator().f(a,b,1)); // => 
    }
}

class Operator{
	public int f (int a, int b, int c) { // f (4,1,2,3,4)=
	  int res;     
	  if (b<1)  res=0;
	  else res= a + this.f(a,b-1,c);
	  return res;
	}	    
}
