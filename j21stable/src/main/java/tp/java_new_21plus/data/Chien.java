package tp.java_new_21plus.data;

//déclarée non-sealed , la classe Chien peut pas avoir des sous classes
//public class Chien implements AnimalDomestique{
public non-sealed class Chien implements AnimalDomestique{
	@Override
	public void sayHello() {
		System.out.print("chien  wouf wouf - ");
	}
	
	public void aLaNiche() {
		System.out.println(" dodo niche ");
	}
}
