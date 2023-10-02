package tp.java_new_21plus.ffm;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
//import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

/*
 Attention:
 sans arrêt , de petits changements au niveau de l'api FFM (Foreign Function and Memory)
 entre les previews des versions 18,19,20,21 (PAS STABLE DU TOUT)
 Le code ci-dessous est pour le jdk 21
 https://openjdk.org/jeps/442 pour java 21 (third preview of FFM)
 */


public class FFMTestApp {
	public static void main(String[] args) throws Throwable {
	    // 1. Get a lookup object for commonly used libraries
	    SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();
	    
	    // 2. Get a handle to the "strlen" function in the C standard library
	    MethodHandle strlen = Linker.nativeLinker().downcallHandle(
	        stdlib.find("strlen").orElseThrow(), 
	        FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
	    
	    try (Arena offHeap = Arena.ofConfined()) {//version java 21
	    	
	    	// 3. Convert Java String to C string and store it in off-heap memory
		    MemorySegment str = offHeap.allocateUtf8String("Happy Coding!");//13 caracteres
		    
		    // 4. Invoke the foreign function
		    long len = (long) strlen.invoke(str);

		    System.out.println("len = " + len);//13 (ok)
	    }

	}
}
