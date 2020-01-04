class testArgs{
    public static void main(String[] a){
    	System.out.println(new Operator().f(4,1,2,3,4)); // => 10
    }
}

class Operator{
	public int f (int a, int b, int c, int d, int e) { // f (4,1,2,3,4)=10
	  int res;     
	  if (a<1)  res=0;
	  else res= e + this.f(a-1,e,b,c,d);
	  return res;
	}	    
}
