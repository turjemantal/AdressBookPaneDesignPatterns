import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AddressBookNew1 extends Application implements AddressBookNew1Finals {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage[] stages = new Stage[NUMBER_OF_OBJECTS];
		Scene[] scenes = new Scene[NUMBER_OF_OBJECTS];
		AddressBookPane[] panes = new AddressBookPane[NUMBER_OF_OBJECTS];
		try {
			for (int i = 0; i < 1 + NUMBER_OF_OBJECTS; i++) {
				if (i >= NUMBER_OF_OBJECTS)
					System.out.println(SINGLETON_MESSAGE);
				else {

					panes[i] = AddressBookPane.getInstance();

					scenes[i] = new Scene(panes[i]);
					stages[i] = new Stage();
					stages[i].setTitle(TITLE);
					stages[i].setScene(scenes[i]);
					stages[i].setResizable(true);
					stages[i].show();
					stages[i].setAlwaysOnTop(true);
					stages[i].setOnCloseRequest(event -> {
						AddressBookPane.reduceNumberOfObjects();
					});
				}
			}
		} catch (Exception e) {
			AddressBookPane.resetNumberOfObjects();
		}
	}
}

class AddressBookPane extends GridPane implements AddressBookNew1Finals, AddressBookEvent1, AdressBook {
	private static int number_of_objects = 0;
	private RandomAccessFile raf;
	private TextField jtfName = new TextField();
	private TextField jtfStreet = new TextField();
	private TextField jtfCity = new TextField();
	private TextField jtfState = new TextField();
	private TextField jtfZip = new TextField();
	private FlowPane jpButton;
	private FirstButton jbtFirst;
	private NextButton jbtNext;
	private PreviousButton jbtPrevious;
	private LastButton jbtLast;
	
	private EventHandler<ActionEvent> ae = e -> ((Command) e.getSource()).Execute();

	private AddressBookPane() {
		try {
			raf = new RandomAccessFile(FILE_NAME, FILE_MODE);
		} catch (IOException ex) {
			System.out.println(ex);
			System.exit(0);
		}

		secondStage();
	}

	public RandomAccessFile getRaf() {
		return this.raf;
	}

	public void secondStage() {
		jpButton = new FlowPane();
		jtfState.setAlignment(Pos.CENTER_LEFT);
		jtfState.setPrefWidth(25);
		jtfZip.setPrefWidth(60);

		jbtFirst = new FirstButton(this, raf);
		jbtNext = new NextButton(this, raf);
		jbtPrevious = new PreviousButton(this, raf);
		jbtLast = new LastButton(this, raf);
		
		Label state = new Label(STATE);
		Label zp = new Label(ZIP);
		Label name = new Label(NAME);
		Label street = new Label(STREET);
		Label city = new Label(CITY);
		GridPane p1 = new GridPane();
		p1.add(name, 0, 0);
		p1.add(street, 0, 1);
		p1.add(city, 0, 2);
		p1.setAlignment(Pos.CENTER_LEFT);
		p1.setVgap(8);
		p1.setPadding(new Insets(0, 2, 0, 2));
		GridPane.setVgrow(name, Priority.ALWAYS);
		GridPane.setVgrow(street, Priority.ALWAYS);
		GridPane.setVgrow(city, Priority.ALWAYS);
		GridPane adP = new GridPane();
		adP.add(jtfCity, 0, 0);
		adP.add(state, 1, 0);
		adP.add(jtfState, 2, 0);
		adP.add(zp, 3, 0);
		adP.add(jtfZip, 4, 0);
		adP.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfState, Priority.ALWAYS);
		GridPane.setVgrow(jtfZip, Priority.ALWAYS);
		GridPane.setVgrow(state, Priority.ALWAYS);
		GridPane.setVgrow(zp, Priority.ALWAYS);
		GridPane p4 = new GridPane();
		p4.add(jtfName, 0, 0);
		p4.add(jtfStreet, 0, 1);
		p4.add(adP, 0, 2);
		p4.setVgap(1);
		GridPane.setHgrow(jtfName, Priority.ALWAYS);
		GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setHgrow(adP, Priority.ALWAYS);
		GridPane.setVgrow(jtfName, Priority.ALWAYS);
		GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setVgrow(adP, Priority.ALWAYS);
		GridPane jpAddress = new GridPane();
		jpAddress.add(p1, 0, 0);
		jpAddress.add(p4, 1, 0);
		GridPane.setHgrow(p1, Priority.NEVER);
		GridPane.setHgrow(p4, Priority.ALWAYS);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane.setVgrow(p4, Priority.ALWAYS);
		jpAddress.setStyle(STYLE_COMMAND);

		jpButton.setHgap(5);

		if (eventType.FIRST.getDoEvent())
			jpButton.getChildren().add(jbtFirst);
		if (eventType.NEXT.getDoEvent())
			jpButton.getChildren().add(jbtNext);
		if (eventType.PREVIOUS.getDoEvent())
			jpButton.getChildren().add(jbtPrevious);
		if (eventType.LAST.getDoEvent())
			jpButton.getChildren().add(jbtLast);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		this.setVgap(5);
		this.add(jpAddress, 0, 0);
		this.add(jpButton, 0, 1);
		jbtFirst.setOnAction(ae);
		jbtNext.setOnAction(ae);
		jbtPrevious.setOnAction(ae);
		jbtLast.setOnAction(ae);
		jbtFirst.Execute();
	}

	public void SetName(String text) {
		jtfName.setText(text);
	}

	public void SetStreet(String text) {
		jtfStreet.setText(text);
	}

	public void SetCity(String text) {
		jtfCity.setText(text);
	}

	public void SetState(String text) {
		jtfState.setText(text);
	}

	public void SetZip(String text) {
		jtfZip.setText(text);
	}

	public String GetName() {
		return jtfName.getText();
	}

	public String GetStreet() {
		return jtfStreet.getText();
	}

	public String GetCity() {
		return jtfCity.getText();
	}

	public String GetState() {
		return jtfState.getText();
	}

	public String GetZip() {
		return jtfZip.getText();
	}


	public static AddressBookPane getInstance()

	{
		if (number_of_objects > NUMBER_OF_OBJECTS)
			return null;
		else {
			number_of_objects++;

			if (number_of_objects == 1)
				return new MainAdressBookDecorator(new AddressBookPane()).getPane();
			else
				return new AddressBookPane();

		}
	}

	public static void reduceNumberOfObjects() {
		number_of_objects--;
	}

	public static int getNumberOfObjects() {
		return number_of_objects;
	}

	public static void resetNumberOfObjects() {
		number_of_objects = 0;
	}

	@Override
	public AddressBookPane getPane() {
		// TODO Auto-generated method stub
		return null;
	}

	public FlowPane getButtonsPane() {
		return this.jpButton;
	}

}

interface Command {
	public void Execute();
}

class CommandButton extends Button implements Command, AddressBookNew1Finals {
	private AddressBookPane p;
	private RandomAccessFile raf;
	static Caretaker caretaker = new Caretaker();

	public CommandButton(AddressBookPane pane, RandomAccessFile r) {
		super();
		p = pane;
		raf = r;
	}
	public void clearTextFields() {
		
		getPane().SetName("");
		getPane().SetCity("");
		getPane().SetStreet("");
		getPane().SetState("");
		getPane().SetZip("");
	}

	public AddressBookPane getPane() {
		return p;
	}

	public RandomAccessFile getFile() {
		return raf;
	}

	public void setPane(AddressBookPane p) {
		this.p = p;
	}

	@Override
	public void Execute() {
	}

	public void writeAddress(long position) {
		try {
			getFile().seek(position);
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetName(), NAME_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetStreet(), STREET_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetCity(), CITY_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetState(), STATE_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetZip(), ZIP_SIZE, getFile());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void readAddress(long position) throws IOException {
			getFile().seek(position);
			String name = FixedLengthStringIO1.readFixedLengthString(NAME_SIZE, getFile());
			String street = FixedLengthStringIO1.readFixedLengthString(STREET_SIZE, getFile());
			String city = FixedLengthStringIO1.readFixedLengthString(CITY_SIZE, getFile());
			String state = FixedLengthStringIO1.readFixedLengthString(STATE_SIZE, getFile());
			String zip = FixedLengthStringIO1.readFixedLengthString(ZIP_SIZE, getFile());
			getPane().SetName(name);
			getPane().SetStreet(street);
			getPane().SetCity(city);
			getPane().SetState(state);
			getPane().SetZip(zip);
		
	}

}

class NextButton extends CommandButton {
	public NextButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(NEXT);
	}

	@Override
	public void Execute() {
		try {
			if(getFile().length()==0) {
				clearTextFields();
			return;	
			}
			
			long currentPosition = getFile().getFilePointer();
			if (currentPosition < getFile().length())
				readAddress(currentPosition);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class PreviousButton extends CommandButton {
	public PreviousButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(PREVIOUS);
	}

	@Override
	public void Execute() {
		try {
			if(getFile().length()==0) {
				clearTextFields();
			return;	
			}
			long currentPosition = getFile().getFilePointer();
			if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
				readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
			else
				;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class LastButton extends CommandButton {
	public LastButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(LAST);
	}

	@Override
	public void Execute() {
		try {
			if(getFile().length()==0) {
				clearTextFields();
			return;	
			}
			long lastPosition = getFile().length();
			if (lastPosition > 0)
				readAddress(lastPosition - 2 * RECORD_SIZE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class FirstButton extends CommandButton {
	public FirstButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(FIRST);
	}

	@Override
	public void Execute() {
		try {
			if(getFile().length()==0) {
				clearTextFields();
			return;	
			}
			if (getFile().length() > 0)
				readAddress(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}




