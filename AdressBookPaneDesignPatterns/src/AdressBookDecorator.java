public abstract class AdressBookDecorator  implements AdressBook {
		   protected AddressBookPane decoratedAdressBook;

		   public AdressBookDecorator(AddressBookPane decoratedAdressBook){
			   
		      this.decoratedAdressBook = decoratedAdressBook;
		      
		   }
	      @Override
		   public void secondStage(){
			   decoratedAdressBook.secondStage();
		   }
	      @Override
	      public AddressBookPane getPane() {
	  		return decoratedAdressBook;
	  	}
	
	}