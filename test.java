import hoenheim.filter.Filter;

class test
{
	public static void main(String[] args) {
		Filter f = new Filter();
		String x = f.alphanumericsOnlyFilter("a!@#!@#sdfadsfa234234");
		System.out.println(x);
	}
}
