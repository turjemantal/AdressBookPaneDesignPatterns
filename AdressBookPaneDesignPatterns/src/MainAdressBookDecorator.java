import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;



public class MainAdressBookDecorator extends AdressBookDecorator implements AddressBookNew1Finals {

	public MainAdressBookDecorator(AddressBookPane decoratedAdressBook) {
		super(decoratedAdressBook);
		setMainStageProperties(decoratedAdressBook);

	}

	@Override
	public void secondStage() {
		setMainStageProperties(decoratedAdressBook);
	}

	private void setMainStageProperties(AddressBookPane decorateAdressBookPane) {
		RedoButton jbtRedo = new RedoButton(this.getPane(), decorateAdressBookPane.getRaf());
		UndoButton jbtUndo = new UndoButton(this.getPane(), decorateAdressBookPane.getRaf());
		AddButton jbtAdd = new AddButton(this.getPane(), decorateAdressBookPane.getRaf());
		jbtUndo.setOnAction(e -> jbtUndo.Execute());
		jbtRedo.setOnAction(e -> jbtRedo.Execute());
		jbtAdd.setOnAction(e -> jbtAdd.Execute());
		decorateAdressBookPane.getButtonsPane().getChildren().addAll(jbtUndo, jbtRedo, jbtAdd);
	}

	class AddButton extends CommandButton {
		public AddButton(AddressBookPane pane, RandomAccessFile r) {
			super(pane, r);
			this.setText(ADD);
		}

		@Override
		public void Execute() {
			try {
				writeAddress(getFile().length());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class RedoButton extends CommandButton {
		public RedoButton(AddressBookPane pane, RandomAccessFile r) {
			super(pane, r);
			this.setText(READO);
		}

		@Override
		public void Execute() {
			if (CommandButton.caretaker.getNumOfMemenTo()>0) {
				String recordToAdd = CommandButton.caretaker.getMemento().getState();
				try {
					ArrayList<String> array = new ArrayList<String>();
					int size0fFile = (int) getFile().length() / RECORD_SIZE / 2;
					getFile().seek(0);
					for (int i = 0; i < (size0fFile); i++)
						array.add(FixedLengthStringIO1.readFixedLengthString(CommandButton.RECORD_SIZE, getFile()));
					getFile().setLength(0);
					array.add(recordToAdd);
					for (int i = 0; i < array.size(); i++)
						FixedLengthStringIO1.writeFixedLengthString(array.get(i), RECORD_SIZE, getFile());
					readAddress(getFile().length() - (RECORD_SIZE * 2));

				}

				catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		}
	}

	class UndoButton extends CommandButton {
		public UndoButton(AddressBookPane pane, RandomAccessFile r) {
			super(pane, r);

			this.setText(UNDO);
		}

		@Override
		public void Execute() {
			try {
				if(getFile().length()==0)
				{
					clearTextFields();
					return;
				}
					
					ArrayList<String> array = new ArrayList<String>();
					int currentPosition = (int) getFile().length() - (RECORD_SIZE * 2);
					int size0fFile = (int) getFile().length() / RECORD_SIZE / 2;
					getFile().seek(0);
					for (int i = 0; i < (size0fFile); i++)
						array.add(FixedLengthStringIO1.readFixedLengthString(CommandButton.RECORD_SIZE, getFile()));
					getFile().setLength(0);
					CommandButton.caretaker.addMemento(new Memento(array.get((currentPosition / RECORD_SIZE / 2))));
					array.remove((currentPosition / RECORD_SIZE / 2));
					for (int i = 0; i < (size0fFile) - 1; i++)
						FixedLengthStringIO1.writeFixedLengthString(array.get(i), RECORD_SIZE, getFile());
					if(getFile().length()!=0)
					readAddress(0);
					else
						clearTextFields();
					
			
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}