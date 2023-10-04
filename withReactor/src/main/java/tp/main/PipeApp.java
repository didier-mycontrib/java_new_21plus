package tp.main;

import java.util.ArrayList;

import reactor.core.publisher.Flux;
import tp.data.Product;
import tp.util.ProductUtil;

public class PipeApp {

	public static void main(String[] args) {
		collectingTransformedElements();
		filteringAndSortingElements();
	}
	
	public static void collectingTransformedElements() {
		var resultElements = new ArrayList<>();

		Flux.just(1, 2, 3, 4)
		  .map(i -> i * 2)
		  .subscribe( e -> resultElements.add(e));
	
		System.out.println("resultElements="+resultElements);
	}
	
	public static void filteringAndSortingElements() {
		var sourceElements = ProductUtil.initSampleProductList();
		System.out.println("sourceElements="+sourceElements);
		
		var resultElements = new ArrayList<Product>();

		Flux.fromArray(sourceElements.toArray(new Product[0] /* as element type*/))
		  .filter(p -> p.getPrice() <= 300 )
		  .sort((p1,p2)-> p1.getLabel().compareToIgnoreCase(p2.getLabel()))
		  .subscribe( e -> resultElements.add(e));
	
		System.out.println("resultElements="+resultElements);
	}

}
