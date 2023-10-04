package tp.data;

public class MyBag<T> {
	private T value;
	private String sType;
	
	public MyBag() {
		super();
	}

	public MyBag(T value, String sType) {
		super();
		this.value = value;
		this.sType = sType;
	}

	@Override
	public String toString() {
		return "MyBag [value=" + value + ", sType=" + sType + "]";
	}
	
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public String getsType() {
		return sType;
	}
	public void setsType(String sType) {
		this.sType = sType;
	}
	
	
}
