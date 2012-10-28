import hoenheim.encoder.Encoder;

class test2
{
	public static void main(String[] args) {
		Encoder f = new Encoder();
		String x = f.urlEncoder("adsf");
		System.out.println(x);
	}
}
