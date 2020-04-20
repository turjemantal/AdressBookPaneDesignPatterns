public class Memento implements AddressBookNew1Finals {
	private String record;

	public Memento(String record) {
		this.record = record;
	}

	public String getState() {
		return record;
	}
}
