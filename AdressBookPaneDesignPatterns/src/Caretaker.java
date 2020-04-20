import java.util.ArrayList;

class Caretaker {
    private  ArrayList<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento m) {
        mementos.add(m);
    }

    public Memento getMemento() {
    	Memento mementoToReturn=null;
    	if(mementos.size()!=1) {
    	 mementoToReturn=new Memento(mementos.get(mementos.size()-1).getState());
    	mementos.remove(mementos.size()-1);
        
    	}
    	else {
    		 mementoToReturn=new Memento(mementos.get(0).getState());	
    		 mementos.remove(0);
    	}
    	

    	return mementoToReturn;
    }
    
    public int getNumOfMemenTo() {
    	return mementos.size();
    }
}