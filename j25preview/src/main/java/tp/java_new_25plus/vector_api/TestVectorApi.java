package tp.java_new_25plus.vector_api;

import jdk.incubator.vector.IntVector;

public class TestVectorApi {
	//in run configuration : VM arguments
	//--add-modules=jdk.incubator.vector
	
	 public static void main(String[] args) {
		  smallVectorAddition();
		  largeVectorAdditionV1(); //with IntVector.SPECIES_512
		  largeVectorAdditionV2(); //with IntVector.SPECIES_128 , 4 iterations
	  }
	
	
    public static void display_intTab(String tabName, int[] values) {
	    System.out.print(tabName+"={");
	    for(int v : values)
		   System.out.print(" " + v);
	    System.out.print("}\n");
    }
 
    public static void smallVectorAddition() {
		int[] a = {1, 2, 3, 4};
		int[] b = {17, 18, 19, 20};
		int[] c = new int[4];
		
		// Scalar (ordinary) computation:
		//c[0] = a[0] + b[0];
		//...
		//c[15] = a[15] + b[15];
		for (int i = 0; i < c.length;i++)
			c[i] = a[i] + b[i];
		display_intTab("c",c);	
		
		// Vector computation:
		IntVector va = IntVector.fromArray(IntVector.SPECIES_128, a, 0);
		IntVector vb = IntVector.fromArray(IntVector.SPECIES_128, b, 0);
		IntVector vc = va.add(vb); //add operation between 2 vector
		//
		int[] c2 = new int[4];
		vc.intoArray(c2, 0);//store vc in ordinary array of scalar
		display_intTab("c2",c2);
	  }
 
     public static void largeVectorAdditionV1() {
		int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		int[] b = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
		int[] c = new int[16];
		
		
		// Scalar (ordinary) computation:
		//c[0] = a[0] + b[0];
		//...
		//c[15] = a[15] + b[15];
		for (int i = 0; i < c.length;i++)
			c[i] = a[i] + b[i];
		display_intTab("c",c);	
		
		// Vector computation:
		int[] c2 = new int[16];
		IntVector va = IntVector.fromArray(IntVector.SPECIES_512, a, 0);
		IntVector vb = IntVector.fromArray(IntVector.SPECIES_512, b, 0);
		IntVector vc = va.add(vb); //add operation between 2 vector		
		vc.intoArray(c2, 0);//store vc in ordinary array of scalar
		display_intTab("c2",c2);
	  }
     
     public static void largeVectorAdditionV2() {
 		int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
 		int[] b = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
 		int[] c = new int[16];
 		
 		// Scalar (ordinary) computation:
 		//c[0] = a[0] + b[0];
 		//...
 		//c[15] = a[15] + b[15];
 		for (int i = 0; i < c.length;i++)
 			c[i] = a[i] + b[i];
 		display_intTab("c",c);	
 		
 		// Vector computation:
 		int[] c2 = new int[16];
 		for (int offset = 0; offset < c.length; offset += IntVector.SPECIES_128.length()) {
	 		IntVector va = IntVector.fromArray(IntVector.SPECIES_128, a, offset);
	 		IntVector vb = IntVector.fromArray(IntVector.SPECIES_128, b, offset);
	 		IntVector vc = va.add(vb); //add operation between 2 vector
	 		vc.intoArray(c2, offset);//store vc in ordinary array of scalar]
 		}
 		display_intTab("c2",c2);
 	  }
	
}
